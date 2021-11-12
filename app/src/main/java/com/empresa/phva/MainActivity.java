package com.empresa.phva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

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
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView mImageView;
    private Button mTextButton;
    private Bitmap mSelectedImage;
    private SuperposicionGrafica mSuperposicionGrafica;
    private Integer mImageMaxWidth;
    private Integer mImageMaxHeight;
    private static final int RESULTS_TO_SHOW = 10;
    Button btnCamara;
    TextView textView;

    PreviewView previewView;
    private ImageCapture imageCapture;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextButton = findViewById(R.id.button_text);
      //  mImageView = findViewById(R.id.image_view);
     //   mSuperposicionGrafica = findViewById(R.id.graphic_overlay);
        btnCamara = findViewById(R.id.btn_camera);
        textView = findViewById(R.id.textView);
        previewView = findViewById(R.id.previewView);

        btnCamara.setOnClickListener(this);
        mTextButton.setOnClickListener(this);


        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() ->{
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                startCameraX(cameraProvider);
            } catch (ExecutionException e){
                e.printStackTrace();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }, getExecutor());

        /*
        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camara();
            }
        });

        mTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runTextRecognition();
            }
        });
        */

    }

    private Executor getExecutor(){
        return ContextCompat.getMainExecutor(this);
    }

    private void startCameraX(ProcessCameraProvider cameraProvider){
        cameraProvider.unbindAll();
        // Camera Selector use Case
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();
        // Preview usecase
        Preview preview =  new Preview.Builder().build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        // Image capture use case
        imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build();

        cameraProvider.bindToLifecycle(this , cameraSelector, preview, imageCapture);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_camera:
                capturePhoto();
                break;
            case R.id.button_text:
                break;
        }
    }

    private void capturePhoto() {
        File photoDir = new  File("/mnt/sdcard/Pictures/CameraXPhotos");

        if(!photoDir.exists()){
            photoDir.mkdir();
        }

        Date date= new Date();
        String timestamp = String.valueOf(date.getTime());
        String phothoFilePath = photoDir.getAbsolutePath() + "/" + timestamp + ".jpg";

        File photoFile = new File(phothoFilePath);

        imageCapture.takePicture(
                new ImageCapture.OutputFileOptions.Builder(photoFile).build(),
                getExecutor(),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        Toast.makeText(MainActivity.this, "Photo has been saved successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Toast.makeText(MainActivity.this, "Error saving phot: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

/*
    private void camara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(intent, 1);
        }
    }
*/
/*
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap)  extras.get("data");
            mImageView.setImageBitmap(imgBitmap);
            onItemSelected(imgBitmap);
        }
    }
*/
    /*
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
*/
/*
    private void processTextRecognitionResult(Text texts){
        List<Text.TextBlock> blocks = texts.getTextBlocks();
        textView.setText(texts.getText());
    }
*/
    /*
    private void  showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    */
     /*
    private Integer getmImageMaxWidth(){
        if (mImageMaxWidth==null){
            mImageMaxWidth=mImageView.getWidth();
        }
        return mImageMaxWidth;
    }
*/
    /*
    private Integer getmImageMaxHeight(){
        if (mImageMaxHeight==null){
            mImageMaxHeight=mImageView.getWidth();
        }
        return mImageMaxHeight;
    }
*/
    /*
    private Pair<Integer, Integer> getTargetedWidthHeight(){
        int targetWidth;
        int targetHeight;
        int maxWidthForPortraidMode=getmImageMaxWidth();
        int maxHeightForPortraidMode=getmImageMaxHeight();
        targetWidth=maxWidthForPortraidMode;
        targetHeight=maxHeightForPortraidMode;
        return new Pair<>(targetWidth, targetHeight);
    }
    */
    /*
    public void onItemSelected(Bitmap imgBitmap){
        mSuperposicionGrafica.clear();
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
*/
    /*
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){ }
*/

}