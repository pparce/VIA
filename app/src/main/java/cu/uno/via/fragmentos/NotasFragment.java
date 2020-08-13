package cu.uno.via.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import cu.uno.via.adaptadores.NotasAdapter;
import cu.uno.via.database.Basedatos;
import cu.uno.via.database.modelos.NotasModelo;
import cu.uno.via.R;
import cu.uno.via.actividades.NuevaNotaActivity;
import cu.uno.via.utiles.GridItemSpacingDecoration;

public class NotasFragment extends Fragment {
    View view;
    Context context;
    FloatingActionButton fab;
    Cursor cursor;
    Basedatos basedatos;
    public static List<NotasModelo> listaNotas;
    RecyclerView recycler;
    RecyclerView.LayoutManager layoutManager;
    NotasAdapter adapter;
    LinearLayout noHay;
    List<View> listaView;
    boolean eliminar = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notas, container, false);
        initView();
        return view;
    }

    private void initView() {

        listaNotas = new ArrayList<>();
        cargarNotas();
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!eliminar) {
                    startActivity(new Intent(context, NuevaNotaActivity.class));
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
                    fab.setImageResource(R.drawable.ic_add);
                    eliminar = false;
                    listaView.clear();
                    Snackbar.make(view, "Nota eliminada", Snackbar.LENGTH_SHORT).show();
                    if (listaNotas.size() == 0) {
                        noHay.setVisibility(View.VISIBLE);
                    } else {
                        noHay.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        noHay = view.findViewById(R.id.noHay);
        recycler = view.findViewById(R.id.recycler);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        adapter = new NotasAdapter(context , listaNotas);
        Resources resources = getResources();
        int spacingPixel = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, resources.getDisplayMetrics());
        GridItemSpacingDecoration itemDecorationEventos = new GridItemSpacingDecoration(2, spacingPixel, true);
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
                        fab.setImageResource(R.drawable.ic_eliminar);


                        if (listaView.isEmpty()) {
                            eliminar = false;
                            fab.setImageResource(R.drawable.ic_add);
                        }

                    } else {
                        view.setSelected(true);
                        listaView.add(view);
                        fab.setImageResource(R.drawable.ic_eliminar);
                    }
                } else {
                    Intent intent = new Intent(context, NuevaNotaActivity.class);
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
                    fab.setImageResource(R.drawable.ic_eliminar);
                }
                return true;
            }
        });
    }

    private void cargarNotas() {
        basedatos = new Basedatos(context, "datos.db");
        cursor = basedatos.cargarNotas();
        List<NotasModelo> listaArticulos = new ArrayList<>();

        if (cursor != null && cursor.getCount() > 0) {
            /**set value to list one by one**/
            while (cursor.moveToNext()) {
                NotasModelo modeloArticulo = new NotasModelo();
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
