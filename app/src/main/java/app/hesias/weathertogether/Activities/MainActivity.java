package app.hesias.weathertogether.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import app.hesias.weathertogether.R;

public class MainActivity extends AppCompatActivity {
    Button report;

    TextView temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        report = findViewById(R.id.reportBtn);
        temp = findViewById(R.id.tv_temp);
    }
}