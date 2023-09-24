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

public class YetiskinClickPostActivity extends AppCompatActivity {
    private ImageView PostImage2;
    private TextView PostDesription2;
    private Button DeletePostButton2,EditPostButon2;
    private String PostKey4;
    private DatabaseReference ClickPostRef2;
    private FirebaseAuth mAuth2;
    private String currentUserID2,databaseUserID2,description3,image2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yetiskin_click_post);
        PostImage2=(ImageView)findViewById(R.id.post_resmi2);
        PostDesription2=(TextView)findViewById(R.id.yorum_yapmak2);
        DeletePostButton2=(Button)findViewById(R.id.postsil2);
        EditPostButon2=(Button)findViewById(R.id.postkaydet2);
        PostKey4=getIntent().getExtras().get("PostKey4").toString();
        ClickPostRef2= FirebaseDatabase.getInstance().getReference().child("Posts3").child(PostKey4);
        mAuth2=FirebaseAuth.getInstance();
        currentUserID2=mAuth2.getCurrentUser().getUid();
        DeletePostButton2.setVisibility(View.INVISIBLE);
        EditPostButon2.setVisibility(View.INVISIBLE);
        ClickPostRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    description3 = dataSnapshot.child("description3").getValue().toString();
                    image2 = dataSnapshot.child("postimage3").getValue().toString();
                    databaseUserID2 = dataSnapshot.child("postid3").getValue().toString();
                    PostDesription2.setText(description3);
                    Picasso.with(YetiskinClickPostActivity.this).load(image2).into(PostImage2);
                    if (currentUserID2.equals(databaseUserID2)) {
                        DeletePostButton2.setVisibility(View.VISIBLE);
                        EditPostButon2.setVisibility(View.VISIBLE);


                    }
                    EditPostButon2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditCurrentPost2(description3);
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DeletePostButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteCurrentPost2();
            }
        });
    }
    private void EditCurrentPost2(String description3) {
        AlertDialog.Builder builder=new AlertDialog.Builder(YetiskinClickPostActivity.this);
        builder.setTitle("Post Güncelle");
        final EditText InputField=new EditText(YetiskinClickPostActivity.this);
        InputField.setText(description3);
        builder.setView(InputField);
        builder.setPositiveButton("Güncelle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ClickPostRef2.child("description3").setValue(InputField.getText().toString());
                Toast.makeText(YetiskinClickPostActivity.this,"Post başarıyla güncellendi",Toast.LENGTH_SHORT).show();
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
    private void DeleteCurrentPost2() {
        ClickPostRef2.removeValue();
        SendUserToMainActivity2();
        Toast.makeText(this,"Post silindi",Toast.LENGTH_SHORT).show();
    }
    private void SendUserToMainActivity2() {
        Intent mainIntent = new Intent(YetiskinClickPostActivity.this,YetiskinActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);

    }

}
