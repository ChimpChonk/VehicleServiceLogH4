package com.example.vehicleservicelog.Model;

public class ServiceListDTO {

    private int id;
    private String vehicleType;
    private String numberPlate;
    private String serviceName;
    private String serviceDate;

    public ServiceListDTO(int id, String vehicleType, String numberPlate, String serviceName, String serviceDate) {
        this.id = id;
        this.vehicleType = vehicleType;
        this.numberPlate = numberPlate;
        this.serviceName = serviceName;
        this.serviceDate = serviceDate;
    }
}
