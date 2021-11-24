package com.empresa.phva;


<<<<<<< HEAD
=======

>>>>>>> 1233e6a9570f33162e38f0bf3a8bff02305e17dd
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
<<<<<<< HEAD
import android.widget.SearchView;
import android.widget.TextView;
=======
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
>>>>>>> 1233e6a9570f33162e38f0bf3a8bff02305e17dd

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

<<<<<<< HEAD
=======

>>>>>>> 1233e6a9570f33162e38f0bf3a8bff02305e17dd
import com.empresa.phva.db.ControllerDocument;
import com.empresa.phva.db.DbHelper;
import com.empresa.phva.db.Document;

import java.util.ArrayList;

<<<<<<< HEAD
public class ListDocumentsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
=======
public class ListDocumentsActivity extends AppCompatActivity {
>>>>>>> 1233e6a9570f33162e38f0bf3a8bff02305e17dd

    // creating variables for our array list,
    // dbhandler, adapter and recycler view.
    private ArrayList<Document> documentArrayList;
    private DbHelper dbHandler;
    private DocumentRVAdapter documentRVAdapter;
    private RecyclerView documentRV;
    private Button btnUrl;
    private TextView tvUrl;
    public String prueba;
<<<<<<< HEAD
    private SearchView searchView;
=======
>>>>>>> 1233e6a9570f33162e38f0bf3a8bff02305e17dd

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_documents);

        prueba = "probando";
        tvUrl = (TextView) findViewById(R.id.tvURL);
        btnUrl = (Button) findViewById(R.id.btnURL);
<<<<<<< HEAD
        searchView=(SearchView)findViewById(R.id.xSearchView);
=======
>>>>>>> 1233e6a9570f33162e38f0bf3a8bff02305e17dd

        Log.e("LOG", "logueo de boton");

        /*btnUrl.setOnClickListener(v -> {
            showWebViewDocument(prueba);
        });*/

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

<<<<<<< HEAD
        //buuscador del texto
        searchView.setOnQueryTextListener(this);
=======
>>>>>>> 1233e6a9570f33162e38f0bf3a8bff02305e17dd


    }







    public void pCreateNovedades (View view) {
        Intent createNovedades = new Intent(this, DocumentActivity.class);
        startActivity(createNovedades);
    }


<<<<<<< HEAD
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        documentRVAdapter.filtrado(s);
        return false;
    }
=======

>>>>>>> 1233e6a9570f33162e38f0bf3a8bff02305e17dd
}
