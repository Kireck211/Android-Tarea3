package mx.iteso.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import mx.iteso.app.beans.City;
import mx.iteso.app.beans.Store;
import mx.iteso.app.beans.User;
import mx.iteso.app.database.CityControl;
import mx.iteso.app.database.DataBaseHandler;
import mx.iteso.app.database.StoreControl;
import mx.iteso.app.utils.Constants;

import static mx.iteso.app.utils.Constants.LGD_PREFERENCE;
import static mx.iteso.app.utils.Constants.NAME_PREFERENCE;
import static mx.iteso.app.utils.Constants.PWD_PREFERENCE;
import static mx.iteso.app.utils.Constants.USER_PREFERENCES;

public class ActivitySplashScreen extends AppCompatActivity {
    private static final String TAG = "Debug " + ActivitySplashScreen.class.getSimpleName();

    private static final String[] specialCities = new String[]{"Zacatecas", "Lazaro cardenas", "Guadalajara"};
    private static final String[] storeNames = new String[]{"Best Buy", "Amazon", "Segunda Mano"};
    private static final double[] storeLatitudes = new double[]{22.775721, 17.931608, 20.606205};
    private static final double[] storeLongitudes = new double[]{-102.572409, -102.225182, -103.415476};

    private static final int THUMBNAIL = 0;
    private static final int LOWER_BOUND = 0;
    private static final int UPPER_BOUND = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        DataBaseHandler dh = DataBaseHandler.getInstance(this);

        ArrayList<Store> stores = StoreControl.getStores(dh);

        if (stores.size() == 0) {
            City city;
            Store store;
            for (int i = 0; i < specialCities.length; i++) {
                city = CityControl.getCityByName(specialCities[i], dh);
                store = new Store();
                store.setName(storeNames[i]);
                store.setPhone(generatePhone());
                store.setThumbnail(THUMBNAIL);
                store.setLatitude(storeLatitudes[i]);
                store.setLongitude(storeLongitudes[i]);
                store.setCity(city);

                if (!StoreControl.addStore(store, dh)) {
                    Log.e(TAG, "Error on adding store");
                }
            }
        }

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

    public String generatePhone() {
        StringBuilder phone = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 10; i++) {
            phone.append(random.nextInt((UPPER_BOUND - LOWER_BOUND) + 1) + LOWER_BOUND);
        }
        return phone.toString();
    }
}
