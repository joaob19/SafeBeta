package com.example.safebeta.Tutorial;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.safebeta.R;


public class Tutorial2 extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tutorial2, container, false);
        Button btnTutorial3 = (Button)view.findViewById(R.id.btnTutorial3);
        btnTutorial3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("com.example.safebeta", Context.MODE_PRIVATE);
                sharedPreferences.edit().putBoolean("primeiroUso",false).apply();
                getActivity().finish();

            }
        });
        return view;
    }


}
