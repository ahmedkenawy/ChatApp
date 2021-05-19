package com.ahmedkenawy.myapplication.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ahmedkenawy.myapplication.MainActivity;
import com.ahmedkenawy.myapplication.R;
import com.ahmedkenawy.myapplication.register.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {
    EditText edEmail, edPassword;
    TextView resetPassword;
    Button btnLogIn, btnRegister;

    ProgressBar progressBar;
    FirebaseAuth auth = FirebaseAuth.getInstance();

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//            Intent intent = new Intent(StartActivity.this, MainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        }
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        edEmail = findViewById(R.id.edemail);
        edPassword = findViewById(R.id.edpasswoed);
        btnLogIn = findViewById(R.id.btnlogin);
        btnRegister = findViewById(R.id.btnpassword);

        resetPassword=findViewById(R.id.resetpassword);
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent intent=new Intent(StartActivity.this,ResetPassword.class);
                startActivity(intent);
            }
        });

        try {
            setBtnLogIn();
            goToRegisterActivity();
        } catch (Exception e) {

        }


    }

    public void goToRegisterActivity() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setBtnLogIn() {
        try {
            progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            btnLogIn.setOnClickListener(v -> {
                String email = edEmail.getText().toString();
                String password = edPassword.getText().toString();
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(StartActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        progressBar.setVisibility(View.INVISIBLE);
                    } else {
                        Toast.makeText(StartActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });


            });
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }}