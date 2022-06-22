package com.example.gps_app.services;

import android.Manifest;
import android.app.NotificationChannel;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LifecycleService;

import com.example.gps_app.repositories.PosicaoRepository;
import com.example.gps_app.views.GPSActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.List;

public class GPSService extends LifecycleService {

    public static final String TAG = "GPSService";
    private LocationManager locationManager;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location lastLocation = null;
    private NotificationChannel notificationChannel;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate criado");

    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.w(TAG, "onStartCommand");

        LocationRequest currentLocationRequest = LocationRequest.create();
        currentLocationRequest.setInterval(5000)
                .setMaxWaitTime(180000)
                .setSmallestDisplacement(0)
                .setFastestInterval(3000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.requestLocationUpdates(currentLocationRequest,
                new LocationCallback() {
                    @Override
                    public void onLocationResult(@NonNull LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        float distance = 0;
                        Location loc = locationResult.getLastLocation();

                        Log.d("Location change", loc + "");

                        if (lastLocation != null) {
                            distance = loc.distanceTo(lastLocation);
                        } else {
                            lastLocation = loc;
                        }
                       PosicaoRepository.getInstance(getApplicationContext()).incluir(loc);
                        if (distance > loc.getAccuracy()) {
                            lastLocation = loc;
                        }
                    }
                }, null);

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return null;
    }
}