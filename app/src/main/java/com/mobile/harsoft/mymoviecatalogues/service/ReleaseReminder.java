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
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.mobile.harsoft.mymoviecatalogues.BuildConfig;
import com.mobile.harsoft.mymoviecatalogues.DetailMovieActivity;
import com.mobile.harsoft.mymoviecatalogues.R;
import com.mobile.harsoft.mymoviecatalogues.api.APIClient;
import com.mobile.harsoft.mymoviecatalogues.model.Movie;
import com.mobile.harsoft.mymoviecatalogues.response.ResultMovie;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReleaseReminder extends BroadcastReceiver {

    public static final String KEY_ONE_TIME = "OneTime";
    public static final String KEY_REPEATING = "Repeating";
    public static final String KEY_TYPE = "type";

    private final int KEY_NOTIF_ID_REPEATING = 101;

    private ArrayList<Movie> movies;

    @Override
    public void onReceive(Context context, Intent intent) {
        loadMovie(context);
    }

    private void loadMovie(final Context context) {

        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        Call<ResultMovie> call = APIClient.getInstance()
                .baseAPI()
                .getMovieReleaseToday(BuildConfig.API_KEY, today, today);

        call.enqueue(new Callback<ResultMovie>() {
            @Override
            public void onResponse(@NonNull Call<ResultMovie> call, @NonNull Response<ResultMovie> response) {
                if (response.isSuccessful()) {
                    ResultMovie resultMovie = response.body();
                    assert resultMovie != null;
                    movies = new ArrayList<>(Arrays.asList(resultMovie.getMovies()));

                    int notifId = 200;

                    for (int i = 0; i < movies.size(); i++) {
                        String title = movies.get(i).getTitle();
                        releaseReminder(context, title, i, notifId);
                    }

//                    int index = new Random().nextInt(movies.size());


                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultMovie> call, @NonNull Throwable t) {

            }
        });
    }

    private void releaseReminder(Context context, String title, int index, int notifId) {
        String CHANNEL_ID = "Channel_2";
        String CHANNEL_NAME = "Release Reminder channel";

        NotificationManager notifManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Movie movie = new Movie();
        movie.setId(movies.get(index).getId());
        movie.setTitle(movies.get(index).getTitle());
        movie.setOverview(movies.get(index).getOverview());
        movie.setPoster_path(movies.get(index).getPoster_path());
        movie.setRelease_date(movies.get(index).getRelease_date());
        movie.setVote_average(movies.get(index).getVote_average());
        movie.setVote_count(movies.get(index).getVote_count());
        movie.setGenre_ids(movies.get(index).getGenre_ids());
        movie.setPopularity(movies.get(index).getPopularity());
        Intent intent = new Intent(context, DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(title + " " + context.getString(R.string.release_text))
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setContentIntent(pendingIntent)
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

    public void repeatingReleaseReminder(Context mContext, String type, String time) {
        AlarmManager am = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

        Intent i = new Intent(mContext, ReleaseReminder.class);
        i.putExtra(KEY_TYPE, type);

        String[] timeArray = time.split(":");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        cal.set(Calendar.SECOND, 0);

        if (cal.before(Calendar.getInstance())) cal.add(Calendar.DATE, 1);

        PendingIntent pi = PendingIntent.getBroadcast(mContext, KEY_NOTIF_ID_REPEATING, i, PendingIntent.FLAG_UPDATE_CURRENT);

        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
    }

    public void cancelReleaseReminder(Context mContext, String type) {
        AlarmManager am = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(mContext, ReleaseReminder.class);

        int requestCode = type.equalsIgnoreCase(KEY_ONE_TIME) ? KEY_NOTIF_ID_REPEATING : KEY_NOTIF_ID_REPEATING;
        PendingIntent pi = PendingIntent.getBroadcast(mContext, requestCode, i, 0);

        am.cancel(pi);
    }
}
