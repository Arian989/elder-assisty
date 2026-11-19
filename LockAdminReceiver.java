package com.example.elderassist;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class LockAdminReceiver extends DeviceAdminReceiver {
    @Override
    public void onEnabled(Context context, Intent intent) {
        Toast.makeText(context, "স্ক্রিন লক পারমিশন চালু হয়েছে", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        Toast.makeText(context, "স্ক্রিন লক পারমিশন বন্ধ হয়েছে", Toast.LENGTH_SHORT).show();
    }
}

