package cu.uno.via.utiles;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Darush on 9/2/2016.
 */
public class PrefManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared  preferences file name
    private static final String PREF_NAME = "settings";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_LOGIN = "isRegistrado";
    private static final String PRIMER_EXAMEN_COMPLETADO = "primerExamenCompletado";
    private static final String ULTIMO_RESULTADO = "ultimoResultado";

    public PrefManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return sharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setIsLogin(boolean isLogin) {
        editor.putBoolean(IS_LOGIN, isLogin);
        editor.commit();
    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public void setPrimerExamenCompletado(boolean primerExamenCompletado) {
        editor.putBoolean(PRIMER_EXAMEN_COMPLETADO, primerExamenCompletado);
        editor.commit();
    }

    public boolean getPrimerExamenCompletado() {
        return sharedPreferences.getBoolean(PRIMER_EXAMEN_COMPLETADO, false);
    }

    public void setUltimoResultado(int ultimoResultado) {
        editor.putInt(ULTIMO_RESULTADO, ultimoResultado);
        editor.commit();
    }

    public Integer getUltimoResultado() {
        return sharedPreferences.getInt(ULTIMO_RESULTADO, 0);
    }
}
