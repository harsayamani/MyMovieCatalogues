package com.mobile.harsoft.mymoviecatalogues;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Switch;

import com.mobile.harsoft.mymoviecatalogues.service.AppPreference;
import com.mobile.harsoft.mymoviecatalogues.service.DailyReminder;
import com.mobile.harsoft.mymoviecatalogues.service.ReleaseReminder;

public class SettingActivity extends AppCompatActivity {

    private Switch dailySwitch, releaseSwitch;
    private Context context;
    private AppPreference appPreference;
    private DailyReminder dailyReminder;
    private ReleaseReminder releaseReminder;
    private String time_release = "08:00";
    private String time_daily = "07:00";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        prepareView();
        enableDisableNotification();
        dailyReminder();
        releaseReminder();
    }

    private void releaseReminder() {
        releaseSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appPreference.getReleaseReminderMovie()) {
                    releaseSwitch.setChecked(false);
                    appPreference.setReleaseReminderMovie(false);
                    releaseReminder.cancelReleaseReminder(context, ReleaseReminder.KEY_REPEATING);

                } else if (!appPreference.getReleaseReminderMovie()) {
                    releaseSwitch.setEnabled(true);
                    appPreference.setReleaseReminderMovie(true);
                    releaseReminder.repeatingReleaseReminder(context, ReleaseReminder.KEY_REPEATING, time_release);
                }
            }
        });
    }

    private void enableDisableNotification() {

        if (appPreference.getDailyReminderMovie()) {
            dailySwitch.setChecked(true);
            appPreference.setDailyReminderMovie(true);
            dailyReminder.repeatingDailyReminder(context, DailyReminder.KEY_REPEATING, time_daily, getString(R.string.daily_text));
        } else if (!appPreference.getDailyReminderMovie()) {
            dailySwitch.setChecked(false);
            appPreference.setDailyReminderMovie(false);
            dailyReminder.cancelDailyReminder(context, DailyReminder.KEY_REPEATING);
        }

        if (appPreference.getReleaseReminderMovie()) {
            releaseSwitch.setChecked(true);
            appPreference.setReleaseReminderMovie(true);
            releaseReminder.repeatingReleaseReminder(context, ReleaseReminder.KEY_REPEATING, time_release);
        } else if (!appPreference.getReleaseReminderMovie()) {
            releaseSwitch.setChecked(false);
            appPreference.setReleaseReminderMovie(false);
            releaseReminder.cancelReleaseReminder(context, ReleaseReminder.KEY_REPEATING);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        enableDisableNotification();
    }

    private void dailyReminder() {
        dailySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appPreference.getDailyReminderMovie()) {
                    dailySwitch.setChecked(false);
                    appPreference.setDailyReminderMovie(false);
                    dailyReminder.cancelDailyReminder(context, DailyReminder.KEY_REPEATING);
                } else if (!appPreference.getDailyReminderMovie()) {
                    dailySwitch.setEnabled(true);
                    appPreference.setDailyReminderMovie(true);
                    dailyReminder.repeatingDailyReminder(context, DailyReminder.KEY_REPEATING, time_daily, getString(R.string.daily_text));
                }
            }
        });
    }

    private void prepareView() {
        dailySwitch = findViewById(R.id.switchDaily);
        releaseSwitch = findViewById(R.id.switchRelease);
        appPreference = new AppPreference(this);
        dailyReminder = new DailyReminder();
        releaseReminder = new ReleaseReminder();
        context = getApplicationContext();
    }


}
