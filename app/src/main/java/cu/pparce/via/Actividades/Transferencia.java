package cu.pparce.via.Actividades;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ServiceInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import cu.pparce.via.R;
import cu.pparce.via.Utiles.USSDService;

public class Transferencia extends AppCompatActivity {

    Button btnTransferir;
    LinearLayout btnAccesibilidad;
    ImageView icono;
    EditText password;

    TelephonyManager mTelephonyManager;
    MyPhoneStateListener mPhoneStatelistener;
    int mSignalStrength = 0;
    boolean isActivado = false;

    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferencia);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
    }


    private void initView() {

        registerReceiver(receiver, new IntentFilter("respuesta_ussd"));

        isActivado = isAccessibilityServiceEnabled(Transferencia.this, USSDService.class);

        icono = (ImageView) findViewById(R.id.icono_accesibilidad);
        password = (EditText) findViewById(R.id.password);
        validarServicio();

        btnTransferir = (Button) findViewById(R.id.btn_transferir);
        btnAccesibilidad = (LinearLayout) findViewById(R.id.btn_accesibilidad);
        btnTransferir.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                //requisitos para tranferencia
                mPhoneStatelistener = new MyPhoneStateListener();
                mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                mTelephonyManager.listen(mPhoneStatelistener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);

                if (password.length() == 4) {
                    if (mSignalStrength < 0) {
                        if (isAccessibilityServiceEnabled(Transferencia.this, USSDService.class)) {
                            String telf = "*234*1*" + "58358801" + "*" + password.getText().toString() + "*" + "1" + Uri.encode("#");
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel:" + telf));
                            startActivity(intent);
                        } else {

                            Snackbar.make(view, "Debe activar el servicio de accesibilidad", Snackbar.LENGTH_SHORT).show();
                        }

                    } else {
                        Snackbar.make(view, "Hay problemas de covertura. Busque señal", Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar.make(view, "El campo contraseña debe contener cuatro digitos", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        btnAccesibilidad.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                if (isAccessibilityServiceEnabled(Transferencia.this, USSDService.class)) {

                    Snackbar.make(view, "El servicio de accesibilidad esta activado", Snackbar.LENGTH_SHORT).show();

                } else {
                    Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                    startActivity(intent);

                    /*while (true){
                        if (isAccessibilityServiceEnabled(Transferencia.this, USSDService.class)) {

                            startActivity(new Intent(Transferencia.this, Transferencia.class));
                            finish();
                            break;
                        }
                    }*/


                }

            }
        });
    }

    private void validarServicio(){
        isActivado = isAccessibilityServiceEnabled(Transferencia.this, USSDService.class);

        if (isActivado) {
            icono.setImageResource(R.drawable.ic_check);
        } else {
            icono.setImageResource(R.drawable.ic_flecha_derecha);
        }
    }

    public boolean isAccessibilityServiceEnabled(Context context, Class<? extends AccessibilityService> service) {
        AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> enabledServices = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK);

        for (AccessibilityServiceInfo enabledService : enabledServices) {
            ServiceInfo enabledServiceInfo = enabledService.getResolveInfo().serviceInfo;
            Log.e(".........", enabledServiceInfo.packageName);
            if (enabledServiceInfo.packageName.equals(context.getPackageName()) || enabledServiceInfo.name.equals(service.getName()))
                return true;
        }

        return false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        validarServicio();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {

            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
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

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //Toast.makeText(context, "" + intent.getStringExtra("texto"), Toast.LENGTH_SHORT).show();

            SharedPreferences preferences = getSharedPreferences("app", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            String aux = encriptarImei(encriptarImei(getImei() + "via") + "comprado");
            editor.putString("via", aux);
            editor.commit();
            Toast.makeText(Transferencia.this, "Aplicacion desbloqueada", Toast.LENGTH_SHORT).show();


            Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);

            unregisterReceiver(receiver);
            registerReceiver(receiver, new IntentFilter("respuesta_ussd"));
        }
    };

    public class MyPhoneStateListener extends PhoneStateListener {

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            mSignalStrength = signalStrength.getGsmSignalStrength();
            mSignalStrength = (2 * mSignalStrength) - 113; // -> dBm
        }
    }
}
