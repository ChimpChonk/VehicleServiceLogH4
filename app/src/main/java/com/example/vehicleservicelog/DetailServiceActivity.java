package com.example.vehicleservicelog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vehicleservicelog.Model.DataService;
import com.example.vehicleservicelog.Repo.DataServiceRepository;

public class DetailServiceActivity extends AppCompatActivity {
    private EditText numberPlateTextView, vehicleTypeTextView, serviceTypeTextView, serviceDateTextView, serviceDescriptionTextView;
    private ImageView capturedImageView;
    private Button updateServiceButton, deleteServiceButton, backButton;

    private DataServiceRepository repository;
    private DataService dataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_service);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        repository = new DataServiceRepository(getApplication());

        numberPlateTextView = findViewById(R.id.numberPlateTextView);
        vehicleTypeTextView = findViewById(R.id.vehicleTypeTextView);
        serviceTypeTextView = findViewById(R.id.serviceTypeTextView);
        serviceDateTextView = findViewById(R.id.serviceDateTextView);
        serviceDescriptionTextView = findViewById(R.id.serviceDescriptionTextView);
        capturedImageView = findViewById(R.id.capturedImageView);
        updateServiceButton = findViewById(R.id.updateServiceButton);
        deleteServiceButton = findViewById(R.id.deleteServiceButton);
        backButton = findViewById(R.id.backButton);

        int serviceId = getIntent().getIntExtra("SERVICE_ID", -1);

        if (serviceId != -1) {
            fetchServiceDetails(serviceId);
        } else {
            Toast.makeText(this, "Invalid Service ID", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchServiceDetails(int serviceId) {
        dataService = repository.getServiceById(serviceId);

        if (dataService != null) {
            numberPlateTextView.setText(dataService.getNumberPlate());
            vehicleTypeTextView.setText(dataService.getVehicleType());
            serviceTypeTextView.setText(dataService.getServiceType());
            serviceDateTextView.setText(dataService.getServiceDate());
            serviceDescriptionTextView.setText(dataService.getServiceDescription());
            if (dataService.getImageUri() != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(dataService.getImageUri());
                capturedImageView.setImageBitmap(bitmap);
            }
        } else {
            Toast.makeText(this, "Service not found", Toast.LENGTH_SHORT).show();
        }
    }
}