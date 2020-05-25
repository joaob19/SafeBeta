package com.example.safe_v02.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.safe_v02.Agenda_de_eventos.MeusEventos;
import com.example.safe_v02.Bloco_de_notas.TelaAnotacoes;
import com.example.safe_v02.Ferramentas_de_calculo.InicioCalculadora;
import com.example.safe_v02.GoogleTradutor.GoogleTradutor;
import com.example.safe_v02.Horarios.Horarios;
import com.example.safe_v02.Materias_e_notas.TelaMaterias;
import com.example.safe_v02.Gerenciar_Conta.GerenciarConta;
import com.example.safe_v02.R;
import com.example.safe_v02.Tutorial.MainTutorial;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verificaPrimeiroUso();

    }

   public void openActivity(View view){
        Intent intent = null;

        switch (view.getId()){
            case R.id.card_agenda:
                intent = new Intent(MainActivity.this,MeusEventos.class);
                startActivity(intent);
                break;
            case R.id.card_anotacoes:
                intent = new Intent(MainActivity.this,TelaAnotacoes.class);
                startActivity(intent);
                break;
            case R.id.card_ferramentas_de_calculo:
                intent = new Intent(MainActivity.this,InicioCalculadora.class);
                startActivity(intent);
                break;
            case R.id.card_google_tradutor:
                intent = new Intent(MainActivity.this, GoogleTradutor.class);
                startActivity(intent);
                break;
            case R.id.card_horarios:
                intent = new Intent(MainActivity.this, Horarios.class);
                startActivity(intent);
                break;
            case R.id.card_materias:
                intent = new Intent(MainActivity.this,TelaMaterias.class);
                startActivity(intent);
                break;
            case R.id.card_conta:
                intent = new Intent(MainActivity.this,GerenciarConta.class);
                startActivity(intent);
                break;
        }
   }

   public void showHelpDialog(View view){
       AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this)
               .setTitle("Info e suporte:")
               .setMessage(R.string.Info_suporte)
               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                   }
               });
       adb.show();
   }

    //Verifica se é o primeirouso do app e mostra uma mensagem para o usuario cadastrar suas informações
    public void verificaPrimeiroUso(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.safe_v02", Context.MODE_PRIVATE);
        boolean primeiroUso = sharedPreferences.getBoolean("primeiroUso",true);

        if(primeiroUso==true){
            Intent ger_conta = new Intent(MainActivity.this, MainTutorial.class);
            startActivity(ger_conta);
        }
    }

}
