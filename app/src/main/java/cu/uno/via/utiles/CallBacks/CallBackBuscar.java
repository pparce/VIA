package cu.uno.via.utiles.CallBacks;


import java.util.List;

import cu.uno.via.DataBase.ModeloArticulo;
import cu.uno.via.DataBase.ModeloSenal;

/**
 * Created by Administrador on 06/02/2019.
 */
public interface CallBackBuscar {

    public void pasarAdaptador(List<ModeloArticulo> lista);
    public void pasarAdaptadorSenal(List<ModeloSenal> lista);
}
