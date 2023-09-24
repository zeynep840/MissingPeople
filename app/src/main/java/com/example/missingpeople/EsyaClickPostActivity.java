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

public class EsyaClickPostActivity extends AppCompatActivity {
    private ImageView PostImage1;
    private TextView PostDesription1;
    private Button DeletePostButton1,EditPostButon1;
    private String PostKeyEsya;
    private DatabaseReference ClickPostRef1;
    private FirebaseAuth mAuth1;
    private String currentUserID1,databaseUserID1,esya_description,image1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esya_click_post);
        PostImage1=(ImageView)findViewById(R.id.esya_post_resmi);
        PostDesription1=(TextView)findViewById(R.id.esya_yorum_yapmak);
        DeletePostButton1=(Button)findViewById(R.id.esya_post_sil);
        EditPostButon1=(Button)findViewById(R.id.esya_post_kaydet);
        PostKeyEsya=getIntent().getExtras().get("PostKeyEsya").toString();
        ClickPostRef1= FirebaseDatabase.getInstance().getReference().child("EsyaPosts").child(PostKeyEsya);
        mAuth1=FirebaseAuth.getInstance();
        currentUserID1=mAuth1.getCurrentUser().getUid();
        DeletePostButton1.setVisibility(View.INVISIBLE);
        EditPostButon1.setVisibility(View.INVISIBLE);

        ClickPostRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    esya_description = dataSnapshot.child("esya_description").getValue().toString();
                    image1 = dataSnapshot.child("esya_postimage").getValue().toString();
                    databaseUserID1 = dataSnapshot.child("esya_postid").getValue().toString();
                    PostDesription1.setText(esya_description);
                    Picasso.with(EsyaClickPostActivity.this).load(image1).into(PostImage1);
                    if (currentUserID1.equals(databaseUserID1)) {
                        DeletePostButton1.setVisibility(View.VISIBLE);
                        EditPostButon1.setVisibility(View.VISIBLE);


                    }
                    EditPostButon1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditCurrentPost1(esya_description);
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DeletePostButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteCurrentPost1();
            }
        });

    }
    private void EditCurrentPost1(String esya_description) {
        AlertDialog.Builder builder=new AlertDialog.Builder(EsyaClickPostActivity.this);
        builder.setTitle("Post Güncelle");
        final EditText InputField=new EditText(EsyaClickPostActivity.this);
        InputField.setText(esya_description);
        builder.setView(InputField);
        builder.setPositiveButton("Güncelle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ClickPostRef1.child("esya_description").setValue(InputField.getText().toString());
                Toast.makeText(EsyaClickPostActivity.this,"Post başarıyla güncellendi",Toast.LENGTH_SHORT).show();
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
    private void DeleteCurrentPost1() {
        ClickPostRef1.removeValue();
        SendUserToMain2Activity();
        Toast.makeText(this,"Post silindi",Toast.LENGTH_SHORT).show();
    }

    private void SendUserToMain2Activity() {
        Intent mainIntent2 = new Intent(EsyaClickPostActivity.this,EsyaActivity.class);
        mainIntent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent2);

    }
}