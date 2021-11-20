package com.empresa.phva;

import static android.graphics.Color.GREEN;
import static com.empresa.phva.R.color.*;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ImageView mImageView;
    private ImageView mImageView2;
    private Bitmap mSelectedImage;
    private Integer mImageMaxWidth;
    private Integer mImageMaxHeight;
    private String docCarnet = "";
    private String docCedula = "";
    private String cedulavalue;
    private TextInputEditText inputValidacionCedula;
    private TextInputLayout lyValidacionCedula;
    private static final int RESULTS_TO_SHOW = 10;
    TextView textView, textCarnet, textCedula;
    View viewCarnet, viewCedula;
    CardView cardCarnet;
    CardView cardCedula;
    boolean imageSelect = Boolean.parseBoolean(null);


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mImageView = findViewById(R.id.image_view);
        mImageView2 = findViewById(R.id.image_view2);
        textView = findViewById(R.id.textView);
        cardCarnet = findViewById(R.id.card_carnet);
        cardCedula = findViewById(R.id.card_cedula);
        textCarnet = findViewById(R.id.txt_carnet);
        textCedula = findViewById(R.id.txt_cedula);
        viewCarnet = findViewById(R.id.view_carnet);
        viewCedula = findViewById(R.id.view_cedula);
        inputValidacionCedula = findViewById(R.id.input_validacion_cedula);
        lyValidacionCedula = findViewById(R.id.ly_validacion_cedula);


        verificarPermisosCamara();

        cardCarnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageSelect = true;
                camara();
            }
        });

        cardCedula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageSelect = false;
                camara();
            }
        });


        inputValidacionCedula.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //System.out.println(s.toString() + " " + start + " " + count + " " + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //System.out.println(s.toString() + " " + start + " " + count);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (inputValidacionCedula.length() >= 6) {
                    lyValidacionCedula.setError(null);
                }else{
                    lyValidacionCedula.setError("Ingrese por favor la cedula completa, esta debe tener mas de 6 dígitos");
                }
            }
        });
    }



    private void camara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 1);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");

            if (imageSelect == true) {
                viewCarnet.setVisibility(View.INVISIBLE);
                textCarnet.setVisibility(View.INVISIBLE);
                mImageView.setImageBitmap(imgBitmap);
            } else {
                viewCedula.setVisibility(View.INVISIBLE);
                textCedula.setVisibility(View.INVISIBLE);
                mImageView2.setImageBitmap(imgBitmap);
            }
            onItemSelected(imgBitmap);
            runTextRecognition();
        }
    }

    private void runTextRecognition() {
        InputImage image = InputImage.fromBitmap(mSelectedImage, 0);
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        recognizer.process(image).addOnSuccessListener(new OnSuccessListener<Text>() {
            @Override
            public void onSuccess(@NonNull Text text) {
                processTextRecognitionResult(text);
            }
/*            @Override
            public void onSuccess(@NonNull Text text) {
                processTextRecognitionResult(text);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                //metodo de retoma de foto hasta que sea exitoso
            }*/
        });
    }


    @SuppressLint("ResourceAsColor")
    private void processTextRecognitionResult(Text texts) {
        List<Text.TextBlock> blocks = texts.getTextBlocks();
        if (imageSelect) {
            docCarnet = texts.getText();

        } else {
            docCedula = texts.getText();
        }


        if (!docCarnet.equals("")) {
            comparacionDatos(docCarnet, docCedula);
        }

        if (!docCarnet.equals("") && !docCedula.equals("")) {
            comparacionDatos(docCarnet, docCedula);
        }


    }

    public void comparacionDatos(String datosCarnet, String datosCedula) {

        String[] miVacuna = {"M", "i", "V", "a", "c", "u", "n", "a"};
        String[] covid19 = {"C", "o", "v", "i", "d", "-", "1", "9"};

        cedulavalue = inputValidacionCedula.getText().toString();
        //showToast("La cedula es: " + cedulavalue);
       // showToast(("Cadena: " + cadena.replaceAll("[^a-b]", "")));
        datosCarnet = datosCarnet.replace(" ", "");
        //showToast("El carnet es: " + datosCarnet);
        datosCedula = datosCedula.replace(".", "");
        //showToast("Los datos foto cedula son: " + datosCedula);

        String[] datosCarnetVector = datosCarnet.split("\n");
        String[] datosCedulaVector = datosCedula.split("\n");
        String[] cedulaVector = cedulavalue.split("");


        int[] resultadoMivacuna = validar(datosCarnetVector, miVacuna, "MiVacuna");
        int[] resultadoCovid19 = validar(datosCarnetVector, covid19, "Covid-19");
        int[] resultadoCedula = validarCedulaCarnet(datosCarnetVector, cedulaVector, cedulavalue);
        /*
        int m = 0;
        int i = 0;
        int v = 0;
        int a = 0;
        int c = 0;
        int u = 0;
        int n = 0;*/

            String imprecion;
            Boolean validacion = true;

            if (resultadoMivacuna[0] == 1) {
                imprecion = datosCarnetVector[resultadoMivacuna[1]];;
                textView.setText(imprecion);
                validacion = false;

            }

            if ( resultadoCovid19[0] ==1){
                imprecion = textView.getText() + "\n" + datosCarnetVector[resultadoCovid19[1]];;
                textView.setText(imprecion);
                validacion = false;
            }

            if(resultadoCedula[0] == 1){

                imprecion = textView.getText() + "\n" + datosCarnetVector[resultadoCedula[1]];;
                textView.setText(imprecion);
                validacion = false;
            }

            if(validacion){
                textView.setTextSize(15);
                textView.setText("Error al Leer el documento del Carnet de Vacunación del Covid-19\n Por Favor Tomar la foto Nuevamente.");
            }
    }

