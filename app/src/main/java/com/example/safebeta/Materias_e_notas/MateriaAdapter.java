package com.example.safebeta.Materias_e_notas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.safebeta.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MateriaAdapter extends ArrayAdapter {
   private final Context context;
   private final ArrayList<Materia> materias;
   private TextView txtTitulo,txtQntdNotas,txtMediaAtual;

   public MateriaAdapter(Context context, ArrayList materias) {
      super(context, R.layout.layout_materia, materias);
      this.context = context;
      this.materias = materias;
   }

   public View getView(int position, View convertView, ViewGroup parent) {
      LayoutInflater inflater = (LayoutInflater) context
              .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      View rowView = inflater.inflate(R.layout.layout_materia, parent, false);
      txtTitulo = (TextView) rowView.findViewById(R.id.txtMateria1);
      txtQntdNotas = (TextView) rowView.findViewById(R.id.txtMateria2);
      txtMediaAtual = (TextView) rowView.findViewById(R.id.txtMateria3);

      Materia materia = materias.get(position);
      txtTitulo.setText(materia.getNome());
      txtQntdNotas.setText("Qntd de notas: "+obterTotalNotasCadastradas(materia)+"/"+materia.getQntdNotas());
      DecimalFormat df = new DecimalFormat("0.00");
      txtMediaAtual.setText("Média atual: "+df.format(materia.getMedia()));

      return rowView;
   }


   public int obterTotalNotasCadastradas(Materia materia) {
      Gson gson = new Gson();
      String json = materia.getNotas();
      Type type = new TypeToken<ArrayList>() {}.getType();
      int notasCadastradas=0;
      ArrayList<Double> arrayList = gson.fromJson(json, type);
      if (arrayList != null) {
         notasCadastradas=arrayList.size();
      }
      return notasCadastradas;
   }

   @Override
   public void notifyDataSetChanged() {
      super.notifyDataSetChanged();
   }
}
