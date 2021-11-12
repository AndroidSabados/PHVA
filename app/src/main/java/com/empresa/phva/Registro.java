package com.empresa.phva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class Registro extends AppCompatActivity {

    Spinner spinnerRoles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        spinnerRoles = (Spinner) findViewById(R.id.idSpinnerRoles);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.select_roles, android.R.layout.simple_spinner_item);
        spinnerRoles.setAdapter(adapter);



        }
    public void modulos (View view){
        Intent modulos = new Intent(this, AccesoModulos.class);
        startActivity(modulos);
    }
}