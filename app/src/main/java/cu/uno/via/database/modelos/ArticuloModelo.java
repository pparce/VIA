package cu.uno.via.database.modelos;

public class ArticuloModelo {

    int nombre = 0;
    String descripcion = "";

    public ArticuloModelo() {
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre){
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

}

