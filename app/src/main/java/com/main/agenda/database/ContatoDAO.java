package com.main.agenda.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.main.agenda.model.Contato;

import java.util.ArrayList;
import java.util.List;

public class ContatoDAO {
    private DatabaseHelper dbHelper;

    public ContatoDAO(Context context) {
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public long inserir(Contato contato) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_NOME, contato.getNome());
        values.put(DatabaseHelper.COL_TEL, contato.getTelefone());
        values.put(DatabaseHelper.COL_EMAIL, contato.getEmail());
        values.put(DatabaseHelper.COL_END, contato.getEndereco());
        return db.insert(DatabaseHelper.TABELA, null, values);
    }

    public int atualizar(Contato contato) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_NOME, contato.getNome());
        values.put(DatabaseHelper.COL_TEL, contato.getTelefone());
        values.put(DatabaseHelper.COL_EMAIL, contato.getEmail());
        values.put(DatabaseHelper.COL_END, contato.getEndereco());
        return db.update(DatabaseHelper.TABELA, values, DatabaseHelper.COL_ID + " = ?", new String[]{String.valueOf(contato.getId())});
    }

    public int deletar(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(DatabaseHelper.TABELA, DatabaseHelper.COL_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public List<Contato> listarTodos() {
        List<Contato> contatos = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABELA, null, null, null, null, null, DatabaseHelper.COL_NOME + " ASC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ID));
                String nome = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NOME));
                String telefone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TEL));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EMAIL));
                String endereco = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_END));

                contatos.add(new Contato(id, nome, telefone, email, endereco));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return contatos;
    }

    public Contato buscarPorId(int idContato) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABELA, null, DatabaseHelper.COL_ID + " = ?", new String[]{String.valueOf(idContato)}, null, null, null);
        Contato contato = null;
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ID));
            String nome = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NOME));
            String telefone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TEL));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EMAIL));
            String endereco = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_END));
            contato = new Contato(id, nome, telefone, email, endereco);
            cursor.close();
        }
        return contato;
    }
}
