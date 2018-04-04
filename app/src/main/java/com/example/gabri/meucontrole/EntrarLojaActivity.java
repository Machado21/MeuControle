package com.example.gabri.meucontrole;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EntrarLojaActivity extends AppCompatActivity {

    private Intent intent;

    private TextView cnpjET, senhaET;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference pessoaRef, lojaRef;

    private Button botaoEntrar;

    private Pessoa pessoa;
    private Loja loja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar_loja);

        cnpjET = findViewById(R.id.cnpj_entrar_loja_et);
        senhaET = findViewById(R.id.senha_entrar_loja_et);
        botaoEntrar = findViewById(R.id.entrar_loja_bt);

        user = mAuth.getCurrentUser();
        final String userEmail = user.getEmail();

        pessoaRef = database.getReference("Usuarios");
        pessoaRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue(Pessoa.class).getmEmail().equals(userEmail)) {

                    String name = dataSnapshot.getValue(Pessoa.class).getmNome();
                    String cpf = dataSnapshot.getValue(Pessoa.class).getmCpf();
                    String email = dataSnapshot.getValue(Pessoa.class).getmEmail();
                    String uid = dataSnapshot.getValue(Pessoa.class).getUid();

                    pessoa = new Pessoa(name, cpf, email, uid);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String cnpj = cnpjET.getText().toString();
                final String senha = senhaET.getText().toString();

                lojaRef = database.getReference("Lojas");
                lojaRef.addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if (dataSnapshot.getValue(Loja.class).getmCnpj().equals(cnpj) && dataSnapshot.getValue(Loja.class).getmSenha().equals(senha)) {
                            String nomel = dataSnapshot.getValue(Loja.class).getmNome();
                            String cnpjl = dataSnapshot.getValue(Loja.class).getmCnpj();
                            String senhal = dataSnapshot.getValue(Loja.class).getmSenha();
                            String propril = dataSnapshot.getValue(Loja.class).getmProrietario();

                            pessoa.setmLoja(dataSnapshot.getValue(Loja.class).getmNome());
                            loja = new Loja(nomel, cnpjl, senhal, propril);

                            DatabaseReference pessoaRefImput = database.getReference("Usuarios").child(pessoa.getmCpf());
                            pessoaRefImput.setValue(pessoa);

                            intent = new Intent(EntrarLojaActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });
    }
}
