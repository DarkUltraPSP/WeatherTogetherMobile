package app.hesias.weathertogether.DAO;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import app.hesias.weathertogether.Model.City;
import app.hesias.weathertogether.utils.JSONArrayCallback;

public class CityDAO {
    private final Context context;

    String baseUrl = "https://geo.api.gouv.fr/communes";


    public CityDAO(Context context) {
        this.context = context;
    }

    public void getClosestCity(double lat, double lon, JSONArrayCallback callback){
        Uri.Builder builder = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter("lat", String.valueOf(lat))
                .appendQueryParameter("lon", String.valueOf(lon))
                .appendQueryParameter("fields", "nom")
                .appendQueryParameter("format", "json")
                .appendQueryParameter("geometry", "centre");
        String url = builder.build().toString();

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });

        requestQueue.add(request);
    }
}
