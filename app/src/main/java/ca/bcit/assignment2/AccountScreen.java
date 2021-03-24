package ca.bcit.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AccountScreen extends AppCompatActivity {

    EditText userEmail;
    EditText userPassword;
    Button registerButton;
    Button loginButton;
    ProgressBar progressBar;

    // instance variable of firebaseauth
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_screen);

        userEmail = findViewById(R.id.EmailRegister);
        userPassword = findViewById(R.id.PasswordRegister);
        registerButton = findViewById(R.id.ButtonRegister);
        loginButton = findViewById(R.id.ButtonLogin);
        progressBar = findViewById(R.id.progressBar);

        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        // register button add listener
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String registerEmail = userEmail.getText().toString().trim();
                String registerPassword = userPassword.getText().toString().trim();

                if (TextUtils.isEmpty(registerEmail)) {
                    userEmail.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(registerPassword)) {
                    userPassword.setError("Password is required");
                    return;
                }

                if (registerPassword.length() <6) {
                    userEmail.setError("Password must be >= 6 characters.");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // register user
                fAuth.createUserWithEmailAndPassword(registerEmail, registerPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AccountScreen.this, "User Created", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(AccountScreen.this, "ERROR: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }

        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                    String loginEmail = userEmail.getText().toString().trim();
                    String loginPassword = userPassword.getText().toString().trim();

                    if (TextUtils.isEmpty(loginEmail)) {
                        userEmail.setError("Email required");
                        return;
                    }

                    if (TextUtils.isEmpty(loginPassword)) {
                        userPassword.setError("Password required");
                        return;
                    }

                    if (loginPassword.length() < 6) {
                        userPassword.setError("Password must be greater than 6 characters long");
                        return;
                    }

                    progressBar.setVisibility(View.VISIBLE);

                    // authenticate user
                    fAuth.signInWithEmailAndPassword(loginEmail, loginPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AccountScreen.this, "Login successful!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                Toast.makeText(AccountScreen.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
            }
        });
    }
}