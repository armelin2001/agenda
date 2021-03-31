package com.example.agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.agenda.model.ExercicioAgenda;
import com.example.agenda.model.User;

import java.io.Serializable;
import java.util.ArrayList;

public class ListaAgenda extends AppCompatActivity {
    User user;
    ListView listView;
    ArrayList<ExercicioAgenda> listExercicios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_agenda);
        Bundle extras = getIntent().getExtras();
        if(extras.containsKey("usuarioAlterado")){
            user = (User) extras.getSerializable("usuarioAlterado");
        }
        else {
            user = (User) extras.getSerializable("data");
        }
        listExercicios = new ArrayList<>();
        listView = findViewById(R.id.listView);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setSelector(android.R.color.darker_gray);
        ArrayAdapter<ExercicioAgenda> adapter = new ArrayAdapter<ExercicioAgenda>(this, android.R.layout.simple_list_item_1);
        if(user.getAgenda()==null){
            user.setAgenda(listExercicios);
        }
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListaAgenda.this,EdicaoExercicio.class);
                intent.putExtra("exercicio",user);
                intent.putExtra("indicie",position);
                startActivity(intent);
            }
        });
    }
    public void addExercicio(View view){
        Intent intent = new Intent(
                ListaAgenda.this,EdicaoExercicio.class);
        intent.putExtra("exercicio", user);
        intent.putExtra("indicie",-1);
        startActivity(intent);
    }
}