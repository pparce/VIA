package cu.uno.via.database.modelos;

public class ModeloNotas {

    int id = 0;
    String descripcion = "";

    public ModeloNotas() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

}

