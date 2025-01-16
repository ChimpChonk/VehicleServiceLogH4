package com.example.vehicleservicelog;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vehicleservicelog.Model.DataService;
import com.example.vehicleservicelog.Repo.DataServiceRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DetailServiceActivity extends AppCompatActivity {
    private EditText numberPlateTextView, vehicleTypeTextView, serviceTypeTextView, serviceDateTextView, serviceDescriptionTextView;
    private TextView serviceLocation;
    private ImageView capturedImageView;
    private Button updateServiceButton, deleteServiceButton, backButton;
    private DatePicker datePicker;

    private DataServiceRepository repository;
    private DataService dataService;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private int serviceId;

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
        initializeViews();

        // Get Service ID passed via Intent
        serviceId = getIntent().getIntExtra("SERVICE_ID", -1);

        if (serviceId != -1) {
            // Fetch service details in the background
            fetchServiceDetails(serviceId);
        } else {
            Toast.makeText(this, "Invalid Service ID", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if no valid ID is provided
        }

        serviceDateTextView.setOnClickListener(v -> {
            datePicker.setVisibility(datePicker.getVisibility() == DatePicker.VISIBLE ? DatePicker.GONE : DatePicker.VISIBLE);
            serviceDateTextView.setText(datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear());
        });
        // Back Button logic
        backButton.setOnClickListener(v -> finish());
        updateServiceButton.setOnClickListener(v -> updateData());
        deleteServiceButton.setOnClickListener(v -> deleteData());
    }

    private void initializeViews() {
        numberPlateTextView = findViewById(R.id.numberPlateTextView);
        vehicleTypeTextView = findViewById(R.id.vehicleTypeTextView);
        serviceTypeTextView = findViewById(R.id.serviceTypeTextView);
        serviceDateTextView = findViewById(R.id.serviceDateTextView);
        serviceDescriptionTextView = findViewById(R.id.serviceDescriptionTextView);
        capturedImageView = findViewById(R.id.capturedImageView);
        updateServiceButton = findViewById(R.id.updateServiceButton);
        deleteServiceButton = findViewById(R.id.deleteServiceButton);
        backButton = findViewById(R.id.backButton);
        serviceLocation = findViewById(R.id.serviceLocation);
        datePicker = findViewById(R.id.datePicker);
    }

    private void fetchServiceDetails(int serviceId) {
        executorService.execute(() -> {
            // Perform the database operation on a background thread
            dataService = repository.getServiceById(serviceId);

            // Update the UI on the main thread
            handler.post(() -> {
                if (dataService != null) {
                    updateUIWithServiceDetails();
                } else {
                    Toast.makeText(DetailServiceActivity.this, "Service not found", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void updateUIWithServiceDetails() {
        numberPlateTextView.setText(dataService.getNumberPlate());
        vehicleTypeTextView.setText(dataService.getVehicleType());
        serviceTypeTextView.setText(dataService.getServiceType());
        serviceDateTextView.setText(dataService.getServiceDate());
        serviceDescriptionTextView.setText(dataService.getServiceDescription());
        serviceLocation.setText(dataService.getLocation());

        if (dataService.getImageUri() != null) {
//            Bitmap bitmap = BitmapFactory.decodeFile(dataService.getImageUri());
            capturedImageView.setImageURI(Uri.parse(dataService.getImageUri()));
//            Toast.makeText(this, "Image Loaded" + dataService.getImageUri(), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateData()
    {
        String numberPlate = numberPlateTextView.getText().toString();
        String vehicleType = vehicleTypeTextView.getText().toString();
        String serviceType = serviceTypeTextView.getText().toString();
        String serviceDescription = serviceDescriptionTextView.getText().toString();
        String serviceDate = serviceDateTextView.getText().toString();

        if (!numberPlate.isEmpty() && !vehicleType.isEmpty() && !serviceType.isEmpty() && !serviceDescription.isEmpty() && !serviceDate.isEmpty()){
            repository.updateService(serviceId, numberPlate, vehicleType, serviceType, serviceDate, serviceDescription);
            Intent intent = new Intent(DetailServiceActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    private void deleteData()
    {
        new AlertDialog.Builder(DetailServiceActivity.this)
                .setTitle("Delete Service")
                .setMessage("Are you sure you want to delete this service?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // If the user clicks "Yes", delete the service
                        repository.deleteService(serviceId);
                        Toast.makeText(DetailServiceActivity.this, "Service Deleted", Toast.LENGTH_SHORT).show();
                        // Start the HomeActivity after deletion
                        Intent intent = new Intent(DetailServiceActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", null) // If the user clicks "No", nothing happens
                .show();
    }
}