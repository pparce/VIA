package cu.uno.via.actividades;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import cu.uno.via.R;
import cu.uno.via.App;

public class DesbloqueoActivity extends AppCompatActivity {

    MaterialButton btnLibre, btnTransferencia, btnQr;
    boolean bloqueado = false;
    int atras = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desbloqueo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
    }

    private void initView() {
        btnLibre = findViewById(R.id.btn_libre);
        btnTransferencia = findViewById(R.id.btn_transferencia);
        btnQr = findViewById(R.id.btn_qr);

//        bloqueado = isBloqueado();
        if (false) {
            btnLibre.setEnabled(false);
            btnLibre.setBackgroundTintList(getResources().getColorStateList(R.color.colorDisabled));
            btnLibre.setTextColor(Color.GRAY);

        } else {
            shakeAnimation(btnLibre);
        }

        btnLibre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!bloqueado) {
                    startActivity(new Intent(DesbloqueoActivity.this, PrincipalActivity.class));
                    finish();
                }
            }
        });
        btnTransferencia.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DesbloqueoActivity.this, TransferenciaActivity.class));



            }
        });
        btnQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(Desbloqueo.this, QrDesbloqueo.class));

            }
        });

    }


    @Override
    protected void onRestart() {
        super.onRestart();
//        bloqueado = isBloqueado();
        if (false) {
            btnLibre.setEnabled(false);
            btnLibre.setBackgroundTintList(getResources().getColorStateList(R.color.colorDisabled));
            btnLibre.setTextColor(Color.GRAY);
        } else {
            shakeAnimation(btnLibre);
        }
    }

    public void shakeAnimation(View view) {
        Animation shake = AnimationUtils.loadAnimation(DesbloqueoActivity.this, R.anim.shake_lento);
        shake.setRepeatCount(Animation.INFINITE);
        view.startAnimation(shake);
    }

    private Boolean isBloqueado() {

        Calendar calendario = Calendar.getInstance();
        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH) + 1;
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        int hora = calendario.get(Calendar.HOUR_OF_DAY);

        String aux = "";
        if (String.valueOf(hora).length() == 1){
            aux = "0" + hora;
        } else {
            aux = "" + hora;
        }

        String fecha = "" + ano + mes + dia + aux;

        if (Integer.parseInt(App.FECHA) <= Integer.parseInt(fecha)) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public void onBackPressed() {
        if (atras == 1) {
            super.onBackPressed();
        }
        if (atras == 0) {
            atras = 1;
            Toast.makeText(DesbloqueoActivity.this, "Presione una vez mas para cerrar ", Toast.LENGTH_SHORT).show();

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

    private void showMensaje(final String texto) {
        final AlertDialog alertDialog = new AlertDialog.Builder(DesbloqueoActivity.this).create();
        alertDialog.setTitle("Atención");
        alertDialog.setMessage(texto);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "localizar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        String aux = texto.substring(texto.indexOf("(X:"), texto.length() - 2);
                        aux = aux.substring(3, aux.length());
                        aux = aux.replaceAll("Y:", "");
                        String[] split = aux.split("  ");

                        String lat = split[1].trim();
                        lat = lat.replace(" ", "");
                        String log = split[0].trim();
                        log = log.replaceAll(" ", "");
                        Log.e("...", aux);

                        Intent i = new Intent(Intent.ACTION_VIEW);
                        String url = String.valueOf(Uri.parse("https://osmand.net/go?lat=" + lat + "&lon=" + log + "&z=16"));
                        i.setData(Uri.parse(url));
                        startActivity(i);


                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }




}
