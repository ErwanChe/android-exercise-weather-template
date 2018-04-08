package fr.iut_amiens.weatherapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import fr.iut_amiens.weatherapplication.openweathermap.WeatherManager;
import fr.iut_amiens.weatherapplication.openweathermap.WeatherResponse;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private WeatherManager weatherManager;
    private EditText nom_ville;
    private TextView nom_ville_textView,temperature,humidite,pression,vent,condition;
    private Button search;
    private DownloadTask downloadTask = null;
    private LocationManager locationmanager;
    private static final int REQUEST_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nom_ville = findViewById(R.id.nom_ville);
        search = findViewById(R.id.rechercher);
        nom_ville_textView = findViewById(R.id.nom_ville_text);
        temperature = findViewById(R.id.temperature);
        humidite = findViewById(R.id.humidite);
        pression = findViewById(R.id.pression);
        vent = findViewById(R.id.vent);
        condition = findViewById(R.id.condition);
        weatherManager = new WeatherManager();
        locationmanager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_PERMISSION);

            return;
        }else {
            Location location = locationmanager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        search.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION)
        {
            if (grantResults.length>0 && PackageManager.PERMISSION_GRANTED == grantResults[0])
            {
                @SuppressLint("MissingPermission") Location location = locationmanager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }else{
                Toast.makeText(this,"permission refusée",Toast.LENGTH_LONG).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onClick(View v) {
        downloadTask = new DownloadTask(this, nom_ville.getText().toString());
        downloadTask.execute();
    }

    @SuppressLint("MissingPermission")
    private void displayLocation(){
        Location location = locationmanager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
             location = locationmanager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
    }

    public void UpdateWeather(WeatherResponse.Main list_main, WeatherResponse.Wind Vent, List<WeatherResponse.Weather> Temp)
    {
        nom_ville_textView.setText(nom_ville.getText().toString());
        temperature.setText(String.valueOf(list_main.getTemp())+" °C");
        pression.setText(String.valueOf(list_main.getPressure())+" hPa");
        humidite.setText(String.valueOf(list_main.getHumidity())+" %");
        vent.setText(String.valueOf(Vent.getSpeed())+" m/s");
        condition.setText(String.valueOf(Temp.get(0).getDescription()));

    }

    // Récupération de la météo actuelle :

        // WeatherResponse weather = weatherManager.findWeatherByCityName("Amiens");
        // WeatherResponse weather = weatherManager.findWeatherByGeographicCoordinates(49.8942, 2.2957);

        // documentation : https://openweathermap.org/current

        // Récupération des prévisions par nom de la ville :

        // ForecastResponse forecast = weatherManager.findForecastByCityName("Amiens");
        // ForecastResponse forecast = weatherManager.findForecastByGeographicCoordinates(49.8942, 2.2957);

        // documentation : https://openweathermap.org/forecast5

}
