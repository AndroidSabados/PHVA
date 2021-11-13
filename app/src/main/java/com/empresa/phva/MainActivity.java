package com.empresa.phva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.view.View;

public class MainActivity extends AppCompatActivity {



import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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


}}

        Button btnDocument = findViewById(R.id.btnDocument);
        EditText etUser = findViewById(R.id.etUser);


        btnDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = etUser.getText().toString();
                if(user.length()>0) {
                    showDocument(user);
                }else{
                    Toast.makeText(getBaseContext(), "no es lenght>0", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnListDocuments = findViewById(R.id.btnListDocuments);
        btnListDocuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showListDocuments(user);
            }
        });




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

    public void showListDocuments(String user) {
        Toast.makeText(this, "Aca OK: "+user, Toast.LENGTH_SHORT).show();

        intent = new Intent(this, ListDocumentsActivity.class);
        intent.putExtra("userId", user);
        startActivity(intent);
    }


}
