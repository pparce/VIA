package cu.pparce.via.Actividades;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.zxing.WriterException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cu.pparce.via.R;
import cu.pparce.via.Utiles.qrcodescanner.QrCodeActivity;
import cu.pparce.via.Utiles.qrgenearator.QRGContents;
import cu.pparce.via.Utiles.qrgenearator.QRGEncoder;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class QrDesbloqueo extends AppCompatActivity {

    Button getLicencia;
    ImageView qrImage;
    QRGEncoder qrgEncoder;
    Bitmap bitmap;
    String imeiEncriptado;
    private static final int REQUEST_CODE_QR_SCAN = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_desbloqueo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        iniView();

    }

    private void iniView(){
        String aux = null;

        aux = encriptarImei(getImei() + "via");

        getLicencia = (Button) findViewById(R.id.btn_transferir);
        qrImage = (ImageView) findViewById(R.id.qr_imagen);

        imeiEncriptado = aux;
        Log.e(".....................", encriptarImei(imeiEncriptado + "comprado"));
        if (aux.length() > 0) {
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3 / 4;

            qrgEncoder = new QRGEncoder(
                    aux, null,
                    QRGContents.Type.TEXT,
                    smallerDimension);
            try {
                bitmap = qrgEncoder.encodeAsBitmap();
                qrImage.setImageBitmap(bitmap);
            } catch (WriterException e) {

            }
        } else {
        }

        getLicencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QrDesbloqueo.this, QrCodeActivity.class);
                startActivityForResult(i, REQUEST_CODE_QR_SCAN);
            }
        });
    }

    private String getImei() {
        SharedPreferences preferences = getSharedPreferences("configuracion", MODE_PRIVATE);
        String imei = preferences.getString("imei", "");

        return imei;
    }

    private String encriptarImei(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pass.getBytes());
            byte messageDigest[] = md.digest();
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2) {
                    h = "0" + h;
                }
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {

            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_QR_SCAN && data != null) {
            //Getting the passed result
            String result = data.getStringExtra("com.parce.paleer.qrcodescanner.got_qr_scan_relult");
            if (result.equals(encriptarImei(imeiEncriptado + "comprado"))){
                SharedPreferences preferences = getSharedPreferences("app", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("via", result);
                editor.commit();
                Toast.makeText(this, "Aplicacion desbloqueada", Toast.LENGTH_SHORT).show();


                Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

            } else {
                Toast.makeText(this, "Licencia no válida", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
