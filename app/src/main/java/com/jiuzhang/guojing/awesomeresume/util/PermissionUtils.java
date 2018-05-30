package com.jiuzhang.guojing.awesomeresume.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissionUtils {
    public static final int REQ_CODE_WRITE_EXTERNAL_STORAGE = 200;

    public static boolean checkPermission(Context context,String permission){
        return Build.VERSION.SDK_INT <Build.VERSION_CODES.M
                || ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermissions (Activity activity, String[] permission, int reqCode){
        ActivityCompat.requestPermissions(activity,permission,reqCode);
    }

    public static void requestReadExternalStoragePermission(Activity activity){
        requestPermissions(activity, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_CODE_WRITE_EXTERNAL_STORAGE);
    }
}
