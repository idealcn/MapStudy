package com.idealcn.map;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        "android.permission.ACCESS_FINE_LOCATION";
   "android.permission.ACCESS_COARSE_LOCATION";
         */

        if (PackageManager.PERMISSION_GRANTED!=ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) ||
                PackageManager.PERMISSION_GRANTED!=ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)){
            ActivityCompat.requestPermissions(this,new String[]{
             Manifest.permission_group.LOCATION
            },REQUEST_LOCATION);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION){
            if (grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission_group.LOCATION)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("权限申请")
                            .setMessage("定位权限需要开启,请务必同意")
                            .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                                }
                            }).setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    System.exit(0);
                                }
                            });
                    builder.show();
                }else {
                    System.exit(0);
                }
            }else {
                startActivity(new Intent(this,MainActivity.class));
            }
        }
    }
}
