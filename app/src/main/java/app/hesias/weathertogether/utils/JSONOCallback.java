package app.hesias.weathertogether.utils;

import org.json.JSONObject;

public interface JSONOCallback {
    void onSuccess(JSONObject jsono);
    void onError(String error);
}
