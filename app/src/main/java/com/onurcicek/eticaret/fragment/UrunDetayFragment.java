package com.onurcicek.eticaret.fragment;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.onurcicek.eticaret.R;
import com.onurcicek.eticaret.activity.MainActivity;
import com.onurcicek.eticaret.model.Urun;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by plox on 03.01.2017.
 */

public class UrunDetayFragment extends Fragment {
    private static final String TAG = MainActivity.class.getSimpleName();
    private List<Urun> urunList = new ArrayList<Urun>();
    public View view_instance;
    private TextView textUrunName,textUrunKategori,textUrunAciklama,textUrunDetay;
    private ImageView imageUrunFotograf;
    private Button sepeteEkle;
    private String urlUrunFotograf;
    String url,urunid;
    private String server = "http://ertugrulkarakaya.com/eticaret/showSoruDetayCevap.php";
    public int sayac=0;
    String urun_id,urun_name,urun_metin,urun_fiyat;
    private ScrollView scrollView;
    private ListView listView;
    private ProgressDialog pDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_urundetay, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Ürün Detay");
        Bundle args = getArguments();
        urun_id=args.getString("urun_id");
        urun_name=args.getString("urun_name");
        urun_metin=args.getString("urun_metin");
        urun_fiyat=args.getString("urun_fiyat");

        textUrunName = (TextView) view.findViewById(R.id.textKullanici);
        textUrunKategori = (TextView) view.findViewById(R.id.textDers);
        textUrunAciklama = ( TextView) view.findViewById(R.id.textMetin);
        textUrunDetay = ( TextView) view.findViewById(R.id.textDetay);

        sepeteEkle = (Button) view.findViewById(R.id.cevapGonder);

        sepeteEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager;
                FragmentTransaction fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                SepeteEkleFragment sepeteEkleFragment = new SepeteEkleFragment();

                Bundle args = new Bundle();
                args.putString("urun_id",urun_id);
                args.putString("urun_name",urun_name);
                args.putString("urun_metin", urun_metin);
                args.putString("urun_fiyat",urun_fiyat);
                args.putString("urun_fotograf",urlUrunFotograf);

                sepeteEkleFragment.setArguments(args);
                fragmentTransaction.replace(R.id.fragment, sepeteEkleFragment);
                fragmentTransaction.commit();
            }
        });

        scrollView = (ScrollView) view.findViewById(R.id.scrollView) ;

        imageUrunFotograf = (ImageView) view.findViewById(R.id.imageView);

        textUrunName.setText(args.getString("urun_name"));
        textUrunKategori.setText(args.getString("urun_kategori"));
        textUrunAciklama.setText(args.getString("urun_aciklama"));
        textUrunDetay.setText(args.getString("urun_detay"));
        urlUrunFotograf = args.getString("urun_fotograf");
        urunid=args.getString("urun_id");



        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Yükleniyor...");
        pDialog.show();

        new DownloadSoruFotograf(urlUrunFotograf).execute();

        //requestQueue.add(jsonObjectRequest);
        //AppController.getInstance().addToRequestQueue(jsonObjectRequest);

        return view;
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
                imageUrunFotograf.setImageBitmap(bitmap);
                pDialogGizle();

            } else {
                imageUrunFotograf.setImageBitmap(bitmap);
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
