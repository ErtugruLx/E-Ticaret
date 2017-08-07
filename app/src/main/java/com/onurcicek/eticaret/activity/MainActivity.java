package com.onurcicek.eticaret.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    private NavigationView navigationVieww;
    private View navHeader;



    private SQLiteHandler db;
    private SessionManager session;

    private ImageView imgProfile;
    public TextView txtName, txtWebsite;
    String url;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);
        //navigationVieww = (NavigationView) findViewById(R.id.navigation_view);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }
        navHeader = navigationView.getHeaderView(0);
        setupNavigationDrawerContent(navigationView);

        setFragment(0);


        txtName = (TextView) navHeader.findViewById(R.id.namee);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);






        db = new SQLiteHandler(getApplicationContext());

        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        loadNavHeader();

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                loadNavHeader();
                return true;
            case R.id.sepetim:
                Intent git1 = new Intent(MainActivity.this, SepetimActivity.class);
                startActivity(git1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupNavigationDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {


                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        //loadNavHeader();
                        switch (menuItem.getItemId()) {
                            case R.id.item_anasayfa:
                                menuItem.setChecked(true);
                                setFragment(0);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_ciftlik:
                                menuItem.setChecked(true);
                                setFragment(1);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_domates_biber:
                                menuItem.setChecked(true);
                                setFragment(2);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_sut_peynir:
                                menuItem.setChecked(true);
                                setFragment(3);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_yumurta:
                                menuItem.setChecked(true);
                                setFragment(4);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_salca_tursu:
                                menuItem.setChecked(true);
                                setFragment(5);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_zeyin_zeytinyagi:
                                menuItem.setChecked(true);
                                setFragment(6);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_recel_bal:
                                menuItem.setChecked(true);
                                setFragment(7);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_ekmek:
                                menuItem.setChecked(true);
                                setFragment(8);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_hakkinda:
                                menuItem.setChecked(true);
                                setFragment(9);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_yardim:
                                menuItem.setChecked(true);
                                setFragment(10);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.logout:
                                menuItem.setChecked(true);
                                Toast.makeText(getApplicationContext(),"Çıkış Yapıldı!",Toast.LENGTH_LONG).show();
                                logoutUser();
                                return true;
                        }
                        return true;
                    }
                });
    }

    public void setFragment(int position) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        switch (position) {
            case 0:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                AnasayfaFragment anasayfaFragment = new AnasayfaFragment();
                fragmentTransaction.replace(R.id.fragment, anasayfaFragment);
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                CiftlikFragment soruGonder = new CiftlikFragment();
                fragmentTransaction.replace(R.id.fragment, soruGonder);
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                DomatesBiberFragment domatesBiberFragment = new DomatesBiberFragment();
                fragmentTransaction.replace(R.id.fragment, domatesBiberFragment);
                fragmentTransaction.commit();
                break;
            case 3:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                SutPeynirFragment sutPeynirFragment = new SutPeynirFragment();
                fragmentTransaction.replace(R.id.fragment, sutPeynirFragment);
                fragmentTransaction.commit();
                break;
            case 4:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                YumurtaFragment yumurtaFragment = new YumurtaFragment();
                fragmentTransaction.replace(R.id.fragment, yumurtaFragment);
                fragmentTransaction.commit();
                break;
            case 5:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                SalcaTursuFragment salcaTursuFragment = new SalcaTursuFragment();
                fragmentTransaction.replace(R.id.fragment, salcaTursuFragment);
                fragmentTransaction.commit();
                break;
            case 6:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                ZeytinZeytinYagiFragment zeytinZeytinYagiFragment = new ZeytinZeytinYagiFragment();
                fragmentTransaction.replace(R.id.fragment, zeytinZeytinYagiFragment);
                fragmentTransaction.commit();
                break;
            case 7:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                RecelBalFragment recelBalFragment = new RecelBalFragment();
                fragmentTransaction.replace(R.id.fragment, recelBalFragment);
                fragmentTransaction.commit();
                break;
            case 8:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                EkmekFragment ekmekFragment = new EkmekFragment();
                fragmentTransaction.replace(R.id.fragment, ekmekFragment);
                fragmentTransaction.commit();
                break;
            case 9:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                HakkindaFragment hakkindaFragment = new HakkindaFragment();
                fragmentTransaction.replace(R.id.fragment, hakkindaFragment);
                fragmentTransaction.commit();
                break;
            case 10:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                YardimFragment yardimFragment = new YardimFragment();
                fragmentTransaction.replace(R.id.fragment, yardimFragment);
                fragmentTransaction.commit();
                break;
        }
    }


    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void loadNavHeader() {
        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        HashMap<String, String> user = db.getUserDetails();
        String name = user.get("name");
        String email = user.get("email");

        txtName.setText(name);
        txtWebsite.setText("Öğrenci");

        //new DownloadImage(email).execute();
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
