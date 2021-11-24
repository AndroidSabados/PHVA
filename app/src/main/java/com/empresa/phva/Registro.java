package com.empresa.phva;

<<<<<<< HEAD
=======
import androidx.appcompat.app.AppCompatActivity;

>>>>>>> 1233e6a9570f33162e38f0bf3a8bff02305e17dd
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

<<<<<<< HEAD
import androidx.appcompat.app.AppCompatActivity;

=======
>>>>>>> 1233e6a9570f33162e38f0bf3a8bff02305e17dd

public class Registro extends AppCompatActivity {

    Spinner spinnerRoles;
    Button btnSelectRole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        spinnerRoles = (Spinner) findViewById(R.id.idSpinnerRoles);
        btnSelectRole = (Button) findViewById(R.id.btnSelectRole);
        btnSelectRole.setOnClickListener(v -> showAlertDialog());


<<<<<<< HEAD
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.select_roles, android.R.layout.simple_spinner_item);
=======
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.select_roles, android.R.layout.simple_spinner_item);
>>>>>>> 1233e6a9570f33162e38f0bf3a8bff02305e17dd
        spinnerRoles.setAdapter(adapter);



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