package com.example.gabri.meucontrole;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Gabriel R. Machado on 21/03/2018.
 */

public class ProdutoAdapter extends ArrayAdapter<Produto> {

    private int mColorResoucerId;

    public ProdutoAdapter(Context context, ArrayList<Produto> produtos) {
        super(context,0, produtos);
        //mColorResoucerId = colorResoucerId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listItenView = convertView;
        if (listItenView == null) {
            listItenView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_iten, parent, false);
        }

        final Carrinho carrinho = Carrinho.getInstance();
        final Produto currentProduto = getItem(position);


        TextView nomeTextView = listItenView.findViewById(R.id.nome_produto_tv);
        nomeTextView.setText(currentProduto.getmNome());

        TextView quantiTextView = listItenView.findViewById(R.id.quant_produto_tv);
        quantiTextView.setText("Quant: " + currentProduto.getmQuantidade());

        TextView valorTextView = listItenView.findViewById(R.id.valor_produto_tv);
        valorTextView.setText("R$ " + currentProduto.getmValor());

        TextView putInShop = listItenView.findViewById(R.id.put_in_store);
        putInShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carrinho.setProdutosCarro(currentProduto);

            }
        });

        return listItenView;
    }
}
