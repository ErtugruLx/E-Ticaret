package com.onurcicek.eticaret.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.onurcicek.eticaret.R;
import com.onurcicek.eticaret.activity.MainActivity;
import com.onurcicek.eticaret.activity.SepetimActivity;
import com.onurcicek.eticaret.adapter.UrunAdapter;
import com.onurcicek.eticaret.model.Urun;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by plox on 04.01.2017.
 */

public class SepeteEkleFragment extends Fragment {

    private static final String TAG = MainActivity.class.getSimpleName();

    String url;
    private List<Urun> urunList = new ArrayList<Urun>();

    private UrunAdapter adapter;

    private Button ekleButon;
    private TextView textName,textMetin;
    private ImageView imageView;
    private EditText adet;
    private ProgressDialog pDialog;

    String urunismi,sayi,fiyat,urunid;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_sepetekle, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Sepete Ekle");

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Yükleniyor...");
        pDialog.show();

        textName = (TextView) view.findViewById(R.id.textName);
        textMetin = (TextView) view.findViewById(R.id.textAciklama);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        adet = (EditText) view.findViewById(R.id.adet);
        ekleButon = (Button) view.findViewById(R.id.ekleButon);

        ekleButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sayi = adet.getText().toString();
                System.out.println(sayi);
                System.out.println(urunismi);
                System.out.println(fiyat);

                Intent intent = new Intent(getActivity(), SepetimActivity.class);
                intent.putExtra("sayi",sayi);
                intent.putExtra("urunismi",urunismi);
                intent.putExtra("fiyat",fiyat);
                intent.putExtra("urunid",urunid);
                startActivity(intent);

            }
        });

        Bundle args = getArguments();
        urunismi=args.getString("urun_name");
        fiyat=args.getString("urun_fiyat");
        urunid=args.getString("urun_id");
        textName.setText(urunismi);
        textMetin.setText(args.getString("urun_metin"));
        url=args.getString("urun_fotograf");


/*
        Integer[] items = new Integer[]{1,2,3,4};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, items);
        spQuantity.setAdapter(adapter);
*/

        new DownloadSoruFotograf(url).execute();


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                FragmentManager fragmentManager;
                FragmentTransaction fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                AnasayfaFragment anasayfaFragment = new AnasayfaFragment();
                fragmentTransaction.replace(R.id.fragment, anasayfaFragment);
                fragmentTransaction.commit();
                return true;
            }
        });

    }


    //
    //Soru DOWNLOAD
    //
    public class DownloadSoruFotograf extends AsyncTask<Void,Void,Bitmap> {


        String name;

        public DownloadSoruFotograf(String name) {
            this.name = name;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {

            url = name;

            try {
                URLConnection connection = new URL(url).openConnection();
                connection.setConnectTimeout(1000 * 30);
                connection.setReadTimeout(1000 * 30);

                return BitmapFactory.decodeStream((InputStream) connection.getContent(), null, null);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
                pDialogGizle();

            } else {
                imageView.setImageBitmap(bitmap);
                pDialogGizle();

            }

        }
    }


    private void pDialogGizle() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
