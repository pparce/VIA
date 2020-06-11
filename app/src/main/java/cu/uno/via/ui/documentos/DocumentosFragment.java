package cu.uno.via.ui.documentos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cu.uno.via.adaptadores.LibrosAdapter;
import cu.uno.via.R;
import cu.uno.via.actividades.ActivityArbol;
import cu.uno.via.actividades.ActivityPdfView;

public class DocumentosFragment extends Fragment {

    private DocumentosViewModel slideshowViewModel;

    View view;
    Context context;
    RecyclerView rvLibros;
    LinearLayoutManager layoutManager;
    LibrosAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(DocumentosViewModel.class);
        view = inflater.inflate(R.layout.fragment_documentos, container, false);
        /*final TextView textView = root.findViewById(R.id.text_slideshow);
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        initView();
        return view;
    }

    private void initView() {

        rvLibros = view.findViewById(R.id.rvLibros);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        adapter = new LibrosAdapter(context);
        rvLibros.setLayoutManager(layoutManager);
        rvLibros.setAdapter(adapter);
        /*SpacesItemDecorationEventos spacesItemDecoration = new SpacesItemDecorationEventos(30, 15);
        rvLibros.addItemDecoration(spacesItemDecoration);*/

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(context, ActivityVisualizarPdf.class);
                Bundle bundle = new Bundle();
                bundle.putInt("posicion", rvLibros.getChildAdapterPosition(view));
                intent.putExtras(bundle);
                startActivity(intent);*/

                int posicion = rvLibros.getChildAdapterPosition(view);
                Intent intent = new Intent(context, ActivityPdfView.class);
                switch (posicion){
                    case 0:
                        startActivity(new Intent(context, ActivityArbol.class));
                        break;
                    case 1:
                        intent.putExtra("LIBRO","Codigo vial ilustrado.pdf");
                        startActivity(intent);
                        break;
                    case 2:
                        intent.putExtra("LIBRO","Guia de estudio.pdf");
                        startActivity(intent);
                        break;
                }



            }
        });

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
        }*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
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
