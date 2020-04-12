package cu.pparce.via.Fragmentos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import cu.pparce.via.R;


public class FragmentEvaluacion extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    View view;
    Context context;

    CardView itemPersonalizado;




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public FragmentEvaluacion() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_evaluacion, container, false);

        Inicializar();
        return view;
    }
    private void Inicializar() {

        itemPersonalizado = (CardView) view.findViewById(R.id.itemPersonalizado);
        itemPersonalizado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] items = {"Señales","Limites de Velocidad", "Importes"};
                boolean[] itemsEstados = {false, false, false, false};
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder
                        .setTitle("Evaluación Personalizada")
                        .setMultiChoiceItems(items, itemsEstados, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i, boolean b) {

                            }
                        })
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                Dialog dialog = builder.create();
                dialog.getWindow().getAttributes().windowAnimations = R.style.AnimacionDialog1;
                dialog.show();
            }
        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }



}
