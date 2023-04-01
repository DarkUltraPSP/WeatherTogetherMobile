package app.hesias.weathertogether.DAO;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.hesias.weathertogether.Model.Weather;
import app.hesias.weathertogether.utils.JSONArrayCallback;

public class WeatherDAO {

    private Context context;

    public WeatherDAO(Context context) {
        this.context = context;
    }

    String baseUrl = "http://192.168.1.44:8080/weather";

    public void getAllWeather(JSONArrayCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        List<Weather> weatherList = new ArrayList<>();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, baseUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                Toast.makeText(context, "Error" + error, Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(request);
    }

    public Weather JSONObjectToWeather(JSONObject response)  {
        Weather weather = null;
        try {
            weather = new Weather(response.getInt("id"), response.getString("weather"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return weather;
    }

    public List<Weather> JSONArrayToWeatherList(JSONArray response){
        List<Weather> weatherList = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject weatherJSON = response.getJSONObject(i);
                Weather weather = JSONObjectToWeather(weatherJSON);
                weatherList.add(weather);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return weatherList;
    }
}
