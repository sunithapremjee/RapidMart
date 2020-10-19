/*
 * Created on Sat Oct 3 2020
 *
 * Copyright (c) 2020 - RapidMart
 */
package com.app.rapidshopper;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationFragment extends Fragment implements
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback{

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private boolean permissionDenied = false;

    private GoogleMap map;
    SupportMapFragment mapFragment = null;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LocationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocationFragment.
     */

    public static LocationFragment newInstance(String param1, String param2) {
        LocationFragment fragment = new LocationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_location, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);
        enableMyLocation();

    }


    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (map != null) {
                map.setMyLocationEnabled(true);

                LatLng RapidMart_Shop1 = new LatLng(39.2137, -85.8792);
                map.addMarker(new MarkerOptions()
                        .position(RapidMart_Shop1)
                        .title("RapidMart_Shop1")).showInfoWindow();
                map.moveCamera(CameraUpdateFactory.newLatLng(RapidMart_Shop1));

                LatLng RapidMart_Shop2 = new LatLng(39.200939, -85.941926);
                map.addMarker(new MarkerOptions()
                        .position(RapidMart_Shop2)
                        .title("RapidMart_Shop2")).showInfoWindow();
                map.moveCamera(CameraUpdateFactory.newLatLng(RapidMart_Shop2));

                LatLng RapidMart_Shop3 = new LatLng(39.200629, -85.935915);
                map.addMarker(new MarkerOptions()
                        .position(RapidMart_Shop3)
                        .title("RapidMart_Shop3")).showInfoWindow();
                map.moveCamera(CameraUpdateFactory.newLatLng(RapidMart_Shop3));

                LatLng RapidMart_Shop4 = new LatLng(39.200090, -85.932102);
                map.addMarker(new MarkerOptions()
                        .position(RapidMart_Shop4)
                        .title("RapidMart_Shop4")).showInfoWindow();
                map.moveCamera(CameraUpdateFactory.newLatLng(RapidMart_Shop4));

            }
        } else {

            PermissionUtils.requestPermission(getActivity(), LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(getContext(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();

        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(getContext(), "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.permissionGranted(requestCode, LOCATION_PERMISSION_REQUEST_CODE,grantResults )) {

            enableMyLocation();
        } else {

            permissionDenied = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (permissionDenied) {

            Toast.makeText(getActivity(), R.string.permission_required,
                    Toast.LENGTH_SHORT).show();
            permissionDenied = false;
        }
    }
}