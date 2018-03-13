package mx.iteso.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import mx.iteso.app.beans.User;
import mx.iteso.app.utils.Constants;

import static mx.iteso.app.utils.Constants.LGD_PREFERENCE;
import static mx.iteso.app.utils.Constants.NAME_PREFERENCE;
import static mx.iteso.app.utils.Constants.PWD_PREFERENCE;
import static mx.iteso.app.utils.Constants.USER_PREFERENCES;

public class ActivitySplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                User user = loadUser();
                Intent intent;
                if (user.isLogged()) {
                    intent = new Intent(ActivitySplashScreen.this, ActivityMain.class);
                } else {
                    intent = new Intent(ActivitySplashScreen.this, ActivityLogin.class);
                }
                startActivity(intent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 2000);
    }

    public User loadUser() {
        SharedPreferences sharedPreferences =
                getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
        User user = new User();
        user.setName(sharedPreferences.getString(NAME_PREFERENCE, null));
        user.setPassword(sharedPreferences.getString(PWD_PREFERENCE, null));
        user.setLogged(sharedPreferences.getBoolean(LGD_PREFERENCE, false));
        sharedPreferences = null;
        return user;
    }
}
