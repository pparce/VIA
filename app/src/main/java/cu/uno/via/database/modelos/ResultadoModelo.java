package cu.uno.via.database.modelos;

import java.util.ArrayList;
import java.util.List;

public class ResultadoModelo {
    int id;
    String fecha;
    int resultado;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }

    public int getResultado() {
        return resultado;
    }
}
