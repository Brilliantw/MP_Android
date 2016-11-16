package com.example.lab.gpscode;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.vision.barcode.Barcode;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.lab.gpscode.R;

import static com.example.lab.gpscode.R.id.map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    static public int count;
    static LatLng pnt[] = new LatLng[30];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        count = 0;

        startLocationService();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        startLocationService();

    }

    public void startLocationService() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        GPSListener gpsLsn = new GPSListener();
        int minT = 1000 * 60 * 60;
        float minD = 0;


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           return;
        }
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minT, minD, gpsLsn);

    }

    public class GPSListener implements LocationListener {

        public void onLocationChanged(Location location) {

            Double lat = location.getLatitude();
            Double lot = location.getLongitude();

            String str = "위도: " + lat + "\n경도: " + lot;
            Log.e("GPSLocationService", str);

            LatLng point = new LatLng(lat, lot);
            pnt[count] = point;

            View marking = LayoutInflater.from(MapsActivity.this).inflate(R.layout.activity_maps,null);
            mMap.addMarker(new MarkerOptions().position(point).snippet("현재 위도: " + location.getLatitude() + "현재 경도: " + location.getLongitude()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).title("현재 위치"));
            mMap.moveCamera( CameraUpdateFactory.newLatLng(point) );

            if(count>=1){
                LatLng temp = pnt[count-1];
                int i =1;
                while (temp == pnt[count]){ temp = pnt[count-1-i]; i++;}
                mMap.addPolyline(new PolylineOptions().add(temp,pnt[count]).width(3).color(Color.BLUE));
            }
            if(count<31)
                count++;

        }
            public void onProviderDisabled(String provider) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }





        }
    }

