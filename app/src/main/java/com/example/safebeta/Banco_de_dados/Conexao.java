package com.example.safebeta.Banco_de_dados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class Conexao extends SQLiteOpenHelper {
   private static final String nomeDoBanco = "BancoSafe";
   private static final int versaoBanco = 2;

   public Conexao(Context context) {
      super(context, "BancoSafe", (CursorFactory)null, 1);
   }

   public void onCreate(SQLiteDatabase db) {
      db.execSQL("create table eventos(id integer primary key ,nome varchar(50),tipo varchar(20),materia varchar(50),data varchar(50),hora varchar(10),descricao varchar(200),idAlarme1 int,idAlarme2 int)");
      db.execSQL("create table horarios(id integer primary key  ,dia integer,horaInicio varchar(5),horaTermino varchar(5),materia varchar(50))");
      db.execSQL("create table materias(id integer primary key  ,nome varchar(50),tipoDeMedia varchar(20),qntdNotas integer,media double,notas varchar(200))");
      db.execSQL("create table notas(id integer primary key,titulo varchar(50),texto varchar(500),dataModificacao varchar(15))");
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

   }
}
