package com.onurcicek.eticaret.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.onurcicek.eticaret.R;
import com.onurcicek.eticaret.fragment.AnasayfaFragment;
import com.onurcicek.eticaret.fragment.CiftlikFragment;
import com.onurcicek.eticaret.fragment.DomatesBiberFragment;
import com.onurcicek.eticaret.fragment.EkmekFragment;
import com.onurcicek.eticaret.fragment.HakkindaFragment;
import com.onurcicek.eticaret.fragment.RecelBalFragment;
import com.onurcicek.eticaret.fragment.SalcaTursuFragment;
import com.onurcicek.eticaret.fragment.SutPeynirFragment;
import com.onurcicek.eticaret.fragment.YardimFragment;
import com.onurcicek.eticaret.fragment.YumurtaFragment;
import com.onurcicek.eticaret.fragment.ZeytinZeytinYagiFragment;
import com.onurcicek.eticaret.helper.SQLiteHandler;
import com.onurcicek.eticaret.helper.SessionManager;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by plox on 04.01.2017.
 */

public class SepetimActivity extends AppCompatActivity {

    Button bClear;
    Button bShop;
    TextView tvTotalPrice;
    int adet,fiyat;
    float toplam=0;
    String urunid;
    private SQLiteHandler db;
    private SessionManager session;
    String url = "http://ertugrulkarakaya.com/eticaret/siparisGonder.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_sepetim);
        Intent i = getIntent();
        adet= Integer.parseInt(i.getExtras().getString("sayi"));
        fiyat=Integer.parseInt(i.getExtras().getString("fiyat"));
        toplam=adet*fiyat;
        System.out.println(toplam);
        urunid=i.getExtras().getString("urunid");

        final TextView tvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);

        tvTotalPrice.setText(Float.toString(toplam));

        final TextView urun = (TextView) findViewById(R.id.urun);

        urun.setText("   "+i.getExtras().getString("urunismi") + "       " + i.getExtras().getString("sayi") + " ADET ");


        bClear = (Button) findViewById(R.id.bClear);
        bShop = (Button) findViewById(R.id.bShop);

        bClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urun.setText("");
                tvTotalPrice.setText("0");
            }
        });

        bShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new SQLiteHandler(SepetimActivity.this);
                session = new SessionManager(SepetimActivity.this);
                HashMap<String, String> user = db.getUserDetails();
                String emailx = user.get("email");
                System.out.println(urunid);
                System.out.println(emailx);
                System.out.println(adet);
                System.out.println(toplam);

                new SiparisGonder(urunid,emailx,Integer.toString(adet),Float.toString(toplam)).execute();

            }
        });

    }



    //
    //SİPARİŞ GÖNDER
    //
    public class SiparisGonder extends AsyncTask<Void, Void, Void> {


        String urunid,emailx,adet,toplam;

        public SiparisGonder(String urunid,String emailx,String adet,String toplam){
            this.urunid=urunid;
            this.emailx = emailx;
            this.adet=adet;
            this.toplam=toplam;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(SepetimActivity.this,"Sipariş Başarılı!", Toast.LENGTH_LONG).show();
        }

        private HttpParams getHttpRequestParams(){
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams,1000*300);
            HttpConnectionParams.setSoTimeout(httpRequestParams,1000*30);
            return httpRequestParams;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();

            dataToSend.add(new BasicNameValuePair("urunid",urunid));
            dataToSend.add(new BasicNameValuePair("email",emailx));
            dataToSend.add(new BasicNameValuePair("adet",adet));
            dataToSend.add(new BasicNameValuePair("toplam",toplam));

            HttpParams httpRequestParams = getHttpRequestParams();

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(url);
            try{
                HttpResponse response = client.execute(post);
                System.out.println(response);
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

    }



    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && isTaskRoot()) {
            //setFragment(0);
            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }
    }

}
