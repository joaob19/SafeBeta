package com.example.safebeta.TelaDeAbertura;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.safebeta.Main.MainActivity;
import com.example.safebeta.R;

public class StartActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_start);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                //Mostra a tela de abertura até o tempo estipulado acabar e em segudida,
                //abre a tela inicial do app
                Intent StartIntent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(StartIntent);
                finish();
            }
        },SPLASH_TIME_OUT);

    }
}
