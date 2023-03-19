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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.List;

import app.hesias.weathertogether.DAO.ReportDAO;
import app.hesias.weathertogether.DAO.WeatherDAO;
import app.hesias.weathertogether.Model.Report;
import app.hesias.weathertogether.Model.Weather;
import app.hesias.weathertogether.R;
import app.hesias.weathertogether.utils.JSONArrayCallback;
import app.hesias.weathertogether.utils.JSONOCallback;


public class SendReport extends AppCompatActivity {
    FusedLocationProviderClient fusedLocationProviderClient;
    Button sendBtn;
    EditText temperature, username;
    Spinner weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_report);

        sendBtn = findViewById(R.id.sendBtn);
        temperature = findViewById(R.id.et_temp);
        weather = findViewById(R.id.sp_weather);
        username = findViewById(R.id.et_username);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        fill_spinner();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurLocation(new JSONOCallback() {
                    final ReportDAO reportDAO = new ReportDAO(SendReport.this);
                    @Override
                    public void onSuccess(JSONObject jsono) {
                        try {
                            Report report = new Report(
                                    LocalDateTime.now(),
                                    jsono.getDouble("lat"),
                                    jsono.getDouble("lon"),
                                    Double.parseDouble(temperature.getText().toString()),
                                    (Weather) weather.getSelectedItem(),
                                    username.getText().toString()

                            );
                            reportDAO.postReport(report, new JSONArrayCallback() {
                                @Override
                                public void onSuccess(JSONArray response) {
                                    System.out.println(response);
                                }

                                @Override
                                public void onError(String error) {
                                    System.out.println(error);
                                }
                            });
                        } catch (JSONException e) {
                            Toast.makeText(SendReport.this, e.toString(), Toast.LENGTH_SHORT).show();
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onError(String error) {
                        System.out.println(error);
                    }
                });
            }
        });
    }

    private void getCurLocation(JSONOCallback callback) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
                Location location = task.getResult();
                if (location != null) {
                    JSONObject coords = new JSONObject();
                    try {
                        coords.put("lat", location.getLatitude());
                        coords.put("lon", location.getLongitude());
                        callback.onSuccess(coords);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    private void fill_spinner() {
        WeatherDAO weatherDAO = new WeatherDAO(SendReport.this);
        weatherDAO.getAllWeather(new JSONArrayCallback() {
            @Override
            public void onSuccess(JSONArray response) {
                List<Weather> weatherList = weatherDAO.JSONArrayToWeatherList(response);
                ArrayAdapter<Weather> adapter = new ArrayAdapter<Weather>(SendReport.this, android.R.layout.simple_spinner_item, weatherList);
                weather.setAdapter(adapter);
            }

            @Override
            public void onError(String error) {
                System.out.println(error);
            }
        });
    }
}