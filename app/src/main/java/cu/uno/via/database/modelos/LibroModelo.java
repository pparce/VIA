package cu.uno.via.database.modelos;

import android.graphics.Bitmap;

public class LibroModelo {

    int id;
    String nombre = "";
    String autor = "";
    String sinopsis = "";
    Bitmap caratula = null;

    public LibroModelo() {
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
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


}

