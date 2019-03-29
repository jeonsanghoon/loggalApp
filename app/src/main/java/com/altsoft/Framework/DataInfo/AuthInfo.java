package com.altsoft.Framework.DataInfo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.altsoft.Framework.Global;

public class AuthInfo {

    //endregion

    final static class LogTag {
        public static String StorageTag = "ExternalStoragePermission";
        public static String CameraTag = "CameraPermission";
    }
    public boolean grantExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (Global.getCurrentActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v(LogTag.StorageTag, "External Storage Permission is granted");
                return true;
            } else {
                Log.v(LogTag.StorageTag, "External Storage Permission is revoked");
                ActivityCompat.requestPermissions(Global.getCurrentActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                return false;
            }
        } else {
            Toast.makeText(Global.getCurrentActivity(), "External Storage Permission is Grant", Toast.LENGTH_SHORT).show();
            Log.d(LogTag.StorageTag, "External Storage Permission is Grant ");
            return true;
        }
    }

    public boolean grantDevicePermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (Global.getCurrentActivity().checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                Log.v(LogTag.StorageTag, "Read Phone Permission is granted");
                return true;
            } else {
                Log.v(LogTag.StorageTag, "Read Phone Permission is revoked");
                ActivityCompat.requestPermissions(Global.getCurrentActivity(), new String[]{Manifest.permission.READ_PHONE_STATE}, 1);

                return false;
            }
        } else {
            Toast.makeText(Global.getCurrentActivity(), "Read Phone Permission is Grant", Toast.LENGTH_SHORT).show();
            Log.d(LogTag.StorageTag, "Read Phone Permission is Grant ");
            return true;
        }
    }


    public boolean grantCameraPermission() {
        if (Build.VERSION.SDK_INT >= 23) {

            if (Global.getCurrentActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Log.v(LogTag.CameraTag, "Camera Permission is granted CAMERA");
                return true;
            } else {
                Log.v(LogTag.CameraTag, "Camera Permission is revoked");
                ActivityCompat.requestPermissions(Global.getCurrentActivity(), new String[]{Manifest.permission.CAMERA}, 1);

                return false;
            }
        } else {
            Toast.makeText(Global.getCurrentActivity(), "Camera Permission is Grant", Toast.LENGTH_SHORT).show();
            Log.d(LogTag.CameraTag, "Camera Permission is Grant ");
            return true;
        }
    }
}
