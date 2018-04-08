package com.example.gabri.meucontrole;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference prodRef, pessoaRef;
    ProdutoAdapter adapter;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user;

    Pessoa usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produto_list);

        user = auth.getCurrentUser();

        final String userEmail = user.getEmail();

        pessoaRef = database.getReference("Usuarios");

        if(usuario == null) {
            pessoaRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot.getValue(Pessoa.class).getmEmail().equals(userEmail)) {

                        String name = dataSnapshot.getValue(Pessoa.class).getmNome();
                        String cpf = dataSnapshot.getValue(Pessoa.class).getmCpf();
                        String email = dataSnapshot.getValue(Pessoa.class).getmEmail();
                        String id = dataSnapshot.getValue(Pessoa.class).getUid();
                        String store = dataSnapshot.getValue(Pessoa.class).getmLoja();

                        usuario = new Pessoa(name, cpf, email, id);
                        usuario.setmLoja(store);

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

        final ArrayList<Produto> produtos = new ArrayList<Produto>();

        produtos.add(new Produto("Teste","00","1","100.00"));


        if (usuario != null) {

            String loja = usuario.getmLoja();
            prodRef = database.getReference("Lojas").child(loja).child("Produtos");

            //###
            prodRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    Produto produtoN = dataSnapshot.getValue(Produto.class);

                    produtos.add(produtoN);
                    adapter.notifyDataSetChanged();
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

            adapter = new ProdutoAdapter(this, produtos);

            ListView listView = findViewById(R.id.list);

            listView.setAdapter(adapter);
    }
}
