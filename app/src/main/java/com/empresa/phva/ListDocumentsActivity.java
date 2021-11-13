package com.empresa.phva;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.empresa.phva.db.ControllerDocument;
import com.empresa.phva.db.DbHelper;
import com.empresa.phva.db.Document;

import java.util.ArrayList;

public class ListDocumentsActivity extends AppCompatActivity {

    // creating variables for our array list,
    // dbhandler, adapter and recycler view.
    private ArrayList<Document> documentArrayList;
    private DbHelper dbHandler;
    private DocumentRVAdapter documentRVAdapter;
    private RecyclerView documentRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_documents);

        // initializing our all variables.
        documentArrayList = new ArrayList<>();
        dbHandler = new DbHelper(ListDocumentsActivity.this);
        ControllerDocument controller = new ControllerDocument(this);

        // getting our course array
        // list from db handler class.
        documentArrayList = controller.getListDocuments();

        // on below line passing our array lost to our adapter class.
        documentRVAdapter = new DocumentRVAdapter(documentArrayList, ListDocumentsActivity.this);
        documentRV = findViewById(R.id.idRVCourses);

        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListDocumentsActivity.this, RecyclerView.VERTICAL, false);
        documentRV.setLayoutManager(linearLayoutManager);

        // setting our adapter to recycler view.
        documentRV.setAdapter(documentRVAdapter);
    }


    public void pCreateNovedades (View view) {
        Intent createNovedades = new Intent(this, DocumentActivity.class);
        startActivity(createNovedades);
    }



}
