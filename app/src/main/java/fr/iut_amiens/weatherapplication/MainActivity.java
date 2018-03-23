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
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import fr.iut_amiens.weatherapplication.openweathermap.WeatherManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private WeatherManager weatherManager;
    private EditText nom_ville;
    private TextView res;
    private Button search;
    private DownloadTask downloadTask = null;
    private LocationManager locationmanager;
    private static final int REQUEST_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nom_ville = findViewById(R.id.nom_ville);
        res = findViewById(R.id.resultat);
        search = findViewById(R.id.rechercher);
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
        downloadTask = new DownloadTask(nom_ville.getText().toString());
        downloadTask.execute();
    }

    @SuppressLint("MissingPermission")
    private void displayLocation(){
        Location location = locationmanager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
             location = locationmanager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        Log.d("Activity",location.toString());
    }

    public void UpdateWeather(String idVille)
    {
        Log.d("lol",idVille);
        res.setText(idVille);
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
