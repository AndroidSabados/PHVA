package com.empresa.phva;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    Switch swi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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