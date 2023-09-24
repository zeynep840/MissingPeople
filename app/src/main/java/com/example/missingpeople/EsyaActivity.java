package com.example.missingpeople;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class EsyaActivity extends AppCompatActivity {
    private Toolbar mToolbar1;
    private ImageButton EsyaCamera;
    private DatabaseReference PostsRef1,SetupRef;
    private RecyclerView EsyaRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esya);
        PostsRef1= FirebaseDatabase.getInstance().getReference().child("EsyaPosts");
        EsyaCamera=(ImageButton)findViewById(R.id.esya_camera);
        EsyaRecyclerView=(RecyclerView)findViewById(R.id.esya_recyclerview);
        EsyaRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setReverseLayout(true);
        EsyaRecyclerView.setLayoutManager(linearLayoutManager);

        mToolbar1=(Toolbar)findViewById(R.id.forget_post_toolbar1);
        setSupportActionBar(mToolbar1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("EŞYA KAYIP");
        EsyaCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent post2=new Intent(EsyaActivity.this,EsyaPostsActivity.class);
                startActivity(post2);
                finish();
            }
        });
        DisplayAllUserPosts();
    }

    private void DisplayAllUserPosts() {
        final FirebaseRecyclerAdapter<EsyaPosts, EsyaPostsViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<EsyaPosts, EsyaPostsViewHolder>(
                EsyaPosts.class,
                R.layout.all_esya_post_layout,
                EsyaPostsViewHolder.class,
                PostsRef1
        ) {
            @Override
            protected void populateViewHolder(EsyaPostsViewHolder viewHolder, EsyaPosts model, int position) {
                final String PostKeyEsya =getRef(position).getKey();

                viewHolder.setEsya_description(model.getEsya_description());
                viewHolder.setUsername(model.getUsername());

                viewHolder.setEsya_postimage(getApplicationContext(),model.getEsya_postimage());
                viewHolder.setEsya_date(model.getEsya_date());
                viewHolder.setEsya_time(model.getEsya_time());
                viewHolder.buttonComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent commentPostIntent1=new Intent(EsyaActivity.this,EsyaCommentActivity.class);
                        commentPostIntent1.putExtra("PostKeyEsya",PostKeyEsya);
                        startActivity(commentPostIntent1);


                    }
                });
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent clickPostIntent=new Intent(EsyaActivity.this,EsyaClickPostActivity.class);
                        clickPostIntent.putExtra("PostKeyEsya",PostKeyEsya);
                        startActivity(clickPostIntent);

                    }
                });




            }
        };
//post sayfasında gözükmeyi sağlar
        EsyaRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    public static class EsyaPostsViewHolder extends RecyclerView.ViewHolder {
        View mView;
        //ImageView CommentPostButton1;
        Button buttonComment;

        public EsyaPostsViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            //  CommentPostButton1=(ImageView)mView.findViewById(R.id.yorum2);
            buttonComment = (Button) mView.findViewById(R.id.esya_yorum);
        }
//buraya setler yazılacak


        public void setEsya_date(String esya_date) {
            TextView esya_datem = (TextView) mView.findViewById(R.id.esya_date);
            esya_datem.setText(" " + esya_date);

        }

        public void setEsya_time(String esya_time) {
            TextView esya_timem = (TextView) mView.findViewById(R.id.esya_time);
            esya_timem.setText(" " + esya_time);

        }

        public void setUsername(String username) {
            TextView usernamem2 = (TextView) mView.findViewById(R.id.esya_post_username);
            usernamem2.setText(" " + username);
        }

        public void setEsya_description(String esya_description) {
            TextView EsyaDescreption = (TextView) mView.findViewById(R.id.esya_descreption);
            EsyaDescreption.setText(" " + esya_description);
        }



        public void setEsya_postimage(Context ctx, String esya_postimage) {
            ImageView post_IMAGE2 = (ImageView) mView.findViewById(R.id.esya_post_IMAGE);
            Picasso.with(ctx).load(esya_postimage).into(post_IMAGE2);


        }
    }

}