package com.paymentez.plazez.sdk.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.Task;
import com.paymentez.plazez.sdk.models.PmzStore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GpsManager implements LocationListener {

    private static final long MIN_TIME = 2000;
    private static final float MIN_DISTANCE = 1;

    private static GpsManager instance;
    private LocationManager locationManager;
    private Location location;
    private boolean running = false;
    private boolean keepListening;
    private IProLocationListener proListener;
    private boolean gpsEnabled = false;

    private Context lastContext;

    private GpsManager() {
    }

    public static GpsManager getInstance() {
        if (instance == null) {
            instance = new GpsManager();
        }
        return instance;
    }

    public boolean isGPSEnabled() {
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public enum Locations {
        OBELISK,
        FENNOMA
    }

    public LatLng getLatLng() {
        if(location == null){
            return null;
        }
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    public void findLocation(Context context, boolean keepLooking, IProLocationListener listener) {
        if(!running) {
            running = true;
            this.proListener = listener;
            this.keepListening = keepLooking;
            if (ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(context,
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            if(location != null){
                proListener.bestLastKnownLocation(gpsEnabled, location);
            } else {
                proListener.warnGpsIsDisabled();
            }
            if(locationManager != null) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
            }
        }
    }

    public Task<LocationSettingsResponse> requestGPSEnable(final Activity activity, final int requestKey) {
        LocationRequest mLocationRequestBalancedPowerAccuracy = LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationRequest mLocationRequestHighAccuracy = LocationRequest.create().setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequestHighAccuracy)
                .addLocationRequest(mLocationRequestBalancedPowerAccuracy);
        builder.setNeedBle(true);
        final Task<LocationSettingsResponse> task = LocationServices.getSettingsClient(activity).checkLocationSettings(builder.build());
        task.addOnCanceledListener(activity, new OnCanceledListener() {
            @Override
            public void onCanceled() {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    if(response != null) {
                        response.getLocationSettingsStates();
                    }
                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                resolvable.startResolutionForResult(activity, requestKey);
                            } catch (IntentSender.SendIntentException e) {
                                e.printStackTrace();
                            } catch (ClassCastException e) {
                                e.printStackTrace();
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                }
            }
        });
        return task;
    }

    public boolean areServicesEnabled() {
        return locationManager != null && (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
    }

    public void forceToFindLocation(Context context) {
        location = getBestLastKnownLocation(context);
        if(proListener != null){
            proListener.onGotLocation(location);
        }
    }

    public interface ILocationListener {
        void onGotLocation(Location location);
        void onFailedGettingLocation();
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        if (proListener != null) {
            proListener.onGotLocation(location);
        }
        if (!keepListening) {
            stopFindingLocation();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.d("ENABLE", s);
    }

    @Override
    public void onProviderDisabled(String s) {
        location = null;
        if(proListener != null){
            proListener.warnGpsIsDisabled();
        }
        stopFindingLocation();
    }

    public Location getLocation() {
        return location;
    }

    public void startUp(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        location = getBestLastKnownLocation(context);
    }

    public void stopFindingLocation() {
        running = false;
        if(locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    public boolean isRunning(){
        return running;
    }

    public interface IProLocationListener{
        void onGotLocation(Location location);
        void bestLastKnownLocation(boolean gpsEnabled, Location location);
        void warnGpsIsDisabled();
    }

    private Location getBestLastKnownLocation(Context context) {
        Location networkLocation = getLocationByProvider(context, LocationManager.NETWORK_PROVIDER);
        Location gpsLocation = getLocationByProvider(context, LocationManager.GPS_PROVIDER);
        if (gpsLocation == null) {
            gpsEnabled = false;
            return networkLocation;
        }
        if (networkLocation == null) {
            gpsEnabled = true;
            return gpsLocation;
        }
        if (gpsLocation.getTime() > networkLocation.getTime()) {
            return gpsLocation;
        } else {
            return networkLocation;
        }
    }

    private Location getLocationByProvider(Context context, String provider) {
        if (!locationManager.isProviderEnabled(provider)) {
            return null;
        }
        try {
            if (ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(context,
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            Location lastKnownLocation = locationManager.getLastKnownLocation(provider);
            if(lastKnownLocation != null) {
                location = lastKnownLocation;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return location;
    }

    public interface OnReverseGeocodingListener{
        void onAddressResolved(Location location, List<Address> list);
    }

    public static void reverseGeocoding(Activity activity, Location location, OnReverseGeocodingListener listener) {

        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
        try {
            if(location != null) {
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                listener.onAddressResolved(location, addresses);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface OnGeocodingListener{
        void onLocationResolved(String address, List<Location> locations);
    }

    public static void directGeocoding(Activity activity, String address, OnGeocodingListener listener){

        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            if(addresses != null && !addresses.isEmpty()) {
                List<Location> locations = new ArrayList<>();
                for (Address gotAddress : addresses){
                    Location location = new Location("");
                    location.setLatitude(gotAddress.getLatitude());
                    location.setLongitude(gotAddress.getLongitude());
                    locations.add(location);
                }
                listener.onLocationResolved(address, locations);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Float getDistanceFromCurrent(PmzStore store) {
        if(getLatLng() != null) {
            return calculateDistanceInKms(getLatLng(), store);
        }
        return null;
    }

    public static Float calculateDistanceInKms(LatLng latLng, PmzStore store) {
        if (latLng == null || store.getLatLng() == null) {
            return null;
        }
        Location locationA = new Location("A");
        locationA.setLatitude(latLng.latitude);
        locationA.setLongitude(latLng.longitude);
        Location locationB = new Location("B");
        locationB.setLatitude(store.getLatLng().latitude);
        locationB.setLongitude(store.getLatLng().longitude);

        return locationA.distanceTo(locationB) / 1000;
    }

}