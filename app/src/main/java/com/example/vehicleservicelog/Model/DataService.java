package com.example.vehicleservicelog.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "service_table")
public class DataService {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String numberPlate;
    private String vehicleType;
    private String serviceType;
    private String serviceDate;
    private String serviceDescription;
    private String imageUri;
    private String location;

    public DataService(String numberPlate, String vehicleType, String serviceType,
                   String serviceDate, String serviceDescription, String imageUri, String location) {
        this.numberPlate = numberPlate;
        this.vehicleType = vehicleType;
        this.serviceType = serviceType;
        this.serviceDate = serviceDate;
        this.serviceDescription = serviceDescription;
        this.imageUri = imageUri;
        this.location = location;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
