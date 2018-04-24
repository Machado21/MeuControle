package com.example.gabri.meucontrole;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    EditText nomeEditText, cpfEditText, emailEditText, senhaEditText, confirmEditText;

    private Button criarLoja, entrarLoja;

    private Intent intent, intentEntra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        mAuth = FirebaseAuth.getInstance();

        entrarLoja = findViewById(R.id.entrar_loja_cad_p_bt);
        criarLoja = findViewById(R.id.cadastrar_loja_cad_p_bt);

        nomeEditText = findViewById(R.id.nome_pessoa_cad_et);
        cpfEditText = findViewById(R.id.cpf_pessoa_cad_et);
        emailEditText = findViewById(R.id.email_cad_et);
        senhaEditText = findViewById(R.id.senha_pessoa_cad_et);
        confirmEditText = findViewById(R.id.confirm_senha_pessoa_et);

        cpfEditText.addTextChangedListener(MaskEditUtil.mask(cpfEditText,MaskEditUtil.FORMAT_CPF));

        criarLoja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String nome, cpf, email, senha, confirmSenha;

                nome = nomeEditText.getText().toString();
                cpf = cpfEditText.getText().toString();
                email = emailEditText.getText().toString();
                senha = senhaEditText.getText().toString();
                confirmSenha = confirmEditText.getText().toString();

                if (!nome.isEmpty() && !cpf.isEmpty() && !email.isEmpty() && !senha.isEmpty() && !confirmSenha.isEmpty()) {

                    nomeEditText.setText("");
                    cpfEditText.setText("");
                    emailEditText.setText("");
                    senhaEditText.setText("");
                    confirmEditText.setText("");

                    mAuth.createUserWithEmailAndPassword(email, senha)
                            .addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {

                                        FirebaseUser user = mAuth.getCurrentUser();
                                        String uid = user.getUid();

                                        final Pessoa pessoa = new Pessoa(nome, cpf, email, uid);

                                        myRef = database.getReference("Usuarios" ).child(pessoa.getmCpf());
                                        myRef.setValue(pessoa);

                                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(pessoa.getmNome()).build();

                                        user.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                Toast.makeText(CadastroActivity.this, "Updated",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                        Toast.makeText(CadastroActivity.this, "Cadastro Realizado com Sucesso",
                                                Toast.LENGTH_SHORT).show();

                                        intent = new Intent(CadastroActivity.this, CadastrarLojaActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(CadastroActivity.this, "Nao foi possivel Realizar cadastro", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                } else {
                    Toast.makeText(CadastroActivity.this, "Campo Vazio", Toast.LENGTH_SHORT).show();
                }
            }
        });



        entrarLoja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String nome, cpf, email, senha, confirmSenha;

                nome = nomeEditText.getText().toString();
                cpf = cpfEditText.getText().toString();
                email = emailEditText.getText().toString();
                senha = senhaEditText.getText().toString();
                confirmSenha = confirmEditText.getText().toString();

                if (!nome.isEmpty() && !cpf.isEmpty() && !email.isEmpty() && !senha.isEmpty() && !confirmSenha.isEmpty()) {

                    nomeEditText.setText("");
                    cpfEditText.setText("");
                    emailEditText.setText("");
                    senhaEditText.setText("");
                    confirmEditText.setText("");

                    mAuth.createUserWithEmailAndPassword(email, senha)
                            .addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {

                                        FirebaseUser user = mAuth.getCurrentUser();
                                        String uid = user.getUid();

                                        final Pessoa pessoa = new Pessoa(nome, cpf, email, uid);

                                        myRef = database.getReference("Usuarios").child(pessoa.getmCpf());
                                        myRef.setValue(pessoa);

                                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(pessoa.getmNome()).build();

                                        user.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                Toast.makeText(CadastroActivity.this, "Update",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                        Toast.makeText(CadastroActivity.this, "Cadastro Realizado com Sucesso",
                                                Toast.LENGTH_SHORT).show();

                                        intentEntra = new Intent(CadastroActivity.this, EntrarLojaActivity.class);
                                        startActivity(intentEntra);
                                    } else {
                                        Toast.makeText(CadastroActivity.this, "Nao foi possivel Realizar cadastro", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                } else {
                    Toast.makeText(CadastroActivity.this, "Campo Vazio", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
}
