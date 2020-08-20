package com.example.safebeta.Horarios;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.safebeta.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class Horarios extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DialogCriarHorario.DialogCriarHorarioListener {

    Toolbar toolbar;
    Spinner spinnerDiasDaSemana;
    ListView listaHorarios;
    FloatingActionButton btnAdicionaraula;
    ArrayList<Horario> horario_segunda = new ArrayList<Horario>();
    ArrayList<Horario> horario_terca = new ArrayList<Horario>();
    ArrayList<Horario> horario_quarta = new ArrayList<Horario>();
    ArrayList<Horario> horario_quinta = new ArrayList<Horario>();
    ArrayList<Horario> horario_sexta = new ArrayList<Horario>();

    static ArrayAdapter<Horario> adapter_lista_horarios = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horarios);

        toolbar = findViewById(R.id.toolbarHorarios);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Horários de aula");

        spinnerDiasDaSemana=(Spinner)findViewById(R.id.spinnerDiasDaSemana);
        listaHorarios=(ListView)findViewById(R.id.listaHorarios);
        ArrayAdapter<CharSequence> adapter_spinner = ArrayAdapter.createFromResource(this,R.array.horarios_array, android.R.layout.simple_spinner_item);
        adapter_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDiasDaSemana.setAdapter(adapter_spinner);
        spinnerDiasDaSemana.setOnItemSelectedListener(this);

        carregarHorarios();

        btnAdicionaraula = (FloatingActionButton)findViewById(R.id.btnAdicionaraula);
        btnAdicionaraula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dia=spinnerDiasDaSemana.getSelectedItem().toString();
                if(dia.equalsIgnoreCase("Selecione o dia")) {
                    Toast.makeText(getApplicationContext(),"Você deve selecionar um dia para adicionar uma aula", Toast.LENGTH_SHORT).show();
                }
                else{
                    abrirDialogCriarHorario();
                }
            }
        });

        listaHorarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder opcoes = new AlertDialog.Builder(Horarios.this);
                opcoes.setTitle("O que deseja fazer?")
                        .setItems(R.array.opcoes_array, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // O "which" contem a posição do indice do item selecionado
                                switch (which){
                                    case 0:
                                        AlertDialog.Builder adb= new AlertDialog.Builder(Horarios.this);
                                        adb.setTitle("Excluir");
                                        adb.setMessage("Deseja excluir esse horário?");
                                        final int positionToMove = position;
                                        adb.setNegativeButton("NÂO",null);
                                        adb.setPositiveButton("SIM",new AlertDialog.OnClickListener(){
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                excluirHorario(position);
                                            }
                                        });
                                        adb.show();
                                        break;
                                    case 1:
                                        modificarHorario(position);
                                        break;
                                }

                            }
                        });
                opcoes.show();
            }
        });

