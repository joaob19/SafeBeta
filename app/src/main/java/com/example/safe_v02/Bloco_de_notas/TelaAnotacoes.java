package com.example.safe_v02.Bloco_de_notas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.safe_v02.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;

public class TelaAnotacoes extends AppCompatActivity {

    ListView listView;
    FloatingActionButton btnAdd;
    static ArrayList<Nota> notas;
    static AdapterNota adapter;
    Toolbar toolbar;
    TextView txtAviso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_anotacoes);

        toolbar = findViewById(R.id.toolbar_bn1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Minhas anotações");

        txtAviso = (TextView)findViewById(R.id.txtAvisoAnotacoes);
        listView = (ListView) findViewById(R.id.lista_de_notas);

        carregarNotas();


        //Chama a tela de edição de texto ao clicar no botão add
        btnAdd = (FloatingActionButton) findViewById(R.id.btnAddNota);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaAnotacoes.this, TelaEditorTexto.class);
                startActivity(intent);
            }
        });


        //Caso seja exercido um toque curto em um itemda listView, ele será aberto na tela de edição
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TelaAnotacoes.this, TelaEditorTexto.class);
                intent.putExtra("noteId",notas.get(position).getId());
                startActivity(intent);
            }
        });

        //Caso seja exercido um toque longo em um item da listView, será exibido um dialog perguntando se deseja apagar o item
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder adb= new AlertDialog.Builder(TelaAnotacoes.this);
                adb.setTitle("Excluir anotação");
                adb.setMessage("Deseja excluir essa anotação?");
                final int positionToMove = position;
                adb.setNegativeButton("NÂo",null);
                adb.setPositiveButton("SIM",new AlertDialog.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NotasDAO notasDAO = new NotasDAO(TelaAnotacoes.this);
                        notasDAO.excluirNota(notas.get(position));
                        carregarNotas();
                        Toast.makeText(TelaAnotacoes.this, "Nota excluída", Toast.LENGTH_SHORT).show();
                    }
                });
                adb.show();
                return true;
            }
        });
    }

    public void carregarNotas(){
        NotasDAO notasDAO = new NotasDAO(TelaAnotacoes.this);
        if (notasDAO.obterNotas().size()>0){
            notas = new ArrayList<Nota>(notasDAO.obterNotas());
        }
        else{
            notas = new ArrayList<Nota>();
        }
        adapter = new AdapterNota(this,notas);
        listView.setAdapter(adapter);
        verificarNotas();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarNotas();
    }

    public void verificarNotas(){
        if(notas.size()>0){
            txtAviso.setText(" ");
        }
        else{
            txtAviso.setText("Para adicionar uma nova anotação clique nota botão + no canto inferior direito.");
        }
    }

}
