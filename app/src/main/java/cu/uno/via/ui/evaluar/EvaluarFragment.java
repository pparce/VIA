package cu.uno.via.ui.evaluar;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import cu.uno.via.R;

public class EvaluarFragment extends Fragment {

    private EvaluarViewModel slideshowViewModel;
    View view;
    Context context;
    CardView itemPersonalizado;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(EvaluarViewModel.class);
        view = inflater.inflate(R.layout.fragment_evaluar, container, false);
        initView();
        return view;
    }

    private void initView() {

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
