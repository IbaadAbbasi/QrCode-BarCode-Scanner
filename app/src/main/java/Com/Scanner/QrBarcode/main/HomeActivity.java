package Com.Scanner.QrBarcode.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import Com.Scanner.QrBarcode.R;
import Com.Scanner.QrBarcode.create.input.ContactInputFragment;
import Com.Scanner.QrBarcode.create.input.EmailInputFragment;
import Com.Scanner.QrBarcode.create.input.EventInputFragment;
import Com.Scanner.QrBarcode.create.input.GooglePlayFragment;
import Com.Scanner.QrBarcode.create.input.LocationInputFragment;
import Com.Scanner.QrBarcode.create.input.PhoneInputFragment;
import Com.Scanner.QrBarcode.create.input.SmsInputFragment;
import Com.Scanner.QrBarcode.create.input.TextInputFragment;
import Com.Scanner.QrBarcode.create.input.UpdateView;
import Com.Scanner.QrBarcode.create.input.UrlInputFragment;
import Com.Scanner.QrBarcode.create.input.WifiInputFragment;
import Com.Scanner.QrBarcode.details.DetailsActivity;
import Com.Scanner.QrBarcode.history.HistoryFragment;
import Com.Scanner.QrBarcode.scan.ScanFragment;
import Com.Scanner.QrBarcode.setting.SettingsActivity;


public class HomeActivity extends AppCompatActivity
        implements UpdateView {

    private static final long NAVDRAWER_LAUNCH_DELAY = 250;
    private Handler mHandler;
    private Fragment fragment;
    public static final String QR_STRING =  "QR_STRING";
    public static final String QR_TYPE = "QR_TYPE";
    public static final String ADD_TO_HISTORY = "ADD_TO_HISTORY";
    public static final String ADD_TO_CLIP = "ADD_TO_CLIP";
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Scan");
        setSupportActionBar(toolbar);

        mHandler = new Handler();
//        setNavigationDrawer();




        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerSlideAnimationEnabled(false);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,
            ScanFragment.newInstance()).commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                fragment = null;

                switch (item.getItemId())
                {

                    case R.id.nav_home:

                      /*  Intent intent = new Intent(HomeActivity.this, ScanFragment.class);
                        startActivity(intent);
                        finish();*/
                            getSupportActionBar().setTitle("Scan");
                            fragment = ScanFragment.newInstance();
                        break;
                    case R.id.nav_text:
                        fragment =  TextInputFragment.newInstance();
                        getSupportActionBar().setTitle("Text");

                        break;
                    case R.id.nav_phone:
                        fragment =  PhoneInputFragment.newInstance();
                        getSupportActionBar().setTitle("Phone");
                        break;
                    case R.id.nav_sms:
                        fragment =  SmsInputFragment.newInstance();
                        getSupportActionBar().setTitle("SMS");
                        break;
                    case R.id.nav_website:
                        fragment = UrlInputFragment.newInstance();
                        getSupportActionBar().setTitle("Website");
                        break;
                    case R.id.nav_wifi:
                        fragment =  WifiInputFragment.newInstance();
                        getSupportActionBar().setTitle("Wifi");
                        break;
                    case R.id.nav_Location:
                        fragment =  LocationInputFragment.newInstance();
                        getSupportActionBar().setTitle("Location");
                        break;
                    case R.id.nav_contacts:
                        fragment =  ContactInputFragment.newInstance();
                        getSupportActionBar().setTitle("Contact");
                        break;
                    case R.id.nav_event:
                        fragment =  EventInputFragment.newInstance();
                        getSupportActionBar().setTitle("Event");
                        break;
                    case R.id.nav_email:
                        fragment =  EmailInputFragment.newInstance();
                        getSupportActionBar().setTitle("Email");
                        break;
                    case R.id.nav_app:
                        fragment = GooglePlayFragment.newInstance();
                        getSupportActionBar().setTitle("App");
                        break;
                    case R.id.nav_history:
                        getSupportActionBar().setTitle(R.string.history_fragment_tittle);
                        fragment = HistoryFragment.newInstance();
                        break;

                    case R.id.nav_setting:
                        startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                        break;



                }

               /* navigationView.getMenu().getItem(0).setChecked(true);
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        CreateFragment.newInstance()).commit();*/

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (fragment != null)
                        { FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            FrameLayout container =findViewById(R.id.container);
                            transaction.replace(R.id.container, fragment);
                            transaction.addToBackStack(null);
                                    transaction.commit();
                            drawer.closeDrawers();

                        }

                    }
                }, NAVDRAWER_LAUNCH_DELAY);

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START, true);
                return true;
