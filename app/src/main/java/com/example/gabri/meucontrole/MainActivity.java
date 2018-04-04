package com.example.gabri.meucontrole;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText nomeProduto, codigoProduto, quantProduto, valorProduto;
    private String sNomeProd, sCodProd, sQuantProd, sValorProd;
    private TextView intentProfutos;

    private Button botaoCad, botaoBusc;
    private Intent intent;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference, lojaRef, pessoaRef;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private Produto produto;
    private Pessoa pessoa;
    private Loja loja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomeProduto = findViewById(R.id.nome_produto_ed);
        codigoProduto = findViewById(R.id.codigo_produto_ed);
        quantProduto = findViewById(R.id.quant_produto_ed);
        valorProduto = findViewById(R.id.valor_produto_ed);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        final String userEmail = user.getEmail();

        pessoaRef = database.getReference("Usuarios");
        pessoaRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue(Pessoa.class).getmEmail().equals(userEmail)){

                    String name = dataSnapshot.getValue(Pessoa.class).getmNome();
                    String cpf = dataSnapshot.getValue(Pessoa.class).getmCpf();
                    String email = dataSnapshot.getValue(Pessoa.class).getmEmail();
                    String id = dataSnapshot.getValue(Pessoa.class).getUid();
                    String store = dataSnapshot.getValue(Pessoa.class).getmLoja();

                    pessoa = new Pessoa(name,cpf,email,id);
                    pessoa.setmLoja(store);
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

        intent = new Intent(this, ProdutoActivity.class);
        botaoCad = findViewById(R.id.cadastrar_produto_bt);

        botaoCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nomeProd = nomeProduto.getText().toString();
                String codProd = codigoProduto.getText().toString();
                String quantProd = quantProduto.getText().toString();
                String valorProd = valorProduto.getText().toString();

                produto = new Produto(nomeProd, codProd, quantProd, valorProd);
                loja.cadastraProdutos(produto, pessoa.getmLoja());

                //reference.setValue(produto);
            }
        });

        botaoBusc = findViewById(R.id.produtos_intent_tv);

        botaoBusc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

    }
}
