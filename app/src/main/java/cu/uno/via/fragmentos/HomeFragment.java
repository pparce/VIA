package cu.uno.via.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import cu.uno.via.R;
import cu.uno.via.actividades.ExamenActivity;
import cu.uno.via.App;
import cu.uno.via.actividades.VistaInformacionActivity;
import cu.uno.via.adaptadores.InformacionUtilAdapter;
import cu.uno.via.database.modelos.ArticuloModelo;
import cu.uno.via.database.modelos.InformacionModelo;
import cu.uno.via.utiles.PrefManager;
import cu.uno.via.utiles.SpacesItemDecorationEventos;

public class HomeFragment extends Fragment {

    View view;
    Context context;
    CardView primerExamen, estdisticasCard;
    TextView promedioTextView;
    MaterialButton otraVez;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    InformacionUtilAdapter adapter;
    List<InformacionModelo> informacion;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        return view;
    }

    private void initView() {
        informacion = new ArrayList<>();
        buildInformationList();
        primerExamen = view.findViewById(R.id.primerExamen);
        estdisticasCard = view.findViewById(R.id.estdisticasCard);
        promedioTextView = view.findViewById(R.id.promedio);
        otraVez = view.findViewById(R.id.otravez);
        recyclerView  = view.findViewById(R.id.recycler);

        if (App.LISTA_RESULTADOS.size() > 0) {
            validarPrimerExamen();
        }
        primerExamen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(context, ExamenActivity.class), 123);
            }
        });

        otraVez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(context, ExamenActivity.class), 123);
            }
        });

        layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        adapter = new InformacionUtilAdapter(context, informacion);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        SpacesItemDecorationEventos spacesItemDecoration = new SpacesItemDecorationEventos(45, 45);
        recyclerView.addItemDecoration(spacesItemDecoration);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, VistaInformacionActivity.class));
            }
        });
    }

    private void buildInformationList(){
        for (int i = 0; i < 5; i++) {
            InformacionModelo modelo = new InformacionModelo();
            modelo.setNombre("Escuelas");
            modelo.setDescripcion("Esto es una descripcion que tengo que poner aqui para simular una descripcion");
            informacion.add(modelo);
        }
    }

    private void validarPrimerExamen() {
        PrefManager prefManager = new PrefManager(context);
        if (prefManager.getPrimerExamenCompletado()) {
            primerExamen.setVisibility(View.GONE);
            estdisticasCard.setVisibility(View.VISIBLE);
            promedioTextView.setText(getPromedio() + " pts");
        }
    }

    private Integer getPromedio() {
        int aux = 0;
        for (int i = 0; i < App.LISTA_RESULTADOS.size(); i++) {
            aux += App.LISTA_RESULTADOS.get(i).getResultado();
        }
        return aux / App.LISTA_RESULTADOS.size();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            validarPrimerExamen();
        }
    }
}
