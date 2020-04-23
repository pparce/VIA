package cu.uno.via.ui.senales;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cu.uno.via.Adaptadores.SenalesAdapter;
import cu.uno.via.R;
import cu.uno.via.actividades.VisualizarSenales;
import cu.uno.via.utiles.SpacesItemDecorationEventos;

public class SenalesFragment extends Fragment {

    private SenalesViewModel slideshowViewModel;
    View view;
    Context context;
    RecyclerView rvLibros;
    LinearLayoutManager layoutManager;
    SenalesAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SenalesViewModel.class);
        view = inflater.inflate(R.layout.fragment_senales, container, false);
        initView();
        return view;
    }

    private void initView() {


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
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

}
