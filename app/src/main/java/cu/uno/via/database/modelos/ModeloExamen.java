package cu.uno.via.database.modelos;

import java.util.ArrayList;
import java.util.List;

public class ModeloExamen {
    int id;
    String nombre;
    List<ModeloPregunta> listaPreguntas = new ArrayList<>();

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setListaPreguntas(List<ModeloPregunta> listaPreguntas) {
        this.listaPreguntas = listaPreguntas;
    }

    public List<ModeloPregunta> getListaPreguntas() {
        return listaPreguntas;
    }
}
