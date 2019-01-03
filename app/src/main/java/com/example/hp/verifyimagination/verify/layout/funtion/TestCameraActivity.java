package com.example.hp.verifyimagination.verify.layout.funtion;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.hp.verifyimagination.ParametersWrapper;
import com.example.hp.verifyimagination.R;
import com.example.hp.verifyimagination.util.Blur;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by brant on 18-7-21.
 */

public class TestCameraActivity extends Activity {

    private SurfaceView surfaceCamera;
    private SurfaceHolder holder;
    private Camera camera;
    private int viewWidth;
    private int viewHeight;
    private ImageView ivBlur;
    private Bitmap bmp;
    private Handler mHandler = new Handler(){};
    private Bitmap nbmp2;
    private SeekBar seekBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        final PackageManager packageManager = getPackageManager();
        try {
            ActivityInfo activityInfo = packageManager.getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
            int orientation = activityInfo.screenOrientation;
            Log.e("TestCameraActivity","onCreate() -->>  orientation : "+orientation);// -1
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        surfaceCamera = findViewById(R.id.surface_camera);
        holder = surfaceCamera.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                initCamera();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

                if( camera != null ){
                    camera.stopPreview();
                }
            }
        });
        ivBlur = findViewById(R.id.iv_blur);
        seekBar = findViewById(R.id.seek_alpha);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                ivBlur.setAlpha(255*progress/100);
//                Log.e("TestCamera","onProgressChanged");
//                Camera.Parameters parameters = camera.getParameters();
//                parameters.setWhiteBalance("manual");
//                parameters.set("manual-wb-type",""+0);
//                parameters.set("manual-wb-value",""+2000+600*progress);
//                camera.setParameters(parameters);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                Log.e("TestCamera","onProgressChanged");
                Camera.Parameters parameters = camera.getParameters();
                ParametersWrapper.setZSLMode(parameters, "off");
                ParametersWrapper.setCameraMode(parameters, 1);
                List<String> supportedWhiteBalance = parameters.getSupportedWhiteBalance();
                for (String white : supportedWhiteBalance){
                    Log.e("TestCamera","support  "+white+"  "+ParametersWrapper.getISOValue(parameters)+"  "+Integer.toString(ParametersWrapper.getContrast(parameters)));
                }
                Method[] declaredMethods = parameters.getClass().getDeclaredMethods();
                for (int i = 0; i < declaredMethods.length; i++){
                    Log.e("TestCamera","declaredMethods : "+declaredMethods[i].getName());
                }
                Field[] declaredFields = parameters.getClass().getDeclaredFields();
                for (int i = 0; i < declaredFields.length; i++){
                    Log.e("TestCamera","declaredFields : "+declaredFields[i].getName()+"  ");
                }
                try {
//                    Field whiteBalanceManualCCT = parameters.getClass().getDeclaredField("WHITE_BALANCE_MANUAL_CCT");
//                    String name = whiteBalanceManualCCT.getName();
//                    String value = (String) whiteBalanceManualCCT.get(parameters);
//                    parameters.set(value,""+(2000+60*progress));
//                    Method method_setAutoWhiteBalanceLock = parameters.getClass().getDeclaredMethod("setAutoWhiteBalanceLock");
//                    method_setAutoWhiteBalanceLock.invoke(parameters,true);
                    Method method_setWBManualCCT = parameters.getClass().getDeclaredMethod("setWBManualCCT");
                    method_setWBManualCCT.setAccessible(true);
                    method_setWBManualCCT.invoke(parameters,(2000+60*progress));

//                    parameters.setWhiteBalance("manual");
                    //0 corresponds to manual CCT mode
                    parameters.set("manual-wb-type", 0);
                    parameters.set("manual-wb-value", (2000+60*progress));

                    camera.setParameters(parameters);

                    Method method_getWBCurrentCCT = parameters.getClass().getDeclaredMethod("getWBCurrentCCT");
                    String currentCCT = (String) method_getWBCurrentCCT.invoke(parameters);
                    Log.e("TestCamera","currentCCT : "+currentCCT);
                } catch (IllegalAccessException e) {
                    Log.e("TestCamera","IllegalAccessException : currentCCT");
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    Log.e("TestCamera","NoSuchMethodException : currentCCT  "+e.getMessage());
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    Log.e("TestCamera","InvocationTargetException : currentCCT");
                    e.printStackTrace();
                }
                String str = parameters.get("manual-wb-modes");
//                Log.e("TestCamera","manual-wb-modes support  "+str+parameters.getInt("min-wb-cct"));
//                Integer.toString(ParametersWrapper.getSaturation(parameters));
//                        Integer.toString(ParametersWrapper.getContrast(parameters));
//                        Integer.toString(ParametersWrapper.getSharpness(parameters));
//                parameters.setWhiteBalance("auto");
//                parameters.set("manual-wb-type",""+0);
//                parameters.set("manual-wb-value",""+2000+600*progress);

