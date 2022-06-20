package com.example.gps_app;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class GPSActivity extends AppCompatActivity {
    public static final String TAG = "GPSActivity";
    /*private FusedLocationProviderClient fusedLocationClient;*/


    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION, false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                localizar();

                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                // Somente localização aproximada autorizada
                            } else {
                                // Nenhuma localização autorizada
                            }
                        }
                );
        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
    }


    private void localizar() {

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Log.d(TAG, "onLocationChanged: " + location);
            }
        });

         FusedLocationProviderClient fusedLocationClient;
         fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
         fusedLocationClient.getLastLocation()
                 .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                     @Override
                     public void onSuccess(Location location) {
                         Log.d(TAG, "onSuccess: "+location);
                     }
                 });
         fusedLocationClient.getCurrentLocation(LocationRequest.QUALITY_HIGH_ACCURACY,
                 new CancellationToken() {
                     @Override
                     public boolean isCancellationRequested() {
                         return false;
                     }
                     @NonNull
                     @Override
                     public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                         return null;
                     }
                 })
                 .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                     @Override
                     public void onSuccess(Location location) {
                         Log.d(TAG, "onSuccess current location: "+location);
                     }
                 });
    }
}