package com.empresa.phva;

<<<<<<< HEAD
=======
import androidx.appcompat.app.AppCompatActivity;

>>>>>>> 1233e6a9570f33162e38f0bf3a8bff02305e17dd
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

<<<<<<< HEAD
import androidx.appcompat.app.AppCompatActivity;

=======
>>>>>>> 1233e6a9570f33162e38f0bf3a8bff02305e17dd
public class ListadoAlertas extends AppCompatActivity {

    ListView listaEstados;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_alertas);


        listaEstados = (ListView) findViewById(R.id.listaEstados);
<<<<<<< HEAD
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.DatosList, android.R.layout.simple_list_item_1);
=======
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.DatosList, android.R.layout.simple_list_item_1);
>>>>>>> 1233e6a9570f33162e38f0bf3a8bff02305e17dd

        listaEstados.setAdapter(adapter);
    }
}