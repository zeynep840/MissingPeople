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

public class HayvanActivity extends AppCompatActivity {
    private Toolbar mToolbar1;
    private ImageButton camera23;
    private DatabaseReference PostsRef1,SetupRef;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hayvan);
        PostsRef1= FirebaseDatabase.getInstance().getReference().child("HayvanPosts");
        camera23=(ImageButton)findViewById(R.id.camera23);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        mToolbar1=(Toolbar)findViewById(R.id.forget_post_toolbar1);
        setSupportActionBar(mToolbar1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("HAYVAN KAYIP");
        camera23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent post2=new Intent(HayvanActivity.this,HayvanPostsActivity.class);
                startActivity(post2);
                finish();
            }
        });
        DisplayAllUserPosts();
    }

    private void DisplayAllUserPosts() {
        final FirebaseRecyclerAdapter<HayvanPosts,HayvanPostsViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<HayvanPosts, HayvanPostsViewHolder>(
                HayvanPosts.class,
                R.layout.all_hayvan_post_layout,
                HayvanPostsViewHolder.class,
                PostsRef1
        ) {
            @Override
            protected void populateViewHolder(HayvanPostsViewHolder viewHolder, HayvanPosts model, int position) {
                final String PostKey3 =getRef(position).getKey();

                viewHolder.setDescription2(model.getDescription2());
                viewHolder.setUsername(model.getUsername());

                viewHolder.setPostimage2(getApplicationContext(),model.getPostimage2());
                viewHolder.setDate2(model.getDate2());
                viewHolder.setTime2(model.getTime2());
                viewHolder.buttonComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent commentPostIntent1=new Intent(HayvanActivity.this,HayvanCommentActivity.class);
                        commentPostIntent1.putExtra("PostKey3",PostKey3);
                        startActivity(commentPostIntent1);


                    }
                });
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent clickPostIntent=new Intent(HayvanActivity.this,HayvanClickPostActivity.class);
                        clickPostIntent.putExtra("PostKey3",PostKey3);
                        startActivity(clickPostIntent);

                    }
                });




            }
        };
//post sayfasında gözükmeyi sağlar
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    public static class HayvanPostsViewHolder extends RecyclerView.ViewHolder {
        View mView;
        //ImageView CommentPostButton1;
        Button buttonComment;

        public HayvanPostsViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            //  CommentPostButton1=(ImageView)mView.findViewById(R.id.yorum2);
            buttonComment = (Button) mView.findViewById(R.id.yorum2);
        }
//buraya setler yazılacak


        public void setDate2(String date2) {
            TextView datem2 = (TextView) mView.findViewById(R.id.date2);
            datem2.setText(" " + date2);

        }

        public void setTime2(String time2) {
            TextView timem2 = (TextView) mView.findViewById(R.id.time2);
            timem2.setText(" " + time2);

        }

        public void setUsername(String username) {
            TextView usernamem2 = (TextView) mView.findViewById(R.id.post_username2);
            usernamem2.setText(" " + username);
        }

        public void setDescription2(String description2) {
            TextView descreption2 = (TextView) mView.findViewById(R.id.descreption2);
            descreption2.setText(" " + description2);
        }



        public void setPostimage2(Context ctx, String postimage2) {
            ImageView post_IMAGE2 = (ImageView) mView.findViewById(R.id.post_IMAGE2);
            Picasso.with(ctx).load(postimage2).into(post_IMAGE2);


        }
    }

}