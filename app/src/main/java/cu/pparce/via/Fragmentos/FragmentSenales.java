package cu.pparce.via.Fragmentos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cu.pparce.via.Actividades.Principal;
import cu.pparce.via.Actividades.VisualizarSenales;
import cu.pparce.via.Adaptadores.SenalesAdapter;
import cu.pparce.via.R;
import cu.pparce.via.Utiles.SpacesItemDecorationEventos;


public class FragmentSenales extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    View view;
    Context context;



    RecyclerView rvLibros;
    LinearLayoutManager layoutManager;
    SenalesAdapter adapter;




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public FragmentSenales() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_senales, container, false);

        Inicializar();
        return view;
    }
    @SuppressLint("WrongConstant")
    private void Inicializar() {


        rvLibros = (RecyclerView) view.findViewById(R.id.rvLibros);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        adapter = new SenalesAdapter(context);
        rvLibros.setLayoutManager(layoutManager);
        rvLibros.setAdapter(adapter);
        SpacesItemDecorationEventos spacesItemDecoration = new SpacesItemDecorationEventos(30, 15);
        rvLibros.addItemDecoration(spacesItemDecoration);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VisualizarSenales.class);
                Bundle bundle = new Bundle();
                bundle.putInt("posicion", rvLibros.getChildAdapterPosition(view));

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rvLibros.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                    if (rvLibros.canScrollVertically(-1)) {
                        Principal.onScrollCallback.onScroll(false);
                    } else {
                        Principal.onScrollCallback.onScroll(true);
                    }
                }
            });
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }



}
