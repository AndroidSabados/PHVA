package com.empresa.phva;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Registro extends AppCompatActivity {

    private String userId;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private EditText editTextNombreUsuario, editContraseña,editTextNombre, editTextApellido, editTextCedula, editTextTelefono, editCelular, editDireccion;
    private CheckBox checkBox;
    private Button btnfinalizar;
    TextView txvRol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        editTextNombreUsuario = findViewById(R.id.editTextNombreUsuario);
        editContraseña = findViewById(R.id.editContraseña);
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellido = findViewById(R.id.editTextApellido);
        editTextCedula = findViewById(R.id.editTextCedula);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        editCelular = findViewById(R.id.editCelular);
        editDireccion = findViewById(R.id.editDireccion);
        checkBox = findViewById(R.id.checkBox);
        btnfinalizar = findViewById(R.id.finalizar);

        txvRol = (TextView) findViewById(R.id.TxvSelectRole);
        txvRol.setOnClickListener(v -> showAlertDialog());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.select_roles, android.R.layout.simple_spinner_item);
    }

    public void createUser(){
        String nameUser = editTextNombreUsuario.getText().toString();
        String password = editContraseña.getText().toString();
        String name = editTextNombre.getText().toString();
        String apellido = editTextApellido.getText().toString();
        String cedula = editTextCedula.getText().toString();
        String telefono = editTextTelefono.getText().toString();
        String celular = editCelular.getText().toString();
        String direccion = editDireccion.getText().toString();
    }

    public void modulos (View view){
        Intent modulos = new Intent(this, MainActivity.class);
        startActivity(modulos);
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Registro.this);
        alertDialog.setTitle("Seleccione su perfil.");
        String[] items = {"Administrador", "Empleado", "Supervisor", "Presidente COCOLA", "Presidente BE", "Presidente COPASST"};
        boolean[] checkedItems = {false, false, false, false, false, false};
        alertDialog.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                SharedPreferences preferences = getSharedPreferences("which", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                for (int i=0; i < items.length; i++ ) {

                    Log.e("LOG", "booleano : " + checkedItems[i]);
                    Log.e("LOG", "nombre Item: " + items[i]);

                    if (checkedItems[i]==true){
                        txvRol.setText(items[i]);
                        dialog.dismiss();
                    }

                }

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