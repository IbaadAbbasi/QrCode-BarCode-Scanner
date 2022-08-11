package Com.Scanner.QrBarcode.scan;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;
import com.google.zxing.ResultPoint;
import com.google.zxing.WriterException;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import Com.Scanner.QrBarcode.R;
import Com.Scanner.QrBarcode.create.QRCode;
import Com.Scanner.QrBarcode.details.DetailsActivity;
//import ga.awsapp.qrscanner.main.HomeActivity;


public class ScanFragment extends Fragment{
    private static final String TAG = ScanFragment.class.getSimpleName();
    @BindView(R.id.barcode_scanner) DecoratedBarcodeView barcodeView;
    @BindView((R.id.flash)) ToggleButton flash;
    private BeepManager beepManager;
    private Bitmap qrBitmap;
    private boolean cameraFlashOn = false;
    public static final int BROWSE_IMAGE_REQUEST_CODE = 99;

    private static final long NAVDRAWER_LAUNCH_DELAY = 250;
    private Handler mHandler;
    private Fragment fragment;
    public static final String QR_STRING =  "QR_STRING";
    public static final String QR_TYPE = "QR_TYPE";
    public static final String ADD_TO_HISTORY = "ADD_TO_HISTORY";
    public static final String ADD_TO_CLIP = "ADD_TO_CLIP";

    public final int CAMERA_PERMISSION_CODE = 0123;
    public static final String BROWSE_IMAGE_URI = "BROWSE_IMAGE_URI";
    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if(result.getText() == null  ) {

                return;
            }

            try {

                QRCode  qrCode =  new QRCode(result.getText());
                qrBitmap =  qrCode.getSimpleBitmap(Color.BLACK, null, result.getBarcodeFormat()+"");
                if (qrBitmap!= null)
                {
                Intent intent =  new Intent(getActivity(), DetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra(QR_STRING,result.getText());
                intent.putExtra(QR_TYPE,result.getBarcodeFormat().toString());
                intent.putExtra(ADD_TO_HISTORY,true);
                intent.putExtra(ADD_TO_CLIP,true);
                startActivity(intent);


                    beepManager.setBeepEnabled(true);
                    beepManager.setVibrateEnabled(false);
                    beepManager.updatePrefs();
                    beepManager.playBeepSoundAndVibrate();
//                    finish();
                }

            } catch (WriterException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.activity_home);
        setContentView(R.layout.continuous_scan);*/

    public static ScanFragment newInstance() {
        return new ScanFragment();
    }
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.continuous_scan, container, false);
       ButterKnife.bind(this, view);
//        ButterKnife.bind(getActivity());

       /* Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }*/

        barcodeView.initializeFromIntent(getActivity().getIntent());
        barcodeView.decodeContinuous(callback);
        beepManager = new BeepManager(getActivity());
        TorchEventListener torchEventListener = new TorchEventListener(this);
        barcodeView.setTorchListener(torchEventListener);

///////////////
       /* Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
//        toolbar.setTitle("Scan");
        setSupportActionBar(toolbar);*/

        mHandler = new Handler();

       /* DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerSlideAnimationEnabled(false);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
             *//*   @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {*//*
                fragment = null;

                switch (item.getItemId())
                {

                    case R.id.nav_home:

                            Intent intent = new Intent(ScanActivity.this, ScanActivity.class);
                            startActivity(intent);
                            finish();

                           *//* getSupportActionBar().setTitle(R.string.scan_fragment_tittle);
                            fragment = Sc.newInstance();*//*
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
//                        fragment =  ContactInputFragment.newInstance();
                            Intent intent1 =new Intent(ScanActivity.this,ContactInputFragment.class);
                            startActivity(intent1);
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
                        startActivity(new Intent(ScanActivity.this, SettingsActivity.class));
                        break;



                }

//                navigationView.getMenu().getItem(0).setChecked(true);
//                getSupportFragmentManager().beginTransaction().replace(R.id.container,
//                        CreateFragment.newInstance()).commit();

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (fragment != null)
                        { FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            FrameLayout container =findViewById(R.id.container);
                            container.setVisibility(View.VISIBLE);
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
            }

//                return false;
//            }
        });
      
    */
///////////////
       return view;
    }

/*    @OnClick(R.id.back_btn) void pressedBack( )
    {
//        onBackPressed();
    }*/

    @OnClick(R.id.image_scan) void openGallery( )
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select the image with the QR Code"), BROWSE_IMAGE_REQUEST_CODE);

    }

    @Override
    public void onResume() {
        super.onResume();

        barcodeView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();

        barcodeView.pause();
        if(cameraFlashOn){
            barcodeView.setTorchOff();
        }
    }

    private boolean hasFlash() {
        return getActivity().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }


  /*  @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }*/


    public void updateView(){
        if(cameraFlashOn){
            flash.setChecked(true);
        }else{
            flash.setChecked(false);

        }
    }


    @OnClick(R.id.flash)
    void turnFlash( )
    {
        if(cameraFlashOn){
            barcodeView.setTorchOff();
        }else{
            barcodeView.setTorchOn();
        }
    }


    class TorchEventListener implements DecoratedBarcodeView.TorchListener{
        private ScanFragment activity;

        TorchEventListener(ScanFragment activity){
            this.activity = activity;
        }

        @Override
        public void onTorchOn() {
            cameraFlashOn = true;
            this.activity.updateView();
        }

        @Override
        public void onTorchOff() {
            cameraFlashOn = false;
            this.activity.updateView();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BROWSE_IMAGE_REQUEST_CODE && data != null && data.getData() != null) {
            Uri uri = data.getData();
            Intent intent =  new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra(BROWSE_IMAGE_URI,uri.toString());
            intent.putExtra(ADD_TO_HISTORY,true);
            startActivity(intent);
//            finish();
        }
    }
    /*private long mLastPress = 0;
 int TOAST_DURATION = 5000;
    Toast onBackPressedToast;
    @Override
    public void onBackPressed() {
//        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
            long currentTime = System.currentTimeMillis();
            if (currentTime - mLastPress > TOAST_DURATION) {
//
                onBackPressedToast = Toast.makeText(this, R.string.press_once_again_to_exit, Toast.LENGTH_SHORT);
                onBackPressedToast.show();
              *//*  Intent intent = new Intent(ScanActivity.this, ScanActivity.class);
                startActivity(intent);
                finish();*//*
                mLastPress = currentTime;
            } else {
                if (onBackPressedToast != null) {
                    onBackPressedToast.cancel();  //Difference with previous answer. Prevent continuing showing toast after application exit.
                    onBackPressedToast = null;
                }
                super.onBackPressed();
//            }
        }*/
    }
/*
    @Override
    public void onBackPressed() {
*//*        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);*//*
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 1) {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }*/



