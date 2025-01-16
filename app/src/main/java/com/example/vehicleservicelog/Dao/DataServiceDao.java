package com.example.vehicleservicelog.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.vehicleservicelog.Model.DataService;

import java.util.List;

@Dao
public interface DataServiceDao {

    @Insert
    void insert(DataService dataService);

    @Query("SELECT * FROM service_table")
    LiveData<List<DataService>> getAllServiceList();

    @Query("DELETE FROM service_table WHERE id = :id")
    void deleteService(int id);

    @Query("SELECT * FROM service_table WHERE id = :id")
    DataService getServiceById(int id);

    @Query("SELECT id, vehicleType, numberPlate, serviceType, serviceDate FROM service_table")
    LiveData<DataService> getServiceLogList();

    @Query("UPDATE service_table SET numberPlate = :numberPlate, vehicleType = :vehicleType, serviceType = :serviceType, serviceDate = :serviceDate, serviceDescription = :serviceDescription WHERE id = :id")
    void updateService(int id, String numberPlate, String vehicleType, String serviceType, String serviceDate, String serviceDescription);


}
