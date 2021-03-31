package com.example.agenda.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable{
    private String name;
    private String password;
    private JSONObject userJson;
    private JSONArray agendaJson;
    private ArrayList<ExercicioAgenda> agenda;

    public void criaUserJson(String name, String password){
        getUserJson().put("name",name);
        getUserJson().put("password",password);
        getUserJson().put("agenda",getAgendaJson());
    }
    public User() {
    }
    public void adicionarTarefa(ExercicioAgenda exercicioAgenda){
        agenda.add(exercicioAgenda);
    }
    public User(String name, String password){
        this.userJson = new JSONObject();
        this.agendaJson = new JSONArray();
        this.agenda = new ArrayList<>();
        this.name = name;
        this.password = password;
        criaUserJson(name,password);
    }

    public JSONArray getAgendaJson() {
        return agendaJson;
    }

    public void setAgendaJson(JSONArray agendaJson) {
        this.agendaJson = agendaJson;
    }

    public List<ExercicioAgenda> getAgenda() {
        return agenda;
    }

    public void setAgenda(ArrayList<ExercicioAgenda> agenda) {
        this.agenda = agenda;
    }

    public JSONObject getUserJson() {
        return userJson;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
