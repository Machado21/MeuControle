package com.example.gabri.meucontrole;

/**
 * Created by Gabriel R. Machado on 19/03/2018.
 */

public class Produto {

    private String mNome;

    private String mCodigo;

    private int mQuantidade;

    private String mValor;

    public Produto() {

    }

    public Produto (String nome, String code, int quantidade, String valor) {
        mNome = nome;
        mCodigo = code;
        mQuantidade = quantidade;
        mValor = valor;
    }

    public String getmNome() {
        return mNome;
    }

    public String getmCodigo() {
        return mCodigo;
    }

    public int getmQuantidade() {
        return mQuantidade;
    }

    public void setmQuantidade(int quantidade) {
        mQuantidade += quantidade;
    }

    public String getmValor() {
        return mValor;
    }
}
