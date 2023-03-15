package app.hesias.weathertogether.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;

import java.util.List;

import app.hesias.weathertogether.utils.DAO.WeatherDAO;
import app.hesias.weathertogether.Model.Weather;
import app.hesias.weathertogether.R;
import app.hesias.weathertogether.utils.MyNavigationService;
import app.hesias.weathertogether.utils.VolleyResponseCallback;


public class SendReport extends AppCompatActivity {
    Button search;
    EditText city;
    ListView cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_report);

        MyNavigationService location = new MyNavigationService();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, MyNavigationService.MY_PERMISSIONS_REQUEST_LOCATION);
        }

        search = findViewById(R.id.searchBtn);
        city = findViewById(R.id.et_dataInput);
        cityList = findViewById(R.id.lv_view);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location currentLocation = location.getLocation(SendReport.this);
                Toast.makeText(SendReport.this, currentLocation.toString(), Toast.LENGTH_SHORT).show();
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