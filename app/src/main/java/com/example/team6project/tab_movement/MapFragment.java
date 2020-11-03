package com.example.team6project.tab_movement;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.team6project.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private String fullAddress;
    private GpsTracker gpsTracker;
    private Button btnRecordWindow;
  
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize view
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // Initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        // Async map
        supportMapFragment.getMapAsync(this);

        btnRecordWindow = view.findViewById(R.id.btnRecordWindow);

        btnRecordWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), RecordMovement.class);
                in.putExtra("address", fullAddress);
                startActivity(in);
            }
        });

        // Return view
        return view;
    }

    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        gpsTracker = new GpsTracker(getActivity());
        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();

        LatLng latLng = new LatLng(37.557667, 126.926546);
        String test = getCityName(latLng);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        //googleMap.moveCamera(CameraUpdateFactory.zoomTo(12));
        final MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(test);
        googleMap.addMarker(markerOptions);



        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            LatLng curlatLng = new LatLng(latitude, longitude);
            fullAddress = getCityName(curlatLng);
            MarkerOptions curpos = new MarkerOptions().position(curlatLng).title(fullAddress);
            googleMap.addMarker(curpos);

        } else {
            checkLocationPermissionWithRationale();
        }




    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    // 위치정보 접근권한에 대한 허용을 물어보는 함수
    private void checkLocationPermissionWithRationale() {
        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this.getContext())
                        .setTitle("위치정보")
                        .setMessage("이 앱을 사용하기 위해서는 위치정보에 접근이 필요합니다. 위치정보 접근을 허용해주세요")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        }).create().show();
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }
    

    // 현재위치를 경도와 위도를 이용하여 그 위치의 주소를 반환
    private String getCityName(LatLng myCoordinates) {
        String myCity = "";
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses = null;
        try{
            addresses = geocoder.getFromLocation(myCoordinates.latitude, myCoordinates.longitude, 10);
            String address = addresses.get(0).getAddressLine(0);
            myCity = address;

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(getContext(), "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }

        return myCity;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        googleMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    // Address, 장소이름을 받아 marker 추가 및 이동
    public void addMarker(Address address, String title){
        LatLng point = new LatLng(address.getLatitude(), address.getLongitude());
        MarkerOptions mOptions = new MarkerOptions();
        mOptions.position(point);
        mOptions.title(title);
        String[] subAddress = address.getAddressLine(0).split(", ");
        String snippetText = subAddress[0] + " " + subAddress[1] + "\n" + subAddress[2] + " " + subAddress[3];
                mOptions.snippet(snippetText);
        googleMap.addMarker(mOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));
    }
}