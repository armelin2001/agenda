 package com.example.agenda;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.agenda.model.ExercicioAgenda;
import com.example.agenda.model.User;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

 public class MainActivity extends AppCompatActivity {
    EditText editTextName;
    EditText editTextPassword;
    User user;
    JSONObject jsonObject = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextName = findViewById(R.id.editTextName);
        editTextPassword = findViewById(R.id.editTextPassword);
        try {
            user = loadUsersList();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void buttonSingIn(View view){
        if(TextUtils.isEmpty(editTextName.getText().toString())||TextUtils.isEmpty(editTextPassword.getText().toString())){
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    MainActivity.this);
            builder.setTitle(android.R.string.dialog_alert_title);
            builder.setMessage(R.string.alert_error_campo_vazio);
            builder.setPositiveButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            editTextName.setText("");
                            editTextPassword.setText("");
                        }
                    });
            builder.create().show();
        }
        else {
            this.user = new User(editTextName.getText().toString(),editTextPassword.getText().toString());
            saveUserToFile();
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    MainActivity.this);
            builder.setTitle(android.R.string.dialog_alert_title);
            builder.setMessage(R.string.alert_sucesso_criacao_usuario);
            builder.setPositiveButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            editTextName.setText("");
                            editTextPassword.setText("");
                        }
                    });
            builder.create().show();
        }
    }
    public void buttonLogIn(View view){
        if(TextUtils.isEmpty(editTextName.getText().toString())||TextUtils.isEmpty(editTextPassword.getText().toString())){
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    MainActivity.this);
            builder.setTitle(android.R.string.dialog_alert_title);
            builder.setMessage(R.string.alert_error_campo_vazio);
            builder.setPositiveButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            editTextName.setText("");
                            editTextPassword.setText("");
                        }
                    });
            builder.create().show();
        }
        if(user.getName().equals(editTextName.getText().toString())&&user.getPassword().equals(editTextPassword.getText().toString())){
            Intent intent = new Intent(
                MainActivity.this,ListaAgenda.class);
            intent.putExtra("data",user);
            startActivity(intent);
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    MainActivity.this);
            builder.setTitle(android.R.string.dialog_alert_title);
            builder.setMessage(R.string.alert_erro_campo_errado);
            builder.setPositiveButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            editTextName.setText("");
                            editTextPassword.setText("");
                        }
                    });
            builder.create().show();
        }
    }
    User loadUsersList() throws IOException, JSONException {
        InputStream stream = openFileInput("agenda.json");
        InputStreamReader streamReader = new InputStreamReader(stream);
        BufferedReader reader = new BufferedReader(streamReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null){
            stringBuilder  = stringBuilder.append(line);
        }
        reader.close();
        streamReader.close();
        stream.close();
        jsonObject = new JSONObject(stringBuilder.toString());
        User userTemp= new User();
        userTemp.setName(jsonObject.getString("name"));
        userTemp.setPassword(jsonObject.getString("password"));
        JSONArray arrayJsonAgenda = jsonObject.getJSONArray("agenda");
        for(int x=0;x<arrayJsonAgenda.length();x++){
            JSONObject obj = (JSONObject) arrayJsonAgenda.get(x);
            userTemp.getAgenda().add(new ExercicioAgenda(
                obj.getString("name"),
                obj.getString("adredss"),
                obj.getString("phone"),
                    obj.getString("type")
            ));
        }
        reader.close();
        return userTemp;
    }
    void saveUserToFile(){
        try{
            OutputStream stream = MainActivity.this.openFileOutput(
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