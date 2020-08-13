package cu.uno.via.database.modelos;

import android.graphics.Bitmap;
import java.util.ArrayList;
import java.util.List;

public class PreguntaModelo {
    int id = 0;
    String descripcion = "";
    Bitmap imagen = null;
    List<RespuestaModelo> listaRespuestas = new ArrayList<>();

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setListaRespuestas(List<RespuestaModelo> listaRespuestas) {
        this.listaRespuestas = listaRespuestas;
    }

    public List<RespuestaModelo> getListaRespuestas() {
        return listaRespuestas;
    }
}
