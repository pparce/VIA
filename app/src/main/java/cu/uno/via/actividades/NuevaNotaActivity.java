package cu.uno.via.actividades;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import cu.uno.via.database.Basedatos;
import cu.uno.via.database.modelos.NotasModelo;
import cu.uno.via.R;
import cu.uno.via.fragmentos.NotasFragment;

public class NuevaNotaActivity extends AppCompatActivity {

    TextInputEditText nota;
    Basedatos basedatos;
    String textoNota = "";
    int posicion;
    boolean edicion = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_nota);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        iniViews();
    }

    private void iniViews() {
        nota = findViewById(R.id.nota);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            textoNota = bundle.getString("nota");
            posicion = bundle.getInt("posicion");
            edicion = bundle.getBoolean("editar");
            nota.setText(textoNota);
            nota.setSelection(nota.length());
        }


    }

    @Override
    public void onBackPressed() {

        String aux = nota.getText().toString();
        if (aux.length() != 0) {
            if (!textoNota.equals(aux)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NuevaNotaActivity.this);
                builder
                        .setTitle("Guardar")
                        .setMessage("¿Desea guardar la nota?")
                        .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (!edicion) {
                                    NotasModelo notasModelo = new NotasModelo();
                                    notasModelo.setDescripcion(nota.getText().toString());
                                    NotasFragment.listaNotas.add(notasModelo);

                                    basedatos = new Basedatos(NuevaNotaActivity.this, "datos.db");
                                    basedatos.agregarNota(nota.getText().toString());

                                    Toast.makeText(NuevaNotaActivity.this, "Nota guardada", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    NotasModelo notasModelo = new NotasModelo();
                                    notasModelo.setDescripcion(nota.getText().toString());

                                    basedatos = new Basedatos(NuevaNotaActivity.this, "datos.db");
                                    basedatos.editarNota(NotasFragment.listaNotas.get(posicion).getId(), nota.getText().toString());

                                    NotasFragment.listaNotas.set(posicion, notasModelo);
                                    Toast.makeText(NuevaNotaActivity.this, "Nota editada", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        })
                        .setNegativeButton("Descartar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });
                Dialog dialog = builder.create();
                dialog.show();
            } else {
                finish();
            }
        } else {
            finish();
        }

        //super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.menu_notas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        } else if (id == R.id.action_guardar) {

            if (nota.length() != 0) {
                if (!textoNota.equals(nota.getText().toString())) {
                    if (!edicion) {
                        NotasModelo notasModelo = new NotasModelo();
                        notasModelo.setDescripcion(nota.getText().toString());
                        NotasFragment.listaNotas.add(notasModelo);

                        basedatos = new Basedatos(NuevaNotaActivity.this, "datos.db");
                        basedatos.agregarNota(nota.getText().toString());

                        Toast.makeText(NuevaNotaActivity.this, "Nota guardada", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        NotasModelo notasModelo = new NotasModelo();
                        notasModelo.setDescripcion(nota.getText().toString());

                        basedatos = new Basedatos(NuevaNotaActivity.this, "datos.db");
                        basedatos.editarNota(NotasFragment.listaNotas.get(posicion).getId(), nota.getText().toString());

                        NotasFragment.listaNotas.set(posicion, notasModelo);
                        Toast.makeText(NuevaNotaActivity.this, "Nota editada", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    finish();

                }
            } else {
                finish();
            }
        }


        return super.onOptionsItemSelected(item);
    }
}
