package com.example.missingpeople;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText userEmailEt;
    private EditText userPasswordEt;
    private Button registerBtn;
    private Button loginBtn;
    private FirebaseAuth mAuth;
    private TextView ForgetPasswordLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ForgetPasswordLink=(TextView)findViewById(R.id.forget_password_link);


        userEmailEt = (EditText) findViewById(R.id.email23);
        userPasswordEt = (EditText) findViewById(R.id.parola);
        registerBtn = (Button) findViewById(R.id.btnregister);
        loginBtn = (Button) findViewById(R.id.btnLogin);
        mAuth = FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegister();
            }
        });
        ForgetPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ResetPasswordActivity.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = userEmailEt.getText().toString().trim();
                String userPassword = userPasswordEt.getText().toString().trim();
                if (!userEmail.isEmpty() && !userPassword.isEmpty()) {
                    login(userEmail, userPassword);
                } else {
                    Toast.makeText(getApplicationContext(), "Email ya da parola boş bırakılamaz!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void goToRegister() {
        Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(registerIntent);
    }

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loginBtn.setEnabled(false);

                            Intent main2Inrent=new Intent(getApplicationContext(), Home2Activity.class);
                            main2Inrent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(main2Inrent);
                            finish();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("EMail", "signInWithEmail:success");
                        } else {
                            loginBtn.setEnabled(true);
                            // If sign in fails, display a message to the user.
                            Log.w("Fail", "signInWithEmail:failure", task.getException());

                        }
                    }
                });
    }
}