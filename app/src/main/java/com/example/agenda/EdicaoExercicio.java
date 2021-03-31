package com.example.agenda;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.agenda.model.ExercicioAgenda;
import com.example.agenda.model.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class EdicaoExercicio extends AppCompatActivity {
    EditText editTextExercicioName;
    EditText editTextExercicioAdress;
    EditText editTextPhone;
    Spinner spinnerType;
    User user;
    int indicie;
    int indicieType;
    ArrayAdapter<CharSequence> adapter;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicao_exercicio);
        editTextExercicioName = findViewById(R.id.editTextExercicioName);
        editTextExercicioAdress = findViewById(R.id.editTextExercicioAdress);
        editTextPhone = findViewById(R.id.editTextPhone);
        spinnerType = findViewById(R.id.spinnerType);
        adapter = ArrayAdapter.createFromResource(this,R.array.types,android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);
        Bundle extras = getIntent().getExtras();
        user = (User) extras.getSerializable("exercicio");
        indicie = extras.getInt("indicie");
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = spinnerType.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if(user.getAgenda().size()>0 && user.getAgenda().contains(indicie)){
            editTextExercicioName.setText(user.getAgenda().get(indicie).getName());
            editTextExercicioAdress.setText(user.getAgenda().get(indicie).getAdredss());
            editTextPhone.setText(user.getAgenda().get(indicie).getPhone());
            indicieType = adapter.getPosition(user.getAgenda().get(indicie).getType());
            spinnerType.getItemAtPosition(indicieType);
            user.getAgenda().remove(indicie);
        }

    }
    public void buttonAdd(View view){
        if(TextUtils.isEmpty(editTextExercicioName.getText().toString()) || TextUtils.isEmpty(editTextExercicioAdress.getText().toString())
        || TextUtils.isEmpty(editTextPhone.getText().toString())){
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    EdicaoExercicio.this);
            builder.setTitle(android.R.string.dialog_alert_title);
            builder.setMessage(R.string.campos_nao_prenchidos);
            builder.create().show();
        }else {
            ExercicioAgenda exercicioAgenda = new ExercicioAgenda(editTextExercicioName.getText().toString(),
                    editTextExercicioAdress.getText().toString(), editTextPhone.getText().toString(),type);
            user.getAgenda().add(exercicioAgenda);
            saveUserToFile();
            Intent intent = new Intent(EdicaoExercicio.this,
                    ListaAgenda.class);
            intent.putExtra("usuarioAlterado",user);
            startActivity(intent);
        }
    }
    void saveUserToFile(){
        try{
            OutputStream stream = EdicaoExercicio.this.openFileOutput(
                    "agenda.json",
                    MODE_PRIVATE
            );
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            writer.write(user.getUserJson().toJSONString());
            writer.flush();
            writer.close();
            stream.close();
        }catch (FileNotFoundException ex){
            ex.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
}