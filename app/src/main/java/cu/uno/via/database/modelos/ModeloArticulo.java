package cu.uno.via.database.modelos;

public class ModeloArticulo {

    int nombre = 0;
    String descripcion = "";

    public ModeloArticulo() {
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

