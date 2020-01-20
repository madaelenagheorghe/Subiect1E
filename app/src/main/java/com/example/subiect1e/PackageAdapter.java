package com.example.subiect1e;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class PackageAdapter extends ArrayAdapter<DataPackage> {
    private int resourceID;

    public PackageAdapter(@NonNull Context context, int resource, @NonNull List<DataPackage> objects) {
        super(context, resource, objects);
        resourceID=resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DataPackage dp=getItem(position);
        LayoutInflater layoutInflater=LayoutInflater.from(getContext());
        View view=layoutInflater.inflate(resourceID,null);
        TextView id = view.findViewById(R.id.p_id);
        TextView type = view.findViewById(R.id.p_state);
        TextView lat = view.findViewById(R.id.p_lat);
        TextView longit = view.findViewById(R.id.p_long);
        TextView timestamp = view.findViewById(R.id.p_time);
        id.setText(String.valueOf(dp.getPackageId()));
        type.setText(String.valueOf(dp.getPackageType()));
        lat.setText(String.valueOf(dp.getLatitude()));
        longit.setText(String.valueOf(dp.getLongitude()));
        timestamp.setText(String.valueOf(dp.getTimestamp()));

        return view;
    }
}
