package com.example.missingpeople;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CocukClickPostActivity extends AppCompatActivity {
    private ImageView PostImage;
    private TextView PostDesription;
    private Button DeletePostButton,EditPostButon;
    private String PostKey;
    private DatabaseReference ClickPostRef;
    private FirebaseAuth mAuth;
    private String currentUserID,databaseUserID,description,image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocuk_click_post);
        PostImage=(ImageView)findViewById(R.id.post_resmi);
        PostDesription=(TextView)findViewById(R.id.yorum_yapmak);
        DeletePostButton=(Button)findViewById(R.id.postsil);
        EditPostButon=(Button)findViewById(R.id.postkaydet);
        PostKey=getIntent().getExtras().get("PostKey2").toString();
        ClickPostRef= FirebaseDatabase.getInstance().getReference().child("Posts4").child(PostKey);
        mAuth=FirebaseAuth.getInstance();
        currentUserID=mAuth.getCurrentUser().getUid();
        DeletePostButton.setVisibility(View.INVISIBLE);
        EditPostButon.setVisibility(View.INVISIBLE);


        ClickPostRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    description = dataSnapshot.child("description").getValue().toString();
                    image = dataSnapshot.child("postimage").getValue().toString();
                    databaseUserID = dataSnapshot.child("postid").getValue().toString();
                    PostDesription.setText(description);
                    Picasso.with(CocukClickPostActivity.this).load(image).into(PostImage);
                    if (currentUserID.equals(databaseUserID)) {
                        DeletePostButton.setVisibility(View.VISIBLE);
                        EditPostButon.setVisibility(View.VISIBLE);


                    }
                    EditPostButon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditCurrentPost(description);
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DeletePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteCurrentPost();
            }
        });

    }
    //postu güncellemek için
    private void EditCurrentPost(String description) {
        AlertDialog.Builder builder=new AlertDialog.Builder(CocukClickPostActivity.this);
        builder.setTitle("Post Güncelle");
        final EditText InputField=new EditText(CocukClickPostActivity.this);
        InputField.setText(description);
        builder.setView(InputField);
        builder.setPositiveButton("Güncelle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ClickPostRef.child("description").setValue(InputField.getText().toString());
                Toast.makeText(CocukClickPostActivity.this,"Post başarıyla güncellendi",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        Dialog dialog=builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_orange_dark);
    }

    //postu silmek için
    private void DeleteCurrentPost() {
        ClickPostRef.removeValue();
        SendUserToMainActivity();
        Toast.makeText(this,"Post silindi",Toast.LENGTH_SHORT).show();
    }

    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(CocukClickPostActivity.this,CocukActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);

    }
}
