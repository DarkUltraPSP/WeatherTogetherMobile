package app.hesias.weathertogether.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;

import app.hesias.weathertogether.DAO.ReportDAO;
import app.hesias.weathertogether.Model.Report;
import app.hesias.weathertogether.R;
import app.hesias.weathertogether.utils.JSONArrayCallback;

public class MainActivity extends AppCompatActivity {

    Button btn_gotoSendReport, btn_goToMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_gotoSendReport = findViewById(R.id.btn_gotoSendReport);
        btn_goToMap = findViewById(R.id.btn_goToMap);

        btn_gotoSendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SendReport.class);
                startActivity(intent);
            }
        });

        btn_goToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getReports() {
        ReportDAO reportDAO = new ReportDAO(this);
        reportDAO.getAllReports(new JSONArrayCallback() {
            @Override
            public void onSuccess(JSONArray response) {

            }

            @Override
            public void onError(String error) {

            }
        });
    }
}