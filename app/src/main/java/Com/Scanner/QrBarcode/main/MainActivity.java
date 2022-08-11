package Com.Scanner.QrBarcode.main;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import Com.Scanner.QrBarcode.create.input.UpdateView;
import Com.Scanner.QrBarcode.details.DetailsActivity;


public class MainActivity extends AppCompatActivity  implements UpdateView {


    public final int CAMERA_PERMISSION_CODE = 0123;
    public static final String QR_STRING = "QR_STRING";
    private static final long NAVDRAWER_LAUNCH_DELAY = 250;
    public static final String QR_TYPE = "QR_TYPE";
    public static final String ADD_TO_HISTORY = "ADD_TO_HISTORY";
    public static final String ADD_TO_CLIP = "ADD_TO_CLIP";


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
/*//        Intent intent = new Intent(this, HomeActivity.class);
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);*/
        if (checkPermission()) {
//            Intent intent = new Intent(this, ScanActivity.class);
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            requestPermission();
        }


    }
    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermission() {

        requestPermissions(
                new String[]{Manifest.permission.CAMERA},
                CAMERA_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {

                        }
                    }
                }
                break;
        }
    }
    @Override
    public void showQr(String qrString, String Type) {

        Intent intent =  new Intent(this, DetailsActivity.class);
        intent.putExtra(QR_STRING,qrString);
        intent.putExtra(QR_TYPE,Type);
        intent.putExtra(ADD_TO_HISTORY,true);
        startActivity(intent);
        finish();
    }
}
