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

public class HayvanPostsActivity extends AppCompatActivity {
    private StorageTask uploadTask2;

    private ImageButton backButton2;
    private ImageView SelecPostImage2;
    private Button UpdatePostButton2;
    private EditText PostDescription2;
    private Uri ImageUri2;
    private String Description2, saveCurrentTime2, saveCurrentDate2, postRandom2, current_user_id2;
    private String myUrl;
    private StorageReference PostsImageReference2;

    private DatabaseReference usersReference, postsRef1;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    private static final int Gallery_Pick = 1;

    public void init() {
        backButton2 = (ImageButton) findViewById(R.id.geri2);
        SelecPostImage2 = (ImageView) findViewById(R.id.post_imageButton2);
        UpdatePostButton2 = (Button) findViewById(R.id.post_button3);
        PostDescription2 = (EditText) findViewById(R.id.post_editText2);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hayvan_posts);
        init();
        mAuth = FirebaseAuth.getInstance();
        current_user_id2 = mAuth.getCurrentUser().getUid();

        PostsImageReference2 = FirebaseStorage.getInstance().getReference().child("Product2 Images");
        usersReference = FirebaseDatabase.getInstance().getReference().child("Users2");

        loadingBar = new ProgressDialog(this);

        SelecPostImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setAspectRatio(1, 1)
                        .start(HayvanPostsActivity.this);
            }
        });
        UpdatePostButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidePost2Info();
            }
        });
        backButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent geriIntent = new Intent(HayvanPostsActivity.this, HayvanActivity.class);
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

    private void ValidePost2Info() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Posting");
        progressDialog.show();
        if (ImageUri2 != null) {
            final StorageReference filerefrence = PostsImageReference2.child(System.currentTimeMillis() + "." + getFileExtension(ImageUri2));
            uploadTask2 = filerefrence.putFile(ImageUri2);
            uploadTask2.continueWithTask(new Continuation() {
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
                        myUrl = downloadUri.toString();
                        postsRef1 = FirebaseDatabase.getInstance().getReference().child("HayvanPosts");
                        String postid = postsRef1.push().getKey();
                        Calendar calFordDate = Calendar.getInstance();
                        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMM-yyyy");
                        saveCurrentDate2 = currentDate.format(calFordDate.getTime());
                        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");

                        saveCurrentTime2 = currentTime.format(calFordDate.getTime());
                        postRandom2 = saveCurrentDate2 + saveCurrentTime2;
                        //users den alınması için kullanılır...
                        usersReference.child(current_user_id2).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String userFullname = dataSnapshot.child("username").getValue().toString();

                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("postid2", current_user_id2);
                                hashMap.put("date2", saveCurrentDate2);
                                hashMap.put("time2", saveCurrentTime2);
                                hashMap.put("postimage2", myUrl);

                                hashMap.put("username", userFullname);
                                hashMap.put("description2", PostDescription2.getText().toString());
                                hashMap.put("publisher2", mAuth.getCurrentUser().getUid());
                                postsRef1.child(current_user_id2 + postRandom2).setValue(hashMap);
                                progressDialog.show();
                                startActivity(new Intent(HayvanPostsActivity.this, HayvanActivity.class));
                                finish();


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    } else {
                        Toast.makeText(HayvanPostsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(HayvanPostsActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        } else {
            Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void StoringImageToFirebaseStoronge() {
        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentData = new SimpleDateFormat("dd-MMM-yyyy");

        saveCurrentDate2 = currentData.format(calFordDate.getTime());
        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");

        saveCurrentTime2 = currentTime.format(calFordDate.getTime());
        postRandom2 = saveCurrentDate2 + saveCurrentTime2;
        StorageReference FilePath = PostsImageReference2.child("Post2 Images").child(ImageUri2.getLastPathSegment() + postRandom2 + ".jpg");
        FilePath.putFile(ImageUri2).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(HayvanPostsActivity.this, "Görüntü Başarılı Bir Şekilde Yüklendi...", Toast.LENGTH_SHORT).show();
                } else {
                    String message = task.getException().getMessage();
                    Toast.makeText(HayvanPostsActivity.this, "Hata Oluştu" + message, Toast.LENGTH_SHORT).show();

                }
            }
        });


    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            ImageUri2 = result.getUri();
            SelecPostImage2.setImageURI(ImageUri2);


        } else {
            Toast.makeText(HayvanPostsActivity.this, "Hata", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HayvanPostsActivity.this, HayvanActivity.class));

        }
    }
}
