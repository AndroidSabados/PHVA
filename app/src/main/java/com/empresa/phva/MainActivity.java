package com.empresa.phva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Button entrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicioUsuario();
        mAuth = FirebaseAuth.getInstance();
        /*user=(TextView) findViewById(R.id.txtUser);
        password=(TextView) findViewById(R.id.txtPassword);*/

        entrar = (Button) findViewById(R.id.iniciar);
        entrar.setOnClickListener(view -> {
            login();
        });
    }


    public void pRegistro(View view) {
        Intent registrarse = new Intent(this, Registro.class);
        startActivity(registrarse);
    }

    public void pListadoAlertas(View view) {
        login();
    }

  /*  public void ClaroOscuro(){
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
    }*/


    //ClaroOscuro();


    public void inicioUsuario() {
        preferences = getSharedPreferences("usuarios", MODE_PRIVATE);
        editor = preferences.edit();

        editor.putString("Name1", "sg-sst");
        editor.putString("Pass1", "123456789");
        editor.putString("rol1", "1");

        editor.putString("Name2", "empleado");
        editor.putString("Pass2", "123456789");
        editor.putString("rol2", "8");
        editor.commit();
    }

    public void login() {
        EditText editTxtuser, ediTxtPassword;
        editTxtuser = (EditText) findViewById(R.id.txtUser);
        ediTxtPassword = (EditText) findViewById(R.id.txtPassword);
        preferences = getSharedPreferences("usuarios", MODE_PRIVATE);
        String user1 = preferences.getString("Name1", ""); // Nombre Shared
        String user2 = preferences.getString("Name2", "");
        String pass = preferences.getString("Pass1", ""); //Pass Shared

        String email = editTxtuser.getText().toString();
        String password = ediTxtPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            editTxtuser.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Ingrese una contraseña", Toast.LENGTH_SHORT).show();
            ediTxtPassword.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Bienvenid@", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, AccesoModulos.class));
                            } else {
                                Log.w("TAG", "Error", task.getException());
                            }
                        }
                    });
        }

        if (user1.equals(email) && pass.equals(password)) {
            //Toast.makeText(MainActivity.this, "SG SST", Toast.LENGTH_SHORT).show();
            preferences = getSharedPreferences("guest", MODE_PRIVATE);
            editor = preferences.edit();
            editor.putString("Name", "sg-sst");
            editor.putString("Pass", "123456789");
            editor.putString("rol", "1");
            editor.commit();
            Intent intent = new Intent(this, AccesoModulos.class);
            startActivity(intent);
        } else {
            if (user2.equals(email) && pass.equals(password)) {
                //Toast.makeText(MainActivity.this, "empleado", Toast.LENGTH_SHORT).show();
                preferences = getSharedPreferences("guest", MODE_PRIVATE);
                editor = preferences.edit();
                editor.putString("Name", "empleado");
                editor.putString("Pass", "123456789");
                editor.putString("rol", "8");
                editor.commit();
                Intent intent = new Intent(this, AccesoModulos.class);
                startActivity(intent);
            }
        }
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
