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

public class YetiskinActivity extends AppCompatActivity {
    private Toolbar mToolbar4;
    private ImageButton camera4;
    private DatabaseReference PostsRef4,SetupRef4;
    private RecyclerView recyclerView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yetiskin);

        PostsRef4= FirebaseDatabase.getInstance().getReference().child("Posts3");

        recyclerView4=(RecyclerView)findViewById(R.id.recyclerview4);
        recyclerView4.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setReverseLayout(true);
        recyclerView4.setLayoutManager(linearLayoutManager);

        mToolbar4=(Toolbar)findViewById(R.id.forget_post_toolbar4);
        setSupportActionBar(mToolbar4);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("YETİŞKİN KAYIP");
        camera4=(ImageButton)findViewById(R.id.camera4);
        camera4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4=new Intent(YetiskinActivity.this,YetiskinPostsActivity.class);
                startActivity(intent4);
                finish();
            }
        });
        DisplayAllUserPosts4();
    }

    private void DisplayAllUserPosts4() {
        final FirebaseRecyclerAdapter<YetiskinPosts,YetiskinPostsViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<YetiskinPosts, YetiskinPostsViewHolder>(
                YetiskinPosts.class,
                R.layout.all_yetiskin_post_layout,
                YetiskinPostsViewHolder.class,
                PostsRef4
        ) {
            @Override
            protected void populateViewHolder(YetiskinPostsViewHolder viewHolder, YetiskinPosts model, int position) {
                final String PostKey4=getRef(position).getKey();

                viewHolder.setDescription3(model.getDescription3());
                viewHolder.setUsername(model.getUsername());

                viewHolder.setPostimage3(getApplicationContext(),model.getPostimage3());
                viewHolder.setDate3(model.getDate3());
                viewHolder.setTime3(model.getTime3());
                viewHolder.CommentPostButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent commentPostIntent1=new Intent(YetiskinActivity.this,YetiskinCommentActivity.class);
                        commentPostIntent1.putExtra("PostKey4",PostKey4);
                        startActivity(commentPostIntent1);


                    }
                });
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent clickPostIntent=new Intent(YetiskinActivity.this,YetiskinClickPostActivity.class);
                        clickPostIntent.putExtra("PostKey4",PostKey4);
                        startActivity(clickPostIntent);

                    }
                });



            }
        };
        recyclerView4.setAdapter(firebaseRecyclerAdapter);

    }
    public static class YetiskinPostsViewHolder extends RecyclerView.ViewHolder {
        View mView;
        Button CommentPostButton2;


        public YetiskinPostsViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            CommentPostButton2=(Button) mView.findViewById(R.id.yorum3);

        }
//buraya setler yazılacak


        public void setDate3(String date3) {
            TextView datem3 = (TextView) mView.findViewById(R.id.date3);
            datem3.setText(" " + date3);

        }

        public void setTime3(String time3) {
            TextView timem3 = (TextView) mView.findViewById(R.id.time3);
            timem3.setText(" " + time3);

        }

        public void setUsername(String username) {
            TextView usernamem3 = (TextView) mView.findViewById(R.id.post_username3);
            usernamem3.setText(" " + username);
        }

        public void setDescription3(String description3) {
            TextView descreption3 = (TextView) mView.findViewById(R.id.descreption3);
            descreption3.setText(" " + description3);
        }



        public void setPostimage3(Context ctx, String postimage3) {
            ImageView post_IMAGE3 = (ImageView) mView.findViewById(R.id.post_IMAGE3);
            Picasso.with(ctx).load(postimage3).into(post_IMAGE3);


        }
    }

}
