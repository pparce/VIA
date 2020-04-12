package cu.pparce.via.Utiles.CallBacks;

import java.util.List;

import cu.pparce.via.DataBase.ModeloArticulo;
import cu.pparce.via.DataBase.ModeloSenal;


/**
 * Created by Administrador on 06/02/2019.
 */
public interface CallBackBuscar {

    public void pasarAdaptador(List<ModeloArticulo> lista);
    public void pasarAdaptadorSenal(List<ModeloSenal> lista);
}
