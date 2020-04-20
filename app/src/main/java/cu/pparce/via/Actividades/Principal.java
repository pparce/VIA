package cu.pparce.via.Actividades;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.AppBarLayout;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


import cu.pparce.via.Aplicacion;
import cu.pparce.via.Fragmentos.FragmentAcercaDe;
import cu.pparce.via.Fragmentos.FragmentDocumentacion;
import cu.pparce.via.Fragmentos.FragmentEvaluacion;
import cu.pparce.via.Fragmentos.FragmentInfracciones;
import cu.pparce.via.Fragmentos.FragmentInicio;
import cu.pparce.via.Fragmentos.FragmentNotas;
import cu.pparce.via.Fragmentos.FragmentSenales;
import cu.pparce.via.R;
import cu.pparce.via.Utiles.OnScrollCallback;
import cu.pparce.via.Utiles.ResideMenu.ResideMenu;
import cu.pparce.via.Utiles.ResideMenu.ResideMenuItem;

public class Principal extends AppCompatActivity implements View.OnClickListener, OnScrollCallback {
    Toolbar toolbar;
    int atras = 0;
    int fragmento = 0;
    public static Menu menu;

    public static OnScrollCallback onScrollCallback;
    AppBarLayout appBarLayout;

    private ResideMenu resideMenu;
    private ResideMenuItem inicio;
    private ResideMenuItem libros;
    private ResideMenuItem senales;
    private ResideMenuItem evaluar;
    private ResideMenuItem favoritos;
    private ResideMenuItem acercade;
    private ResideMenuItem infracciones;
    private ResideMenuItem notas;
    CoordinatorLayout coordinatorLayout;

    View seleccionado;

    boolean buscarInfraccion = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        Inicializar();
        setUpMenu();

        onScrollCallback = this;


