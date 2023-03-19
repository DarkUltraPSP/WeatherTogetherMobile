package app.hesias.weathertogether.utils;

import com.android.volley.VolleyError;

import org.json.JSONArray;

public interface JSONArrayCallback {
    void onSuccess(JSONArray response);
    void onError(String error);
}