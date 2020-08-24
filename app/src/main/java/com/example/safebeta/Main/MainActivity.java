package com.example.safebeta.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.safebeta.Agenda_de_eventos.MeusEventos;
import com.example.safebeta.Bloco_de_notas.TelaAnotacoes;
import com.example.safebeta.Ferramentas_de_calculo.InicioCalculadora;
import com.example.safebeta.GoogleTradutor.GoogleTradutor;
import com.example.safebeta.Horarios.Horarios;
import com.example.safebeta.Materias_e_notas.TelaMaterias;
import com.example.safebeta.R;
import com.example.safebeta.Tutorial.MainTutorial;

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
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.safebeta", Context.MODE_PRIVATE);
        boolean primeiroUso = sharedPreferences.getBoolean("primeiroUso",true);

        if(primeiroUso==true){
            Intent ger_conta = new Intent(MainActivity.this, MainTutorial.class);
            startActivity(ger_conta);
        }
    }

}
