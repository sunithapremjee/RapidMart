/*
 * Created on Tue Sep 29 2020
 *
 * Copyright (c) 2020 - RapidMart
 */
package com.app.rapidshopper;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YourListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YourListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    List<Item> mData;
    String docPath = null;

    YourListAdapter mAdapter;
    RecyclerView mRecyclerView;

    public YourListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YourListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YourListFragment newInstance(String param1, String param2) {
        YourListFragment fragment = new YourListFragment();
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
        mData = new ArrayList<>();

        mAdapter = new YourListAdapter(getContext());
      // runQuery();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_your_list, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_viewyourlist);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = view.findViewById(R.id.fabAddNewData);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.changeFragment(R.id.pdfdatafragment);
            }
        });
        FloatingActionButton fabDoctoPDF = view.findViewById(R.id.fabCreatefrmDoc);
        fabDoctoPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PermissionUtils.requestPermission(getActivity(), DOC_REQUEST,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        void changeFragment(int fragmentId);
    }

    private int DOC_REQUEST = 1;
    public void openGalleryForDoc() {

        if (PermissionUtils.requestPermission(getActivity(), DOC_REQUEST,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
            chooseFile.setType("*/*");
            chooseFile = Intent.createChooser(chooseFile, "Choose a file");
            startActivityForResult(chooseFile, DOC_REQUEST);
        }
    }


    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {

        if (requestCode == DOC_REQUEST && null != data)
        {

                Uri uri = data.getData();
                try {

                    docPath = getDocPath(uri);
                }
                catch (Exception e) {
                    //handle exception
                    e.printStackTrace();

                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

        }
    }

    public void runQuery(){

        mData.clear();

        Amplify.DataStore.query(
                Item.class,
                items -> {

                    while (items.hasNext()) {
                        Item item = items.next();
                        mData.add(item);

                        Log.i("YourListFragment", "==== Item ====");
                        Log.i("YourListFragment", "Name: " + item.getTitle());

                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            mAdapter.notifyDataSetChanged();
                        }
                    });

                },
                failure -> Log.e("YourListFragment", "Could not query DataStore", failure)
        );
    }

    public String getDocPath( Uri uri )
    {
        String[] data = {MediaStore.Files.FileColumns.DATA};


        Cursor cursor = getContext().getContentResolver().query(uri,
                data, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(data[0]);
        String docPath = cursor.getString(columnIndex);
        cursor.close();

        return docPath;
    }


    public class YourListAdapter extends RecyclerView.Adapter<YourListAdapter.ViewHolder>
    {
        private LayoutInflater mInflater;
        Context context;

        private View.OnClickListener mClickListener;

        YourListAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
            this.context = context;

        }

        // inflates the row layout from xml when needed
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = mInflater.inflate(R.layout.yourlist_adapter_row, parent, false);
            return new ViewHolder(view);
        }

        public void setClickListener(View.OnClickListener clickListener)
        {
            mClickListener = clickListener;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.bindData(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return  mData.size();
        }

        // resets the list with a new set of data
        public void setItems(List<Item> items) {
            mData = items;
        }
        public List<Item>  getItems()
        {
            return mData;
        }


        class ViewHolder extends RecyclerView.ViewHolder {

            TextView txt_name;

            ImageView image_view;


            ViewHolder(View itemView) {
                super(itemView);
                txt_name = itemView.findViewById(R.id.txt_name_yourpdf);
                image_view = itemView.findViewById(R.id.image_viewyourpdf);

            }

            void bindData(Item item) {
                txt_name.setText("Title : " + item.getTitle());

            }
        }
    }
}



