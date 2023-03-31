package app.hesias.weathertogether.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.hesias.weathertogether.DAO.ReportDAO;
import app.hesias.weathertogether.Model.Report;
import app.hesias.weathertogether.R;
import app.hesias.weathertogether.utils.JSONArrayCallback;
import app.hesias.weathertogether.utils.JSONOCallback;
import app.hesias.weathertogether.utils.Location;
import app.hesias.weathertogether.utils.RecyclerAdapter;

public class MainActivity extends AppCompatActivity {

    Button btn_gotoSendReport, btn_goToMap;
    Spinner sp_filter;
    SeekBar sb_radius;
    TextView tv_radius;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_gotoSendReport = findViewById(R.id.btn_gotoSendReport);
        btn_goToMap = findViewById(R.id.btn_goToMap);
        recyclerView = findViewById(R.id.recycler);
        sp_filter = findViewById(R.id.sp_filter);
        tv_radius = findViewById(R.id.tv_radius);
        sb_radius = findViewById(R.id.sb_radius);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        fillSpinner();

        fillRecyclerByDate(sp_filter.getSelectedItemId());


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

        sp_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch ((int) sp_filter.getSelectedItemId()) {
                    case 0:
                        fillRecyclerByDate(0);
                        break;
                    case 1:
                        fillRecyclerByDate(1);
                        break;
                    case 2:
                        fillRecyclerByTemp(2);
                        break;
                    case 3:
                        fillRecyclerByTemp(3);
                        break;
                    case 4:
                        tv_radius.setVisibility(View.VISIBLE);
                        sb_radius.setVisibility(View.VISIBLE);
                        tv_radius.setText("Rayon : " + sb_radius.getProgress() + "km");
                        sb_radius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                                tv_radius.setText("Rayon : " + i + "km");
                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {

                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {
                                ReportDAO reports = new ReportDAO(MainActivity.this);
                                app.hesias.weathertogether.utils.Location.getCurLocation(new JSONOCallback() {
                                    @Override
                                    public void onSuccess(JSONObject jsono) {
                                        try {
                                            reports.getReportsRadius(jsono.getDouble("lat"), jsono.getDouble("lon"), sb_radius.getProgress(), MainActivity.this, new JSONArrayCallback() {
                                                @Override
                                                public void onSuccess(JSONArray response) {
                                                    List<Report> listReport = reports.JSONArrayToReportList(response);
                                                    recyclerView.setAdapter(new RecyclerAdapter(MainActivity.this, listReport));
                                                }

                                                @Override
                                                public void onError(String error) {

                                                }
                                            });
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onError(String error) {

                                    }
                                }, MainActivity.this);

                            }
                        });
                        break;
                    default:
                        tv_radius.setVisibility(View.GONE);
                        sb_radius.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    protected void onResume() {
        super.onResume();
        fillRecyclerByDate(0);
    }

    private void fillRecyclerByDate(long filterID){
        ReportDAO reports = new ReportDAO(this);
        reports.getLatestReports(new JSONArrayCallback() {
            @Override
            public void onSuccess(JSONArray response) {
                List<Report> listReport = reports.JSONArrayToReportList(response);
                if (filterID == 1) {
                    List<Report> listReportReverse = new ArrayList<>();
                    for (int i = listReport.size() - 1; i >= 0; i--) {
                        listReportReverse.add(listReport.get(i));
                    }
                    listReport = listReportReverse;
                }

                recyclerView.setAdapter(new RecyclerAdapter(MainActivity.this, listReport));
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void fillRecyclerByTemp(long filterID){
        ReportDAO reports = new ReportDAO(this);
        reports.getReportOrderByTemp(new JSONArrayCallback() {
            @Override
            public void onSuccess(JSONArray response) {
                List<Report> listReport = reports.JSONArrayToReportList(response);
                if (filterID == 3) {
                    List<Report> listReportReverse = new ArrayList<>();
                    for (int i = listReport.size() - 1; i >= 0; i--) {
                        listReportReverse.add(listReport.get(i));
                        System.out.println(listReport.get(i).getTemperature());
                    }
                    listReport = listReportReverse;
                }
                recyclerView.setAdapter(new RecyclerAdapter(MainActivity.this, listReport));
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void fillSpinner() {
        List<String> list = new ArrayList<>();
        list.add("Du plus récent au plus ancien");
        list.add("Du plus ancien au plus récent");
        list.add("Du plus chaud au plus froid");
        list.add("Du plus froid au plus chaud");
        list.add("Dans un rayon de :");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_filter.setAdapter(adapter);
    }

}