package app.hesias.weathertogether.utils.DAO;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.hesias.weathertogether.Model.Report;
import app.hesias.weathertogether.utils.MySingleton;
import app.hesias.weathertogether.utils.VolleyResponseCallback;

public class ReportDAO extends Service {

    private Context context;

    public ReportDAO(Context context) {
        this.context = context;
    }

    String url = "http://192.168.1.44:8080/report/";

    public void getAllReports(VolleyResponseCallback callback) {
        List<Report> reportList = new ArrayList<>();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                Toast.makeText(context, "Error" + error, Toast.LENGTH_LONG).show();
            }
        }
        );

        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void postReport(Report report, VolleyResponseCallback callback) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("latitude", "");
                params.put("logitude", "");
                params.put("temperature", "");

                return params;
            }
        };

        MySingleton.getInstance(context).addToRequestQueue(postRequest);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
