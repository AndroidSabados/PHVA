package com.empresa.phva;

<<<<<<< HEAD
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    public String user;


=======
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Switch swi;
>>>>>>> 1233e6a9570f33162e38f0bf3a8bff02305e17dd

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< HEAD










        }

    public void pRegistro (View view){
        Intent registrarse = new Intent(this, Registro.class);
        startActivity(registrarse);
    }

    public void pListadoAlertas (View view){
        Intent listado = new Intent(this, ListadoAlertas.class);
        startActivity(listado);

    }



    public Intent intent;


    public void showDocument(String user) {
        Log.d("Aca", "xxxxxxxxxxxxxxx"+user);
        Toast.makeText(this, "Aca OK: "+user, Toast.LENGTH_SHORT).show();

        String userId = "texto ok" + user;
        intent = new Intent(this, DocumentActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }







}

=======
        swi= (Switch) findViewById(R.id.tema);
        ClaroOscuro();

        }
    public void pRegistro (View view){
        Intent registrarse = new Intent(this, Registro.class);
        startActivity(registrarse);
    }

    public void pListadoAlertas (View view){
        Intent listado = new Intent(this, ListadoAlertas.class);
        startActivity(listado);


}

    public void ClaroOscuro(){
        SharedPreferences sp = getSharedPreferences("SP", this.MODE_PRIVATE);
        int theme = sp.getInt("Theme",1);
        if (theme==0){
            //getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            swi.setChecked(true);
        }
        else{
            //getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            swi.setChecked(false);
        }
    }

     public void onClick(View view){
         SharedPreferences sp = getSharedPreferences("SP", this.MODE_PRIVATE);
         SharedPreferences.Editor editor = sp.edit();

         if (view.getId()==R.id.tema){
            if (swi.isChecked()){
               editor.putInt("Theme",0);
            } else{
                editor.putInt("Theme",1);
            }
            editor.commit();
            ClaroOscuro();
        }
     }



    /*swi.(new View.OnClickListener(){
        @Override
        public void onClick(View v){
            if (swi.isChecked()) {
                ClaroOscuro(0);
            } else {
                ClaroOscuro(1);
            }
        }
    });*/


}
>>>>>>> 1233e6a9570f33162e38f0bf3a8bff02305e17dd
