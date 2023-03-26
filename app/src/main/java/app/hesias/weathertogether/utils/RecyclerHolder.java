package app.hesias.weathertogether.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import app.hesias.weathertogether.R;

public class RecyclerHolder extends RecyclerView.ViewHolder {
    ImageView image;
    TextView city,temp;

    public RecyclerHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.imgView);
        city = itemView.findViewById(R.id.city);
        temp = itemView.findViewById(R.id.temp);
    }
}
