/*
 * Created on Mon Sep 28 2020
 *
 * Copyright (c) 2020 - RapidMart
 */
package com.app.rapidshopper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements
        YourListFragment.OnFragmentInteractionListener,PdfDataFragment.OnFragmentInteractionListener{

    Fragment curFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            curFragment = new LocationFragment();

            ft.replace(R.id.fragment_container, curFragment);
            ft.addToBackStack(null);
            ft.commit();
        }

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_shoppers:
                                changeFragment(R.id.idshopperFragment);
                                break;
                            case R.id.action_yourlist:
                                changeFragment(R.id.yourlistfragment);
                                break;
                            case R.id.action_location:
                                changeFragment(R.id.idLocationFragment);
                                break;
                            case R.id.pdfdatafragment:
                                changeFragment(R.id.pdfdatafragment);
                                break;

                        }
                        return true;
                    }
                });

    }
    public void changeFragment(int fragmentId)
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (fragmentId == R.id.idshopperFragment) {

            curFragment = ShopperFragment.newInstance(null,null);
        }
        else if (fragmentId == R.id.yourlistfragment) {

            curFragment = YourListFragment.newInstance(null,null);
        }
        else if (fragmentId == R.id.pdfdatafragment) {

            curFragment = PdfDataFragment.newInstance(null,null);
        }
        else if (fragmentId == R.id.idLocationFragment) {

            curFragment = LocationFragment.newInstance(null,null);
        }
        ft.replace(R.id.fragment_container, curFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}