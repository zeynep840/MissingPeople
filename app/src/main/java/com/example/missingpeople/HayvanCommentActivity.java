package com.example.missingpeople;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class HayvanCommentActivity extends AppCompatActivity {
    private EditText edt_yorum_ekle1;
    private ImageView profil_resmi1, gonder1;
    private String Post_Key3, saveCurrentDate1, saveCurrentTime1, postRandom1,current_user_id1;
    private DatabaseReference postsRef1,postsRef2,UsersReference1;
    private RecyclerView CommentsList1;
    private FirebaseAuth mAuth1;

    private String uid, postid2;
    private FirebaseUser firebaseUser1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hayvan_comment);
        Post_Key3 = getIntent().getExtras().get("PostKey3").toString();
        postsRef1 = FirebaseDatabase.getInstance().getReference().child("HayvanPosts").child(Post_Key3).child("Comments2");
        postsRef2 = FirebaseDatabase.getInstance().getReference().child("Notifications");
        mAuth1=FirebaseAuth.getInstance();
        current_user_id1=mAuth1.getCurrentUser().getUid();
        UsersReference1=FirebaseDatabase.getInstance().getReference().child("Users2");



        firebaseUser1 = FirebaseAuth.getInstance().getCurrentUser();
        edt_yorum_ekle1 = (EditText) findViewById(R.id.edt_yorumEkle_yorumlarActivity1);
        gonder1 = (ImageView) findViewById(R.id.post_comment_btn1);
        Intent intent = getIntent();
        postid2 = intent.getStringExtra(postid2);
        uid = intent.getStringExtra(uid);
        CommentsList1=(RecyclerView)findViewById(R.id.recycler_view_yorumlarActivity1);
        CommentsList1.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        CommentsList1.setLayoutManager(linearLayoutManager);

        gonder1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //usersreference komutları eklenecek
                UsersReference1.child(current_user_id1).addValueEventListener(new ValueEventListener() {
                    @Override

                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String comments = edt_yorum_ekle1.getText().toString();
                            if (TextUtils.isEmpty(comments)) {
                                Toast.makeText(HayvanCommentActivity.this, "Boş Yorum Gönderemezsin...", Toast.LENGTH_SHORT).show();
                            } else {
                                String userFullname = dataSnapshot.child("username").getValue().toString();
                                String postid2 = postsRef1.push().getKey();
                                Calendar calFordDate = Calendar.getInstance();
                                SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMM-yyyy");
                                saveCurrentDate1 = currentDate.format(calFordDate.getTime());
                                Calendar calFordTime=Calendar.getInstance();
                                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");

                                saveCurrentTime1 = currentTime.format(calFordTime.getTime());
                                postRandom1 = saveCurrentDate1 + saveCurrentTime1;

                                DatabaseReference yorumlarYolu = FirebaseDatabase.getInstance().getReference("Yorumlar").child(postid2);
                                HashMap<String, Object> commentsMap = new HashMap<>();
                                commentsMap .put("yorum1", comments);
                                commentsMap .put("date1", saveCurrentDate1);
                                commentsMap .put("time1", saveCurrentTime1);
                                commentsMap.put("username", userFullname);
                                edt_yorum_ekle1.setText("");
                                addNotifications();



                                postsRef1.child(postRandom1).updateChildren(commentsMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {


                                            Toast.makeText(HayvanCommentActivity.this, "Başarılı bir şekilde yüklendi", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(HayvanCommentActivity.this, "Eror", Toast.LENGTH_SHORT).show();

                                        }


                                    }
                                });
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });







            }

        });

    }


    protected void onStart() {

        super.onStart();
        FirebaseRecyclerAdapter<HayvanComment,HayvanCommentViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<HayvanComment, HayvanCommentViewHolder>
                (HayvanComment.class,
                        R.layout.all_hayvan_comment_layout,
                        HayvanCommentViewHolder.class,
                        postsRef1

                )
        {
            @Override
            protected void populateViewHolder(HayvanCommentViewHolder viewHolder, HayvanComment model, int position) {
                viewHolder.setUsername(model.getUsername());
                viewHolder.setDate1(model.getDate1());
                viewHolder.setTime1(model.getTime1());
                viewHolder.setYorum1(model.getYorum1());


            }

        };
        CommentsList1.setAdapter(firebaseRecyclerAdapter);
    }

    public static class HayvanCommentViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public HayvanCommentViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }
        //setler yazılacak
        public void setUsername(String username) {
            TextView myUsername1=(TextView)mView.findViewById(R.id.comment_username1);
            myUsername1.setText("@ "+username);

        }
        public void setTime1(String time1) {
            TextView myTime1=(TextView)mView.findViewById(R.id.comment_time1);
            myTime1.setText(" Time:"+time1);

        }
        public void setDate1(String date1) {
            TextView myDate1=(TextView)mView.findViewById(R.id.comment_date1);
            myDate1.setText(" Date:"+date1);

        }
        public void setYorum1(String yorum1) {
            TextView myComment1=(TextView)mView.findViewById(R.id.comment_text1);
            myComment1.setText(yorum1);

        }




    }
    private void addNotifications(){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Notifications").child(firebaseUser1.getUid());
        HashMap<String,Object>hashMap=new HashMap<>();
        hashMap.put("userid",firebaseUser1.getUid());
        hashMap.put("text",edt_yorum_ekle1.getText().toString());
        hashMap.put("postid2",postid2);
        reference.push().setValue(hashMap);

    }
}
