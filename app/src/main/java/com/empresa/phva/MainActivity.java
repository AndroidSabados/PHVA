package com.empresa.phva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
    private Button mTextButton;
    private Bitmap mSelectedImage;
    private SuperposicionGrafica mSuperposicionGrafica;
    private Integer mImageMaxWidth;
    private Integer mImageMaxHeight;
    private static final int RESULTS_TO_SHOW = 10;
    Button btnCamara;
    TextView textView, textCarnet, textCedula;
    View viewCarnet, viewCedula;
    CardView cardCarnet;
    CardView cardCedula;
    boolean imageSelect= Boolean.parseBoolean(null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextButton = findViewById(R.id.button_text);
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

        mTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runTextRecognition();
            }
        });

    }


    private void camara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(intent, 1);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap)  extras.get("data");

            if(imageSelect==true){
                viewCarnet.setVisibility(View.INVISIBLE);
                textCarnet.setVisibility(View.INVISIBLE);
                mImageView.setImageBitmap(imgBitmap);
            }

            if(imageSelect==false){
                viewCedula.setVisibility(View.INVISIBLE);
                textCedula.setVisibility(View.INVISIBLE);
                mImageView2.setImageBitmap(imgBitmap);
            }
            onItemSelected(imgBitmap);
        }
    }

    private void runTextRecognition(){
        InputImage image=InputImage.fromBitmap(mSelectedImage, 0);
        TextRecognizer recognizer= TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        mTextButton.setEnabled(false);
        recognizer.process(image).addOnSuccessListener(new OnSuccessListener<Text>() {
            @Override
            public void onSuccess(@NonNull Text text) {
                mTextButton.setEnabled(true);
                processTextRecognitionResult(text);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mTextButton.setEnabled(true);
                e.printStackTrace();
                //metodo de retoma de foto hasta que sea exitoso
            }
        });
    }


    private void processTextRecognitionResult(Text texts){
        List<Text.TextBlock> blocks = texts.getTextBlocks();
        textView.setText(texts.getText());
    }

    private void  showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    private Integer getmImageMaxWidth(){
        if (mImageMaxWidth==null){
            mImageMaxWidth=mImageView.getWidth();
        }
        return mImageMaxWidth;
    }

    private Integer getmImageMaxHeight(){
        if (mImageMaxHeight==null){
            mImageMaxHeight=mImageView.getWidth();
        }
        return mImageMaxHeight;
    }

    private Pair<Integer, Integer> getTargetedWidthHeight(){
        int targetWidth;
        int targetHeight;
        int maxWidthForPortraidMode=getmImageMaxWidth();
        int maxHeightForPortraidMode=getmImageMaxHeight();
        targetWidth=maxWidthForPortraidMode;
        targetHeight=maxHeightForPortraidMode;
        return new Pair<>(targetWidth, targetHeight);
    }
    public void onItemSelected(Bitmap imgBitmap){
        mSelectedImage = imgBitmap;
        if (mSelectedImage!= null){
            Pair<Integer, Integer> targetedSize = getTargetedWidthHeight();
            int targeteWidth = targetedSize.first;
            int maxHeight = targetedSize.second;
            float scaleFactor = Math.max((float) mSelectedImage.getWidth() / (float) targeteWidth,
                    (float) mSelectedImage.getHeight() / (float) maxHeight);
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(mSelectedImage,
                    (int)(mSelectedImage.getWidth()/ scaleFactor),
                    (int)(mSelectedImage.getHeight()/ scaleFactor),true);
            mImageView.setImageBitmap(resizedBitmap);
            mSelectedImage = resizedBitmap;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){ }


}