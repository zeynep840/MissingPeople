package com.example.missingpeople;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Home2Activity extends AppCompatActivity {
    private CardView cocuk,yetiskin,esya,tanima,hayvan,ihbar_hattı;
    private Toolbar actionbar;

    private TabLayout tabsMain;

    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private DatabaseReference UsersRef;
    public void init(){
        actionbar=(Toolbar) findViewById(R.id.actionBar);
        setSupportActionBar(actionbar);
        getSupportActionBar().setTitle(R.string.app_name);
        auth=FirebaseAuth.getInstance();
        UsersRef= FirebaseDatabase.getInstance().getReference().child("Users");
        hayvan=(CardView) findViewById(R.id.hayvanKayip);
        currentUser=auth.getCurrentUser();
        cocuk=(CardView) findViewById(R.id.cocukKayip);
        yetiskin=(CardView) findViewById(R.id.yetiskinKayip);
        esya=(CardView) findViewById(R.id.esyaKayip);
        tanima=(CardView) findViewById(R.id.yuzTanima);
        ihbar_hattı=(CardView) findViewById(R.id.ihbar_hattı);






    }
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        init();



        cocuk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(Home2Activity.this,CocukActivity.class);
                startActivity(intent);

            }
        });


        // anim
        //    final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        //  myAnim.setRepeatCount(Animation.INFINITE);


        //   hayvan.startAnimation(myAnim);

        hayvan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Home2Activity.this,HayvanActivity.class);
                startActivity(intent1);


            }
        });
        yetiskin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5=new Intent(Home2Activity.this,YetiskinActivity.class);
                startActivity(intent5);


            }
        });
        esya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5=new Intent(Home2Activity.this,EsyaActivity.class);
                startActivity(intent5);


            }
        });

        tanima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5=new Intent(Home2Activity.this,FaceVerficationActivity.class);
                startActivity(intent5);


            }
        });
        ihbar_hattı.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5=new Intent(Home2Activity.this,PhoneCallActivity.class);
                startActivity(intent5);


            }
        });




    }

    @Override
    //aktif kullanıcı değilse mainactivity e gönderir
    protected void onStart() {
        if(currentUser==null){
            //anasayfadan giriş sayfasına gönderir
            Intent MainIntent=new Intent(Home2Activity.this,MainActivity.class);
            startActivity(MainIntent);
            finish();
        }
        else{
            CheckUserExistence();
        }
        super.onStart();

    }
    //kullanıcı bilgilerini kaydetmek için
    private void CheckUserExistence() {
        final String current_user_id=auth.getCurrentUser().getUid();
        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChild(current_user_id)){


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    //3 nokta için kullanıldı
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    //Çıkış yapa basıldığında giriş sayfasına yönlendirelecek

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        //neye basıldığına ne yapacağına karar vermek için id le karar veriyoruz
        if(item.getItemId()==R.id.mainLogout){
            auth.signOut();
            Intent loginIntent=new Intent(Home2Activity.this,LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }
        return true;



    }}