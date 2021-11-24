package com.empresa.phva;


import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;


public class Registro extends AppCompatActivity {
    Button btnSelectRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        btnSelectRole = (Button) findViewById(R.id.btnSelectRole);
        btnSelectRole.setOnClickListener(v -> showAlertDialog());


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.select_roles, android.R.layout.simple_spinner_item);

        }

    public void modulos (View view){
        Intent modulos = new Intent(this, AccesoModulos.class);
        startActivity(modulos);
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Registro.this);
        alertDialog.setTitle("Seleccione su perfil");
        String[] items = {"Administrador", "Empleado", "Supervisor", "Presidente COCOLA", "Presidente BE", "Presidente COPASST"};
        boolean[] checkedItems = {false, false, false, false, false, false};
        alertDialog.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                SharedPreferences preferences = getSharedPreferences("which", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("ischeck", isChecked);
                editor.putInt("case", which);
                editor.commit();
                //ShowAdmin();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }


    private void ShowAdmin() {
        //Intent admin = new Intent(this, home_principal.class);
        //startActivity(admin);
    }

}