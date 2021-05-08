package com.huybinh2k.mymusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

/**
 * Create by BinhBH 5/8/2021
 */
public class SplashScreen extends AppCompatActivity {
    private static final int REQUEST_CODE = 113;
    private boolean mAllPermissionsGranted;
    /**
     * Runnable ngủ 1s và chuyển sang ActivityMusic
     */
    private final Runnable mWait1s = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            //BinhBH Thread.sleep bat buoc can try catch
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startActivityMusic();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermission();
        if (mAllPermissionsGranted){
            Thread thread = new Thread(mWait1s);
            thread.start();
        }
    }

    /**
     * Xin quyền đọc/ghi EXTERNAL_STORAGE
     */
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
            }else {
                mAllPermissionsGranted = true;
            }
        }
    }

    /**
     * Khi khi xin quyền xong
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE){
            if(hasAllPermissionsGranted(grantResults)){
                startActivityMusic();
            }else {
                checkPermission();
            }
        }
    }

    /**
     * @return true nếu tất cả quyền được cấp, false nếu có 1 quyền không được cấp
     */
    public boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    /**
     * Chuyển sang ActivityMusic
     */
    private void startActivityMusic(){
        Intent intent = new Intent(SplashScreen.this, ActivityMusic.class);
        startActivity(intent);
        finish();
    }
}