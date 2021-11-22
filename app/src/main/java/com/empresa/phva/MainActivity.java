package com.empresa.phva;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    public String user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);











        }

    public void pRegistro (View view){
        Intent registrarse = new Intent(this, Registro.class);
        startActivity(registrarse);
    }

    public void pListadoAlertas (View view){
        Intent listado = new Intent(this, ListadoAlertas.class);
        startActivity(listado);

    }



    public Intent intent;


    public void showDocument(String user) {
        Log.d("Aca", "xxxxxxxxxxxxxxx"+user);
        Toast.makeText(this, "Aca OK: "+user, Toast.LENGTH_SHORT).show();

        String userId = "texto ok" + user;
        intent = new Intent(this, DocumentActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }







}

