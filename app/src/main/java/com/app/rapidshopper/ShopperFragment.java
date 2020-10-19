/*
 * Created on Mon Sep 28 2020
 *
 * Copyright (c) 2020 - RapidMart
 */

package com.app.rapidshopper;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopperFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopperFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView mRecyclerViewShops;
    ArrayList<String> mDataShops;
    RecyclerView.Adapter mAdapterShops;
    LinearLayoutManager mLinLayoutMgrShops;

    RecyclerView mRecyclerViewAssts;
    ArrayList<String> mDataAssts;
    RecyclerView.Adapter mAdapterAssts;
    LinearLayoutManager mLinLayoutMgrAssts;


    public ShopperFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopperFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopperFragment newInstance(String param1, String param2) {
        ShopperFragment fragment = new ShopperFragment();
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

        View view = inflater.inflate(R.layout.fragment_shopper, container, false);

        mRecyclerViewShops = (RecyclerView)view.findViewById(
                R.id.recyclerviewShops);
        setDataToAdapter();
        mAdapterShops = new Shops_adapter(mDataShops);
        mLinLayoutMgrShops = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false );
        mRecyclerViewShops.setLayoutManager( mLinLayoutMgrShops );
        mRecyclerViewShops.setAdapter( mAdapterShops );

        mRecyclerViewAssts = (RecyclerView)view.findViewById(
                R.id.recyclerviewAssistants);
        setDataToAsstsAdapter();
        mAdapterAssts = new AssistantAdapter(mDataAssts);
        mLinLayoutMgrAssts = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false );
        mRecyclerViewAssts.setLayoutManager( mLinLayoutMgrAssts );
        mRecyclerViewAssts.setAdapter( mAdapterAssts );

        return view;
    }
    public void setDataToAdapter()
    {
        mDataShops = new ArrayList<>();
        mDataShops.add("RapidMart_shop1");
        mDataShops.add("RapidMart_shop2");
        mDataShops.add("RapidMart_shop3");
        mDataShops.add("RapidMart_shop4");
        mDataShops.add("RapidMart_shop5");

    }
    public void setDataToAsstsAdapter()
    {
        mDataAssts = new ArrayList<>();
        mDataAssts.add("Micky");
        mDataAssts.add("Minny");
        mDataAssts.add("Goofy");
        mDataAssts.add("Donald");
        mDataAssts.add("Daisy");

    }
}