//            }
//
//                return false;

            }
        });

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            toolbar = (Toolbar)findViewById(R.id.toolbar);
            toolbar.setTitle("Scan");
        }
    }

  /*  @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
         fragment = null;

        switch (item.getItemId())
        {
            case R.id.nav_home:
                getSupportActionBar().setTitle(R.string.scan_fragment_tittle);
                fragment = CreateFragment.newInstance();
                break;

            case R.id.nav_history:
                getSupportActionBar().setTitle(R.string.history_fragment_tittle);
                fragment = HistoryFragment.newInstance();
                break;

            case R.id.nav_setting:
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));break;

        }

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (fragment != null)
                { FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                }
            }
        }, NAVDRAWER_LAUNCH_DELAY);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START, true);
        return true;
    }*/

    @Override
    public void showQr(String qrString, String Type) {

        Intent intent =  new Intent(this, DetailsActivity.class);
        intent.putExtra(QR_STRING,qrString);
        intent.putExtra(QR_TYPE,Type);
        intent.putExtra(ADD_TO_HISTORY,true);
        startActivity(intent);
        finish();
    }

    /*private void setNavigationDrawer() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout); // initiate a DrawerLayout
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view); // initiate a Navigation View
// implement setNavigationItemSelectedListener event on NavigationView
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Fragment frag = null; // create a Fragment Object
                int itemId = menuItem.getItemId(); // get selected menu item's id
// check selected menu item's id and replace a Fragment Accordingly
                switch (itemId) {

                    case R.id.nav_home:

                        Intent intent = new Intent(HomeActivity.this, ScanActivity.class);
                        startActivity(intent);
                        finish();

                           *//* getSupportActionBar().setTitle(R.string.scan_fragment_tittle);
                        fragment = Sc.newInstance();*//*
                        break;
                    case R.id.nav_text:
//                        fragment = TextInputFragment.newInstance();
                        frag=new TextInputFragment();
                        getSupportActionBar().setTitle("Text");

                        break;
                    case R.id.nav_phone:
                        frag = new PhoneInputFragment();
                        getSupportActionBar().setTitle("Phone");
                        break;
                    case R.id.nav_sms:
                        fragment = SmsInputFragment.newInstance();
                        getSupportActionBar().setTitle("SMS");
                        break;
                    case R.id.nav_website:
                        fragment = UrlInputFragment.newInstance();
                        getSupportActionBar().setTitle("Website");
                        break;
                    case R.id.nav_wifi:
                        fragment = WifiInputFragment.newInstance();
                        getSupportActionBar().setTitle("Wifi");
                        break;
                    case R.id.nav_Location:
                        fragment = LocationInputFragment.newInstance();
                        getSupportActionBar().setTitle("Location");
                        break;
                    case R.id.nav_contacts:
//                        fragment =  ContactInputFragment.newInstance();
                        Intent intent1 = new Intent(HomeActivity.this, ContactInputFragment.class);
                        startActivity(intent1);
                        getSupportActionBar().setTitle("Contact");
                        break;
                    case R.id.nav_event:
                        fragment = EventInputFragment.newInstance();
                        getSupportActionBar().setTitle("Event");
                        break;
                    case R.id.nav_email:
                        fragment = EmailInputFragment.newInstance();
                        getSupportActionBar().setTitle("Email");
                        break;
                    case R.id.nav_app:
                        fragment = GooglePlayFragment.newInstance();
                        getSupportActionBar().setTitle("App");
                        break;
                    case R.id.nav_history:
                        getSupportActionBar().setTitle(R.string.history_fragment_tittle);
                        fragment = HistoryFragment.newInstance();
                        break;

                    case R.id.nav_setting:
                        startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                        break;

                }
// display a toast message with menu item's title
                Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                if (frag != null) {

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, frag);
                    transaction.addToBackStack(null);// replace a Fragment with Frame Layout
                    transaction.commit(); // commit the changes
                    drawer.closeDrawers(); // close the all open Drawer Views
                    return true;

                }
                return false;
            }
        });
    }*/

}
