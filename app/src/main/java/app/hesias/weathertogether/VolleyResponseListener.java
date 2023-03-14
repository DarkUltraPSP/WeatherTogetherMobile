package app.hesias.weathertogether;

import org.json.JSONArray;

public interface VolleyResponseListener {
    void onResponse(JSONArray response);
    void onError(String message);
}