//
//    public void ConvertirNumCedula(String [] num)
//
//
//    }


    public String[] ConvertirNumCarnet(String[] num){

        int [] numero = new int[num.length];

        for(int i =0; i < num.length; i++){
            numero[i]=-1;
            try {
                numero[i] =  Integer.parseInt (num[i]);
              //  showToast("cedula: "+numero[i]);
            }catch (NumberFormatException e){
                  num[i] = num[i].replaceAll("[^0-1-2-3-4-5-6-7-8-9-]", "0");
                  numero[i] = Integer.parseInt(num[i]);
                 // showToast("El número: "+num[i]+" en la posicion :"+i+" se remplazo por "+numero[i]);
            }
        }
        String [] datosCedula = new String[num.length];
        for(int i =0; i < num.length; i++){
            datosCedula[i]= String.valueOf(numero[i]);
        }
        return datosCedula;
    }
    public int[] validar(String[] datosCarnetVector, String[] datoComparar, String textoComparar) {
        double porcentajeValido = 0.0;
        double[] porcentajesDatos = new double[datosCarnetVector.length];

        for (int k = 0; k < datosCarnetVector.length; k++) {
            String[] parts = datosCarnetVector[k].split("");
            porcentajeValido = 0;

            if (datosCarnetVector[k].equals(textoComparar)) {
                porcentajeValido = 100.0;
                showToast("porcentaje del 100 prueba ya :) " + porcentajeValido);
               // break;
            } else {
                if (datosCarnetVector[k].length() >= datoComparar.length - 1 && datosCarnetVector[k].length() <= datoComparar.length + 1) {
                    for (int j = 0; j < parts.length; j++) {
                        try {
                            if (parts[j].equals(datoComparar[j])) {
                                porcentajeValido = porcentajeValido + ((100 * 1.00) / datoComparar.length);
                            }
                        } catch (Exception e) {
                           // showToast("El Error se encuentra en la posicion" + j + " " + datosCarnetVector[k]);
                        }
                    }
                } else {
                    porcentajeValido = 0.0;//bien :)  si
                }
            }
            if (porcentajeValido==100){
                porcentajesDatos[k] = porcentajeValido;
                break;
            }else{
                porcentajesDatos[k] = porcentajeValido;
            }

        }
        int posNumMayor = porcentajeMayor(porcentajesDatos);
        int[] resultado = new int[2];

        showToast(datosCarnetVector[posNumMayor] + "|| Posicion: " + posNumMayor + " || porcentaje: " + porcentajesDatos[posNumMayor]);

        if (porcentajesDatos[posNumMayor] >= 80) {
            resultado[0] = 1;
            resultado[1] = posNumMayor;
            return resultado;
        }else{
            resultado[0] = 0;
            resultado[1] = posNumMayor;
            return resultado;
        }
    }

    public int[] validarCedulaCarnet(String[] datosCarnetVector, String[] datoComparar, String textoComparar) {
        double porcentajeValido = 0.0;
        double[] porcentajesDatos = new double[datosCarnetVector.length];


        for (int k = 0; k < datosCarnetVector.length; k++) {
            String[] parts = datosCarnetVector[k].split("");
            porcentajeValido = 0;
            String[] datosCarnet =  ConvertirNumCarnet(parts);
            if (datosCarnetVector[k].equals(textoComparar)) {
                porcentajeValido = 100.0;
                showToast("porcentaje del 100 prueba ya :) " + porcentajeValido);
                // break;
            } else {
                if (datosCarnetVector[k].length() >= datoComparar.length - 1 && datosCarnetVector[k].length() <= datoComparar.length + 1) {
                    for (int j = 0; j < datosCarnet.length; j++) {
                        try {
                            if (datosCarnet[j].equals(datoComparar[j])) {
                                porcentajeValido = porcentajeValido + ((100 * 1.0) / datoComparar.length);
                            }

                        } catch (Exception e) {
                           // showToast("El Error se encuentra en la posicion" + j + " " + datosCarnetVector[k]);
                        }
                    }
                } else {
                    porcentajeValido = 0.0;//bien :)  si
                }
            }
            if (porcentajeValido==100){
                porcentajesDatos[k] = porcentajeValido;
                break;
            }else{
                porcentajesDatos[k] = porcentajeValido;
            }

            //1128385296
            //6f128
            //f12832526
            //f/28385296
            //1128 35 8296
            //mla1128385296


            showToast(datosCarnetVector[k] + "|| Posicion: " + k + " || porcentaje: " + porcentajesDatos[k]);
        }
        int posNumMayor = porcentajeMayor(porcentajesDatos);
        int[] resultado = new int[2];


        if (porcentajesDatos[posNumMayor] >= 80) {
            resultado[0] = 1;
            resultado[1] = posNumMayor;
            return resultado;
        }else{
            resultado[0] = 0;
            resultado[1] = posNumMayor;
            return resultado;
        }
    }
