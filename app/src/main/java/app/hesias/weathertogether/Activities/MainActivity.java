package app.hesias.weathertogether.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.VolleyError;

import org.json.JSONArray;

import java.util.List;

import app.hesias.weathertogether.DAO.WeatherDAO;
import app.hesias.weathertogether.Model.Weather;
import app.hesias.weathertogether.R;
import app.hesias.weathertogether.VolleyResponseCallback;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}