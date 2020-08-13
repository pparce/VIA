package cu.uno.via.fragmentos;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import cu.uno.via.R;
import cu.uno.via.actividades.ExamenActivity;
import cu.uno.via.App;

public class StepperFragment extends Fragment implements Step {


    Context context;
    View view;
    int position = 0;
    int examen = 0;
    TextView pregunta;
    ImageView imagen;
    RadioGroup grupoPreguntas;
    RadioButton pregunta1, pregunta2, pregunta3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_stepper, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getInt("POSICION");
        }
        initView();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initView() {
        imagen = view.findViewById(R.id.image);
        pregunta = view.findViewById(R.id.pregunta);
        grupoPreguntas = view.findViewById(R.id.grupoPreguntas);
        pregunta1 = view.findViewById(R.id.pregunta1);
        pregunta2 = view.findViewById(R.id.pregunta2);
        pregunta3 = view.findViewById(R.id.pregunta3);

        imagen.setImageBitmap(App.LISTA_EXAMENES.get(ExamenActivity.examen).getListaPreguntas().get(position).getImagen());
        pregunta.setText(App.LISTA_EXAMENES.get(ExamenActivity.examen).getListaPreguntas().get(position).getDescripcion());

        pregunta1.setText(App.LISTA_EXAMENES.get(ExamenActivity.examen).getListaPreguntas().get(position).getListaRespuestas().get(0).getDescripcion());
        pregunta2.setText(App.LISTA_EXAMENES.get(ExamenActivity.examen).getListaPreguntas().get(position).getListaRespuestas().get(1).getDescripcion());
        pregunta3.setText(App.LISTA_EXAMENES.get(ExamenActivity.examen).getListaPreguntas().get(position).getListaRespuestas().get(2).getDescripcion());
        
        grupoPreguntas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.pregunta1:
                        ExamenActivity.callbackRespuestaSelected.selected(position, getPuntos(0));
                        break;
                    case R.id.pregunta2:
                        ExamenActivity.callbackRespuestaSelected.selected(position, getPuntos(1));
                        break;
                    case R.id.pregunta3:
                        ExamenActivity.callbackRespuestaSelected.selected(position, getPuntos(2));
                        break;
                }

            }
        });
    }

    private Integer getPuntos(int position) {
        int aux = 0;
        if (App.LISTA_EXAMENES.get(0).getListaPreguntas().get(this.position).getListaRespuestas().get(position).getIsCorrecta()) {
            aux = 5;
        }
        return aux;
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


    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}

