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

public class CiftlikFragment extends Fragment {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String url = "http://ertugrulkarakaya.com/eticaret/showUrunler.php";

    private RequestQueue requestQueue;
    private ProgressDialog pDialog;
    private List<Urun> urunList = new ArrayList<Urun>();
    private ListView listView;
    private UrunAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_ciftlik, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Çiftlik");

        requestQueue = Volley.newRequestQueue(getActivity());

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
    }


    private void pDialogGizle() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
