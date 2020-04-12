package cu.pparce.via.Fragmentos;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.util.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cu.pparce.via.Actividades.Principal;
import cu.pparce.via.Actividades.VisualizarPdf;
import cu.pparce.via.Adaptadores.LibrosAdapter;
import cu.pparce.via.R;
import cu.pparce.via.Utiles.SpacesItemDecorationEventos;


public class FragmentDocumentacion extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    View view;
    Context context;

    RecyclerView rvLibros;
    LinearLayoutManager layoutManager;
    LibrosAdapter adapter;




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public FragmentDocumentacion() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_documentacion, container, false);

        Inicializar();
        return view;
    }
    private void Inicializar() {

        rvLibros = (RecyclerView) view.findViewById(R.id.rvLibros);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        adapter = new LibrosAdapter(context);
        rvLibros.setLayoutManager(layoutManager);
        rvLibros.setAdapter(adapter);
        SpacesItemDecorationEventos spacesItemDecoration = new SpacesItemDecorationEventos(30, 15);
        rvLibros.addItemDecoration(spacesItemDecoration);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VisualizarPdf.class);
                Bundle bundle = new Bundle();
                bundle.putInt("posicion", rvLibros.getChildAdapterPosition(view));
                intent.putExtras(bundle);
                startActivity(intent);

                /*int posicion = rvLibros.getChildAdapterPosition(view);

                switch (posicion){
                    case 0:
                        break;
                    case 1:
                        abrirPdf("ley109.pdf");
                        break;
                    case 2:
                        abrirPdf("MANUAL.pdf");
                        break;
                }*/



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

    private void abrirPdf(String nombre){

        File file = getRutaAssets(nombre);
        if (file.exists()) {

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            Intent intent1 = Intent.createChooser(intent, "Aplicaciones disponibles para leer archivos PDF");
            try {
                intent1.getData();
                startActivity(intent1);
            } catch (ActivityNotFoundException e) {

            }
        }
    }

    private File getRutaAssets(String nombre){
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "+Luz verde" + File.separator + nombre);
        if (!file.exists()) {
            try {
                InputStream inputStream = context.getAssets().open(nombre);
                int size = inputStream.available();
                byte[] buffer = new byte[size];
                inputStream.read(buffer);
                inputStream.close();

                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(buffer);
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }


}
