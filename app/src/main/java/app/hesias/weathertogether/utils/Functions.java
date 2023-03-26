package app.hesias.weathertogether.utils;

import android.content.Context;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import org.json.JSONObject;

import app.hesias.weathertogether.R;

public class Functions {
    public static int imgforWeather(int idWeather) {
        int img = 0;
        switch (idWeather) {
            case 1:
                img = R.drawable.sunny;
                break;
            case 2:
                img = R.drawable.cloudy_with_sun;
                break;
            case 3:
                img = R.drawable.sun_rain;
                break;
            case 4:
                img = R.drawable.cloudy;
                break;
            case 5:
                img = R.drawable.fog;
                break;
            case 6:
                img = R.drawable.light_rain;
                break;
            case 7:
                img = R.drawable.heavy_rain;
                break;
            case 8:
                img = R.drawable.snow;
                break;
        }
        return img;
    }
}
