package cu.uno.via.fragmentos;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cu.uno.via.Adaptadores.ImagenesSenalesAdapter;
import cu.uno.via.DataBase.ModeloSenal;
import cu.uno.via.R;
import cu.uno.via.actividades.VisualizarSenales;
import cu.uno.via.utiles.App;
import cu.uno.via.utiles.SpacesItemDecorationEventos;

public class Fragmento extends Fragment {


    Context context;
    View view;

    RecyclerView recyclerView;
    ImagenesSenalesAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_tab, container, false);


        Inicializar();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @SuppressLint("WrongConstant")
    @TargetApi(Build.VERSION_CODES.M)
    private void Inicializar() {

        recyclerView = (RecyclerView) view.findViewById(R.id.rvLibros);

        layoutManager = new GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ImagenesSenalesAdapter(context, getArguments().getInt("posicion"));
        recyclerView.setAdapter(adapter);

        SpacesItemDecorationEventos spacesItemDecoration = new SpacesItemDecorationEventos(10, 7);
        recyclerView.addItemDecoration(spacesItemDecoration);
        adapter.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                List<ModeloSenal> list = App.LISTA_SENALES.get(VisualizarSenales.posicion).getListaTipoSenales().get(getArguments().getInt("posicion")).getListaSenal();
                int posicion = recyclerView.getChildAdapterPosition((View) view.getParent());
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                final View myview = layoutInflater.inflate(R.layout.dialog_layout_senales, null);
                ImageView imageView = myview.findViewById(R.id.imageView);
                TextView titulo = myview.findViewById(R.id.titulo);
                TextView descripcion = myview.findViewById(R.id.descripcion);
                imageView.setImageBitmap(list.get(posicion).getCaratula());
                titulo.setText(list.get(posicion).getTipo());
                descripcion.setText(list.get(posicion).getDescripcion());
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

        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                if (recyclerView.canScrollVertically(-1)) {
                    VisualizarSenales.onScrollCallback.onScroll(false);
                } else {
                    VisualizarSenales.onScrollCallback.onScroll(true);
                }
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


}

