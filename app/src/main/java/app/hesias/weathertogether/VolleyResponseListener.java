package app.hesias.weathertogether;

public interface VolleyResponseListener {
    void onResponse(String response);
    void onError(String message);
}
