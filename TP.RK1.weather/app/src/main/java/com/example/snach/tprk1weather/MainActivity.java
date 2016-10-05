package com.example.snach.tprk1weather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ru.mail.weather.lib.City;
import ru.mail.weather.lib.Weather;
import ru.mail.weather.lib.WeatherStorage;
import ru.mail.weather.lib.WeatherUtils;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();
    final String ACTION = "WEATHER_CHANGED";
    private BroadcastReceiver receiver;
    private TextView textDegree;
    private TextView textDescription;
    private TextView textError;

    private City city = City.VICE_CITY;
    WeatherStorage weatherStorage;
    //weatherStorage.setCurrentCity(city);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textDegree = (TextView) findViewById(R.id.text_degree);
        textDescription = (TextView) findViewById(R.id.text_description);
        textError = (TextView) findViewById(R.id.text_error);

        weatherStorage = WeatherStorage.getInstance(MainActivity.this);


        city = WeatherStorage.getInstance(MainActivity.this).getCurrentCity();
        Log.d(TAG, "onCreate");

        Button btnCity = (Button) findViewById(R.id.btn_viceCity);
        btnCity.setText(city.toString());

        btnCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCityActivity();
            }
        });


        final Button updateBackground = (Button) findViewById(R.id.btn_updateBackground);
        final Button notUpdateBackground = (Button) findViewById(R.id.btn_notUpdateBackGround);
        notUpdateBackground.setClickable(false);

        updateBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateBackground.setClickable(false);
                notUpdateBackground.setClickable(true);
                Intent intent = new Intent(MainActivity.this, MyIntentService.class);
                WeatherUtils.getInstance().schedule(MainActivity.this, intent);
                Log.d(TAG, "updateBckgrnd");

            }
        });

        notUpdateBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateBackground.setClickable(true);
                notUpdateBackground.setClickable(false);
                Intent intent = new Intent(MainActivity.this, MyIntentService.class);
                WeatherUtils.getInstance().unschedule(MainActivity.this, intent);
                Log.d(TAG, "notUpdateBckgrnd");

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWeatherInfo(weatherStorage.getCurrentCity());
        IntentFilter filter = new IntentFilter(ACTION);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "onReceive");
                getWeatherInfo(weatherStorage.getCurrentCity());
            }
        };
        registerReceiver(receiver, filter);
    }

    private void startCityActivity() {
        Intent intent = new Intent(this, CityActivity.class);
        startActivity(intent);
    }

    private void getWeatherInfo(City city) {
        Log.d(TAG, city.toString());
        Weather weather = weatherStorage.getLastSavedWeather(city);
        // Log.d(TAG,"Weather " + weather);
        try {
            if (!weather.getDescription().isEmpty()) {


                textError.setText("");
                Integer temp = weather.getTemperature();
                textDegree.setText(temp.toString() + "°C");
                textDescription.setText(weather.getDescription());
                
            }
        } catch (NullPointerException e) {
            textDescription.setText("");
            textDegree.setText("");
            textError.setText("В хранилище нет погоды для этого города, нажмите \"Обновлять в фоне\"");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}
