package com.example.gabri.meucontrole;

import android.content.Intent;
import android.support.v4.view.ViewPager;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText nomeProduto, codigoProduto, quantProduto, valorProduto;
    private String sNomeProd, sCodProd, sQuantProd, sValorProd;
    private TextView intentProfutos;

    private Button botaoCad, botaoBusc;
    private Intent intent;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference, produtoRef, pessoaRef, auxProdRef;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private Produto produto, prodAux;
    private Pessoa pessoa;
    private Loja loja;

    private List<String> mProdutosIds = new ArrayList<>();
    private ArrayList<Produto> mProdutos = new ArrayList<>();

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

        //TODO Colocar tudo isso na classe Pessoa se der
        pessoaRef = database.getReference("Usuarios");
        pessoaRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue(Pessoa.class).getmEmail().equals(userEmail)) {

                    String name = dataSnapshot.getValue(Pessoa.class).getmNome();
                    String cpf = dataSnapshot.getValue(Pessoa.class).getmCpf();
                    String email = dataSnapshot.getValue(Pessoa.class).getmEmail();
                    String id = dataSnapshot.getValue(Pessoa.class).getUid();
                    String store = dataSnapshot.getValue(Pessoa.class).getmLoja();

                    pessoa = new Pessoa(name, cpf, email, id);
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

        //TODO Rezolver problema


        botaoCad = findViewById(R.id.cadastrar_produto_bt);
        botaoCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nomeProd = nomeProduto.getText().toString();
                final String codProd = codigoProduto.getText().toString();
                String quantProd = quantProduto.getText().toString();
                String valorProd = valorProduto.getText().toString();


                auxProdRef = database.getReference("Lojas").child(pessoa.getmLoja()).child("Produtos");

                auxProdRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        Produto produto = dataSnapshot.getValue(Produto.class);

                        mProdutosIds.add(dataSnapshot.getKey());
                        mProdutos.add(produto);

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

                Produto novoAux = verifica(mProdutos, codProd);
                if (novoAux != null) {
                    produto = novoAux;
                    produto.setmQuantidade(Integer.parseInt(quantProd));
                } else {

                    produto = new Produto(nomeProd, codProd, Integer.parseInt(quantProd), valorProd);
                }
                //Pega a referencia de um banco de dados Geral PARA FUTURAS PESQUISAS
                reference = database.getReference("Geral").child(codProd);
                //Pega a referencia da LOJA
                produtoRef = database.getReference("Lojas").child(pessoa.getmLoja()).child("Produtos").child(produto.getmCodigo());
                //Adiciona o Produto Geral
                reference.setValue(produto);
                //Adiciona o Produto na loja
                produtoRef.setValue(produto);

                //Limpa os campos
                nomeProduto.setText("");
                codigoProduto.setText("");
                quantProduto.setText("");
                valorProduto.setText("");

                //Mostra uma mensagem na tela
                Toast.makeText(MainActivity.this, "Produto cadastrado", Toast.LENGTH_SHORT).show();

                //reference.setValue(produto);
            }
        });

        botaoBusc = findViewById(R.id.produtos_intent_tv);

        intent = new Intent(this, ProdutoActivity.class);

        botaoBusc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intent);
            }
        });
    }

    public Produto verifica(ArrayList<Produto> lista, String str) {
        Produto aux = null;
        Iterator iterator = lista.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(str)) {
                aux = (Produto) iterator.next();
                return aux;
            }
        }
        return null;
    }
}
