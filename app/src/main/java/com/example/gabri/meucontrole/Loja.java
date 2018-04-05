package com.example.gabri.meucontrole;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by gabri on 27/03/2018.
 */

public class Loja {

    private String mNome;
    private String mCnpj;
    private String mSenha;
    private String mProrietario;
    private Loja store;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference pullRef;

    public Loja() {

    }

    public Loja(String nome, String cnpj, String senha, String proprietario) {
        mNome = nome;
        mCnpj = cnpj;
        mSenha = senha;
        mProrietario = proprietario;
    }

    public String getmNome() {
        return mNome;
    }

    public void setmNome(String mNome) {
        this.mNome = mNome;
    }

    public String getmCnpj() {
        return mCnpj;
    }

    public void setmCnpj(String mCnpj) {
        this.mCnpj = mCnpj;
    }

    public String getmSenha() {
        return mSenha;
    }

    public void setmSenha(String mSenha) {
        this.mSenha = mSenha;
    }

    public String getmProrietario() {
        return mProrietario;
    }

    public void setmProrietario(String mProrietario) {
        this.mProrietario = mProrietario;
    }

    public void cadastraProdutos(Produto produto, String loja) {

        pullRef = database.getReference("Lojas").child(loja).child("Produtos").child(produto.getmNome());
        pullRef.setValue(produto);
    }

}