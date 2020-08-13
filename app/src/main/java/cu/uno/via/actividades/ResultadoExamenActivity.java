package cu.uno.via.actividades;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import cu.uno.via.R;
import cu.uno.via.database.Basedatos;
import cu.uno.via.database.modelos.ResultadoModelo;
import cu.uno.via.App;
import cu.uno.via.utiles.PrefManager;

public class ResultadoExamenActivity extends AppCompatActivity {
    int resultado = 0;
    TextView titulo, descripcion, puntos, auxPuntos;
    Button finalizar, otraVez;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_examen);
        resultado = getIntent().getIntExtra("RESULTADO", 0);
        iniViews();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void iniViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_aprobado));

        puntos = findViewById(R.id.puntos);
        auxPuntos = findViewById(R.id.auxpuntos);
        titulo = findViewById(R.id.titulo);
        descripcion = findViewById(R.id.descripcion);
        finalizar = findViewById(R.id.finalizar);
        otraVez = findViewById(R.id.otravez);

        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        otraVez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultadoExamenActivity.this, ExamenActivity.class));
                finish();
            }
        });

        validarResultado();
        PrefManager prefManager = new PrefManager(ResultadoExamenActivity.this);
        prefManager.setPrimerExamenCompletado(true);
        prefManager.setUltimoResultado(resultado);
//        DB.getInstance(this).addResultado(getFecha(), resultado);
        Basedatos basedatos = new Basedatos(ResultadoExamenActivity.this, "datos.db");
        basedatos.addResultado(getFecha(), resultado);
        ResultadoModelo resultadoModelo = new ResultadoModelo();
        resultadoModelo.setFecha(getFecha());
        resultadoModelo.setResultado(resultado);
        App.LISTA_RESULTADOS.add(resultadoModelo);
    }

    private void validarResultado() {
        puntos.setText(resultado + "");
        if (resultado < 60) {
            getSupportActionBar().setTitle(getResources().getString(R.string.title_suspenso));
            puntos.setTextColor(getResources().getColor(R.color.colorSuspenso));
            auxPuntos.setTextColor(getResources().getColor(R.color.colorSuspenso));
            titulo.setText(getResources().getString(R.string.aux_negativo));
            descripcion.setText(getResources().getString(R.string.aux_negativo1));

        } else if (resultado > 60 && resultado < 80) {
            puntos.setTextColor(getResources().getColor(R.color.colorAprobado));
            auxPuntos.setTextColor(getResources().getColor(R.color.colorAprobado));
            titulo.setText(getResources().getString(R.string.aux_titulo_positivo));
            descripcion.setText(getResources().getString(R.string.aux_descripcion_positivo));

        } else if (resultado > 80 && resultado < 100) {
            puntos.setTextColor(getResources().getColor(R.color.colorAprobado));
            auxPuntos.setTextColor(getResources().getColor(R.color.colorAprobado));
            titulo.setText(getResources().getString(R.string.aux_titulo_positivo1));
            descripcion.setText(getResources().getString(R.string.aux_descripcion_positivo1));

        } else if (resultado == 100) {
            puntos.setTextColor(getResources().getColor(R.color.colorAprobado));
            auxPuntos.setTextColor(getResources().getColor(R.color.colorAprobado));
            titulo.setText(getResources().getString(R.string.aux_titulo_positivo2));
            descripcion.setText(getResources().getString(R.string.aux_descripcion_positivo2));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getFecha() {
        String aux = "";
        Calendar calendario = Calendar.getInstance();
        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH) + 1;
        int dia = (calendario.get(Calendar.DAY_OF_MONTH));
        aux = dia + "/" + mes + "/" + ano;
        return aux;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
