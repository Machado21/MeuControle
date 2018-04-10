package com.example.gabri.meucontrole;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class ProdutoActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference prodRef, userRef;
    ProdutoAdapter adapter;

    FirebaseAuth auth;

    Pessoa usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produto_list);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user;
        user = auth.getCurrentUser();
        final String emailUser = user.getEmail();

        userRef = database.getReference("Usuarios");
        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue(Pessoa.class).getmEmail().equals(emailUser)){
                    String nomeU = dataSnapshot.getValue(Pessoa.class).getmNome();
                    String cpfU = dataSnapshot.getValue(Pessoa.class).getmCpf();
                    String emailU = dataSnapshot.getValue(Pessoa.class).getmEmail();
                    String idU = dataSnapshot.getValue(Pessoa.class).getUid();
                    String lojaU = dataSnapshot.getValue(Pessoa.class).getmLoja();

                    usuario = new Pessoa(nomeU, cpfU, emailU, idU);
                    usuario.setmLoja(lojaU);
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

        final ArrayList<Produto> produtos = new ArrayList<Produto>();

        produtos.add(new Produto("Teste", "00", "1", "100.00"));

        Button atualizar = findViewById(R.id.atulizar);
        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usuario != null) {

                    String loja = usuario.getmLoja();
                    prodRef = database.getReference("Lojas").child(loja).child("Produtos");

                    //###
                    prodRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                            Produto produtoN = dataSnapshot.getValue(Produto.class);

                            if (!produtos.contains(produtoN)) {
                                produtos.add(produtoN);
                                adapter.notifyDataSetChanged();
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
                }

            }
        });

        adapter = new ProdutoAdapter(this, produtos);

        ListView listView = findViewById(R.id.list);

        listView.setAdapter(adapter);
    }
}
