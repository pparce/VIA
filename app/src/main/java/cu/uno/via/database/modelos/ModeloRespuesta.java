package cu.uno.via.database.modelos;

public class ModeloRespuesta {
    int id = 0;
    String descripcion = "";
    boolean isCorrecta = false;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setDescripcion(String descriocion) {
        this.descripcion = descriocion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setCorrecta(boolean correcta) {
        isCorrecta = correcta;
    }

    public boolean getIsCorrecta() {
        return isCorrecta;
    }
}
