package com.example.elderassist;

import android.Manifest;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.webkit.JavascriptInterface;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionBridge {
    private final Activity activity;
    private final DevicePolicyManager dpm;
    private final ComponentName adminComponent;

    public PermissionBridge(Activity activity) {
        this.activity = activity;
        this.dpm = (DevicePolicyManager) activity.getSystemService(Context.DEVICE_POLICY_SERVICE);
        this.adminComponent = new ComponentName(activity, LockAdminReceiver.class);
    }

    // ১. স্ক্রিন লক পারমিশন চালু করা
    @JavascriptInterface
    public void requestLockPermission() {
        if (!dpm.isAdminActive(adminComponent)) {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponent);
            intent.putExtra(
                DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "সহজ সহায়তা অ্যাপ দিয়ে স্ক্রিন লক করার জন্য এই অনুমতি দিন।"
            );
            activity.startActivity(intent);
        }
    }

    // স্ক্রিন লক পারমিশন চালু আছে কি না চেক করা
    @JavascriptInterface
    public boolean isLockPermissionGranted() {
        return dpm.isAdminActive(adminComponent);
    }

    // সাথে সাথে স্ক্রিন লক করা
    @JavascriptInterface
    public boolean lockScreenNow() {
        if (dpm.isAdminActive(adminComponent)) {
            dpm.lockNow();
            return true;
        } else {
            requestLockPermission();
            return false;
        }
    }

    // ২. নোটিফিকেশন ও কল পারমিশন চাওয়া
    @JavascriptInterface
    public void requestRuntimePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    activity,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS, Manifest.permission.CALL_PHONE},
                    101
                );
            }
        }
    }

    // ৩. অ্যাপের সেটিংস পেজ ওপেন করা (অন্যান্য পারমিশনের জন্য)
    @JavascriptInterface
    public void openAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivity(intent);
    }
}

