package com.example.vehicleservicelog;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vehicleservicelog.Model.DataService;
import com.example.vehicleservicelog.Model.ServiceLogList;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

    Context context;
    List<DataService> serviceLogList;

    public MyAdapter(Context context, List<DataService> serviceLogList) {
        this.context = context;
        this.serviceLogList = serviceLogList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.service_log_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.vehicleType.setText(serviceLogList.get(position).getVehicleType());
        holder.numberPlate.setText(serviceLogList.get(position).getNumberPlate());
        holder.serviceName.setText(serviceLogList.get(position).getServiceType());
        holder.serviceDate.setText(serviceLogList.get(position).getServiceDate());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailServiceActivity.class);
            intent.putExtra("SERVICE_ID", serviceLogList.get(position).getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return serviceLogList.size();
    }
}