//Verifica em que dia da semana está e mostra os horários desse dia
        verificarODia();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String dia=spinnerDiasDaSemana.getSelectedItem().toString();
        switch(dia){
            case "Selecione o dia":
                listaHorarios.setAdapter(null);
                break;
            case "Segunda-feira":
                adapter_lista_horarios = new HorarioAdapter(this,horario_segunda);
                listaHorarios.setAdapter(adapter_lista_horarios);
                break;
            case "Terça-feira":
                adapter_lista_horarios = new HorarioAdapter(this,horario_terca);
                listaHorarios.setAdapter(adapter_lista_horarios);
                break;
            case "Quarta-feira":
                adapter_lista_horarios = new HorarioAdapter(this,horario_quarta);
                listaHorarios.setAdapter(adapter_lista_horarios);
                break;
            case "Quinta-feira":
                adapter_lista_horarios = new HorarioAdapter(this,horario_quinta);
                listaHorarios.setAdapter(adapter_lista_horarios);
                break;
            case "Sexta-feira":
                adapter_lista_horarios = new HorarioAdapter(this,horario_sexta);
                listaHorarios.setAdapter(adapter_lista_horarios);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void excluirHorario(final int position){
        String dia=spinnerDiasDaSemana.getSelectedItem().toString();
        HorarioDAO horarioDAO = new HorarioDAO(this);
        switch(dia){
            case "Segunda-feira":
                horarioDAO.excluir(horario_segunda.get(position));
                horario_segunda.remove(position);
                adapter_lista_horarios.notifyDataSetInvalidated();
                break;
            case "Terça-feira":
                horarioDAO.excluir(horario_terca.get(position));
                horario_terca.remove(position);
                adapter_lista_horarios.notifyDataSetInvalidated();
                break;
            case "Quarta-feira":
                horarioDAO.excluir(horario_quarta.get(position));
                horario_quarta.remove(position);
                adapter_lista_horarios.notifyDataSetInvalidated();
                break;
            case "Quinta-feira":
                horarioDAO.excluir(horario_quinta.get(position));
                horario_quinta.remove(position);
                adapter_lista_horarios.notifyDataSetInvalidated();
                break;
            case "Sexta-feira":
                horarioDAO.excluir(horario_sexta.get(position));
                horario_sexta.remove(position);
                adapter_lista_horarios.notifyDataSetInvalidated();
                break;
        }
    }

    public void modificarHorario(final int position){
        String dia=spinnerDiasDaSemana.getSelectedItem().toString();
        Horario horario = null;
        switch(dia){
            case "Segunda-feira":
                horario=horario_segunda.get(position);
                break;
            case "Terça-feira":
                horario=horario_terca.get(position);
                break;
            case "Quarta-feira":
                horario=horario_quarta.get(position);
                break;
            case "Quinta-feira":
                horario=horario_quinta.get(position);
            case "Sexta-feira":
                horario=horario_sexta.get(position);
                break;
        }
        DialogCriarHorario dialog = new DialogCriarHorario(horario);
        dialog.show(getSupportFragmentManager(),"Editar horário");
    }


    public void abrirDialogCriarHorario(){
        DialogCriarHorario dialog = new DialogCriarHorario(null);
        dialog.show(getSupportFragmentManager(),"Criar horário");
    }

    @Override
    public void salvarHorario(Horario horario) {
        String dia=spinnerDiasDaSemana.getSelectedItem().toString();
        if(horario!=null){
            switch(dia) {
                case "Segunda-feira":
                    horario.setDia(1);
                    horario_segunda.add(horario);
                    adapter_lista_horarios.notifyDataSetChanged();
                    break;
                case "Terça-feira":
                    horario.setDia(2);
                    horario_terca.add(horario);
                    adapter_lista_horarios.notifyDataSetChanged();
                    break;
                case "Quarta-feira":
                    horario.setDia(3);
                    horario_quarta.add(horario);
                    adapter_lista_horarios.notifyDataSetChanged();
                    break;
                case "Quinta-feira":
                    horario.setDia(4);
                    horario_quinta.add(horario);
                    adapter_lista_horarios.notifyDataSetChanged();
                    break;
                case "Sexta-feira":
                    horario.setDia(5);
                    horario_sexta.add(horario);
                    adapter_lista_horarios.notifyDataSetChanged();
                    break;
            }
            HorarioDAO horarioDAO = new HorarioDAO(this);
            horarioDAO.inserirHorario(horario);
        }
    }

    //Verifica em que dia da semana está e mostra os horários desse dia
    public void verificarODia(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                spinnerDiasDaSemana.setSelection(0);
                break;
            case Calendar.MONDAY:
                spinnerDiasDaSemana.setSelection(1);
                break;
            case Calendar.TUESDAY:
                spinnerDiasDaSemana.setSelection(2);
                break;
            case Calendar.WEDNESDAY:
                spinnerDiasDaSemana.setSelection(3);
                break;
            case Calendar.THURSDAY:
                spinnerDiasDaSemana.setSelection(4);
                break;
            case Calendar.FRIDAY:
                spinnerDiasDaSemana.setSelection(5);
                break;
            case Calendar.SATURDAY:
                spinnerDiasDaSemana.setSelection(0);
                break;
        }
    }

    public void carregarHorarios(){
        HorarioDAO horarioDAO = new HorarioDAO(this);
        ArrayList<Horario> horarios = new ArrayList<Horario>(horarioDAO.obterTodos());
        for(int i=0;i<horarios.size();i++){
            switch(horarios.get(i).getDia()) {
                case 1:
                    horario_segunda.add(horarios.get(i));
                    break;
                case 2:
                    horario_terca.add(horarios.get(i));
                    break;
                case 3:
                    horario_quarta.add(horarios.get(i));
                    break;
                case 4:
                    horario_quinta.add(horarios.get(i));
                    break;
                case 5:
                    horario_sexta.add(horarios.get(i));
                    break;
            }
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
