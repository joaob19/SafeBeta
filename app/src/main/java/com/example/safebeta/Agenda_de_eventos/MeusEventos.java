package com.example.safebeta.Agenda_de_eventos;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.widget.Toolbar;

import com.example.safebeta.Notificacoes.AlarmManagerUtil;
import com.example.safebeta.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MeusEventos extends AppCompatActivity {
   static ArrayAdapter adapter = null;
   static ArrayList<Evento> eventos = new ArrayList<Evento>();
   ListView ListaDeEventos;
   FloatingActionButton btnCriarEvento;
   EventoDAO eventoDAO;
   Toolbar toolbar;
   TextView txtAviso;

   protected void onCreate(Bundle bundle) {
      super.onCreate(bundle);
      setContentView(R.layout.activity_meus_eventos);
      toolbar = (Toolbar)findViewById(R.id.toolbarAg1);
      setSupportActionBar(toolbar);
      getSupportActionBar().setTitle("Meus eventos");
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      ListaDeEventos = (ListView)findViewById(R.id.ListaDeEventos);
      eventoDAO = new EventoDAO(this);
      carregarEventos();

      txtAviso = (TextView)findViewById(R.id.txtAvisoEventos);
      verificarEventtos();

      btnCriarEvento = (FloatingActionButton)findViewById(R.id.btnCriarEvento);
      btnCriarEvento.setOnClickListener(new OnClickListener() {
         public void onClick(View view) {
            Intent intent = new Intent(MeusEventos.this, CadastrarEvento.class);
            startActivity(intent);
         }
      });
      ListaDeEventos.setOnItemLongClickListener(new OnItemLongClickListener() {
         public boolean onItemLongClick(AdapterView parent, View view, final int position, long id) {
            Builder builder = new Builder(MeusEventos.this);
            builder.setTitle("Excluir evento");
            builder.setMessage("Deseja excluir esse evento?");
            builder.setNegativeButton("Não", (android.content.DialogInterface.OnClickListener)null);
            builder.setPositiveButton("Sim", new android.content.DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int which) {
                   int idAlarme1 = eventos.get(position).getIdAlarme1();
                   int idAlarme2 = eventos.get(position).getIdAlarme2();
                   String titulo=eventos.get(position).getTituloEvento();
                   String descricao=eventos.get(position).getDataEvento()+" "+eventos.get(position).getHorarioevento();
                   AlarmManagerUtil alarmManagerUtil = new AlarmManagerUtil(MeusEventos.this);
                   alarmManagerUtil.cancelarAlarme(idAlarme1,titulo,descricao,1);
                   alarmManagerUtil.cancelarAlarme(idAlarme2,titulo,descricao,2);
                   eventoDAO.excluir(eventos.get(position));
                   carregarEventos();
                   verificarEventtos();
               }
            });
            builder.show();
            return true;
         }
      });
      ListaDeEventos.setOnItemClickListener(new OnItemClickListener() {
         public void onItemClick(AdapterView parent, View view, int position, long id) {
            Intent intent = new Intent(MeusEventos.this, InfoEvento.class);
            intent.putExtra("idEvento", position);
            intent.putExtra("idAlarme1", eventos.get(position).getIdAlarme1());
            intent.putExtra("idAlarme2", eventos.get(position).getIdAlarme2());
            startActivity(intent);
         }
      });


   }

   public void verificarEventtos(){
      if(eventos.size()<=0){
         txtAviso.setText(R.string.MsgEventos);
      }
      else{
         txtAviso.setText(" ");
      }
   }

   @Override
   protected void onResume() {
      super.onResume();
      carregarEventos();
      verificarEventtos();
   }

   public void carregarEventos(){
      eventos = new ArrayList<Evento>(eventoDAO.obterTodos());
      adapter = new EventoAdapter(this, eventos);
      ListaDeEventos.setAdapter(adapter);
   }
}
