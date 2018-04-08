package fr.iut_amiens.weatherapplication;

import android.content.Context;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import fr.iut_amiens.weatherapplication.openweathermap.WeatherManager;
import fr.iut_amiens.weatherapplication.openweathermap.WeatherResponse;

/**
 * Created by rewan on 16/03/18.
 */

public class DownloadTask extends AsyncTask<Object, Integer, String>
{
    private WeatherManager weatherManager = new WeatherManager();
    private String ville;
    private WeatherResponse weather = null;
    private MainActivity activity;

    public DownloadTask(MainActivity activity, String _ville) {
        this.activity = activity;
        ville = _ville;
    }

    @Override
    protected String doInBackground(Object... objects) {


        try {
            weather = weatherManager.findWeatherByCityName(ville);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "terminé";
    }

    @Override
    protected void onPostExecute(String s) {
        activity.UpdateWeather(weather.getMain(),weather.getWind(),weather.getWeather());
    }
}
