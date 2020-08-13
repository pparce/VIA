package cu.uno.via.utiles.CallBacks;


import java.util.List;

import cu.uno.via.database.modelos.ArticuloModelo;
import cu.uno.via.database.modelos.SenalModelo;

/**
 * Created by Administrador on 06/02/2019.
 */
public interface CallBackBuscar {

    public void pasarAdaptador(List<ArticuloModelo> lista);
    public void pasarAdaptadorSenal(List<SenalModelo> lista);
}
