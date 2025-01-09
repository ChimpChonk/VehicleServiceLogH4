package com.example.vehicleservicelog;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView vehicleType, numberPlate, serviceName, serviceDate;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        vehicleType = itemView.findViewById(R.id.vehicleType);
        numberPlate = itemView.findViewById(R.id.numberPlate);
        serviceName = itemView.findViewById(R.id.serviceName);
        serviceDate = itemView.findViewById(R.id.serviceDate);

    }
}
