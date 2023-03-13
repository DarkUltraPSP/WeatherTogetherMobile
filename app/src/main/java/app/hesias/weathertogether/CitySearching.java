package app.hesias.weathertogether;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.hesias.weathertogether.Model.City;
import app.hesias.weathertogether.Model.Weather;

public class CitySearching {
    private Context context;
    private String cityID;
    public CitySearching(Context context) {
        this.context = context;
    }

    public void getCityCode(String cityName, VolleyResponseListener volleyResponseListener) {
        String url = String.format("https://geo.api.gouv.fr/communes?nom=%s&fields=nom,codesPostaux,centre&format=json&geometry=centre", cityName);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override

            public void onResponse(JSONArray response) {
                try {
                    JSONObject cityInfo = response.getJSONObject(0);
                    cityID = cityInfo.getString("codesPostaux");
                    volleyResponseListener.onResponse(cityID);
                } catch (JSONException e) {
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

        MySingleton.getInstance(context).addToRequestQueue(request);
    }
}
