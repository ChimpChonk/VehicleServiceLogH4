package com.example.vehicleservicelog;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vehicleservicelog.Model.DataService;
import com.example.vehicleservicelog.Model.ServiceLogList;
import com.example.vehicleservicelog.Repo.DataServiceRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private DataServiceRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FloatingActionButton btn;
        repository = new DataServiceRepository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.serviceRecyclerView);

//        List<DataService> serviceLogList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository.getAllServiceList().observe(this, serviceLogList -> {
            if (serviceLogList != null) {
                recyclerView.setAdapter(new MyAdapter(this, serviceLogList));
            }
        });

//        recyclerView.setAdapter(new MyAdapter(getApplicationContext(),serviceLogList));

        btn = findViewById(R.id.addServiceButton);
        btn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AddServiceActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
