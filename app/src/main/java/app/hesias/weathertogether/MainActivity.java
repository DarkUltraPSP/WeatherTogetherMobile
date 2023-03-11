package app.hesias.weathertogether;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Button search;
    EditText city;
    ListView cityList;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = findViewById(R.id.searchBtn);
        city = findViewById(R.id.et_dataInput);
        cityList = findViewById(R.id.lv_view);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                // Instantiate the RequestQueue.
                RequestQueue queue =  Volley.newRequestQueue(MainActivity.this);
                String url = String.format("https://geo.api.gouv.fr/communes?nom=%s&fields=nom,codesPostaux,centre&format=json&geometry=centrew", city.getText().toString());

                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject cityInfo = response.getJSONObject(0);
                            String postCode = cityInfo.getString("codesPostaux");
                            Toast.makeText(MainActivity.this, postCode, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error" + error, Toast.LENGTH_SHORT).show();
                    }
                });

                // Request a string response from the provided URL.
//                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                // Display the first 500 characters of the response string.
//                                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(MainActivity.this, "Error" + error, Toast.LENGTH_SHORT).show();
//                    }
//                });

                // Add the request to the RequestQueue.
                queue.add(request);
//                Toast.makeText(MainActivity.this, "Searching for" + city.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}