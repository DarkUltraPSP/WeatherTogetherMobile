package app.hesias.weathertogether.Activities;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.hesias.weathertogether.DAO.ReportDAO;
import app.hesias.weathertogether.R;
import app.hesias.weathertogether.databinding.ActivityMapsBinding;
import app.hesias.weathertogether.utils.JSONArrayCallback;
import app.hesias.weathertogether.utils.JSONOCallback;
import app.hesias.weathertogether.utils.Location;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady (GoogleMap googleMap) {
        mMap = googleMap;

        Location.getCurLocation(new JSONOCallback() {
            @Override
            public void onSuccess (JSONObject jsono) {
                try {
                    LatLng curLocation = new LatLng(jsono.getDouble("lat"), jsono.getDouble("lon"));
                    mMap.addMarker(new MarkerOptions().position(curLocation).title("Current Location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLocation, 15));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onError (String error) {

            }
        }, this);

        displayReports();
    }

    private void displayReports (){
        ReportDAO reportDAO = new ReportDAO(this);
        reportDAO.getAllReports(new JSONArrayCallback() {
            @Override
            public void onSuccess (JSONArray response) {
                for (int i = 0; i < response.length(); i++)
                    try {
                        JSONObject report = response.getJSONObject(i);
                        System.out.println(report);
                        LatLng location = new LatLng(report.getDouble("latitude"), report.getDouble("longitude"));
                        mMap.addMarker(new MarkerOptions().position(location).title(report.getString("temperature")));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
            }

            @Override
            public void onError (String error) {

            }
        });
    }
}