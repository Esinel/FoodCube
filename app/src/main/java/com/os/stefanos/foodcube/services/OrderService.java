package com.os.stefanos.foodcube.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.os.stefanos.foodcube.R;
import com.os.stefanos.foodcube.activities.MealActivity;

/**
 * Created by stefanos on 12.9.16..
 */
public class OrderService extends IntentService{

    private NotificationManager mNotificationManager;
    public static final int NOTIFICATION_ID = 1;
    public static final String TRANSITION_INTENT_SERVICE = "ReceiveTransitionsIntentService";


    public OrderService(){
        super("Gas");
    }

    public OrderService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Thread.sleep(5000);
            sendNotification("Narudzbina prihvacena! Stize na vasu adresu za 45min.");
        } catch (Exception e) {
            // Restore interrupt status.
            Thread.currentThread().interrupt();
        }
    }

    private void sendNotification(String msg){
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent resultIntent = new Intent(this, MealActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MealActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("FoodCube")
                        .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(msg))
                        .setContentText(msg)
                        .setSmallIcon(R.drawable.ic_swap_horizontal_white_24dp).setSound(alarmSound);

        mBuilder.setContentIntent(resultPendingIntent);

        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
