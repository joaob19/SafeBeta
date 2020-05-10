package com.example.safe_v02.Notificacoes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.safe_v02.Agenda_de_eventos.Evento;
import com.example.safe_v02.Agenda_de_eventos.EventoDAO;

import java.util.ArrayList;
import java.util.Calendar;

public class AlarmManagerUtil {

    private Context context;

    public AlarmManagerUtil(Context context){
        this.context=context;
    }

    public void salvarAlarme(Calendar c,int idAlarme, String titulo, String descricao,int tipoAlarme) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);
        intent.putExtra("Titulo",titulo);
        intent.putExtra("Descricao",descricao);
        if(tipoAlarme==1){
            intent.putExtra("idAlarme1",idAlarme);
        }
        else{
            intent.putExtra("idAlarme2",idAlarme);
        }
        intent.putExtra("tipoAlarme",tipoAlarme);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, idAlarme, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    public void cancelarAlarme(int idAlarme,String titulo,String descricao,int tipoAlarme) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);
        intent.putExtra("Titulo",titulo);
        intent.putExtra("Descricao",descricao);
        if(tipoAlarme==1){
            intent.putExtra("idAlarme1",idAlarme);
        }
        else{
            intent.putExtra("idAlarme2",idAlarme);
        }
        intent.putExtra("tipoAlarme",tipoAlarme);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, idAlarme, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);

    }

    public void recuperarAlarmes(Context context){
        EventoDAO eventoDAO = new EventoDAO(context);
        ArrayList<Evento> eventos = new ArrayList<Evento>(eventoDAO.obterTodos());
        for(int i=0;i<eventos.size();i++){
            if(eventos.get(i).getIdAlarme1()!=0){
                Evento evento = eventos.get(i);
                recriarAlarme(evento.getIdAlarme2(),evento.getTituloEvento(),evento.getDataEvento()+" às "+evento.getHorarioevento(),2);
            }
        }
    }

    public void recriarAlarme(int idAlarme,String titulo,String descricao,int tipoAlarme){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);
        intent.putExtra("Titulo",titulo);
        intent.putExtra("Descricao",descricao);
        if(tipoAlarme==1){
            intent.putExtra("idAlarme1",idAlarme);
        }
        else{
            intent.putExtra("idAlarme2",idAlarme);
        }
        intent.putExtra("tipoAlarme",tipoAlarme);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, idAlarme, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, idAlarme, pendingIntent);
    }

}
