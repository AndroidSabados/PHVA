package com.empresa.phva;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.empresa.phva.db.ControllerDocument;
import com.empresa.phva.db.DbHelper;
import com.empresa.phva.db.Document;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class DocumentActivity extends AppCompatActivity {

    public String userId;

    private EditText etType, etDescription, etUrl, etStatus;
    private String strType, strDescription, strUrl, strStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        TextView tvUserId = findViewById(R.id.tvUserId);

        etType = findViewById(R.id.etType);
        etDescription = findViewById(R.id.etDescription);
        etUrl = findViewById(R.id.etUrl);
        etStatus = findViewById(R.id.etStatus);

        Button btnCreate = findViewById(R.id.btnCreate);

        btnCreate.setOnClickListener(view -> validator());



        userId = getIntent().getStringExtra("userId");

        tvUserId.setText(userId);

    }

    private void validator() {
        getData();
        if (strType.length()<=3) Toast.makeText(this, "The type is required", Toast.LENGTH_SHORT).show();
        else if (strDescription.length()<=3) Toast.makeText(this, "The description is required", Toast.LENGTH_SHORT).show();
        else if (strUrl.length()<=3) Toast.makeText(this, "The Url is required", Toast.LENGTH_SHORT).show();
        else if (strStatus.length()<=3) Toast.makeText(this, "The Status is required", Toast.LENGTH_SHORT).show();
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
        strType = etType.getText().toString();
        strDescription = etDescription.getText().toString();
        strUrl = etUrl.getText().toString();
        strStatus = etStatus.getText().toString();
    }


    private void clearDocumentScreen() {
        etType.setText("");
        etDescription.setText("");
        etUrl.setText("");
        etStatus.setText("");
    }

    private void insertData() {

        Document document = new Document(strType, strUrl, strDescription, strStatus);

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