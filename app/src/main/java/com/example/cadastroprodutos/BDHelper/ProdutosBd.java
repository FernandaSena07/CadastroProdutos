package com.example.cadastroprodutos.BDHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cadastroprodutos.model.Produtos;

import java.util.ArrayList;

public class ProdutosBd extends SQLiteOpenHelper {

    private static final String DATABASE = "bdprodutos";
    private static final int VERSION = 2;

    private static final String COL_ID = "id";
    private static final String COL_NOME_PRODUTO = "nomeproduto";
    private static final String COL_DESCRICAO = "descricao";
    private static final String COL_QUANTIDADE = "quantidade";

    public ProdutosBd(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String produto = "CREATE TABLE produtos(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nomeproduto TEXT NOT NULL, descricao TEXT NOT NULL, quantidade INTEGER);";
        db.execSQL(produto);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String produto = "DROP TABLE IF EXISTS produtos";
        db.execSQL(produto);

        // Agora, você precisa criar a tabela novamente
        onCreate(db);
    }


    public void salvarProduto(Produtos produto) {
        ContentValues values = new ContentValues();
        values.put(COL_NOME_PRODUTO, produto.getNomeProduto());
        values.put(COL_DESCRICAO, produto.getDescricao());
        values.put(COL_QUANTIDADE, produto.getQuantidade());
        getWritableDatabase().insert("produtos", null, values);
    }

    public void alterarProduto(Produtos produto) {
        ContentValues values = new ContentValues();
        values.put(COL_NOME_PRODUTO, produto.getNomeProduto());
        values.put(COL_DESCRICAO, produto.getDescricao());
        values.put(COL_QUANTIDADE, produto.getQuantidade());

        String[] args = {String.valueOf(produto.getId())};
        getWritableDatabase().update("produtos", values, COL_ID + "=?", args);
    }

    public void deletarProduto(Produtos produto) {
        if (produto.getId() != null) {
            String[] args = {String.valueOf(produto.getId())};
            getWritableDatabase().delete("produtos", COL_ID + "=?", args);
        }
    }

    public ArrayList<Produtos> getLista() {
        String[] columns = {COL_ID, COL_NOME_PRODUTO, COL_DESCRICAO, COL_QUANTIDADE};
        try (Cursor cursor = getWritableDatabase().query("produtos", columns, null, null, null, null, null, null)) {
            ArrayList<Produtos> produtos = new ArrayList<>();

            while (cursor.moveToNext()) {
                Produtos produto = new Produtos();
                int columnIndexId = cursor.getColumnIndex(COL_ID);
                int columnIndexNome = cursor.getColumnIndex(COL_NOME_PRODUTO);
                int columnIndexDescricao = cursor.getColumnIndex(COL_DESCRICAO);
                int columnIndexQuantidade = cursor.getColumnIndex(COL_QUANTIDADE);

                produto.setId(cursor.getLong(columnIndexId));
                produto.setNomeProduto(cursor.getString(columnIndexNome));
                produto.setDescricao(cursor.getString(columnIndexDescricao));
                produto.setQuantidade(cursor.getInt(columnIndexQuantidade));

                produtos.add(produto);
            }
            return produtos;
        }
    }

}
