package com.example.vehicleservicelog;

public class ServiceLogList {

    String vehicleType;
    String numberPlate;
    String serviceName;
    String serviceDate;

    public ServiceLogList(String vehicleType, String numberPlate, String serviceName, String serviceDate) {
        this.vehicleType = vehicleType;
        this.numberPlate = numberPlate;
        this.serviceName = serviceName;
        this.serviceDate = serviceDate;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }
}

