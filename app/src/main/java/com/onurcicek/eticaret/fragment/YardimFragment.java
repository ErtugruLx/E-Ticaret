package com.onurcicek.eticaret.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.onurcicek.eticaret.R;
import com.onurcicek.eticaret.activity.MainActivity;
import com.onurcicek.eticaret.adapter.UrunAdapter;
import com.onurcicek.eticaret.app.AppController;
import com.onurcicek.eticaret.model.Urun;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by plox on 03.01.2017.
 */

public class YardimFragment extends Fragment {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String url = "http://ertugrulkarakaya.com/eticaret/showUrunler.php";

    private RequestQueue requestQueue;
    private ProgressDialog pDialog;
    private List<Urun> urunList = new ArrayList<Urun>();
    private ListView listView;
    private UrunAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_anasayfa, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Tüm Ürünler");

        requestQueue = Volley.newRequestQueue(getActivity());

        listView = (ListView) view.findViewById(R.id.list);
        adapter = new UrunAdapter(this, urunList);
        listView.setAdapter(adapter);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Yükleniyor...");
        pDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
                pDialogGizle();
                try {
                    JSONArray sorular = response.getJSONArray("urunler");
                    for (int i = 0; i < sorular.length(); i++) {
                        JSONObject soru= sorular.getJSONObject(i);
                        Urun nesne = new Urun();
                        nesne.setUrun_id(soru.getString("urun_id"));
                        nesne.setUrun_name(soru.getString("urun_name"));
                        nesne.setUrun_metin(soru.getString("urun_metin"));
                        nesne.setUrun_fotograf(soru.getString("urun_fotograf"));
                        nesne.setUrun_kategori(soru.getString("kategori_adi"));
                        urunList.add(nesne);
                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialogGizle();

            }
        });
        //requestQueue.add(jsonObjectRequest);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
/*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long rowId) {
                FragmentManager fragmentManager;
                FragmentTransaction fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                SoruDetayFragment soruDetayFragment = new SoruDetayFragment();
                Bundle args = new Bundle();
                args.putString("Name", soruList.get(position).getName());
                args.putString("KullaniciFotograf", soruList.get(position).getKullanici_fotograf());
                args.putString("Soru_metin",soruList.get(position).getSoru_metin());
                args.putString("Soru_id",soruList.get(position).getSoru_id());
                args.putString("Ders", soruList.get(position).getDers_adi());
                args.putString("Konu", soruList.get(position).getKonu_adi());
                args.putString("Fotograf", soruList.get(position).getSoru_fotograf());
                soruDetayFragment.setArguments(args);
                fragmentTransaction.replace(R.id.fragment, soruDetayFragment);
                fragmentTransaction.commit();

            }
        });
        */
    }


    private void pDialogGizle() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
