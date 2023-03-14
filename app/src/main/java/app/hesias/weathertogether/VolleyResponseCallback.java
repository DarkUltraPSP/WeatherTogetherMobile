package app.hesias.weathertogether;

import com.android.volley.VolleyError;

import org.json.JSONArray;

public interface VolleyResponseCallback {
    void onSuccess(JSONArray response);
    void onError(VolleyError error);
}