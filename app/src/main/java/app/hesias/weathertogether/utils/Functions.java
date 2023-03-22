package app.hesias.weathertogether.utils;

import android.content.Context;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import org.json.JSONObject;

import app.hesias.weathertogether.R;

public class Functions {
    public static BitmapDescriptor imgforWeather(Context context, int idWeather) {
        BitmapDescriptor img = null;
        switch (idWeather) {
            case 1:
                img = BitmapDescriptorFactory.fromResource(R.drawable.sunny);
                break;
            case 2:
                img = BitmapDescriptorFactory.fromResource(R.drawable.cloudy_with_sun);
                break;
            case 3:
                img = BitmapDescriptorFactory.fromResource(R.drawable.sun_rain);
                break;
            case 4:
                img = BitmapDescriptorFactory.fromResource(R.drawable.cloudy);
                break;
            case 5:
                img = BitmapDescriptorFactory.fromResource(R.drawable.fog);
                break;
            case 6:
                img = BitmapDescriptorFactory.fromResource(R.drawable.light_rain);
                break;
            case 7:
                img = BitmapDescriptorFactory.fromResource(R.drawable.heavy_rain);
                break;
            case 8:
                img = BitmapDescriptorFactory.fromResource(R.drawable.snow);
                break;
        }
        return img;
    }
}
