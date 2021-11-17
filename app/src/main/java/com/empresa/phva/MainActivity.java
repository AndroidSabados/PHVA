package com.empresa.phva;

import static android.graphics.Color.GREEN;
import static com.empresa.phva.R.color.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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
    private TextInputEditText inputValidacionCedula;
    private TextInputLayout lyValidacionCedula;
    private static final int RESULTS_TO_SHOW = 10;
    TextView textView, textCarnet, textCedula;
    View viewCarnet, viewCedula;
    CardView cardCarnet;
    CardView cardCedula;
    boolean imageSelect = Boolean.parseBoolean(null);


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
        String[] datosCarnetVector = datosCarnet.split("\n");
        String[] datosCedulaVector = datosCedula.split("\n");

        String[] miVacuna = {"M", "i", "V", "a", "c", "u", "n", "a"};
        String[] covid19 = {"C", "o", "v", "i", "d", "-", "1", "9"};

        int[] resultado1 = validar(datosCarnetVector, miVacuna, "MiVacuna");
        int[] resultado2 = validar(datosCarnetVector, covid19, "Covid-19");
        /*
        int m = 0;
        int i = 0;
        int v = 0;
        int a = 0;
        int c = 0;
        int u = 0;
        int n = 0;*/

       // String imprecion = datosCarnetVector[resultado1[1]] +"\n" +datosCarnetVector[resultado2[1]];
      //  textView.setText(imprecion);

      if (resultado1[0] == 1) {
            String imprecion = datosCarnetVector[resultado1[1]];
            textView.setText(imprecion);
          if ( resultado2[0] ==1){
              imprecion = datosCarnetVector[resultado1[1]] +"\n" +datosCarnetVector[resultado2[1]];
              textView.setText(imprecion);
          }
        }else{
            textView.setTextSize(15);
            textView.setText("Error al Leer el documento del Carnet de Vacunación del Covid-19\n Por Favor Tomar la foto Nuevamente.");
        }
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
                            showToast("El Error se encuentra en la posicion" + j + " " + datosCarnetVector[k]);
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
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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


}