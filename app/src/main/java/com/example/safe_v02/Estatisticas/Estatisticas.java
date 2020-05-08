package com.example.safe_v02.Estatisticas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.safe_v02.Materias_e_notas.MateriaDAO;
import com.example.safe_v02.R;

import java.util.ArrayList;

public class Estatisticas extends AppCompatActivity {

    Toolbar toolbar;
    AdapterEstatistica adapterEstatistica = null;
    ArrayList array_materias = null;
    ListView listaEstatisticas;
    MateriaDAO materiaDAO;
    TextView txtAviso,txtStats1,txtStats2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatisticas);

        toolbar = findViewById(R.id.toolbarestatisticas);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Estatísticas");

        listaEstatisticas = (ListView)findViewById(R.id.listaEstatisticas);
        materiaDAO = new MateriaDAO(this);
        array_materias = materiaDAO.obterTodas();
        adapterEstatistica = new AdapterEstatistica(this, array_materias);
        listaEstatisticas.setAdapter(adapterEstatistica);

        txtAviso = (TextView)findViewById(R.id.txtAvisoEstatisticas);
        txtStats1 = (TextView)findViewById(R.id.txtStats1);
        txtStats2 = (TextView)findViewById(R.id.txtStats2);
        verificarEstatisticas();

    }

    public void verificarEstatisticas(){
        if(array_materias.size()<=0){
            txtAviso.setText(R.string.MsgMaterias);
            txtStats1.setText(" ");
            txtStats2.setText(" ");
        }
        else{
            txtAviso.setText(" ");
            txtStats1.setText("Nome da matéria:");
            txtStats2.setText("Média atual: ");
        }
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

}
