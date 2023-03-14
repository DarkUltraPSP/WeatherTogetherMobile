package app.hesias.weathertogether.DAO;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.hesias.weathertogether.Model.Weather;
import app.hesias.weathertogether.VolleyResponseListener;

public class WeatherDAO {

    private Context context;

    public WeatherDAO (Context context) {
        this.context = context;
    }

    String url = "http://localhost:8080/weather";
    public void getAllWeather(VolleyResponseListener volleyResponseListener) {
        List<Weather> weatherList = new ArrayList<>();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(context, "Test", Toast.LENGTH_LONG).show();
                try {
                    JSONObject weatherInfo = response.getJSONObject(0);
                    volleyResponseListener.onResponse(weatherInfo.toString());
                } catch (JSONException e) {
                    Toast.makeText(context, "Error" + e, Toast.LENGTH_SHORT).show();
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error" + error, Toast.LENGTH_SHORT).show();
                volleyResponseListener.onError("Something went wrong");
            }
        });
    }

}
