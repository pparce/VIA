package cu.uno.via.database.modelos;

import android.graphics.Bitmap;

public class ModeloCatalogo {

    String nombre = "";
    String autor = "";
    String sinopsis = "";
    Bitmap caratula = null;
    String ruta = "";

    public ModeloCatalogo() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor){
        this.autor = autor;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis){
        this.sinopsis = sinopsis;
    }

    public Bitmap getCaratula() {
        return caratula;
    }

    public void setCaratula(Bitmap caratula){
        this.caratula = caratula;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta){
        this.ruta = ruta;
    }
}

