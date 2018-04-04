package com.example.gabri.meucontrole;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText emailEditText, senhaEditText;

    private Button entrarButton;

    private TextView cadastroIntent;

    private Intent intent, intentLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        entrarButton = findViewById(R.id.login_pessoa_bt);
        cadastroIntent = findViewById(R.id.cadastrar_se);

        emailEditText = findViewById(R.id.login_email_p_et);
        senhaEditText = findViewById(R.id.login_senha_p_et);

        mAuth = FirebaseAuth.getInstance();

        intentLogin = new Intent(this, MainActivity.class);

        entrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email, senha;

                email = emailEditText.getText().toString();
                senha = senhaEditText.getText().toString();

                if (!email.isEmpty() && !senha.isEmpty()) {

                    emailEditText.setText("");
                    senhaEditText.setText("");

                    mAuth.signInWithEmailAndPassword(email, senha)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        startActivity(intentLogin);
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Email ou Senha Incorreto",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(LoginActivity.this, "Campo Vazio", Toast.LENGTH_SHORT).show();
                }

            }
        });

        intent = new Intent(this, CadastroActivity.class);

        cadastroIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }
}