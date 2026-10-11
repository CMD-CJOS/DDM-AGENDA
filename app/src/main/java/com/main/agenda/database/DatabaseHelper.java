package com.main.agenda.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String BANCO = "agenda.db";
    private static final int VERSAO = 1;

    public static final String TABELA = "contatos";
    public static final String COL_ID = "id";
    public static final String COL_NOME = "nome";
    public static final String COL_TEL = "telefone";
    public static final String COL_EMAIL = "email";
    public static final String COL_END = "endereco";

    private static DatabaseHelper instancia;

    private DatabaseHelper(Context context) {
        super(context.getApplicationContext(), BANCO, null, VERSAO);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instancia == null) {
            instancia = new DatabaseHelper(context);
        }
        return instancia;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NOME + " TEXT NOT NULL, "
                + COL_TEL + " TEXT, "
                + COL_EMAIL + " TEXT, "
                + COL_END + " TEXT"
                + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        onCreate(db);
    }
}
