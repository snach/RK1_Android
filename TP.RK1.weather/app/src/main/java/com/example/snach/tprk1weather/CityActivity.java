package com.example.snach.tprk1weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import ru.mail.weather.lib.City;
import ru.mail.weather.lib.WeatherStorage;

/**
 * Created by Snach on 29.09.16.
 */
public class CityActivity extends AppCompatActivity {
    private final static String TAG = CityActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_city);




        Button btnVC = (Button) findViewById(R.id.btn_viceCity);
        btnVC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeatherStorage.getInstance(CityActivity.this).setCurrentCity(City.VICE_CITY);
                startMainActivity();
            }
        });

        Button btnRacCity = (Button) findViewById(R.id.btn_RaccoonCity);
        btnRacCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeatherStorage.getInstance(CityActivity.this).setCurrentCity(City.RACCOON_CITY);
                startMainActivity();

            }
        });

        Button btnSprgld = (Button) findViewById(R.id.btn_springfield);
        btnSprgld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                WeatherStorage.getInstance(CityActivity.this).setCurrentCity(City.SPRINGFIELD);
                startMainActivity();
            }
        });

        Button btnSHill = (Button) findViewById(R.id.btn_silentHill);
        btnSHill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                WeatherStorage.getInstance(CityActivity.this).setCurrentCity(City.SILENT_HILL);
                startMainActivity();
            }
        });

        Button btnSPark = (Button) findViewById(R.id.btn_southPark);
        btnSPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                WeatherStorage.getInstance(CityActivity.this).setCurrentCity(City.SOUTH_PARK);
                startMainActivity();
            }
        });



    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
