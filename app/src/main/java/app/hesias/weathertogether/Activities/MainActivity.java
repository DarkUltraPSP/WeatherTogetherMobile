package app.hesias.weathertogether.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import app.hesias.weathertogether.DAO.ReportDAO;
import app.hesias.weathertogether.Model.Report;
import app.hesias.weathertogether.Model.Weather;
import app.hesias.weathertogether.R;
import app.hesias.weathertogether.utils.JSONArrayCallback;
import app.hesias.weathertogether.utils.RecyclerAdapter;

public class MainActivity extends AppCompatActivity {

    Button btn_gotoSendReport, btn_goToMap, btn_refresh;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_gotoSendReport = findViewById(R.id.btn_gotoSendReport);
        btn_goToMap = findViewById(R.id.btn_goToMap);
        btn_refresh = findViewById(R.id.btn_refresh);
        recyclerView = findViewById(R.id.recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        fillRecycler();

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

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillRecycler();
            }
        });
    }

    private void fillRecycler(){
        ReportDAO reports = new ReportDAO(this);
        reports.getAllReports(new JSONArrayCallback() {
            @Override
            public void onSuccess(JSONArray response) {
                List<Report> listReport = reports.JSONArrayToReportList(response);
                recyclerView.setAdapter(new RecyclerAdapter(MainActivity.this, listReport));
            }

            @Override
            public void onError(String error) {

            }
        });
    }
}