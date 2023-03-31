package app.hesias.weathertogether.DAO;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import app.hesias.weathertogether.Model.Report;
import app.hesias.weathertogether.utils.JSONArrayCallback;
import app.hesias.weathertogether.utils.JSONOCallback;

public class ReportDAO {

    private final Context context;

    public ReportDAO(Context context) {
        this.context = context;
    }

    final String baseUrl = "http://192.168.1.44:8080/report";


    public void getLatestReports(JSONArrayCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
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
        }
        );

        requestQueue.add(request);
    }

    public void getReportOrderByTemp(JSONArrayCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, baseUrl + "/temp", null, new Response.Listener<JSONArray>() {
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
        }
        );

        requestQueue.add(request);
    }

    public void getReportsRadius(double lat, double lon, int radius, Context context, JSONArrayCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = baseUrl + "/radius/" + lat + "/" + lon + "/" + radius;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error" + error, Toast.LENGTH_LONG).show();
            }
        }
        );

        requestQueue.add(request);
    }

    public void postReport(Report report, JSONArrayCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject json = reportToJSONObject(report);
        String requestBody = json.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        requestQueue.add(stringRequest);
    }

    public JSONObject reportToJSONObject(Report report) {
        JSONObject json = new JSONObject();
        try {
            JSONObject weather = new JSONObject();
            weather.put("id", report.getWeather().getId());

            json.put("latitude", report.getLatitude());
            json.put("longitude", report.getLongitude());
            json.put("temperature", report.getTemperature());
            json.put("weather", weather);
            json.put("username", report.getUsername());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    public Report JSONObjectToReport(JSONObject response) {
        Report report = null;
        WeatherDAO wDao = new WeatherDAO(context);
        try {
            report = new Report(
                    LocalDateTime.parse(response.getString("dateReport")),
                    response.getDouble("latitude"),
                    response.getDouble("longitude"),
                    response.getDouble("temperature"),
                    wDao.JSONObjectToWeather(response.getJSONObject("weather")),
                    response.getString("username")
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return report;
    }

    public List<Report> JSONArrayToReportList(JSONArray response) {
        List<Report> reportList = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject reportJSON = response.getJSONObject(i);
                Report report = JSONObjectToReport(reportJSON);
                reportList.add(report);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return reportList;
    }
}
