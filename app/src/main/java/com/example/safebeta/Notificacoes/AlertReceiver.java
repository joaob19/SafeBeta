package com.example.safebeta.Notificacoes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.safebeta.Agenda_de_eventos.Evento;
import com.example.safebeta.Agenda_de_eventos.EventoDAO;

import java.util.ArrayList;

    public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.gerarNotificacao(intent.getStringExtra("Titulo"),intent.getStringExtra("Descricao")).setAutoCancel(true);
        int tipoalarme = intent.getIntExtra("tipoAlarme",0);
        int id=0;
        if(tipoalarme==1){
            id = intent.getIntExtra("idAlarme1",-1);

        }
        else if(tipoalarme==2){
            id = intent.getIntExtra("idAlarme2",-1);

            EventoDAO eventoDAO = new EventoDAO(context);
            ArrayList<Evento> eventos = new ArrayList<Evento>(eventoDAO.obterTodos());
            for(int i=0;i<eventos.size();i++){
                if(eventos.get(i).getIdAlarme2()==id){
                    Evento evento = eventos.get(i);
                    eventoDAO.excluir(evento);
                }
            }

        }
        notificationHelper.getManager().notify(id, nb.build());


    }

//    public void excluirDoBanco(Context context,int id){
//        EventoDAO eventoDAO = new EventoDAO(context);
//        ArrayList<Evento> eventos = new ArrayList<Evento>(eventoDAO.obterTodos());
//        Evento evento = new Evento();
//        for(int i=0;i<eventos.size();i++){
//            if(eventos.get(i).getIdAlarme()==id){
//                evento=eventos.get(i);
//            }
//        }
//
//        eventoDAO.excluir(evento);
//    }

}