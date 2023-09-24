package com.example.missingpeople;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

public class HomeActivity extends AppCompatActivity {
    private Toolbar actionbar;

    private TabLayout tabsMain;

    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private ImageButton AddNewButton;
    private RecyclerView recyclerView1;
    private Button child,adult,animal;
    private Button cocuk,yetiskin,esya,tanima,hayvan;
    private ImageView profil23;

    private DatabaseReference UsersRef;
    public void init(){
        actionbar=(Toolbar) findViewById(R.id.actionBar);
        setSupportActionBar(actionbar);
        getSupportActionBar().setTitle(R.string.app_name);
        auth=FirebaseAuth.getInstance();
        UsersRef= FirebaseDatabase.getInstance().getReference().child("Users");
        hayvan=(Button) findViewById(R.id.hayvan);
        currentUser=auth.getCurrentUser();


        AddNewButton=(ImageButton) findViewById(R.id.add_new_post_button);
        cocuk=(Button)findViewById(R.id.cocuk);
        yetiskin=(Button)findViewById(R.id.yetiskin);
        esya=(Button)findViewById(R.id.Esya);
        tanima=(Button)findViewById(R.id.missingTanima);






    }
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();



        //OneSignal.startInit(Main2Activity.this)
               // .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
               // .unsubscribeWhenNotificationsAreDisabled(true)
               // .init();

        cocuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,CocukActivity.class);
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
                Intent intent1=new Intent(HomeActivity.this,HayvanActivity.class);
                startActivity(intent1);


            }
        });
        yetiskin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5=new Intent(HomeActivity.this,YetiskinActivity.class);
                startActivity(intent5);


            }
        });
        esya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5=new Intent(HomeActivity.this,EsyaActivity.class);
                startActivity(intent5);


            }
        });

        tanima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5=new Intent(HomeActivity.this,FaceVerficationActivity.class);
                startActivity(intent5);


            }
        });




    }

    @Override
    //aktif kullanıcı değilse mainactivity e gönderir
    protected void onStart() {
        if(currentUser==null){
            //anasayfadan giriş sayfasına gönderir
            Intent MainIntent=new Intent(HomeActivity.this,MainActivity.class);
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
            Intent loginIntent=new Intent(HomeActivity.this,LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }
        return true;
    }
}


