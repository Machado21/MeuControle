package com.example.gabri.meucontrole;

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

    public void setProdutosCarro(Produto produtosCarro) {
        this.produtosCarro.add(produtosCarro);
    }
}
