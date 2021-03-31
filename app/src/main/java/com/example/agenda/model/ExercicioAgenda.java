package com.example.agenda.model;


import org.json.simple.JSONObject;

import java.io.Serializable;

public class ExercicioAgenda implements Serializable {
    private String name;
    private String adredss;
    private String phone;
    private String type;
    JSONObject exercicioAgendaJson;
    public void criarJson(String name, String adredss, String phone, String type){
        exercicioAgendaJson.put("name",name);
        exercicioAgendaJson.put("adredss",adredss);
        exercicioAgendaJson.put("phone",phone);
        exercicioAgendaJson.put("type",type);
    }
    public ExercicioAgenda(){

    }

    public ExercicioAgenda(String name, String adredss, String phone, String type) {
        this.exercicioAgendaJson = new JSONObject();
        this.name = name;
        this.adredss = adredss;
        this.phone = phone;
        this.type = type;
        criarJson(name,adredss,phone,type);
    }

    public JSONObject getExercicioAgendaJson() {
        return exercicioAgendaJson;
    }

    public void setExercicioAgendaJson(JSONObject exercicioAgendaJson) {
        this.exercicioAgendaJson = exercicioAgendaJson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdredss() {
        return adredss;
    }

    public void setAdredss(String adredss) {
        this.adredss = adredss;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Atividade " + name;
    }
}
