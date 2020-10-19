/*
 * Created on Sat Oct 10 2020
 *
 * Copyright (c) 2020 - RapidMart
 */
package com.app.rapidshopper;

import android.Manifest;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Item;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PdfDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PdfDataFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private static final int PDFREADWRITE_REQUEST = 5;
    private YourListFragment.OnFragmentInteractionListener mListener;
    EditText editTitle;
    EditText editDescription;
    EditText editData;
    File mPdffilePath;

    public static Handler handler;


    public PdfDataFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PdfDataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PdfDataFragment newInstance(String param1, String param2) {
        PdfDataFragment fragment = new PdfDataFragment();
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

        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1 ) {
                    addNewDatatoAWS();
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_pdf_data, container, false);

        editTitle = view.findViewById(R.id.editTitle);
        editDescription = view.findViewById(R.id.editDescription);
        editData = view.findViewById(R.id.editData);

        Button bSubmit = view.findViewById(R.id.submitButton);
        bSubmit.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
             //   createPdf(editData.getText().toString());
                new CreatePDFTask().execute();
                return false;
            }
        });
        PermissionUtils.requestPermission(getActivity(), PDFREADWRITE_REQUEST,
                new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE});
       return view;
    }
    private class CreatePDFTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            createPdf(editData.getText().toString());
            return null;
        }
        protected void onPostExecute(Void voids) {
            Message msg = handler.obtainMessage();
            msg.what = 1;
            handler.sendMessage(msg);
        }
    }
    public void addNewDatatoAWS()
    {
        Log.d("PdfDataFragment", "addNewDatatoAWS()");
        Item data = Item.builder().title(editTitle.getText().toString())
               .description(editDescription.getText().toString())
                .pdfdoc(editTitle.getText().toString()).build();


        Amplify.DataStore.save(
                data,
                success ->
                {
                    Log.d("PdfDataFragment", "Saved item to DataStore: Amplify.DataStore.save is Success");
                    uploadFile();
                },
                error -> Log.e("PdfDataFragment", "Could not save item to DataStore", error)
        );

    }
    private String getS3Key() {
        //We have read and write ability under the public folder
        return "public/" + editTitle.getText().toString();
    }
    public void uploadFile()
    {
        Log.d("PdfDataFragment", "uploadFile()");
        Amplify.Storage.uploadFile(
                getS3Key(),
                new File(mPdffilePath.getPath()),
                result ->  {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("PdfDataFragment", "Saved item to DataStore:  Amplify.Storage.uploadFile is Success");
                            mListener.changeFragment(R.id.yourlistfragment);

                        }});
                },
                storageFailure -> Log.e("PdfDataFragment", "Upload failed", storageFailure)
        );
    }
    private void createPdf(String textData){

        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();

        PdfDocument.Page pdfPage = pdfDocument.startPage(pageInfo);
        Canvas canvas = pdfPage.getCanvas();
        Paint paint = new Paint();

        paint.setColor(Color.BLACK);
        canvas.drawText(textData, 80, 50, paint);

        pdfDocument.finishPage(pdfPage);

        String filePath = Environment.getExternalStorageDirectory().getPath() + "/rapidshopper/";
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String targetPdf = filePath+"shoplist.pdf";
        mPdffilePath = new File(targetPdf);
        try {
            pdfDocument.writeTo(new FileOutputStream(mPdffilePath));

        } catch (IOException e) {
            Log.e("main", "error "+e.   toString());

        }

        pdfDocument.close();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof YourListFragment.OnFragmentInteractionListener) {
            mListener = (YourListFragment.OnFragmentInteractionListener) context;
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
    public interface OnFragmentInteractionListener {

        void changeFragment(int fragmentId);
    }
}