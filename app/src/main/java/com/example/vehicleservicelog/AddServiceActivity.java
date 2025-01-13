package com.example.vehicleservicelog;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.io.InputStream;

public class AddServiceActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final String IMAGE_DIRECTORY = "VehicleServiceLog";
    private ImageView imageView;
    private Uri imageUri;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private TextView numberPlateEditText, vehicleTypeEditText, serviceTypeEditText, serviceDescriptionEditText, dateTextView;
    private DatePicker datePicker;
    private Button saveServiceButton, getLocationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_service);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        numberPlateEditText = findViewById(R.id.numberPlateEditText);
        vehicleTypeEditText = findViewById(R.id.vehicleTypeSpinner);
        serviceTypeEditText = findViewById(R.id.serviceTypeSpinner);
        serviceDescriptionEditText = findViewById(R.id.serviceDescriptionEditText);
        saveServiceButton = findViewById(R.id.saveServiceButton);
        getLocationButton = findViewById(R.id.getLocationButton);

        Button takePhotoButton = findViewById(R.id.takePhotoButton);
        dateTextView = findViewById(R.id.serviceDateEditText);
        imageView = findViewById(R.id.capturedImageView);
        DatePicker datePicker = findViewById(R.id.datePicker);



        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && imageUri != null) {
                        displayCapturedImage();
                    }
                });

        takePhotoButton.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
            } else {
                captureHighResImage();
            }
        });

        dateTextView.setOnClickListener(v -> {
            datePicker.setVisibility(datePicker.getVisibility() == DatePicker.VISIBLE ? DatePicker.GONE : DatePicker.VISIBLE);
            dateTextView.setText(datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear());
        });

        saveServiceButton.setOnClickListener(v -> saveService());
    }

    private void captureHighResImage() {
        // Create a new entry in the MediaStore
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "vehicle_service_" + System.currentTimeMillis() + ".jpg");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + IMAGE_DIRECTORY);

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        if (imageUri != null) {
            // Start the camera intent to capture a photo
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            cameraLauncher.launch(cameraIntent);
        } else {
            Toast.makeText(this, "Failed to create a file for the image.", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayCapturedImage() {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            if (inputStream != null) {
                // Decode the image stream
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();

                // Correct the orientation
                Bitmap correctedBitmap = correctImageOrientation(bitmap);

                // Update the ImageView with the corrected bitmap
                imageView.setImageBitmap(correctedBitmap);
                Toast.makeText(this, "Image captured and updated successfully.", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to load captured image.", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap correctImageOrientation(Bitmap bitmap) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(imageUri);
        ExifInterface exif = new ExifInterface(inputStream);

        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        Bitmap correctedBitmap = bitmap;

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                correctedBitmap = rotateImage(bitmap, 90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                correctedBitmap = rotateImage(bitmap, 180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                correctedBitmap = rotateImage(bitmap, 270);
                break;
        }

        if (inputStream != null) {
            inputStream.close();
        }

        return correctedBitmap;
    }

    private Bitmap rotateImage(Bitmap bitmap, int degrees) {
        android.graphics.Matrix matrix = new android.graphics.Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                captureHighResImage();
            } else {
                Toast.makeText(this, "Camera permission is required to use this feature.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveService()
    {
        String numberPlate = numberPlateEditText.getText().toString();
        String vehicleType = vehicleTypeEditText.getText().toString();
        String serviceType = serviceTypeEditText.getText().toString();
        String serviceDescription = serviceDescriptionEditText.getText().toString();
        String serviceDate = dateTextView.getText().toString();
    }
}


