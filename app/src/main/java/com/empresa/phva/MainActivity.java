package com.empresa.phva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

        if (!docCarnet.equals("") && !docCedula.equals("")) {
            String imprecion = comparacionDatos(docCarnet, docCedula)[0] + "\n" + comparacionDatos(docCarnet, docCedula)[1] ;
            textView.setText(imprecion);
            textView.setText(docCarnet + "\n" + docCedula);
        }else{
            textView.setTextColor(R.color.color_primary);
            textView.setTextSize(25);
            textView.setText("Error al Leer el documento \n Por Favor Tomar la foto Nuevamente.");
        }


    }

    public String[] comparacionDatos(String datosCarnet, String datosCedula) {
        String[] datosCarnetVector = datosCarnet.split("\n");
        String[] datosCedulaVector = datosCedula.split("\n");

        String[] resultado = limpiarTextoCarnet(datosCarnetVector);
   /*     for(String datos: datosCarnetVector){
            showToast(datos);
        }*/
        return resultado;
    }

    public String[] limpiarTextoCarnet(String[] datosCarnetVector) {

        double porcentajeValido = 0.0;
        double[] VectorCorecto = new double[2];

        for (String datos : datosCarnetVector) {
            String[] parts = datos.split("");
            String[] miVacuna = {"M", "i", "V", "a", "c", "u", "n", "a"};
            int m = 0;
            int i = 0;
            int v = 0;
            int a = 0;
            int c = 0;
            int u = 0;
            int n = 0;

            for (int j = 0; j <= parts.length; j++) {

                if ((parts.length / 8) * (100) >= 80.0 || (parts.length / 8) * (100) <= 115.0) {

                    if (parts[j].equals("M")) {
                        m++;
                    }

                    if (parts[j].equals("i")) {
                        i++;
                    }

                    if (parts[j].equals("V")) {
                        v++;
                    }

                    if (parts[j].equals("a")) {
                        a++;
                    }

                    if (parts[j].equals("c")) {
                        c++;
                    }

                    if (parts[j].equals("u")) {
                        u++;
                    }

                    if (parts[j].equals("n")) {
                        n++;
                    }
                }

                if (i == 1) {
                    porcentajeValido = porcentajeValido + (100 * 1) / 8;
                }

                if (v == 1) {
                    porcentajeValido = porcentajeValido + (100 * 1) / 8;
                }

                if (c == 1) {
                    porcentajeValido = porcentajeValido + (100 * 1) / 8;
                }

                if (u == 1) {
                    porcentajeValido = porcentajeValido + (100 * 1) / 8;
                }

                if (n == 1) {
                    porcentajeValido = porcentajeValido + (100 * 1) / 8;
                }

                if (m == 1) {
                    porcentajeValido = porcentajeValido + (100 * 1) / 8;
                }

                if (a == 2) {
                    porcentajeValido = porcentajeValido + (100 * 2) / 8;
                } else if (a == 1) {
                    porcentajeValido = porcentajeValido + (100 * 1) / 8;
                }

                if (porcentajeValido >= 80){
                    VectorCorecto[0] = porcentajeValido;
                    VectorCorecto[1] = j;
                }

            }

            showToast("Procentaje: "+ VectorCorecto[0]);
            showToast("Valor: " + VectorCorecto[1]);
            showToast("Imprecion Usuario: MiVacuna");

            //MiVacuna

            // if (datos.equals("MiVacuna"))

        }
        //MiVacuna
        //covid-19
        //165494984984
        String[] datosFinales = new String[2];
        datosFinales[0] = VectorCorecto[0]+"";
        datosFinales[1] = VectorCorecto[1]+"";
        return datosFinales;
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