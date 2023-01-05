package com.wakili.whatsappstatusaver.Activity;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.wakili.whatsappstatusaver.R;
import com.wakili.whatsappstatusaver.Util.Method;

import java.util.Objects;


public class SplashScreen extends AppCompatActivity {

    private Method method;
    private static final int REQUEST_PERMISSIONS = 1234;
    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final String MANAGE_EXTERNAL_STORAGE_PERMISSION = "android:manage_external_storage";

    private final Handler handler = new Handler(Looper.getMainLooper());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_splash_screen);


        method = new Method(SplashScreen.this);
        method.forceRTLIfSupported();
        method.changeStatusBarColor();


        // splash screen timer
        int SPLASH_TIME_OUT = 2000;
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                if (method.isAppWA() && method.isAppWB()) {
                    method.editor.putString(method.pref_link, "wball");
                    method.editor.commit();
                } else if (method.isAppWA()) {
                    method.editor.putString(method.pref_link, "w");
                    method.editor.commit();
                } else if (method.isAppWB()) {
                    method.editor.putString(method.pref_link, "wb");
                    method.editor.commit();
                }

                startActivity(new Intent(SplashScreen.this, WelcomeActivity.class));
                finishAffinity();

            }
        }, SPLASH_TIME_OUT);

//        if (!arePermissionDenied()) {
//            next();
//            return;
//        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && arePermissionDenied()) {
//
//            requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);
//        }

    }
}
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (!arePermissionDenied()) {
//            next();
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_PERMISSIONS && grantResults.length > 0) {
//            if (arePermissionDenied()) {
//                // Clear Data of Application, So that it can request for permissions again
//                ((ActivityManager) Objects.requireNonNull(this.getSystemService(ACTIVITY_SERVICE))).clearApplicationUserData();
//                recreate();
//            } else {
//                next();
//            }
//        }
//    }
//
//    private void next() {
//
//        handler.postDelayed(() -> {
//            startActivity(new Intent(SplashScreen.this, MainActivity.class));
//            finish();
//        }, 1000);
//
//    }
//
//    private boolean arePermissionDenied() {
//
//        for (String permissions : PERMISSIONS) {
//            if (ActivityCompat.checkSelfPermission(getApplicationContext(), permissions) != PackageManager.PERMISSION_GRANTED) {
//                return true;
//            }
//        }
//        return false;
//    }
//}