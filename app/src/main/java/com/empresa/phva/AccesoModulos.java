package com.empresa.phva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AccesoModulos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso_modulos);
    }
    public void pOCR (View view) {
        Intent ocr = new Intent(this, OCR.class);
        startActivity(ocr);
    }

        public void pDocumentos (View view) {
            Intent documentos = new Intent(this, Documentos.class);
            startActivity(documentos);
        }


}