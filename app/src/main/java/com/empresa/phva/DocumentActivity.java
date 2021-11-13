package com.empresa.phva;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.empresa.phva.db.ControllerDocument;
import com.empresa.phva.db.DbHelper;
import com.empresa.phva.db.Document;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class DocumentActivity extends AppCompatActivity {

    public String userId;

    private EditText etDescription, etJustificacion, etStatus;
    private String strType, strDescription, strJustificacion, strSeveridad;

    private Spinner  spinnerType, spinnerSeveridad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        spinnerType = findViewById(R.id.idSpinnerTipoNovedades);
        etDescription = findViewById(R.id.editTextDescripcion);
        etJustificacion = findViewById(R.id.editTextJustificacion);
        spinnerSeveridad = findViewById(R.id.idSpinnerSeveridad);


        Button btnCreate = findViewById(R.id.btnCrearNovedad);

        btnCreate.setOnClickListener(view -> validator());


    }

    private void validator() {
        getData();
        if (strType.length()<=3) Toast.makeText(this, "The type is required", Toast.LENGTH_SHORT).show();
        else if (strDescription.length()<=3) Toast.makeText(this, "The description is required", Toast.LENGTH_SHORT).show();
        else if (strJustificacion.length()<=3) Toast.makeText(this, "The Url is required", Toast.LENGTH_SHORT).show();
        else if (strSeveridad.length()<=3) Toast.makeText(this, "The Status is required", Toast.LENGTH_SHORT).show();
        else insertData();
    }

    @Override
    public void onBackPressed(){
        new MaterialAlertDialogBuilder(this).
                setTitle("Exit")
                .setMessage("Are you sure to exit?")
                .setCancelable(false)
                .setNegativeButton("Cancel", ((dialogInterface, i) -> dialogInterface.cancel()))
                .setPositiveButton("Ok", (dialogInterface, i) -> {
                    super.finish();
                })
                .show();
    }

    private void getData(){
        strType = spinnerType.getSelectedItem().toString();
        strDescription = etDescription.getText().toString();
        strJustificacion = etJustificacion.getText().toString();
        strSeveridad = spinnerSeveridad.getSelectedItem().toString();

    }


    private void clearDocumentScreen() {
        spinnerType.setSelection(0);
        etDescription.setText("");
        etJustificacion.setText("");
        spinnerSeveridad.setSelection(0);
    }

    private void insertData() {

        //Document document = new Document(strType, strDescription, strJustificacion, strSeveridad);

        Document document = new Document(strType, strDescription, strJustificacion, strSeveridad);

        ControllerDocument controller = new ControllerDocument(this);

        long db = controller.insertDocument(document);

        if (db>0){
            Toast.makeText(this, "Successfully!", Toast.LENGTH_SHORT).show();
            clearDocumentScreen();
        }else{
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }







}