//                String currentCCT = parameters.get("manual-cct");
//                Log.e("TestCamera","currentCCT : "+currentCCT);
//                ParametersWrapper.setWBManualCCT(parameters,2000+60*progress);
//                try {
//                    Method method_setAutoWhiteBalanceLock = Camera.Parameters.class.getDeclaredMethod("setAutoWhiteBalanceLock");
//                    method_setAutoWhiteBalanceLock.invoke(parameters,true);
//                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//                Log.e("TestCamera", "CCT : "+ParametersWrapper.getWBCurrentCCT(parameters)+" min : "+ParametersWrapper.getMinWBCCT(parameters));
//                camera.setParameters(parameters);
//                Log.e("TestCamera", "2CCT : "+ParametersWrapper.getWBCurrentCCT(parameters));
            }
        });

//        getPreViewImage();
        Button btnTakePicture = findViewById(R.id.btn_take_picture);
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getPreViewImage();
//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Bitmap blur = Blur.fastblur(TestCameraActivity.this,nbmp2,30);
//                        ivBlur.setImageBitmap(blur);
//                    }
//                },50);


//                Bitmap blur = Blur.fastblur(TestCameraActivity.this,nbmp2,25);
//                ivBlur.setImageBitmap(blur);
                Log.e("TestCamera","onclick -->");

                camera.takePicture(null, null, null, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(final byte[] data, final Camera camera) {
                        camera.startPreview();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("TestCamera","onclick --> onPictureTaken");
                                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                String fileName = getPhotoFileName();
                                File file = new File(PHOTO_PATH+fileName);
                                try {
                                    FileOutputStream fos = new FileOutputStream(file);
                                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
                                    bitmap.recycle();
                                    fos.close();
                                    Log.e("TestCamera","save picture success");
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                    Log.e("TestCamera","onclick --> FileNotFound"+e.getMessage());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Log.e("TestCamera","onclick -->     "+e.getMessage());
                                }

                                try {
                                    MediaStore.Images.Media.insertImage(TestCameraActivity.this.getContentResolver(),file.getAbsolutePath(),fileName,null);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                TestCameraActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));

                            }
                        }).start();

                    }
                });
            }
        });


        Button btnDismiss = findViewById(R.id.btn_dismiss);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int progress = seekBar.getProgress();
                Bitmap blur = Blur.fastblur(TestCameraActivity.this,nbmp2,progress);
                ivBlur.setImageBitmap(blur);
            }
        });

    }

    public final static String PHOTO_PATH =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();

    public static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'LOCK'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }


    @Override
    protected void onPause() {
        super.onPause();
        if( camera != null )
            camera.release();
    }

    private void getPreViewImage() {

//        camera.setPreviewCallback(new Camera.PreviewCallback(){
//
//            @Override
//            public void onPreviewFrame(byte[] data, Camera camera) {
//                Camera.Size size = camera.getParameters().getPreviewSize();
//                try{
//                    YuvImage image = new YuvImage(data, ImageFormat.NV21, size.width, size.height, null);
//                    if(image!=null){
//                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                        image.compressToJpeg(new Rect(0, 0, size.width, size.height), 80, stream);
//                        bmp = BitmapFactory.decodeByteArray(data,0,data.length);
////                        bmp = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());
//                        Log.e("TestCamera","onPreviewFrame -> "+bmp.toString());
//                        //**********************
//                        //因为图片会放生旋转，因此要对图片进行旋转到和手机在一个方向上
//                        rotateMyBitmap(bmp);
//                        //**********************************
//
//                        stream.close();
//                    }
//                }catch(Exception ex){
//                    Log.e("Sys","Error:"+ex.getMessage());
//                }
//            }
//        });

        camera.setOneShotPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                Camera.Size size = camera.getParameters().getPreviewSize();
                try{
                    YuvImage image = new YuvImage(data, ImageFormat.NV21, size.width, size.height, null);
                    if(image!=null){
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        image.compressToJpeg(new Rect(0, 0, size.width, size.height), 1, stream);
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 8;
                        bmp = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size(),options);
                        rotateMyBitmap(bmp);
                        bmp.recycle();
                        stream.close();
                    }
                }catch(Exception ex){
                    Log.e("Sys","Error:"+ex.getMessage());
                }
            }
        });
    }


    public void rotateMyBitmap(Bitmap bmp){
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        nbmp2 = Bitmap.createBitmap(bmp, 0,0, bmp.getWidth(),  bmp.getHeight(), matrix, true);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if( surfaceCamera != null ){
            viewWidth = surfaceCamera.getWidth();
            viewHeight = surfaceCamera.getHeight();
        }
    }

    private void initCamera() {
        camera = Camera.open();
        if( camera != null ){
            camera.setDisplayOrientation(90);
            try {
                Camera.Parameters parameters = camera.getParameters();
                parameters.setPreviewFpsRange(viewWidth, viewHeight);
                parameters.setPreviewFpsRange(4, 10);
                parameters.setPictureFormat(ImageFormat.JPEG);
                parameters.set("jpeg-quality", 90);
                parameters.setPictureSize(viewWidth, viewHeight);
                camera.setPreviewDisplay(holder);
                camera.startPreview();
                getPreViewImage();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}
