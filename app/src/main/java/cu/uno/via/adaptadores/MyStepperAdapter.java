package cu.uno.via.adaptadores;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import java.util.List;

import cu.uno.via.database.modelos.PreguntaModelo;
import cu.uno.via.fragmentos.StepperFragment;

public class MyStepperAdapter extends AbstractFragmentStepAdapter {
    List<PreguntaModelo> list;

    public MyStepperAdapter(Context context, FragmentManager fm) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        Fragment fragment = new StepperFragment();

        Bundle b = new Bundle();
        b.putInt("POSICION", position);
        b.putInt("EXAMEN", position);
        fragment.setArguments(b);
        return (Step) fragment;
    }

    @Override
    public int getCount() {
        return 20;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        //Override this method to set Step title for the Tabs, not necessary for other stepper types
        return new StepViewModel.Builder(context)
                .setTitle("" + (position + 1)) //can be a CharSequence instead
                .create();
    }
}