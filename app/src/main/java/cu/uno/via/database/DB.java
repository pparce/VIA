package cu.uno.via.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DB {

    private SQLiteDatabase dataBase;

    @SuppressLint("StaticFieldLeak")
    private static DB instance;

    private DB(Context context) {
//        dataBase = SQLiteDatabase.openDatabase(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/datos.db", null, SQLiteDatabase.OPEN_READONLY);
        dataBase = SQLiteDatabase.openDatabase("/data/user/0/cu.uno.via/databases/datos.db", null, SQLiteDatabase.OPEN_READONLY);
    }

    public static DB getInstance(Context context) {
        if (instance == null) {
            instance = new DB(context);
        }
        return instance;
    }

    public Cursor searchTravelGeneral() {
        return dataBase.rawQuery("SELECT * FROM LIBROS", null);
    }

    public Cursor getAll(String tabla) {
        return dataBase.rawQuery("Select * from " + tabla, null);
    }

    public Cursor getById(String tabla, String columna, int id) {
        return dataBase.rawQuery("Select * from " + tabla + " where " + columna + "='" + id + "'", null);
    }

    public Cursor deleteById(String tabla, int id) {
        return dataBase.rawQuery("delete from " + tabla + " where id= " + id, null);
    }

    public Cursor updateByField(String tabla, String field, String value, int id) {
        return dataBase.rawQuery("update " + tabla + " set " + value + " = " + value + " where id= " + id, null);
    }

    public Cursor cargarArticulos() {
        return dataBase.rawQuery("Select * from ARTICULOS", null);
    }

    public Cursor cargarTipoSenales(int id) {
        return dataBase.rawQuery("select * from CLASIFICACION_SENALES where tipo='" + id + "'", null);
    }

    public Cursor cargarImagenesSenales(int id) {
        return dataBase.rawQuery("select * from IMAGENES_SENALES where clasificacion='" + id + "'", null);
    }

    public Cursor cargarInfraccion() {
        return dataBase.rawQuery("Select * from INFRACCIONES", null);
    }

    public Cursor cargarDisposiciones() {
        return dataBase.rawQuery("Select * from DISPOSICIONES", null);
    }

    public Cursor cargarArticulosInfracciones(int id) {
        return dataBase.rawQuery("Select * from ARTICULOS_INFRACCION where categoria='" + id + "'", null);
    }

    public Cursor cargarNotas() {
        return dataBase.rawQuery("Select * from NOTAS", null);
    }

    public void agregarNota(String nota) {
        dataBase.execSQL("insert into NOTAS (descripcion) values(" + "'" + nota + "')");
    }

    public void eliminarNota(int id) {
        dataBase.execSQL("delete from NOTAS where id= " + id);
    }

    public void editarNota(int id, String nota) {
        dataBase.execSQL("update NOTAS set descripcion = '" + nota + "' where id= " + id);
    }

    public void addResultado(String fecha, int resultado) {
        dataBase.execSQL("insert into RESULTADO (fecha, resultado) values(" + "'" + fecha + "'" + "," + resultado + ")");
    }
}