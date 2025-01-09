package com.example.vehicleservicelog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RecyclerView recyclerView = findViewById(R.id.serviceRecyclerView);

        List<ServiceLogList> serviceLogList = new ArrayList<ServiceLogList>();

        serviceLogList.add(new ServiceLogList("MC Honda", "ABC123", "Oil Change", "2023-10-27"));
        serviceLogList.add(new ServiceLogList("MC Fuck", "ABC231", "Change this", "2023-10-27"));


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(getApplicationContext(),serviceLogList));
    };
}
