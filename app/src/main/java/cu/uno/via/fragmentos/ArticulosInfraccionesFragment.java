package cu.uno.via.fragmentos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cu.uno.via.adaptadores.ArticulosInfraccionAdapter;
import cu.uno.via.database.modelos.ArticuloModelo;
import cu.uno.via.database.modelos.SenalModelo;
import cu.uno.via.R;
import cu.uno.via.actividades.PrincipalActivity;
import cu.uno.via.App;
import cu.uno.via.utiles.CallBacks.CallBackBuscar;
import cu.uno.via.utiles.CallBacks.CallbackFragmentBuscar;


public class ArticulosInfraccionesFragment extends Fragment implements CallbackFragmentBuscar {


    Context context;
    View view;

    List<ArticuloModelo> listaArticulos;

    LinearLayout noHay;

    RecyclerView recyclerview;
    LinearLayoutManager layoutManager;
    ArticulosInfraccionAdapter adapter;

    public static String marcado = "";
    int posicion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_buscar_articulos, container, false);

        Inicializar();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @SuppressLint("WrongConstant")
    private void Inicializar() {

        posicion = getArguments().getInt("posicion");

        App.listaCallBack.add(posicion, this);

        listaArticulos = new ArrayList<>();
        listaArticulos = App.LISTA_INFRACCION.get(posicion).getListaArticulos();

        noHay = view.findViewById(R.id.noHay);
        recyclerview = view.findViewById(R.id.rvArticulos);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        adapter = new ArticulosInfraccionAdapter(context, App.LISTA_INFRACCION.get(posicion).getListaArticulos());
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(adapter);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PrincipalActivity.focusedView != null) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(PrincipalActivity.focusedView .getWindowToken(), 0);
                }
                String nombre = "";
                int posicion = recyclerview.getChildAdapterPosition(view);
                List<ArticuloModelo> lista = adapter.getLista();
                nombre = "Artículo " + lista.get(posicion).getNombre();
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                final View myview = layoutInflater.inflate(R.layout.dialog_layout_articulo, null);
                TextView descripcion = myview.findViewById(R.id.descripcion);
                String content = lista.get(posicion).getDescripcion();
                descripcion.setText(content);
                String[] aux = nombre.split(" ");
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder
                        .setTitle(nombre)
                        .setView(myview)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                final AlertDialog dialog = builder.create();

                dialog.getWindow().getAttributes().windowAnimations = R.style.AnimacionDialog1;
                dialog.setCancelable(true);
                dialog.show();
            }
        });
    }

    private List<ArticuloModelo> filter(List<ArticuloModelo> articulos, String texto) {

        marcado = texto;
        List<ArticuloModelo> list = new ArrayList<>();
        try {
            texto = texto.toLowerCase().trim();
            for (ArticuloModelo articulo : articulos) {
                String descripcion = articulo.getDescripcion().toLowerCase();
                String nombre = "Articulo ".toLowerCase() + articulo.getNombre();
                if (descripcion.contains(texto)) {
                    list.add(articulo);
                }
                if (nombre.contains(texto) && texto.length() != 0) {
                    list.add(articulo);
                }
            }
        } catch (Exception e) {
        }
        return list;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;


    }

    @Override
    public void setBusquedaArticulos(final String text) {

        BuscarAsync async = new BuscarAsync(new CallBackBuscar() {
            @Override
            public void pasarAdaptador(List<ArticuloModelo> lista) {
                adapter.setFilter(lista);
                if (lista.size() == 0) {
                    noHay.setVisibility(View.VISIBLE);
                    InfraccionFragment.canScroll = true;
                } else {
                    noHay.setVisibility(View.INVISIBLE);
                    if (InfraccionFragment.canScroll) {
                        InfraccionFragment.pager.setCurrentItem(posicion);
                        InfraccionFragment.canScroll = false;
                    }
                }
                if (text.length() == 0) {
                    InfraccionFragment.pager.setCurrentItem(0);
                }
            }

            @Override
            public void pasarAdaptadorSenal(List<SenalModelo> lista) {

            }
        }, text, listaArticulos);
    }

    @Override
    public void setBusquedaSenales(String text) {

    }

    public class BuscarAsync extends AsyncTask<Void, Void, List<ArticuloModelo>> {

        String texto;
        List<ArticuloModelo> listaArticulos;
        CallBackBuscar callBackBuscar;

        public BuscarAsync(CallBackBuscar callBackBuscar, String texto, List<ArticuloModelo> listaArticulos) {
            this.callBackBuscar = callBackBuscar;
            this.texto = texto;
            this.listaArticulos = listaArticulos;
            execute();
        }

        @Override
        protected List<ArticuloModelo> doInBackground(Void... voids) {

            List<ArticuloModelo> listafiltrada = filter(listaArticulos, texto);
            return listafiltrada;
        }

        @Override
        protected void onPostExecute(List<ArticuloModelo> articuloModelos) {
            callBackBuscar.pasarAdaptador(articuloModelos);
        }
    }
}