        seleccionado = inicio;
        cambiarFragmento(0, inicio);


    }

    private void Inicializar() {
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.fondo);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);

    }


    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
            coordinatorLayout.setBackgroundResource(R.drawable.fondo_principal_redondeado);
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_atras));

            hideSoftKey(Principal.this);
        }

        @Override
        public void closeMenu() {
            coordinatorLayout.setBackgroundResource(R.color.colorBlanco);
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_menu));
        }
    };

    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        //resideMenu.setUse3D(true);
        resideMenu.setBackground(R.drawable.fondo_menu);
        resideMenu.attachToActivity(this);

        resideMenu.setFitsSystemWindows(true);
        resideMenu.setShadowVisible(true);
        //resideMenu.addIgnoredView(toolbar.getRootView());
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.9f);

        // create menu items;

        inicio = new ResideMenuItem(this, R.drawable.ic_inicio, "Inicio");
        infracciones = new ResideMenuItem(this, R.drawable.ic_infraccion, "Infracciones");
        libros = new ResideMenuItem(this, R.drawable.ic_documentacion, "Documentación");
        senales = new ResideMenuItem(this, R.drawable.ic_senales, "Señales");
        evaluar = new ResideMenuItem(this, R.drawable.ic_evaluar, "Evaluar");
        acercade = new ResideMenuItem(this, R.drawable.ic_acercade, "Acerca de");
        notas = new ResideMenuItem(this, R.drawable.ic_lapiz, "Notas");


        inicio.setOnClickListener(this);
        libros.setOnClickListener(this);
        senales.setOnClickListener(this);
        evaluar.setOnClickListener(this);
        acercade.setOnClickListener(this);
        infracciones.setOnClickListener(this);
        notas.setOnClickListener(this);

        resideMenu.addMenuItem(inicio, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(infracciones, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(libros, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(senales, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(evaluar, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(notas, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(acercade, ResideMenu.DIRECTION_LEFT);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Aplicacion.COMPRADO) {
                    resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
                } else {
                    if (isBloqueado()) {
                        startActivity(new Intent(Principal.this, Desbloqueo.class));
                        finish();
                    } else {
                        resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
                    }
                }


            }
        });

        // You can disable a direction by setting ->
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        /*findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {


        if (validarCompra(getSharedPreferences("app", Context.MODE_PRIVATE)) && fragmento == 0) {
            MenuItem menuItem = menu.findItem(R.id.action_desbloquear);
            menuItem.setVisible(false);

        }

        return super.onPrepareOptionsMenu(menu);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_buscar) {
            if (!Aplicacion.COMPRADO) {
                if (isBloqueado()) {
                    startActivity(new Intent(Principal.this, Desbloqueo.class));
                    finish();
                } else {
                    startActivity(new Intent(Principal.this, Buscar.class));
                }
            } else {
                startActivity(new Intent(Principal.this, Buscar.class));

            }
        } else if (id == R.id.action_desbloquear) {
            startActivity(new Intent(Principal.this, Desbloqueo.class));
            finish();
        } else if (id == R.id.action_info) {

            switch (fragmento) {
                case 1:
                    showInfo(getString(R.string.explicacion_documentacion));
                    break;
                case 2:
                    showInfo(getString(R.string.explicacion_senales));
                    break;
                case 3:
                    showInfo(getString(R.string.explicacion_evaluar));
                    break;
                case 6:
                    showInfo(getString(R.string.explicacion_notas));
                    break;
                case 5:
                    showInfo(getString(R.string.explicacion_infracciones));
                    break;
            }

        }

        return super.onOptionsItemSelected(item);
    }

    private void Fragmento(Fragment targetFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.contenedor_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }


    private void showInfo(String texto) {

        LayoutInflater layoutInflater = LayoutInflater.from(Principal.this);
        final View myview = layoutInflater.inflate(R.layout.dialog_custom_layout, null);
        TextView descripcion = myview.findViewById(R.id.descripcion);
        descripcion.setText(texto);

        AlertDialog.Builder builder = new AlertDialog.Builder(Principal.this);
        builder.setTitle("Información")
                .setView(myview)
                .setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        Dialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.AnimacionDialog1;
        dialog.show();
    }

    private void cambiarFragmento(int fragmento, View view) {
        TextView textView;
        ImageView imageView;
        int colorselected;
        int colorfondo;

        switch (fragmento) {
            case 0:
                Fragmento(new FragmentInicio());
                this.fragmento = 0;
                setTitle("Inicio");
                setItemSelected(view);
                seleccionado = view;

                if (menu != null) {
                    menu.clear();
                    getMenuInflater().inflate(R.menu.menu_principal, menu);
                }
                break;
            case 1:
                Fragmento(new FragmentDocumentacion());
                this.fragmento = 1;
                setTitle("Documentación");
                setItemSelected(view);
                seleccionado = view;

                if (menu != null) {
                    menu.clear();
                    getMenuInflater().inflate(R.menu.menu_documentacion, menu);
                }
                break;
            case 2:
                Fragmento(new FragmentSenales());
                this.fragmento = 2;
                setTitle("Señales");

                setItemSelected(view);

                seleccionado = view;

                if (menu != null) {
                    menu.clear();
                    getMenuInflater().inflate(R.menu.menu_documentacion, menu);
                }
                break;
            case 3:
                Fragmento(new FragmentEvaluacion());
                this.fragmento = 3;
                setTitle("Evaluar");
                setItemSelected(view);
                seleccionado = view;

                if (menu != null) {
                    menu.clear();
                    getMenuInflater().inflate(R.menu.menu_documentacion, menu);
                }
                break;
            case 4:
                Fragmento(new FragmentAcercaDe());
                this.fragmento = 4;
                setTitle("Acerca de");
                setItemSelected(view);
                seleccionado = view;

                if (menu != null) {
                    menu.clear();
                    getMenuInflater().inflate(R.menu.menu_documentacion, menu);
                }
                break;

            case 5:
                Fragmento(new FragmentInfracciones());
                this.fragmento = 5;
                setTitle("Infracciones");
                setItemSelected(view);
                seleccionado = view;

                if (menu != null) {
                    menu.clear();
                    getMenuInflater().inflate(R.menu.menu_infracciones, menu);
                }
                break;
            case 6:
                Fragmento(new FragmentNotas());
                this.fragmento = 6;
                setTitle("Notas");
                setItemSelected(view);
                seleccionado = view;

                if (menu != null) {
                    menu.clear();
                    getMenuInflater().inflate(R.menu.menu_documentacion, menu);
                }
                break;
        }

    }

    private Boolean isBloqueado() {

        Calendar calendario = Calendar.getInstance();
        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH) + 1;
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        int hora = calendario.get(Calendar.HOUR_OF_DAY);

        String aux = "";
        if (String.valueOf(hora).length() == 1) {
            aux = "0" + hora;
        } else {
            aux = "" + hora;
        }

        String fecha = "" + ano + mes + dia + aux;

        if (Integer.parseInt(Aplicacion.FECHA) <= Integer.parseInt(fecha)) {
            return true;
        } else {
            return false;
        }

    }

    private void setItemSelected(View view) {

        TextView textView;
        ImageView imageView;
        int colorselected;
        int colorfondo;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            colorselected = getColor(R.color.colorNegro);
            colorfondo = getColor(R.color.colorBlanco);
        } else {
            colorselected = getResources().getColor(R.color.colorNegro);
            colorfondo = getResources().getColor(R.color.colorBlanco);
        }

        textView = (TextView) seleccionado.findViewById(R.id.tv_title);
        imageView = (ImageView) seleccionado.findViewById(R.id.iv_icon);
        imageView.setColorFilter(colorfondo);
        textView.setTextColor(colorfondo);

        seleccionado.setSelected(false);
        view.setSelected(true);
        textView = (TextView) view.findViewById(R.id.tv_title);
        imageView = (ImageView) view.findViewById(R.id.iv_icon);
        imageView.setColorFilter(colorselected);
        textView.setTextColor(colorselected);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("................", "onRestart");
        if (Aplicacion.COMPRADO) {
        } else {
            if (isBloqueado()) {
                startActivity(new Intent(Principal.this, Desbloqueo.class));
                finish();
            }
        }


    }

    @Override
    public void onClick(View view) {

        if (view == inicio && seleccionado != view) {
            cambiarFragmento(0, view);
        } else if (view == libros && seleccionado != view) {
            cambiarFragmento(1, view);
        } else if (view == senales && seleccionado != view) {
            cambiarFragmento(2, view);
        } else if (view == evaluar && seleccionado != view) {
            cambiarFragmento(3, view);
        } else if (view == acercade && seleccionado != view) {
            cambiarFragmento(4, view);
        } else if (view == infracciones && seleccionado != view) {
            cambiarFragmento(5, view);
        } else if (view == notas && seleccionado != view) {
            cambiarFragmento(6, view);
        }
        resideMenu.closeMenu();

    }

    @Override
    public void onBackPressed() {

        if (resideMenu.isOpened()) {
            resideMenu.closeMenu();
        } else {

            if (atras == 1) {
                super.onBackPressed();
            }
            if (atras == 0) {
                atras = 1;
                Toast.makeText(Principal.this, "Presione una vez mas para cerrar ", Toast.LENGTH_SHORT).show();

                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        atras = 0;
                    }
                };
                Timer timer = new Timer();
                timer.schedule(timerTask, 2000);
            }
        }
    }

    @Override
    public void onScroll(boolean scroll) {
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (scroll) {

                appBarLayout.setElevation(0f);

            } else {
                appBarLayout.setElevation(10f);
            }
        }*/
    }

    private void hideSoftKey(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null)
            view = new View(activity);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }
}
