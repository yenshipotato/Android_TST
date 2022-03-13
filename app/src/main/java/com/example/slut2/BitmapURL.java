package com.example.slut2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.net.URL;
import java.net.URLConnection;
import java.net.MalformedURLException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import android.util.Log;


public class BitmapURL {
    public static final int test=0;
    public static Bitmap URLtoBitmap(String url){
        try {
            URL imgURL = new URL(url);
            URLConnection conn = imgURL.openConnection();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis=new BufferedInputStream(is);
            Bitmap bmp = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
            imgURL =null;
            return bmp;
        } catch (Exception e){
            Log.d("BIT","ERROR on URL");
            return null;
        }
    }
}