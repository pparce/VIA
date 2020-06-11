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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cu.uno.via.adaptadores.BuscarSenalesAdapter;
import cu.uno.via.database.modelos.ModeloArticulo;
import cu.uno.via.database.modelos.ModeloSenal;
import cu.uno.via.R;
import cu.uno.via.actividades.ActivityBuscar;
import cu.uno.via.utiles.App;
import cu.uno.via.utiles.CallBacks.CallBackBuscar;
import cu.uno.via.utiles.CallBacks.CallbackFragmentBuscarSenales;


public class FragmentoBuscarSenales extends Fragment implements CallbackFragmentBuscarSenales {


    Context context;
    View view;

    List<ModeloSenal> listaSenal;
    RecyclerView rvLibros;
    LinearLayoutManager layoutManager;
    BuscarSenalesAdapter adapter;
    LinearLayout noHay;
    public static String marcado = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_buscar_senales, container, false);


        Inicializar();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @SuppressLint("WrongConstant")
    private void Inicializar() {

        listaSenal = new ArrayList<>();
        for (int i = 0; i < App.LISTA_SENALES.size(); i++) {
            for (int j = 0; j < App.LISTA_SENALES.get(i).getListaTipoSenales().size(); j++) {
                List<ModeloSenal> listaAux = App.LISTA_SENALES.get(i).getListaTipoSenales().get(j).getListaSenal();
                this.listaSenal.addAll(listaAux);
            }
        }

        noHay = view.findViewById(R.id.noHay);

        App.callbackFragmentBuscarSenales = this;

        rvLibros = view.findViewById(R.id.rvSenales);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        adapter = new BuscarSenalesAdapter(context, listaSenal);
        rvLibros.setLayoutManager(layoutManager);
        rvLibros.setAdapter(adapter);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ActivityBuscar.searchView != null) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(ActivityBuscar.searchView .getWindowToken(), 0);
                }

                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                final View myview = layoutInflater.inflate(R.layout.dialog_layout_senales, null);
                ImageView imageView = myview.findViewById(R.id.imageView);
                TextView titulo = myview.findViewById(R.id.titulo);
                TextView descripcion = myview.findViewById(R.id.descripcion);
                String content = adapter.getLista().get(rvLibros.getChildAdapterPosition(view)).getDescripcion();
                descripcion.setText(content);
                titulo.setText(adapter.getLista().get(rvLibros.getChildAdapterPosition(view)).getTipo());
                imageView.setImageBitmap(adapter.getLista().get(rvLibros.getChildAdapterPosition(view)).getCaratula());
                builder
                        .setView(myview)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //ESTA IMPLEMENTADO MAS ABAJO
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
    public void setBusquedaSenales(String text) {
        BuscarAsyncSenal async = new BuscarAsyncSenal(new CallBackBuscar() {
            @Override
            public void pasarAdaptador(List<ModeloArticulo> lista) {

            }

            @Override
            public void pasarAdaptadorSenal(List<ModeloSenal> lista) {
                adapter.setFilter(lista);
                if (lista.size() == 0) {
                    noHay.setVisibility(View.VISIBLE);
                } else {
                    noHay.setVisibility(View.INVISIBLE);
                }
            }
        }, text, listaSenal);
    }


    private List<ModeloSenal> filter(List<ModeloSenal> articulos, String texto) {

        marcado = texto;
        List<ModeloSenal> list = new ArrayList<>();
        try {
            texto = texto.toLowerCase().trim();
            for (ModeloSenal senal : articulos) {

                String descripcion = senal.getDescripcion().toLowerCase();
                String nombre = senal.getTipo().toLowerCase();
                if (descripcion.contains(texto) || nombre.contains(texto) && texto.length() != 0) {
                    list.add(senal);
                }
            }

        } catch (Exception e) {


        }


        return list;
    }

    public class BuscarAsyncSenal extends AsyncTask<Void, Void, List<ModeloSenal>> {

        String texto;
        List<ModeloSenal> listaArticulos;
        CallBackBuscar callBackBuscar;

        public BuscarAsyncSenal(CallBackBuscar callBackBuscar, String texto, List<ModeloSenal> listaArticulos) {
            this.callBackBuscar = callBackBuscar;
            this.texto = texto;
            this.listaArticulos = listaArticulos;
            execute();
        }

        @Override
        protected List<ModeloSenal> doInBackground(Void... voids) {

            List<ModeloSenal> listafiltrada = filter(listaArticulos, texto);
            return listafiltrada;
        }

        @Override
        protected void onPostExecute(List<ModeloSenal> modeloArticulos) {
            callBackBuscar.pasarAdaptadorSenal(modeloArticulos);
        }
    }

}

