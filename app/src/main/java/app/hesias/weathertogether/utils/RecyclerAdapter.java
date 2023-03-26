package app.hesias.weathertogether.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import app.hesias.weathertogether.DAO.CityDAO;
import app.hesias.weathertogether.Model.Report;
import app.hesias.weathertogether.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder> {
    Context context;
    List<Report> reports;

    public RecyclerAdapter(Context context, List<Report> reports) {
        this.context = context;
        this.reports = reports;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        CityDAO cityDAO = new CityDAO(context);

        cityDAO.getClosestCity(reports.get(position).getLatitude(), reports.get(position).getLongitude(), new JSONArrayCallback() {
            @Override
            public void onSuccess(JSONArray response) {
                try {
                    holder.city.setText(response.getJSONObject(0).getString("nom"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onError(String error) {

            }
        });

        holder.temp.setText(Double.toString(reports.get(position).getTemperature()));
        holder.image.setImageResource(Functions.imgforWeather(reports.get(position).getWeather().getId()));
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }
}
