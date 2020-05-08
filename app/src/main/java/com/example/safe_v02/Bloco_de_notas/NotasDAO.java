package com.example.safe_v02.Bloco_de_notas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.safe_v02.Banco_de_dados.Conexao;

import java.util.ArrayList;

public class NotasDAO {

    private SQLiteDatabase banco;
    private Conexao conexao;

    public NotasDAO(Context context){
        this.conexao = new Conexao(context);
        this.banco = conexao.getWritableDatabase();
    }

    public long inserirNota(Nota nota){

        ContentValues values = new ContentValues();
        values.put("titulo",nota.getTitulo());
        values.put("texto",nota.getTexto());
        return this.banco.insert("notas",null, values);
    }

    public void excluirNota(Nota nota){

        SQLiteDatabase banco = this.banco;
        String[] id = new String[]{Integer.toString(nota.getId())};
        banco.delete("notas","id=?", id);

    }

    public ArrayList obterNotas(){

        ArrayList<Nota> notas = new ArrayList<>();
        SQLiteDatabase banco = this.banco;
        Cursor cursor = banco.query("notas", new String[]{"id","titulo","texto"}, (String)null, (String[])null, (String)null, (String)null, (String)null);

        while (cursor.moveToNext()){
            Nota nota = new Nota();
            nota.setId(cursor.getInt(0));
            nota.setTitulo(cursor.getString(1));
            nota.setTexto(cursor.getString(2));
            notas.add(nota);
        }

        return notas;
    }

    public void atualizarNota(Nota nota){
        ContentValues values = new ContentValues();
        values.put("id",nota.getId());
        values.put("titulo",nota.getTitulo());
        values.put("texto",nota.getTexto());
        SQLiteDatabase banco = this.banco;
        String[] id = new String[]{Integer.toString(nota.getId())};
        banco.update("notas", values, "id=?", id);
    }

    public Nota obterNota(int id){
        SQLiteDatabase banco = this.banco;
        Cursor cursor = banco.query("notas", new String[]{"id","titulo","texto"}, (String)null, (String[])null, (String)null, (String)null, (String)null);
        Nota nota = new Nota();

        while (cursor.moveToNext()){
            if(cursor.getInt(0)==id){
                nota.setId(cursor.getInt(0));
                nota.setTitulo(cursor.getString(1));
                nota.setTexto(cursor.getString(2));
            }
        }

        return nota;
    }

}