//En cuntra en el vector cual de todas la posiciones tiene un porcentaje mallo para luego retornar su posicion y saver qeu tento el que tiene el mallor porcentaje  es decir lo que buscamos MIVacuna
    private int porcentajeMayor(double[] vectorCorecto) {
        double numMayor = 0;
        int posNumMayor = 0;
        for (int i = 0; i < vectorCorecto.length; i++) {
            if (numMayor <= vectorCorecto[i]) {
                numMayor = vectorCorecto[i];
                posNumMayor = i;
            }
        }
        return posNumMayor;
    }


    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private Integer getmImageMaxWidth() {
        if (mImageMaxWidth == null) {
            mImageMaxWidth = mImageView.getWidth();
        }
        return mImageMaxWidth;
    }

    private Integer getmImageMaxHeight() {
        if (mImageMaxHeight == null) {
            mImageMaxHeight = mImageView.getWidth();
        }
        return mImageMaxHeight;
    }

    private Pair<Integer, Integer> getTargetedWidthHeight() {
        int targetWidth;
        int targetHeight;
        int maxWidthForPortraidMode = getmImageMaxWidth();
        int maxHeightForPortraidMode = getmImageMaxHeight();
        targetWidth = maxWidthForPortraidMode;
        targetHeight = maxHeightForPortraidMode;
        return new Pair<>(targetWidth, targetHeight);
    }

    public void onItemSelected(Bitmap imgBitmap) {
        mSelectedImage = imgBitmap;
        if (mSelectedImage != null) {
            Pair<Integer, Integer> targetedSize = getTargetedWidthHeight();
            int targeteWidth = targetedSize.first;
            int maxHeight = targetedSize.second;
            float scaleFactor = Math.max((float) mSelectedImage.getWidth() / (float) targeteWidth,
                    (float) mSelectedImage.getHeight() / (float) maxHeight);
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(mSelectedImage,
                    (int) (mSelectedImage.getWidth() / scaleFactor),
                    (int) (mSelectedImage.getHeight() / scaleFactor), true);
            //mImageView.setImageBitmap(resizedBitmap);
            mSelectedImage = resizedBitmap;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
    int REQUEST_CODE = 200;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void  verificarPermisosCamara(){
        int permisosCamara = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int permisosAlmacenamientoEditar = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permisosAlmacenamientoLeer = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if( permisosCamara == PackageManager.PERMISSION_GRANTED && permisosAlmacenamientoEditar == PackageManager.PERMISSION_GRANTED && permisosAlmacenamientoLeer == PackageManager.PERMISSION_GRANTED  ){
            showToast("Permiso de la camara otorgado");
        }else{
            requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE);
        }
    }

}