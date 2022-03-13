package com.example.slut2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyService extends FirebaseMessagingService {
    public static final String TAG = MyService.class.getSimpleName()+"My";
    private String CHANNEL_ID = "Coder";
    public MyService() {
    }

    @Override
    public void onNewToken(@NonNull String s){
        super.onNewToken(s);
        Log.d(TAG,"D Token"+s);
    }
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage){
        super.onMessageReceived(remoteMessage);
        Bitmap bitmap=BitmapURL.URLtoBitmap("https://images.pexels.com/photos/1440403/pexels-photo-1440403.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260");
        Log.d(TAG, "onMessageReceived: "+remoteMessage.getData());
        /**檢查手機版本是否支援通知；若支援則新增"頻道"*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "DemoCode", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            assert manager != null;
            manager.createNotificationChannel(channel);

        }
        Map<String,String> s = remoteMessage.getData();
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("data",s.get("key_1"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0
                ,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        /**建置通知欄位的內容*/
        NotificationCompat.Builder builder
                = new NotificationCompat.Builder(MyService.this,CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(s.get("title"))
                .setContentText(s.get("body"))
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(pendingIntent)
                .setLargeIcon(bitmap)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null));

        /**發出通知*/
        NotificationManagerCompat notificationManagerCompat
                = NotificationManagerCompat.from(MyService.this);
        notificationManagerCompat.notify(1,builder.build());
        //到這邊
    }
}