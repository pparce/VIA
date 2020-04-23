package cu.uno.via.ui.notas;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import cu.uno.via.Adaptadores.NotasAdapter;
import cu.uno.via.DataBase.Basedatos;
import cu.uno.via.DataBase.ModeloNotas;
import cu.uno.via.R;
import cu.uno.via.actividades.NuevaNota;
import cu.uno.via.utiles.SpacesItemDecorationEventos;
import cu.uno.via.utiles.floatingtextbutton.FloatingTextButton;

public class NotasFragment extends Fragment {

    private NotasViewModel slideshowViewModel;
    View view;
    Context context;
    FloatingActionButton fab;
    FloatingTextButton floatingTextButton;

    Cursor cursor;
    Basedatos basedatos;
    public static List<ModeloNotas> listaNotas;

    RecyclerView recycler;
    RecyclerView.LayoutManager layoutManager;
    NotasAdapter adapter;

    LinearLayout noHay;

    List<View> listaView;

    boolean eliminar = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(NotasViewModel.class);
        view = inflater.inflate(R.layout.fragment_notas, container, false);
        initView();
        return view;
    }

    private void initView() {

        listaNotas = new ArrayList<>();
        cargarNotas();
        floatingTextButton = view.findViewById(R.id.call_button);
        floatingTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!eliminar) {
                    startActivity(new Intent(context, NuevaNota.class));
                } else {
                    for (int i = 0; i < listaView.size(); i++) {
                        View viewAux = listaView.get(i);
                        int posicion = recycler.getChildAdapterPosition(viewAux);
                        viewAux.setSelected(false);


                        Basedatos basedatos = new Basedatos(context, "datos.db");
                        basedatos.eliminarNota(listaNotas.get(posicion).getId());

                        listaNotas.remove(posicion);
                        adapter.notifyItemRemoved(posicion);
                    }

                    floatingTextButton.setRightIconDrawable(context.getDrawable(R.drawable.ic_add));
                    floatingTextButton.setTitle("Nueva nota");

                    eliminar = false;
                    listaView.clear();

                    Toast.makeText(context, "Nota Eliminada", Toast.LENGTH_SHORT).show();

                    if (listaNotas.size() == 0) {
                        noHay.setVisibility(View.VISIBLE);
                    } else {
                        noHay.setVisibility(View.INVISIBLE);
                    }

                }
            }
        });
        /*fab = (FloatingActionButton) view.findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!eliminar) {
                    startActivity(new  Intent(context, NuevaNota.class));
                } else {
                    for (int i = 0; i < listaView.size(); i++) {
                        View viewAux = listaView.get(i);
                        int posicion = recycler.getChildAdapterPosition(viewAux);
                        viewAux.setSelected(false);


                        Basedatos basedatos = new Basedatos(context, "datos.db");
                        basedatos.eliminarNota(listaNotas.get(posicion).getId());

                        listaNotas.remove(posicion);
                        adapter.notifyItemRemoved(posicion);
                    }

                    eliminar = false;
                    listaView.clear();
                    fab.setImageResource(R.drawable.ic_add);

                    if (listaNotas.size() == 0) {
                        noHay.setVisibility(View.VISIBLE);
                    } else {
                        noHay.setVisibility(View.INVISIBLE);
                    }

                }
            }
        });*/

        noHay = (LinearLayout) view.findViewById(R.id.noHay);

        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        adapter = new NotasAdapter(context , listaNotas);
        SpacesItemDecorationEventos itemDecorationEventos = new SpacesItemDecorationEventos(10,10);
        recycler.addItemDecoration(itemDecorationEventos);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);

        if (listaNotas.size() == 0) {
            noHay.setVisibility(View.VISIBLE);
        } else {
            noHay.setVisibility(View.INVISIBLE);
        }

        adapter.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (eliminar) {
                    if (view.isSelected()) {
                        view.setSelected(false);
                        listaView.remove(view);
                        floatingTextButton.setRightIconDrawable(context.getDrawable(R.drawable.ic_eliminar));
                        floatingTextButton.setTitle(listaView.size() + "");


                        if (listaView.isEmpty()) {
                            eliminar = false;
                            floatingTextButton.setRightIconDrawable(context.getDrawable(R.drawable.ic_add));
                            floatingTextButton.setTitle("Nueva nota");
                        }

                    } else {
                        view.setSelected(true);
                        listaView.add(view);
                        floatingTextButton.setRightIconDrawable(context.getDrawable(R.drawable.ic_eliminar));
                        floatingTextButton.setTitle(listaView.size() + "");
                    }
                } else {
                    Intent intent = new Intent(context, NuevaNota.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("nota", listaNotas.get(recycler.getChildAdapterPosition(view)).getDescripcion());
                    bundle.putInt("posicion", recycler.getChildAdapterPosition(view));
                    bundle.putBoolean("editar", true);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        adapter.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if (!eliminar) {
                    listaView = new ArrayList<View>();

                    view.setSelected(true);
                    listaView.add(view);
                    eliminar = true;
                    floatingTextButton.setRightIconDrawable(context.getDrawable(R.drawable.ic_eliminar));
                    floatingTextButton.setTitle(listaView.size() + "");
                }
                return true;
            }
        });
    }

    private void cargarNotas() {

        basedatos = new Basedatos(context, "datos.db");
        cursor = basedatos.cargarNotas();
        List<ModeloNotas> listaArticulos = new ArrayList<>();


        if (cursor != null && cursor.getCount() > 0) {
            /**set value to list one by one**/
            while (cursor.moveToNext()) {
                ModeloNotas modeloArticulo = new ModeloNotas();
                modeloArticulo.setId(cursor.getInt(0));
                modeloArticulo.setDescripcion(cursor.getString(1));
                listaArticulos.add(modeloArticulo);

            }
        }

        listaNotas = listaArticulos;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public void onStart() {

        adapter.notifyDataSetChanged();

        if (listaNotas.size() == 0) {
            noHay.setVisibility(View.VISIBLE);
        } else {
            noHay.setVisibility(View.INVISIBLE);
        }
        super.onStart();
    }
}
