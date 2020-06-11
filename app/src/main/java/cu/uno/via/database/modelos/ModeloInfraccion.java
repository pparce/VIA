package cu.uno.via.database.modelos;

import java.util.List;

public class ModeloInfraccion {
    String tipo;
    List<ModeloArticulo> listaArticulos;

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public String getTipo(){
        return tipo;
    }

    public void setListaArticulos(List<ModeloArticulo> listaArticulos){
        this.listaArticulos = listaArticulos;
    }

    public List<ModeloArticulo> getListaArticulos(){
        return listaArticulos;
    }
}
