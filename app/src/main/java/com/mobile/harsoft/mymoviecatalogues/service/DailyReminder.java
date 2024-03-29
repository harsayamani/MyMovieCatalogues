package com.mobile.harsoft.mymoviecatalogues.service;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.mobile.harsoft.mymoviecatalogues.MainActivity;
import com.mobile.harsoft.mymoviecatalogues.R;

import java.util.Calendar;

public class DailyReminder extends BroadcastReceiver {

    public static final String KEY_ONE_TIME = "OneTime";
    public static final String KEY_REPEATING = "Repeating";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_TYPE = "type";

    private final int KEY_NOTIF_ID_ONETIME = 100;
    private final int KEY_NOTIF_ID_REPEATING = 101;

    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(KEY_TYPE);
        String message = intent.getStringExtra(KEY_MESSAGE);

        String title;
        int notifId = type.equalsIgnoreCase(KEY_ONE_TIME) ? KEY_NOTIF_ID_ONETIME : KEY_NOTIF_ID_REPEATING;

        title = context.getResources().getString(R.string.app_name);
        dailyReminder(context, title, message, notifId);
    }

    private void dailyReminder(Context mContext, String title, String message, int notifId) {
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "Daily Reminder channel";

        NotificationManager notifManagerCompat = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent i = new Intent(mContext, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(mContext, notifId, i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(mContext, android.R.color.transparent))
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(CHANNEL_ID);

            if (notifManagerCompat != null) {
                notifManagerCompat.createNotificationChannel(channel);
            }
        }
        if (notifManagerCompat != null) {
            notifManagerCompat.notify(notifId, builder.build());
        }

    }

    public void repeatingDailyReminder(Context context, String type, String time, String message) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent i = new Intent(context, DailyReminder.class);
        i.putExtra(KEY_MESSAGE, message);
        i.putExtra(KEY_TYPE, type);

        String[] timeArray = time.split(":");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        cal.set(Calendar.SECOND, 0);

        if (cal.before(Calendar.getInstance())) cal.add(Calendar.DATE, 1);

        PendingIntent pi = PendingIntent.getBroadcast(context, KEY_NOTIF_ID_REPEATING, i, PendingIntent.FLAG_UPDATE_CURRENT);

        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
    }

    public void cancelDailyReminder(Context context, String type) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, DailyReminder.class);

        int requestCode = type.equalsIgnoreCase(KEY_ONE_TIME) ? KEY_NOTIF_ID_ONETIME : KEY_NOTIF_ID_REPEATING;
        PendingIntent pi = PendingIntent.getBroadcast(context, requestCode, i, 0);
        am.cancel(pi);
    }
}
