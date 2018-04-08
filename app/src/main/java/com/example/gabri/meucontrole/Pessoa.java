package com.example.gabri.meucontrole;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by gabri on 27/03/2018.
 */

public class Pessoa{

    private String uid;
    private String mNome;
    private String mCpf;
    private String mEmail;
    private String mLoja;

    public Pessoa() {

    }

    public Pessoa(String nome, String cpf,String email, String id) {
        mNome = nome;
        mCpf = cpf;
        mEmail = email;
        uid = id;
    }

    public String getmNome() {
        return mNome;
    }

    public void setmNome(String mNome) {
        this.mNome = mNome;
    }

    public String getmCpf() {
        return mCpf;
    }

    public void setmCpf(String mCpf) {
        this.mCpf = mCpf;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmLoja() {
        return mLoja;
    }

    public void setmLoja(String mLoja) {
        this.mLoja = mLoja;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
