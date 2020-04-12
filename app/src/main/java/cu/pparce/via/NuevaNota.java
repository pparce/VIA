package cu.pparce.via;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import cu.pparce.via.DataBase.Basedatos;
import cu.pparce.via.DataBase.ModeloNotas;
import cu.pparce.via.Fragmentos.FragmentNotas;

public class NuevaNota extends AppCompatActivity {

    EditText nota;
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
        if (bundle != null){
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
                AlertDialog.Builder builder = new AlertDialog.Builder(NuevaNota.this);
                builder
                        .setTitle("Guardar")
                        .setMessage("¿Desea guardar la nota?")
                        .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (!edicion) {
                                    ModeloNotas modeloNotas = new ModeloNotas();
                                    modeloNotas.setDescripcion(nota.getText().toString());
                                    FragmentNotas.listaNotas.add(modeloNotas);

                                    basedatos = new Basedatos(NuevaNota.this, "datos.db");
                                    basedatos.agregarNota(nota.getText().toString());

                                    Toast.makeText(NuevaNota.this, "Nota guardada", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    ModeloNotas modeloNotas = new ModeloNotas();
                                    modeloNotas.setDescripcion(nota.getText().toString());

                                    basedatos = new Basedatos(NuevaNota.this, "datos.db");
                                    basedatos.editarNota(FragmentNotas.listaNotas.get(posicion).getId(), nota.getText().toString());

                                    FragmentNotas.listaNotas.set(posicion, modeloNotas);
                                    Toast.makeText(NuevaNota.this, "Nota editada", Toast.LENGTH_SHORT).show();
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
                        ModeloNotas modeloNotas = new ModeloNotas();
                        modeloNotas.setDescripcion(nota.getText().toString());
                        FragmentNotas.listaNotas.add(modeloNotas);

                        basedatos = new Basedatos(NuevaNota.this, "datos.db");
                        basedatos.agregarNota(nota.getText().toString());

                        Toast.makeText(NuevaNota.this, "Nota guardada", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        ModeloNotas modeloNotas = new ModeloNotas();
                        modeloNotas.setDescripcion(nota.getText().toString());

                        basedatos = new Basedatos(NuevaNota.this, "datos.db");
                        basedatos.editarNota(FragmentNotas.listaNotas.get(posicion).getId(), nota.getText().toString());

                        FragmentNotas.listaNotas.set(posicion, modeloNotas);
                        Toast.makeText(NuevaNota.this, "Nota editada", Toast.LENGTH_SHORT).show();
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
