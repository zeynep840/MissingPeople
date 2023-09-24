package com.example.missingpeople;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

public class SetupActivity extends AppCompatActivity {

    private EditText kullanıcıadi,adsoyad,ulke1;
    private CircleImageView imageview;
    private Button buttonset;
    private FirebaseAuth mAuth;
    private Uri ImageUri;
    private String downloadUrl,myUrl;
    private static final int Gallery_Pick=1;



    private DatabaseReference UsersRef,postref;
    String CurrentUserID;
    private StorageReference UserProfileImageRef;

    public void init(){
        kullanıcıadi=(EditText) findViewById(R.id.kullaniciadi);
        adsoyad=(EditText)findViewById(R.id.adsoyad);
        ulke1=(EditText) findViewById(R.id.ulke);
        imageview=(CircleImageView) findViewById(R.id.imageView6);
        buttonset=(Button)findViewById(R.id.button10);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        init();


        mAuth=FirebaseAuth.getInstance();
        CurrentUserID=mAuth.getCurrentUser().getUid();
        UsersRef= FirebaseDatabase.getInstance().getReference().child("Users").child(CurrentUserID);
        buttonset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveAccountSetupInformation();

            }
        });
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setAspectRatio(1,1)
                        .start(SetupActivity.this);

            }
        });
        //profil resimleri için kullanılan kodlar
        UserProfileImageRef= FirebaseStorage.getInstance().getReference().child("Profile Images");




    }
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Gallery_Pick && resultCode == RESULT_OK && data != null) {

            ImageUri = data.getData();
            imageview.setImageURI(ImageUri);//fotoğrafın yüklenmesi sağlanıyor...

            CropImage.activity(ImageUri).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1).start(this);


        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            ImageUri = result.getUri();
            imageview.setImageURI(ImageUri);
            ImageUri = result.getUri();
            final StorageReference filePath = UserProfileImageRef.child(System.currentTimeMillis() + "." + getFileExtension(ImageUri));
            final UploadTask uploadTask = filePath.putFile(ImageUri);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String message = e.toString();
                    Toast.makeText(SetupActivity.this, "Eror", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(SetupActivity.this, "resim başarıyla yüklendi", Toast.LENGTH_SHORT).show();
                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {


                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            downloadUrl = filePath.getDownloadUrl().toString();
                            return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                //firebasestorage şeklinde olunabilmesi için yapıldı...
                                Uri downloadUri = task.getResult();
                                myUrl = downloadUri.toString();
                                Toast.makeText(SetupActivity.this, "url resmini başarıyla kaydedetti", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });

                }
            });
        } else {
            Toast.makeText(SetupActivity.this, "Fotoğraf kırpıcıda hata olabilir,TEKRAR DENEYİN", Toast.LENGTH_SHORT).show();

        }

    }

    private void SaveAccountSetupInformation() {
        postref=FirebaseDatabase.getInstance().getReference().child("Users");

        String uid=postref.push().getKey();


        String username=kullanıcıadi.getText().toString();
        String adsoyadi=adsoyad.getText().toString();
        String ulke2=ulke1.getText().toString();
        if(TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Lütfen Kullanıcı Adını Doldurunuz...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(adsoyadi)) {
            Toast.makeText(this, "Lütfen Ad Soyad Kısmını Doldurunuz...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(ulke2)){
            Toast.makeText(this,"Lütfen Şehir-İlçe Kısmını Doldurunuz...",Toast.LENGTH_SHORT).show();
        }
        else{
            HashMap userMap=new HashMap();
            userMap.put("uid",uid);
            userMap.put("fullname",username);
            userMap.put("username",adsoyadi);
            userMap.put("Şehir ilçe",ulke2);
            userMap.put("status","Merhaba burda posterler paylaşılıyor");
            userMap.put("gender","none");
            userMap.put("dob","none");
            userMap.put("relationshipstatus","none");
            //Databaseye çekilmesiiçin kullanılır
            userMap.put("profileimage",myUrl);
            UsersRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SetupActivity.this,"Başarılı",Toast.LENGTH_SHORT).show();
                        Intent SetupIntent = new Intent(SetupActivity.this,LoginActivity.class);
                        startActivity(SetupIntent);
                        finish();
                    }
                    else {
                        String message=task.getException().getMessage();
                        Toast.makeText(SetupActivity.this,"Hata Oluştu"+message,Toast.LENGTH_SHORT).show();

                    }

                }
            });



        }
    }
}
