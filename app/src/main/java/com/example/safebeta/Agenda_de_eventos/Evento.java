package com.example.safebeta.Agenda_de_eventos;

import java.io.Serializable;

public class Evento implements Serializable {
   private String dataEvento;
   private String horarioevento;
   private int id, idAlarme1,idAlarme2;
   private String materiaEvento;
   private String tipoEvento;
   private String tituloEvento;
   private String descricao;

   public int getIdAlarme2() {
      return idAlarme2;
   }

   public void setIdAlarme2(int idAlarme2) {
      this.idAlarme2 = idAlarme2;
   }

   public int getIdAlarme1() {
      return idAlarme1;
   }

   public void setIdAlarme1(int idAlarme1) {
      this.idAlarme1 = idAlarme1;
   }

   public String getDescricao() {
      return descricao;
   }

   public void setDescricao(String descricao) {
      this.descricao = descricao;
   }


   public String getDataEvento() {
      return this.dataEvento;
   }

   public String getHorarioevento() {
      return this.horarioevento;
   }

   public int getId() {
      return this.id;
   }

   public String getMateriaEvento() {
      return this.materiaEvento;
   }

   public String getTipoEvento() {
      return this.tipoEvento;
   }

   public String getTituloEvento() {
      return this.tituloEvento;
   }

   public void setDataEvento(String var1) {
      this.dataEvento = var1;
   }

   public void setHorarioevento(String var1) {
      this.horarioevento = var1;
   }

   public void setId(int var1) {
      this.id = var1;
   }

   public void setMateriaEvento(String var1) {
      this.materiaEvento = var1;
   }

   public void setTipoEvento(String var1) {
      this.tipoEvento = var1;
   }

   public void setTituloEvento(String var1) {
      this.tituloEvento = var1;
   }
}
