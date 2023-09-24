package com.example.missingpeople;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class YetiskinPostsActivity extends AppCompatActivity {
    private StorageTask uploadTask3;
    private ImageButton backButton3;
    private ImageView SelecPostImage3;
    private Button UpdatePostButton3;
    private EditText PostDescription3;
    private String Description3;
    private Uri ImageUri3;
    private StorageReference PostsImageReference3;
    private String saveCurrentDate3,saveCurrentTime3,postRandomName3,current_user_id3,postRandom3;
    private static final int Gallery_Pick=1;
    private DatabaseReference usersReference3, postsRef3;
    private FirebaseAuth mAuth3;
    private ProgressDialog loadingBar;
    private String myUrl4;
    public void init(){
        backButton3=(ImageButton) findViewById(R.id.geri3);
        SelecPostImage3=(ImageView) findViewById(R.id.post_imageButton3);
        UpdatePostButton3=(Button) findViewById(R.id.post_button4);
        PostDescription3=(EditText) findViewById(R.id.post_editText3);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yetiskin_posts);
        init();


        mAuth3 = FirebaseAuth.getInstance();
        current_user_id3 = mAuth3.getCurrentUser().getUid();

        PostsImageReference3= FirebaseStorage.getInstance().getReference().child("Product3 Images");
        usersReference3 = FirebaseDatabase.getInstance().getReference().child("Users2");

        loadingBar = new ProgressDialog(this);

        SelecPostImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setAspectRatio(1, 1)
                        .start(YetiskinPostsActivity.this);
            }
        });
        UpdatePostButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidePost3Info();
            }
        });
        backButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent geriIntent=new Intent(YetiskinPostsActivity.this,YetiskinActivity.class);
                startActivity(geriIntent);
                finish();
            }
        });






    }
    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    private void ValidePost3Info() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Posting");
        progressDialog.show();
        if (ImageUri3 != null) {
            final StorageReference filerefrence = PostsImageReference3.child(System.currentTimeMillis() + "." + getFileExtension(ImageUri3));
            uploadTask3 = filerefrence.putFile(ImageUri3);
            uploadTask3.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();

                    }
                    return filerefrence.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {

                        Uri downloadUri = task.getResult();
                        myUrl4 = downloadUri.toString();
                        postsRef3 = FirebaseDatabase.getInstance().getReference().child("Posts3");
                        String postid = postsRef3.push().getKey();
                        Calendar calFordDate = Calendar.getInstance();
                        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMM-yyyy");
                        saveCurrentDate3 = currentDate.format(calFordDate.getTime());
                        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");

                        saveCurrentTime3 = currentTime.format(calFordDate.getTime());
                        postRandom3 = saveCurrentDate3 + saveCurrentTime3;
                        //users den alınması için kullanılır...
                        usersReference3.child(current_user_id3).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String userFullname = dataSnapshot.child("username").getValue().toString();

                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("postid3", current_user_id3);
                                hashMap.put("date3", saveCurrentDate3);
                                hashMap.put("time3", saveCurrentTime3);
                                hashMap.put("postimage3", myUrl4);

                                hashMap.put("username", userFullname);
                                hashMap.put("description3", PostDescription3.getText().toString());
                                hashMap.put("publisher3", mAuth3.getCurrentUser().getUid());
                                postsRef3.child(current_user_id3 + postRandom3).setValue(hashMap);
                                progressDialog.show();
                                startActivity(new Intent(YetiskinPostsActivity.this, YetiskinActivity.class));
                                finish();


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    } else {
                        Toast.makeText(YetiskinPostsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(YetiskinPostsActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        } else {
            Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void StoringImageToFirebaseStoronge() {
        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentData = new SimpleDateFormat("dd-MMM-yyyy");

        saveCurrentDate3 = currentData.format(calFordDate.getTime());
        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");

        saveCurrentTime3 = currentTime.format(calFordDate.getTime());
        postRandom3 = saveCurrentDate3 + saveCurrentTime3;
        StorageReference FilePath = PostsImageReference3.child("Post3 Images").child(ImageUri3.getLastPathSegment() + postRandom3 + ".jpg");
        FilePath.putFile(ImageUri3).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(YetiskinPostsActivity.this, "Görüntü Başarılı Bir Şekilde Yüklendi...", Toast.LENGTH_SHORT).show();
                } else {
                    String message = task.getException().getMessage();
                    Toast.makeText(YetiskinPostsActivity.this, "Hata Oluştu" + message, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            ImageUri3 = result.getUri();
            SelecPostImage3.setImageURI(ImageUri3);


        } else {
            Toast.makeText(YetiskinPostsActivity.this, "Hata", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(YetiskinPostsActivity.this, YetiskinActivity.class));

        }
    }
}
