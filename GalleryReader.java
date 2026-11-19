package com.example.elderassist;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import java.util.ArrayList;
import java.util.List;

public class GalleryReader {
    private final Context context;

    public GalleryReader(Context context) {
        this.context = context;
    }

    public List<String> getImagePaths(int limit) {
        List<String> paths = new ArrayList<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.Images.Media.DATA };
        String sortOrder = MediaStore.Images.Media.DATE_ADDED + " DESC";

        try (Cursor cursor = context.getContentResolver().query(uri, projection, null, null, sortOrder)) {
            if (cursor != null) {
                int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                int count = 0;
                while (cursor.moveToNext() && count < limit) {
                    String path = cursor.getString(colIndex);
                    if (path != null) {
                        paths.add(path);
                        count++;
                    }
                }
            }
        }
        return paths;
    }
}

