package app.hesias.weathertogether.DAO;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import app.hesias.weathertogether.Model.Weather;

public class WeatherDAO {

    private Context context;

    public WeatherDAO(Context context) {
        this.context = context;
    }

    String url = "http://192.168.1.44:8080/weather";

    public void getAllWeather() {
        List<Weather> weatherList = new ArrayList<>();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println(response);
                Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error" + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
