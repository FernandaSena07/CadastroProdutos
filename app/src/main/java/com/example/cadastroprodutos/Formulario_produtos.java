package com.example.cadastroprodutos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cadastroprodutos.BDHelper.ProdutosBd;
import com.example.cadastroprodutos.model.Produtos;

public class Formulario_produtos extends AppCompatActivity {

    EditText editTextNomeProduto, editTextDescricao, editTextquantidade, editTextPreco;
    Button btn_Polimorf;
    Produtos editarProduto, produto;
    ProdutosBd bdHelper;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.formulario_produtos);

        bdHelper = new ProdutosBd(Formulario_produtos.this);

        Intent intent = getIntent();
        editarProduto = (Produtos) intent.getSerializableExtra("produto-escolhido");

        editTextNomeProduto = findViewById(R.id.editTextNomeProduto);
        editTextDescricao = findViewById(R.id.editTextDescricao);
        editTextquantidade = findViewById(R.id.editTextquantidade);

        btn_Polimorf = findViewById(R.id.btn_Polimorf);


        Button btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        produto = new Produtos();

        if (editarProduto != null) {
            btn_Polimorf.setText("Modificar");

            editTextNomeProduto.setText(editarProduto.getNomeProduto());
            editTextDescricao.setText(editarProduto.getDescricao());
            editTextquantidade.setText(editarProduto.getQuantidade() + "");

            produto.setId(editarProduto.getId());

        } else {
            btn_Polimorf.setText("Cadastrar");
        }

        btn_Polimorf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                produto.setNomeProduto(editTextNomeProduto.getText().toString());
                produto.setDescricao(editTextDescricao.getText().toString());
                produto.setQuantidade(Integer.parseInt(editTextquantidade.getText().toString()));

                if (btn_Polimorf.getText().toString().equals("Cadastrar")) {
                    bdHelper.salvarProduto(produto);
                    bdHelper.close();
                } else {
                    bdHelper.alterarProduto(produto);
                    bdHelper.close();
                }
            }
        });
    }
}
