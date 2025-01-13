package com.example.vehicleservicelog.Repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.vehicleservicelog.AppDatabase;
import com.example.vehicleservicelog.Dao.DataServiceDao;
import com.example.vehicleservicelog.Model.DataService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataServiceRepository {

    private final DataServiceDao dataServiceDao;
    private final LiveData<List<DataService>> allServiceList;
    private final ExecutorService executorService;

    public DataServiceRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        dataServiceDao = database.dataServiceDao();
        allServiceList = dataServiceDao.getAllServiceList();
        executorService = Executors.newFixedThreadPool(2); // Use a background thread pool
    }

    // Retrieve all services (for list display)
    public LiveData<List<DataService>> getAllServiceList() {
        return allServiceList;
    }

    // Insert a service (on a background thread)
    public void insert(DataService dataService) {
        executorService.execute(() -> dataServiceDao.insert(dataService));
    }

    // Delete a specific service by ID
    public void deleteService(int id) {
        executorService.execute(() -> dataServiceDao.deleteService(id));
    }

    // Get a specific service by ID (synchronous, for detail view)
    public DataService getServiceById(int id) {
        return dataServiceDao.getServiceById(id); // Call is synchronous; ensure proper usage
    }

    // Retrieve the service log list (optimized for specific fields)
    public LiveData<DataService> getServiceLogList() {
        return dataServiceDao.getServiceLogList();
    }
}
