package fr.iut_amiens.weatherapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import fr.iut_amiens.weatherapplication.openweathermap.WeatherManager;
import fr.iut_amiens.weatherapplication.openweathermap.WeatherResponse;

/**
 * Created by rewan on 16/03/18.
 */

public class DownloadTask extends AsyncTask<Object, Integer, String>
{
    private WeatherManager weatherManager = new WeatherManager();
    private final Context context;
    private final TextView res;
    private String ville;
    private WeatherResponse weather = null;

    public DownloadTask(Context _context, TextView _res, String _ville)
    {
        context = _context;
        res = _res;
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
        res.setText((int) weather.getId());
    }
}
