package com.example.snach.tprk1weather;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

import ru.mail.weather.lib.City;
import ru.mail.weather.lib.Weather;
import ru.mail.weather.lib.WeatherStorage;
import ru.mail.weather.lib.WeatherUtils;

/**
 * Created by Snach on 29.09.16.
 */
public class MyIntentService extends IntentService {
    private final static String TAG = MyIntentService.class.getSimpleName();
    final String ACTION = "WEATHER_CHANGED";


    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        WeatherStorage weatherStorage = WeatherStorage.getInstance(this);

        try {
            City city = weatherStorage.getCurrentCity();
            Weather weather = WeatherUtils.getInstance().loadWeather(city);
            weatherStorage.saveWeather(city, weather);
            Intent newIntent = new Intent(ACTION);
            sendBroadcast(newIntent);
        } catch (IOException e) {
            Log.d("ERROR","error");
        }


    }



}
