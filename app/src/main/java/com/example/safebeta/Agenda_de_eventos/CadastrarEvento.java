package com.example.safebeta.Agenda_de_eventos;

import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.widget.Toolbar;
import com.example.safebeta.Materias_e_notas.Materia;
import com.example.safebeta.Materias_e_notas.MateriaDAO;
import com.example.safebeta.Notificacoes.AlarmManagerUtil;
import com.example.safebeta.Pickers.DatePickerFragment;
import com.example.safebeta.Pickers.TimePickerFragment;
import com.example.safebeta.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CadastrarEvento<Caldendar> extends AppCompatActivity implements OnDateSetListener, OnTimeSetListener {
   ImageButton botaoEscolherData,botaoEscolherHora;
   FloatingActionButton btnSalvarEvento;
   EventoDAO eventoDAO;
   Spinner spinnerMateriaevento,spinnerTipoEvento;
   Toolbar toolbar;
   TextView txtDataEvento,txtHoraEvento;
   EditText txtTituloEvento,txtDescricaoEvento,txtNotificarAntes;
   Calendar calendarAlarme1 = Calendar.getInstance();
   Calendar calendarAlarme2 = Calendar.getInstance();
   RadioGroup rgAgenda;
   int tipoNotificacao=0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_evento);
        toolbar = (Toolbar)findViewById(R.id.toolbarAg2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Crie um evento");
        txtTituloEvento = (EditText)findViewById(R.id.txtTituloEvento);
        txtDataEvento = (TextView)findViewById(R.id.txtDataEvento);
        txtHoraEvento = (TextView)findViewById(R.id.txtHoraEvento);
        txtDescricaoEvento = (EditText)findViewById(R.id.txtDescricaoEvento);
        txtNotificarAntes = (EditText)findViewById(R.id.txtNotificarAntes);

        rgAgenda = (RadioGroup)findViewById(R.id.radioGroupAgenda);
        rgAgenda.check(R.id.rbAgenda1);
        rgAgenda.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbAgenda1:
                        tipoNotificacao=0;
                        txtNotificarAntes.setHint("Antecedência da notificação( Valores de 1 à 59)");
                        break;
                    case R.id.rbAgenda2:
                        tipoNotificacao=1;
                        txtNotificarAntes.setHint("Antecedência da notificação( Valores de 1 à 23)");
                        break;
                    case R.id.rbAgenda3:
                        tipoNotificacao=2;
                        txtNotificarAntes.setHint("Antecedência da notificação( Valores de 1 à 7)");
                        break;
                }
            }
        });


        eventoDAO = new EventoDAO(this);
        spinnerTipoEvento = (Spinner)findViewById(R.id.spinnerTipoEvento);
        ArrayAdapter adapter_tipo_evento = ArrayAdapter.createFromResource(this, R.array.tipos_de_eventos_array, android.R.layout.simple_spinner_item);
        adapter_tipo_evento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoEvento.setAdapter(adapter_tipo_evento);

        spinnerMateriaevento = (Spinner)findViewById(R.id.spinnerMateriaevento);
        popularSpinnerMaterias();
        final int idEvento = getIntent().getIntExtra("editarEvento", -1);
        if (idEvento != -1) {
            carregarEvento(idEvento);
        }

        btnSalvarEvento = (FloatingActionButton)findViewById(R.id.btnSalvarEvento);
        btnSalvarEvento.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View view) {
                String titulo = txtTituloEvento.getText().toString();
                String data = txtDataEvento.getText().toString();
                String hora = txtHoraEvento.getText().toString();
                String tipo = spinnerTipoEvento.getSelectedItem().toString();
                String materia = spinnerMateriaevento.getSelectedItem().toString();
                String descricao=txtDescricaoEvento.getText().toString();

                if (data.length() > 0 && hora.length()>0 && titulo.length()>0 && tipo.length()>0 && materia.equalsIgnoreCase("Matéria")==false && tipo.equalsIgnoreCase("Tipo")==false && txtNotificarAntes.getText().toString().length()>0) {
                    int avisarAntes = Integer.parseInt(txtNotificarAntes.getText().toString());
                    if((tipoNotificacao==0 && avisarAntes>0 && avisarAntes<=59) || (tipoNotificacao==1 && avisarAntes>0 && avisarAntes<=23) || (tipoNotificacao==2 && avisarAntes>0 && avisarAntes<=7)){
                        AlarmManagerUtil alarmManagerUtil = new AlarmManagerUtil(CadastrarEvento.this);
                        Evento evento = new Evento();
                        evento.setTituloEvento(titulo);
                        evento.setDataEvento(data);
                        evento.setHorarioevento(hora);
                        evento.setTipoEvento(tipo);
                        evento.setMateriaEvento(materia);
                        evento.setDescricao(descricao);
                        evento.setIdAlarme1( (int) calendarAlarme1.getTimeInMillis());

                        switch (tipoNotificacao){
                            case 0:
                                calendarAlarme2.add(Calendar.MINUTE,-avisarAntes);
                                break;
                            case 1:
                                calendarAlarme2.add(Calendar.HOUR_OF_DAY,-avisarAntes);

                                break;
                            case 2:
                                calendarAlarme2.add(Calendar.DAY_OF_MONTH,-avisarAntes);
                                break;
                        }

                        evento.setIdAlarme2((int) calendarAlarme2.getTimeInMillis());


                        if (idEvento != -1) {
                            eventoDAO.atualizar(evento);
                            int idAlarme1 = getIntent().getIntExtra("idAlarme1",0);
                            int idAlarme2 = getIntent().getIntExtra("idAlarme2",0);

                            alarmManagerUtil.cancelarAlarme(idAlarme1,evento.getTituloEvento(),(evento.getDataEvento()+" às "+evento.getHorarioevento()),1);
                            alarmManagerUtil.salvarAlarme(calendarAlarme1,evento.getIdAlarme1(),evento.getTituloEvento(),(evento.getDataEvento()+" às "+evento.getHorarioevento()),1);

                            alarmManagerUtil.cancelarAlarme(idAlarme2,evento.getTituloEvento(),(evento.getDataEvento()+" às "+evento.getHorarioevento()),2);
                            alarmManagerUtil.salvarAlarme(calendarAlarme2,evento.getIdAlarme2(),evento.getTituloEvento(),(evento.getDataEvento()+" às "+evento.getHorarioevento()),2);
                        } else {
                            eventoDAO.inserirEvento(evento);
                            alarmManagerUtil.salvarAlarme(calendarAlarme1,evento.getIdAlarme1(),evento.getTituloEvento(),(evento.getDataEvento()+" às "+evento.getHorarioevento()),1);
                            alarmManagerUtil.salvarAlarme(calendarAlarme2,evento.getIdAlarme2(),evento.getTituloEvento(),(evento.getDataEvento()+" às "+evento.getHorarioevento()),2);
                        }
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Tempo de aviso de antecedência inválido. Por favor utilize valores corretos para continuar.", 0).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Você deve preencher todos os campos para salvar o evento", 0).show();
                }
            }
        });

        botaoEscolherData = (ImageButton)findViewById(R.id.buttonEscolherData);
        botaoEscolherData.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View view) {
                (new DatePickerFragment()).show(getSupportFragmentManager(), "date picker");
            }
        });
        botaoEscolherHora = (ImageButton)findViewById(R.id.buttonEscolherHora);
        botaoEscolherHora.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View view) {
                (new TimePickerFragment()).show(getSupportFragmentManager(), "time picker");
            }
        });
    }

   public void onBackPressed() {
      String nome = txtTituloEvento.getText().toString();
      String data = txtDataEvento.getText().toString();
      String hora = txtHoraEvento.getText().toString();
      String tipo = spinnerTipoEvento.getSelectedItem().toString();
      String materia = spinnerMateriaevento.getSelectedItem().toString();
      if (data.length() <= 0 && hora.length() <= 0 && nome.length() <= 0 && materia == "Matéria" && tipo.equalsIgnoreCase("tipo")) {
         super.onBackPressed();
      } else {
         Builder builder = new Builder(this);
         builder.setTitle("Descartar alterações");
         builder.setMessage("Deseja descartar as alterações?");
         builder.setNegativeButton("CANCELAR", (OnClickListener)null);
         builder.setPositiveButton("DESCARTAR", new OnClickListener() {
            public void onClick(DialogInterface var1, int var2) {
               finish();
            }
         });
         builder.show();
      }
   }

   public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
      calendarAlarme1.set(Calendar.YEAR, year);
      calendarAlarme1.set(Calendar.MONTH, month);
      calendarAlarme1.set(Calendar.DAY_OF_MONTH, dayOfMonth);

      calendarAlarme2.set(Calendar.YEAR, year);
      calendarAlarme2.set(Calendar.MONTH, month);
      calendarAlarme2.set(Calendar.DAY_OF_MONTH, dayOfMonth);

      String data = DateFormat.getDateInstance(2).format(calendarAlarme1.getTime());
      txtDataEvento.setText(data);
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

   public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
      calendarAlarme1.set(Calendar.HOUR_OF_DAY, hourOfDay);
      calendarAlarme1.set(Calendar.MINUTE, minute);
      calendarAlarme1.set(Calendar.SECOND, 0);

      calendarAlarme2.set(Calendar.HOUR_OF_DAY, hourOfDay);
      calendarAlarme2.set(Calendar.MINUTE, minute);
      calendarAlarme2.set(Calendar.SECOND, 0);

      txtHoraEvento.setText(hourOfDay+":"+minute);
   }

   public void popularSpinnerMaterias() {
      ArrayList<String> array_materias = new ArrayList();
      array_materias.add("Matéria");
      MateriaDAO materiaDAO = new MateriaDAO(this);
      ArrayList<Materia> lista_materias = null;
      if (materiaDAO.obterTodas() != null) {
         lista_materias = new ArrayList<Materia>(materiaDAO.obterTodas());
         array_materias.add("Todas");
         for(int i = 0; i < lista_materias.size(); ++i) {
            array_materias.add(lista_materias.get(i).getNome());
         }
      }

      ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array_materias);
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      spinnerMateriaevento.setAdapter(adapter);
   }

    public void carregarEvento(int idEvento) {
        if (idEvento != -1) {
            Evento evento = MeusEventos.eventos.get(idEvento);
            txtTituloEvento.setText(evento.getTituloEvento());
            txtHoraEvento.setText(evento.getHorarioevento());
            txtDataEvento.setText(evento.getDataEvento());
            if(evento.getDescricao()!=null){
                txtDescricaoEvento.setText(evento.getDescricao());
            }
            String tipoEvento = evento.getTipoEvento();

            switch (tipoEvento){
                case "Prova":
                    spinnerTipoEvento.setSelection(1);
                    break;
                case "Trabalho":
                    spinnerTipoEvento.setSelection(2);
                    break;
                case "seminário":
                    spinnerTipoEvento.setSelection(3);
                    break;
            }

            ArrayList<String> array_materias = new ArrayList();
            array_materias.add("Matéria");
            MateriaDAO materiaDAO = new MateriaDAO(this);
            ArrayList<Materia> lista_materias = null;
            if (materiaDAO.obterTodas()!=null) {
                lista_materias = new ArrayList<Materia>(materiaDAO.obterTodas());
                array_materias.add("Todas");
                for(int i=0;i<lista_materias.size();i++){
                    array_materias.add(lista_materias.get(i).getNome());
                }
            }

            for(int i=0; i < array_materias.size(); ++i) {
                if (array_materias.get(i).equalsIgnoreCase(evento.getMateriaEvento())) {
                    spinnerMateriaevento.setSelection(i);
                }
            }
        }
    }

}