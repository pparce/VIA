package cu.pparce.via.Fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import androidx.fragment.app.Fragment;


import cu.pparce.via.Aplicacion;
import cu.pparce.via.R;
import cu.pparce.via.Utiles.CallBacks.CallBackPrincipaToInicio;


public class FragmentInicio extends Fragment implements CallBackPrincipaToInicio {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    View view;
    Context context;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public FragmentInicio() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_inicio, container, false);

        initViews();
        return view;
    }

    private void initViews() {
        Aplicacion.callBackPrincipaToInicio = this;


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public void shakeOff() {

    }

    public void ShakeAnimation(View view) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] ti = {10, 10, 10};
        vibrator.vibrate(new long[]{40, 80, 40, 80, 40, 80, 40}, -1);
        Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
        view.startAnimation(shake);
    }
}
