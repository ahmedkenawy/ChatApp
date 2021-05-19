package com.ahmedkenawy.myapplication.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ahmedkenawy.myapplication.MainActivity;
import com.ahmedkenawy.myapplication.R;
import com.ahmedkenawy.myapplication.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    EditText edName, edEmail, edPassword;
    Button btn;
    FirebaseAuth auth;
    DatabaseReference firebaseDatabase;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        edName = findViewById(R.id.edname);
        progressBar=findViewById(R.id.progressBar2);
        edEmail = findViewById(R.id.edemail1);
        edPassword = findViewById(R.id.edpassword1);
        btn = findViewById(R.id.btnlogin1);

        auth = FirebaseAuth.getInstance();
        goToMainActivity();
    }

    public void goToMainActivity() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edName.getText().toString();
                String email = edEmail.getText().toString();
                String password = edPassword.getText().toString();

                progressBar.setVisibility(View.VISIBLE);
                logIn(name, email, password);

            }
        });
    }

    public void logIn(String Name, String email, String password) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    firebaseDatabase = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    User user=new User(FirebaseAuth.getInstance().getCurrentUser().getUid(),Name,"default","offline",Name.toLowerCase());
                    firebaseDatabase.setValue(user);
                    Toast.makeText(RegisterActivity.this, "Done", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "Yalahoy", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
