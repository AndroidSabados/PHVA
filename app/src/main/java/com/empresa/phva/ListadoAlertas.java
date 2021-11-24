package com.empresa.phva;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class ListadoAlertas extends AppCompatActivity {

    ListView listaEstados;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_alertas);


        listaEstados = (ListView) findViewById(R.id.listaEstados);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.DatosList, android.R.layout.simple_list_item_1);

        listaEstados.setAdapter(adapter);
    }
}