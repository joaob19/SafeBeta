package com.example.safebeta.Bloco_de_notas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.safebeta.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TelaEditorTexto extends AppCompatActivity {
    FloatingActionButton btnSalvar;
    EditText txtTexto,txtTitulo;
    Toolbar toolbar;
    NotasDAO notasDAO;
    Nota nota = new Nota();
    int idNota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_editor_texto);

        txtTexto = (EditText)findViewById(R.id.txtNota);
        txtTitulo = (EditText)findViewById(R.id.txtTituloNota);
        btnSalvar = (FloatingActionButton)findViewById(R.id.btnSalvar);
        toolbar = findViewById(R.id.toolbar_bn2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Escreva sua anotação");

        notasDAO = new NotasDAO(TelaEditorTexto.this);

        Intent intent = getIntent();
        idNota  = intent.getIntExtra("noteId",-1);
        if (idNota>0){
            carregarNota(idNota);
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtTexto.getText().toString().length() > 0 && txtTitulo.getText().toString().length()>0){
                    Date date = new Date();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MMM/y kk:m");
                    nota.setDataModificacao(dateFormat.format(date));
                    if(idNota==-1){
                        nota.setTitulo(txtTitulo.getText().toString());
                        nota.setTexto(txtTexto.getText().toString());
                        notasDAO.inserirNota(nota);
                        Toast.makeText(TelaEditorTexto.this, "Nota salva", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        nota.setTitulo(txtTitulo.getText().toString());
                        nota.setTexto(txtTexto.getText().toString());
                        notasDAO.atualizarNota(nota);
                        Toast.makeText(TelaEditorTexto.this, "Nota alterada", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
                else{
                    finish();
                }
            }
        });

    }


    // Faz com que o botão voltar da toolbar funcione igual ao do celular
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }


    //Veficia se há texto texto para ser salvo se o usuário clicar no botão voltar. Caso haja ele salva.
    @Override
    public void onBackPressed() {

        if (txtTexto.getText().toString().length() > 0 && txtTitulo.getText().toString().length()>0) {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd/MMM/y kk:m");
            nota.setDataModificacao(dateFormat.format(date));
            if(idNota==-1){
                nota.setTitulo(txtTitulo.getText().toString());
                nota.setTexto(txtTexto.getText().toString());
                notasDAO.inserirNota(nota);
                Toast.makeText(TelaEditorTexto.this, "Nota salva", Toast.LENGTH_SHORT).show();
            }
            else{
                nota.setTitulo(txtTitulo.getText().toString());
                nota.setTexto(txtTexto.getText().toString());
                notasDAO.atualizarNota(nota);
                Toast.makeText(TelaEditorTexto.this, "Nota alterada", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
        else{
            super.onBackPressed();
        }
    }

    public void salvarNota(){

    }


    public void carregarNota(int idNota){
        nota = notasDAO.obterNota(idNota);
        txtTexto.setText(nota.getTexto());
        txtTitulo.setText(nota.getTitulo());
    }

}
