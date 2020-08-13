package cu.uno.via.fragmentos;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import cu.uno.via.R;
import cu.uno.via.actividades.ExamenActivity;

public class EvaluarFragment extends Fragment {
    View view;
    Context context;
    CardView itemPersonalizado, itemExamen;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_evaluar, container, false);
        initView();
        return view;
    }

    private void initView() {

        itemPersonalizado = view.findViewById(R.id.itemPersonalizado);
        itemExamen = view.findViewById(R.id.itemExamen);
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
        itemExamen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(context, ActivityResultadoExamen.class);
                intent.putExtra("RESULTADO", 75);
                startActivity(intent);*/
                startActivity(new Intent(context, ExamenActivity.class));
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
