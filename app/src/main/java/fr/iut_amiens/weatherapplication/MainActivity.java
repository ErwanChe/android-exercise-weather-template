package fr.iut_amiens.weatherapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

import fr.iut_amiens.weatherapplication.openweathermap.WeatherManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private WeatherManager weatherManager;
    private EditText nom_ville;
    private TextView res;
    private Button search;
    private DownloadTask downloadTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nom_ville = findViewById(R.id.nom_ville);
        res = findViewById(R.id.resultat);
        search = findViewById(R.id.rechercher);
        weatherManager = new WeatherManager();
        search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        downloadTask.execute(this,res,nom_ville.getText());
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
