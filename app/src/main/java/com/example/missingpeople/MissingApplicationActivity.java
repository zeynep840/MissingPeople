package com.example.missingpeople;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MissingApplicationActivity extends AppCompatActivity {
    private Button yeni_hesap,hesap_var;
    public void init(){
        yeni_hesap=(Button) findViewById(R.id.yeni_hesap_olustur);
        hesap_var=(Button) findViewById(R.id.hesabim_var);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_application);
        init();
        hesap_var.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin=new Intent(MissingApplicationActivity.this,LoginActivity.class);
                startActivity(intentLogin);
                finish();
            }
        });
        yeni_hesap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegister=new Intent(MissingApplicationActivity.this,RegisterActivity.class);
                startActivity(intentRegister);
                //kullanıcı her zaman hoşgeldin ekranını görmesin
                finish();
            }
        });

    }
}