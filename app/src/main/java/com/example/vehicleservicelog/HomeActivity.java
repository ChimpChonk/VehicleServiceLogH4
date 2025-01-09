package com.example.vehicleservicelog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FloatingActionButton btn;

        RecyclerView recyclerView = findViewById(R.id.serviceRecyclerView);

        List<ServiceLogList> serviceLogList = new ArrayList<>();

        serviceLogList.add(new ServiceLogList("MC Honda", "ABC123", "Oil Change", "2023-10-27"));
        serviceLogList.add(new ServiceLogList("MC Fuck", "ABC231", "Change this", "2023-10-27"));


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(getApplicationContext(),serviceLogList));

        btn = findViewById(R.id.addServiceButton);
        btn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AddServiceActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
