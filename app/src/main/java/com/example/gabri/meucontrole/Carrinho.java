package com.example.gabri.meucontrole;

import android.widget.TextView;

import java.util.ArrayList;

public class Carrinho {

    private static Carrinho uniqueInstance = new Carrinho();

    private ArrayList<Produto> produtosCarro = new ArrayList<Produto>();

    private Carrinho() {

    }

    public static Carrinho getInstance() {
        return uniqueInstance;
    }


    public ArrayList<Produto> getProdutosCarro() {
        return produtosCarro;
    }

    public int getQuantCarinho() {
        return produtosCarro.size();
    }

    public void setProdutosCarro(Produto produtosCarro) {
        this.produtosCarro.add(produtosCarro);
    }
}
