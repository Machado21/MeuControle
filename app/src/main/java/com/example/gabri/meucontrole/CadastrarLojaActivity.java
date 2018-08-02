package com.example.gabri.meucontrole;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CadastrarLojaActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference pessoaRef, lojaRef;

    private FirebaseAuth mAuth;

    private Intent intentMain;

    EditText nomeLojaET, cnpjET, senhaLojaET, confirmLojaET;

    private Button cadastrarLoja;

    Pessoa pessoa;
    Loja loja;

    private TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_loja);

        nomeLojaET = findViewById(R.id.nome_loja_cad_l_et);
        cnpjET = findViewById(R.id.cnpj_loja_cad_l_et);
        senhaLojaET = findViewById(R.id.senha_loja_cad_et);
        confirmLojaET = findViewById(R.id.confirm_senha_loja_et);

        //cnpjET.addTextChangedListener(MaskEditUtil.mask(cnpjET, MaskEditUtil.FORMAT_CNPJ));

        cadastrarLoja = findViewById(R.id.cadastra_loja_bt);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final String userEmail = user.getEmail();
        //###
        /*
        * Aqui é pego os dados do usuario logado
        * para cadastrar uma referencia da loja que será criada
        * */
        //TODO Tentar fazer isso dentro da classe Pessoa
        pessoaRef = database.getReference("Usuarios");
        pessoaRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue(Pessoa.class).getmEmail().equals(userEmail)){

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
        //###
        intentMain = new Intent(this, MainActivity.class);
        /*
        * Aqui é feito o cadastro da loja
        * e associa a loja ao usuario logado.
        * */
        cadastrarLoja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nomeLoja, cnpj, senhaLoja, confirmLoja;

                nomeLoja = nomeLojaET.getText().toString();
                cnpj = cnpjET.getText().toString();
                senhaLoja = senhaLojaET.getText().toString();
                confirmLoja = confirmLojaET.getText().toString();

                if (!nomeLoja.isEmpty() && !cnpj.isEmpty() && !senhaLoja.isEmpty() && !confirmLoja.isEmpty()){

                    nomeLojaET.setText("");
                    cnpjET.setText("");
                    senhaLojaET.setText("");
                    confirmLojaET.setText("");

                    loja = new Loja(nomeLoja, cnpj, senhaLoja, pessoa.getmCpf());

                    pessoa.setmLoja(loja.getmCnpj());

                    lojaRef = database.getReference("Lojas").child(loja.getmCnpj());
                    pessoaRef = database.getReference("Usuarios").child(pessoa.getmCpf());
                    lojaRef.setValue(loja);
                    pessoaRef.setValue(pessoa);

                    startActivity(intentMain);
                } else {
                    Toast.makeText(CadastrarLojaActivity.this, "Campo Vazio", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
