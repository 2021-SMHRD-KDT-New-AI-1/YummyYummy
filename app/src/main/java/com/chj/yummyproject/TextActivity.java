package com.chj.yummyproject;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class TextActivity extends Activity implements CameraBridgeViewBase.CvCameraViewListener2{
    private static final String TAG="MainActivity";

    private Mat mRgba;
    private Mat mGray;
    private CameraBridgeViewBase mOpenCvCameraView;

    private ImageView translate_button;
    private ImageView take_picture_button;
    private ImageView show_image_button;

    private ImageView current_image;
    private TextView textview;

    private TextRecognizer textRecognizer;

    private String camera_or_recognizeText = "camera";
    // bitmap ???????????? ??????
    private Bitmap bitmap = null;

    private Uri pictureUri;
    private ImageView save_picture;



    private BaseLoaderCallback mLoaderCallback =new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status){
                case LoaderCallbackInterface
                        .SUCCESS:{
                    Log.i(TAG,"OpenCv Is loaded");
                    mOpenCvCameraView.enableView();
                }
                default:
                {
                    super.onManagerConnected(status);

                }
                break;
            }
        }
    };


    public TextActivity(){
        Log.i(TAG,"Instantiated new "+this.getClass());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        int MY_PERMISSIONS_REQUEST_CAMERA=0;
        if (ContextCompat.checkSelfPermission(TextActivity.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(TextActivity.this, new String[] {Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        }

        setContentView(R.layout.activity_text);

        mOpenCvCameraView= findViewById(R.id.frame_Surface);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);

        textRecognizer = TextRecognition.getClient(new KoreanTextRecognizerOptions.Builder().build());

        textview=findViewById(R.id.textview);
        textview.setVisibility(View.GONE);
        current_image =findViewById(R.id.current_image);
        current_image.setVisibility(View.GONE);


        take_picture_button = findViewById(R.id.take_picture_button);
        take_picture_button.setOnTouchListener(new View.OnTouchListener() {



            public void onSelectImageClick(View view) {
                CropImage.activity(null).setGuidelines(CropImageView.Guidelines.ON);
            }

            protected void onActivityResult(int requestCode, int resultCode, Intent data) {

                requestCode = CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE;
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                //?????? ?????????
                resultCode = RESULT_OK;
                take_picture_button.setImageURI(result.getUri());

            }

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    return true;
                }

                if(event.getAction()==MotionEvent.ACTION_UP){

                    if(camera_or_recognizeText=="camera"){
                        take_picture_button.setColorFilter(Color.DKGRAY);
                        Mat a=mRgba.t();
                        Core.flip(a,mRgba,1);
                        a.release();
                        bitmap = Bitmap.createBitmap(mRgba.cols(), mRgba.rows(),Bitmap.Config.ARGB_8888);
                        Utils.matToBitmap(mRgba,bitmap);
                        mOpenCvCameraView.disableView();

                        camera_or_recognizeText ="recognizeText";

                    }


                    else{
                        take_picture_button.setColorFilter(Color.WHITE);
                        textview.setVisibility(View.GONE);
                        current_image.setVisibility(View.GONE);
                        mOpenCvCameraView.enableView();
                        textview.setText("");
                        camera_or_recognizeText="camera";
                    }


                    return true;
                }


                return false;
            }
        });

        translate_button = findViewById(R.id.translate_button);
        Button btn_send = findViewById(R.id.btn_send);
        translate_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    translate_button.setColorFilter(Color.DKGRAY);
                    return true;
                }

                if(event.getAction()==MotionEvent.ACTION_UP){
                    translate_button.setColorFilter(Color.WHITE);
                    if(camera_or_recognizeText=="recognizeText"){
                        textview.setVisibility(View.VISIBLE);
                        InputImage image = InputImage.fromBitmap(bitmap,0);

                        //recognize text
                        Task<Text> result = textRecognizer.process(image)
                                .addOnSuccessListener(new OnSuccessListener<Text>() {
                                    @Override
                                    public void onSuccess(Text text) {
                                        textview.setText(text.getText());
                                        Log.d("TextActivity","Out"+text.getText());
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });

                    }
                    else{
                        Toast.makeText(TextActivity.this,"Please take a picture",
                                Toast.LENGTH_LONG).show();
                    }

                    return true;
                }
                return false;
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TextActivity.this, FoodInfoActivity.class);
                intent.putExtra("result", textview.getText().toString());
                startActivity(intent);
            }
        });


        show_image_button = findViewById(R.id.show_image_button);
        show_image_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    show_image_button.setColorFilter(Color.DKGRAY);
                    return true;
                }

                if(event.getAction()==MotionEvent.ACTION_UP){
                    show_image_button.setColorFilter(Color.WHITE);
                    if(camera_or_recognizeText=="recognizeText"){

                    }
                    else{
                        Toast.makeText(TextActivity.this,"Please take a picture",
                                Toast.LENGTH_LONG).show();
                    }

                    return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (OpenCVLoader.initDebug()){
            //if load success
            Log.d(TAG,"Opencv initialization is done");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
        else{
            //if not loaded
            Log.d(TAG,"Opencv is not loaded. try again");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0,this,mLoaderCallback);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mOpenCvCameraView!=null){
            mOpenCvCameraView.disableView();
        }
    }

    public void onDestroy(){
        super.onDestroy();
        if(mOpenCvCameraView!=null){
            mOpenCvCameraView.disableView();
        }

    }

    public void onCameraViewStarted(int width ,int height){
        mRgba=new Mat(height,width, CvType.CV_8UC4);
        mGray =new Mat(height,width,CvType.CV_8UC1);
    }
    public void onCameraViewStopped(){
        mRgba.release();
    }
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame){
        mRgba=inputFrame.rgba();
        mGray=inputFrame.gray();

        return mRgba;

    }

}