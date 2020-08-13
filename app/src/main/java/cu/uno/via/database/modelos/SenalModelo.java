package cu.uno.via.database.modelos;

import android.graphics.Bitmap;

import java.util.List;

public class SenalModelo {

    int id;
    String tipo = "";
    Bitmap caratula = null;
    String descripcion = "";
    List<ModeloTipoSenal> listaTipoSenales;


    public SenalModelo() {
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public List<ModeloTipoSenal> getListaTipoSenales(){
        return listaTipoSenales;
    }

    public void setListaTipoSenales(List<ModeloTipoSenal> listaTipoSenales){
        this.listaTipoSenales = listaTipoSenales;
    }

    public Bitmap getCaratula() {
        return caratula;
    }

    public void setCaratula(Bitmap caratula){
        this.caratula = caratula;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }
}

