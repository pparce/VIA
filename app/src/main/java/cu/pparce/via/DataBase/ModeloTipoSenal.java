package cu.pparce.via.DataBase;

import android.graphics.Bitmap;

import java.util.List;

public class ModeloTipoSenal {

    int id;
    String tipo = "";
    Bitmap caratula = null;
    List<ModeloSenal> listaSenal;


    public ModeloTipoSenal() {
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

    public List<ModeloSenal> getListaSenal(){
        return listaSenal;
    }

    public void setListaSenal(List<ModeloSenal> listaSenal){
        this.listaSenal = listaSenal;
    }

    public Bitmap getCaratula() {
        return caratula;
    }

    public void setCaratula(Bitmap caratula){
        this.caratula = caratula;
    }
}

