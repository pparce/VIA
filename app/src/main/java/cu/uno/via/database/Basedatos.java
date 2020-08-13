package cu.uno.via.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Basedatos extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private final Context context;


    public Basedatos(Context context, String name) {
        super(context, name, null, 2);
        this.context = context;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.disableWriteAheadLogging();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void createDataBase(String finalPath) throws IOException {
        boolean dbExist = checkDataBase(finalPath);
        if (dbExist) {

        } else {
            this.getReadableDatabase();
            this.close();

            try {
                copyDataBase(finalPath);
            } catch (IOException e) {
                throw new Error("Error copiando la base de datos");
            }

        }
    }

    public boolean checkDataBase(String path) {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = path;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase(String finalPath) throws IOException {
        InputStream myInput = context.getAssets().open("datos.db");
        String outFileName = finalPath;

        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int lenght;
        while ((lenght = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, lenght);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public Cursor Cargar(String tabla) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("Select * from " + tabla, null);
        return cur;
    }

    public Cursor cargarArticulos() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("Select * from ARTICULOS", null);
        return cur;
    }

    public Cursor cargarNotas() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("Select * from NOTAS", null);
        return cur;
    }

    public Cursor cargarTipoSenales(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("select * from CLASIFICACION_SENALES where tipo='" + id + "'", null);
        return cur;
    }

    public Cursor cargarImagenesSenales(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("select * from IMAGENES_SENALES where clasificacion='" + id + "'", null);
        return cur;
    }

    public Cursor cargarInfraccion() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("Select * from INFRACCIONES", null);
        return cur;
    }

    public Cursor cargarDisposiciones() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("Select * from DISPOSICIONES", null);
        return cur;
    }

    public Cursor cargarArticulosInfracciones(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("Select * from ARTICULOS_INFRACCION where categoria='" + id + "'", null);
        return cur;
    }

    public void agregarNota(String nota) {
        db = getWritableDatabase();
        db.execSQL("insert into NOTAS (descripcion) values(" + "'" + nota + "')");
    }

    public void eliminarNota(int id) {
        db = getWritableDatabase();
        db.execSQL("delete from NOTAS where id= " + id);
    }

    public void editarNota(int id, String nota) {
        db = getWritableDatabase();
        db.execSQL("update NOTAS set descripcion = '" + nota + "' where id= " + id);
    }

    public void addResultado(String fecha, int resultado) {
        db = getWritableDatabase();
        db.execSQL("insert into RESULTADO (fecha, resultado) values(" + "'" + fecha + "'" + "," + resultado + ")");
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select * from LIBROS", null);
        db.close();
        return res;
    }

}

