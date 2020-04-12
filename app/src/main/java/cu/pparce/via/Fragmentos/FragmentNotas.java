package cu.pparce.via.Fragmentos;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import cu.pparce.via.Adaptadores.NotasAdapter;
import cu.pparce.via.DataBase.Basedatos;
import cu.pparce.via.DataBase.ModeloNotas;
import cu.pparce.via.NuevaNota;
import cu.pparce.via.R;
import cu.pparce.via.Utiles.SpacesItemDecorationEventos;
import cu.pparce.via.Utiles.floatingtextbutton.FloatingTextButton;


public class FragmentNotas extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public FragmentNotas() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notas, container, false);

        Inicializar();
        return view;
    }
    private void Inicializar() {

        listaNotas = new ArrayList<>();
        cargarNotas();
        floatingTextButton = view.findViewById(R.id.call_button);
        floatingTextButton.setOnClickListener(new View.OnClickListener() {
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
