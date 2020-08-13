package cu.uno.via.database.modelos;

import java.util.List;

public class InfraccionModelo {
    String tipo;
    List<ArticuloModelo> listaArticulos;

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public String getTipo(){
        return tipo;
    }

    public void setListaArticulos(List<ArticuloModelo> listaArticulos){
        this.listaArticulos = listaArticulos;
    }

    public List<ArticuloModelo> getListaArticulos(){
        return listaArticulos;
    }
}
