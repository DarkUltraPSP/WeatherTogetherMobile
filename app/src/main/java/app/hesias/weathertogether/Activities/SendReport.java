package app.hesias.weathertogether.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.VolleyError;

import org.json.JSONArray;

import java.util.List;

import app.hesias.weathertogether.DAO.WeatherDAO;
import app.hesias.weathertogether.Model.Weather;
import app.hesias.weathertogether.R;
import app.hesias.weathertogether.VolleyResponseCallback;

public class SendReport extends AppCompatActivity {
    Button search;
    EditText city;
    ListView cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_report);

        search = findViewById(R.id.searchBtn);
        city = findViewById(R.id.et_dataInput);
        cityList = findViewById(R.id.lv_view);

        fillLV();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void fillLV() {
        WeatherDAO weatherDAO = new WeatherDAO(SendReport.this);
        weatherDAO.getAllWeather(new VolleyResponseCallback() {
            @Override
            public void onSuccess(JSONArray response) {
                List<Weather> weatherList = weatherDAO.JSONArrayToWeatherList(response);
                ArrayAdapter<Weather> adapter = new ArrayAdapter<Weather>(SendReport.this, android.R.layout.simple_list_item_1, weatherList);
                cityList.setAdapter(adapter);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }
}