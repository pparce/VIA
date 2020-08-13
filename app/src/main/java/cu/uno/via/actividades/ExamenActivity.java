package cu.uno.via.actividades;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.stepstone.stepper.internal.widget.RightNavigationButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cu.uno.via.App;
import cu.uno.via.R;
import cu.uno.via.adaptadores.MyStepperAdapter;
import cu.uno.via.utiles.CallBacks.CallbackRespuestaSelected;

public class ExamenActivity extends AppCompatActivity implements StepperLayout.StepperListener, CallbackRespuestaSelected {
    private StepperLayout mStepperLayout;
    public static CallbackRespuestaSelected callbackRespuestaSelected;
    List<Integer> listaResultados;
    Random random;
    public static int examen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examen);
        examen = App.random.nextInt(5);
        iniViews();
    }

    private void iniViews() {
        listaResultados = new ArrayList<>();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        callbackRespuestaSelected = this;
        mStepperLayout = findViewById(R.id.stepperLayout);
        mStepperLayout.setAdapter(
                new MyStepperAdapter(
                        this,
                        getSupportFragmentManager()));
        mStepperLayout.setListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCompleted(View completeButton) {
        Intent intent = new Intent(ExamenActivity.this, ResultadoExamenActivity.class);
        intent.putExtra("RESULTADO", calcularResultado());
        startActivity(intent);
        finish();
    }

    @Override
    public void onError(VerificationError verificationError) {

    }

    @Override
    public void onStepSelected(int newStepPosition) {
        validateNextButtom(newStepPosition);
        getSupportActionBar().setTitle("Pregunta " + (newStepPosition + 1) + "/20");
    }

    private void validateNextButtom(int newStepPosition) {
        if (newStepPosition < listaResultados.size()) {
            mStepperLayout.setNextButtonEnabled(true);
            mStepperLayout.setCompleteButtonEnabled(true);
            mStepperLayout.setNextButtonColor(getResources().getColor(R.color.colorAccent));
            mStepperLayout.setCompleteButtonColor(getResources().getColor(R.color.colorAccent));
        } else {
            mStepperLayout.setNextButtonEnabled(false);
            mStepperLayout.setCompleteButtonEnabled(false);
            mStepperLayout.setNextButtonColor(mStepperLayout.getUnselectedColor());
            mStepperLayout.setCompleteButtonColor(mStepperLayout.getUnselectedColor());
        }
    }

    private Integer calcularResultado() {
        int auxTotal = 0;
        for (int i = 0; i < listaResultados.size(); i++) {
            auxTotal += listaResultados.get(i);
        }
        return auxTotal;
    }

    @Override
    public void onReturn() {

    }

    @Override
    public void selected(int posicion, int puntos) {

        if (posicion == listaResultados.size()) {
            listaResultados.add(posicion, puntos);
        } else {
            listaResultados.set(posicion, puntos);
        }
        Log.d("...", "punto" + puntos + "   total" + calcularResultado());
        validateNextButtom(posicion);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        final AlertDialog.Builder builder = new AlertDialog.Builder(ExamenActivity.this);
        builder
                .setTitle(getResources().getString(R.string.alert_abandonar))
                .setMessage(getResources().getString(R.string.alert_cerrar_examen))
                .setNegativeButton(getResources().getString(R.string.alert_cancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(getResources().getString(R.string.alert_abandonar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

        final AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();
    }
}
