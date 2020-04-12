package cu.pparce.via.Fragmentos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cu.pparce.via.Adaptadores.BuscarSenalesAdapter;
import cu.pparce.via.Aplicacion;
import cu.pparce.via.DataBase.ModeloArticulo;
import cu.pparce.via.DataBase.ModeloSenal;
import cu.pparce.via.R;
import cu.pparce.via.Utiles.CallBacks.CallBackBuscar;
import cu.pparce.via.Utiles.CallBacks.CallbackFragmentBuscar;
import cu.pparce.via.Utiles.CallBacks.CallbackFragmentBuscarSenales;
import cu.pparce.via.Utiles.SpacesItemDecorationEventos;
import cu.pparce.via.Utiles.flowtextview.FlowTextView;


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
        for (int i = 0; i < Aplicacion.LISTA_SENALES.size(); i++) {
            for (int j = 0; j < Aplicacion.LISTA_SENALES.get(i).getListaTipoSenales().size(); j++) {
                List<ModeloSenal> listaAux = Aplicacion.LISTA_SENALES.get(i).getListaTipoSenales().get(j).getListaSenal();
                this.listaSenal.addAll(listaAux);
            }
        }

        noHay = (LinearLayout) view.findViewById(R.id.noHay);

        Aplicacion.callbackFragmentBuscarSenales = this;

        rvLibros = (RecyclerView) view.findViewById(R.id.rvSenales);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        adapter = new BuscarSenalesAdapter(context, listaSenal);
        rvLibros.setLayoutManager(layoutManager);
        rvLibros.setAdapter(adapter);
        SpacesItemDecorationEventos spacesItemDecoration = new SpacesItemDecorationEventos(30, 15);
        rvLibros.addItemDecoration(spacesItemDecoration);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                final View myview = layoutInflater.inflate(R.layout.dialog_custom_layout, null);
                ImageView imageView = (ImageView) myview.findViewById(R.id.imageView);
                TextView descripcion = (TextView) myview.findViewById(R.id.descripcion);

                String content =  adapter.getLista().get(rvLibros.getChildAdapterPosition(view)).getDescripcion();
                descripcion.setText(content);
                imageView.setImageBitmap(adapter.getLista().get(rvLibros.getChildAdapterPosition(view)).getCaratula());



                builder
                        .setTitle(adapter.getLista().get(rvLibros.getChildAdapterPosition(view)).getTipo())
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

