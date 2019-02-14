package com.example.francisco.myfirebaseapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText mail;
    private EditText passw;
    private Button btnReg;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mail = (EditText)findViewById(R.id.email);
        passw = (EditText)findViewById(R.id.password);

        btnReg = (Button)findViewById(R.id.botonLogin);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mail.getText().toString().trim();
                String password = mail.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this,"se debe ingresar un mail valido",Toast.LENGTH_LONG).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    Toast.makeText(LoginActivity.this,"se ha registrado el usuario con exito", Toast.LENGTH_LONG).show();

                                } else {
                                    // If sign in fails, display a message to the user.

                                }

                                // ...
                            }
                        });


            }
        });


    }
}
