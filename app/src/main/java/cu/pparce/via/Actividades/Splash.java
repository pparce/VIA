package cu.pparce.via.Actividades;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import cu.pparce.via.Adaptadores.SplashAdapter;
import cu.pparce.via.Aplicacion;
import cu.pparce.via.R;


public class Splash extends AppCompatActivity {

    ViewPager viewPager;
    SplashAdapter adapter;
    ImageView indicador;
    int lastposition = 0;


    LinearLayout indicadores;
    RelativeLayout fondo;
    List<String> listaVentanas;
    private static List<ImageView> listaIndicador;

    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    public static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 2;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        isPrimeraVez();

        if (android.os.Build.VERSION.SDK_INT >= 23) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                setTheme(R.style.Splash);

                crearCarpeta();
                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "Download" + File.separator + ".thumbs");
                if (file.exists()) {

                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        Aplicacion.FECHA = reader.readLine();
                        reader.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {

                    Calendar calendario = Calendar.getInstance();
                    int ano = calendario.get(Calendar.YEAR);
                    int mes = calendario.get(Calendar.MONTH) + 1;
                    int dia = (calendario.get(Calendar.DAY_OF_MONTH)) + 1;
                    int hora = calendario.get(Calendar.HOUR_OF_DAY);
                    String aux = "";
                    if (String.valueOf(hora).length() == 1) {
                        aux = "0" + hora;
                    } else {
                        aux = "" + hora;
                    }
                    String fecha = "" + ano + mes + dia + aux;

                    try {
                        file.createNewFile();
                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                        bufferedWriter.write(fecha);
                        bufferedWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        Aplicacion.FECHA = reader.readLine();
                        reader.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        /*SharedPreferences preferences = getSharedPreferences("configuracion", Context.MODE_PRIVATE);
                        boolean primera = preferences.getBoolean("primera_vez", true);

                        if (primera) {

                        }*/
                        boolean aux = validarCompra(getSharedPreferences("app", Context.MODE_PRIVATE));

                        if (aux) {
                            startActivity(new Intent(Splash.this, Principal.class));
                        } else {
                            startActivity(new Intent(Splash.this, Desbloqueo.class));
                        }
                        finish();
                    }
                }, 1000);

            } else {
                setContentView(R.layout.activity_splash);

                Inicializar();
            }


        } else {
            setContentView(R.layout.activity_splash);

            Inicializar();
        }
    }

    public Boolean validarCompra(SharedPreferences preferences) {
        String clave = encriptarImei(encriptarImei(getImei() + "via") + "comprado");

        if (preferences.getString("via", "12345678").equals(clave)) {
            Aplicacion.COMPRADO = true;
            return true;
        } else {
            Aplicacion.COMPRADO = false;
            return false;
        }
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

    public void crearCarpeta() {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "+Luz Verde");
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private void Inicializar() {


        viewPager = (ViewPager) findViewById(R.id.viewpager_splash);

        indicadores = (LinearLayout) findViewById(R.id.indicadores_splash);
        fondo = (RelativeLayout) findViewById(R.id.fondo_splash);
        indicador = new ImageView(getApplicationContext());
        indicador.setImageResource(R.drawable.circulo_viewpager);

        listaIndicador = new ArrayList<ImageView>();

        CrearLista();
        AgregarIndicador();
        setUpColors();


        adapter = new SplashAdapter(getApplicationContext(), listaVentanas);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                /*if (position < (adapter.getCount() - 1) && position < (colors.length - 1)) {

                    fondo.setBackgroundColor((Integer) argbEvaluator.evaluate(positionOffset, colors[position], colors[position + 1]));

                } else {

                    // the last page color
                    fondo.setBackgroundColor(colors[colors.length - 1]);

                }*/
            }

            @Override
            public void onPageSelected(int position) {
                listaIndicador.get(position).setImageResource(R.drawable.circulo_viewpager_seleccionado);
                listaIndicador.get(lastposition).setImageResource(R.drawable.circulo_viewpager);
                lastposition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPermisos();
            }
        });


    }

    private void getPermisos() {

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            //PERMISOS
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(Splash.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);

            } else {
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage(getBaseContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        } else {
            Intent i = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage(getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }


    }

    private void CrearLista() {
        listaVentanas = new ArrayList<>();
        listaVentanas.add("1");
        listaVentanas.add("2");
        listaVentanas.add("3");

    }

    private void AgregarIndicador() {
        for (int i = 0; i < listaVentanas.size(); i++) {
            if (i == 0) {
                ImageView image = new ImageView(getApplicationContext());
                image.setImageResource(R.drawable.circulo_viewpager_seleccionado);
                image.setPadding(10, 0, 10, 0);
                indicadores.removeView(image);
                indicadores.addView(image);
                listaIndicador.add(image);
            } else {
                ImageView image = new ImageView(getApplicationContext());
                image.setImageResource(R.drawable.circulo_viewpager);
                image.setPadding(10, 0, 10, 0);
                indicadores.removeView(image);
                indicadores.addView(image);
                listaIndicador.add(image);
            }
        }
    }

    private void setUpColors() {

        Integer color1 = getResources().getColor(R.color.color1_splash);
        Integer color2 = getResources().getColor(R.color.color2_splash);
        Integer color3 = getResources().getColor(R.color.color3_splash);

        Integer[] colors_temp = {color1, color2, color3};
        colors = colors_temp;

    }

    private boolean isPrimeraVez() {

        SharedPreferences preferences = getSharedPreferences("configuracion", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (preferences.getBoolean("primera_vez", true)) {

            Calendar calendario = Calendar.getInstance();
            int ano = calendario.get(Calendar.YEAR);
            int mes = calendario.get(Calendar.MONTH) + 1;
            int dia = (calendario.get(Calendar.DAY_OF_MONTH)) + 1;
            int hora = calendario.get(Calendar.HOUR_OF_DAY);
            int segundo = calendario.get(Calendar.SECOND);
            String aux = "";
            String aux1 = "";
            if (String.valueOf(hora).length() == 1) {
                aux = "0" + hora;
            } else {
                aux = "" + hora;
            }

            if (String.valueOf(segundo).length() == 1) {
                aux1 = "0" + hora;
            } else {
                aux1 = "" + hora;
            }

            String fecha = "" + ano + mes + dia + aux + aux1;

            editor.putString("imei", encriptarImei(fecha));
            editor.putBoolean("primera_vez", false);
            editor.commit();
        }

        return preferences.getBoolean("primera_vez", false);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(Splash.this, new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);

                } else {
                    finish();
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(Splash.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                } else {
                    finish();
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);

                } else {
                    finish();
                }
                return;
            }
        }

    }
}
