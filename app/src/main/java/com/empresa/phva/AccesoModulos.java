package com.empresa.phva;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class AccesoModulos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso_modulos);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void pOCR (View view) {
        Intent ocr = new Intent(this, OcrLocalActivity.class);
        startActivity(ocr);
    }

    public void pDocumentos (View view) {
        Intent documentos = new Intent(this, Documentos.class);
        startActivity(documentos);
    }

    public void pNovedades (View view) {
        Intent novedades = new Intent(this, ListDocumentsActivity.class);
        startActivity(novedades);
    }

    public void pQR (View view) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        intentIntegrator.setPrompt("Selecciona el QR.");
        intentIntegrator.setTorchEnabled(true);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            Toast.makeText(this, "El valor scaneado es:" + result.getContents(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, WebViewDocumentoActivity.class);
            intent.putExtra("url",result.getContents() );
            startActivity(intent);
        } else {
            Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}