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

import app.hesias.weathertogether.DAO.WeatherDAO;

public class MainActivity extends AppCompatActivity {
    Button search, searchAndDisplay;
    EditText city;
    ListView cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = findViewById(R.id.searchBtn);
        searchAndDisplay = findViewById(R.id.searchAndDisplay);
        city = findViewById(R.id.et_dataInput);
        cityList = findViewById(R.id.lv_view);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CitySearching citySearching = new CitySearching(MainActivity.this);
                citySearching.getCityCode(city.getText().toString(), new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Error" + message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        searchAndDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeatherDAO weatherDAO = new WeatherDAO(MainActivity.this);

                weatherDAO.getAllWeather(new VolleyResponseListener() {
                    @Override
                    public void onResponse (String response) {
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError (String message) {

                    }
                });
            }
        });
    }

}