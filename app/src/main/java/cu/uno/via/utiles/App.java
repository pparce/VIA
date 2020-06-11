package cu.uno.via.utiles;

import android.app.Application;
import android.database.Cursor;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cu.uno.via.database.Basedatos;
import cu.uno.via.database.DB;
import cu.uno.via.database.modelos.ModeloArticulo;
import cu.uno.via.database.modelos.ModeloExamen;
import cu.uno.via.database.modelos.ModeloInfraccion;
import cu.uno.via.database.modelos.ModeloLibro;
import cu.uno.via.database.modelos.ModeloPregunta;
import cu.uno.via.database.modelos.ModeloRespuesta;
import cu.uno.via.database.modelos.ModeloSenal;
import cu.uno.via.database.modelos.ModeloTipoSenal;
import cu.uno.via.utiles.CallBacks.CallBackFragmentInfraccion;
import cu.uno.via.utiles.CallBacks.CallBackPrincipaToInicio;
import cu.uno.via.utiles.CallBacks.CallbackArbolArticulos;
import cu.uno.via.utiles.CallBacks.CallbackFragmentBuscar;
import cu.uno.via.utiles.CallBacks.CallbackFragmentBuscarSenales;
import cu.uno.via.utiles.CallBacks.CallbackLibro;

public class App extends Application {
    public static List<ModeloLibro> LISTA_DOCUMENTOS;
    public static List<ModeloSenal> LISTA_SENALES;
    public static List<ModeloArticulo> LISTA_ARTICULOS_LIBROS;
    public static List<ModeloArticulo> LISTA_ARTICULOS_REGULACIONES;
    public static List<ModeloArticulo> LISTA_ARTICULOS;
    public static List<ModeloExamen> LISTA_EXAMENES;
    public static List<ModeloInfraccion> LISTA_INFRACCION;
    public static List<String> LISTA_REGULACIONES;

    public static CallbackLibro callbackLibro;
    public static CallbackArbolArticulos callbackArbolArticulo;
    public static CallBackPrincipaToInicio callBackPrincipaToInicio;
    public static CallbackFragmentBuscar callbackFragmentBuscar;
    public static List<CallbackFragmentBuscar> listaCallBack;
    public static CallbackFragmentBuscarSenales callbackFragmentBuscarSenales;
    public static CallBackFragmentInfraccion callBackFragmentInfraccion;

    Basedatos basedatos;

    @Override
    public void onCreate() {
        super.onCreate();
        initListas();

        crearBaseDatos();
        getDocumentos();
        getSenales();
        getArticulos();
        getInfracciones();
        getDisposiciones();
        getExamenes();
    }

    private void initListas() {
        listaCallBack = new ArrayList<>();
        LISTA_DOCUMENTOS = new ArrayList<>();
        LISTA_SENALES = new ArrayList<>();
        LISTA_ARTICULOS_LIBROS = new ArrayList<>();
        LISTA_ARTICULOS_REGULACIONES = new ArrayList<>();
        LISTA_ARTICULOS = new ArrayList<>();
        LISTA_INFRACCION = new ArrayList<>();
        LISTA_REGULACIONES = new ArrayList<>();
        LISTA_EXAMENES = new ArrayList<>();
    }

    private void crearBaseDatos() {
        basedatos = new Basedatos(this, "datos.db");
        try {
            basedatos.createDataBase("/data/user/0/cu.uno.via/databases/datos.db");
        } catch (IOException e) {
        }
    }

    private void getDocumentos() {
        Cursor cursor = DB.getInstance(this).searchTravelGeneral();
        List<ModeloLibro> listaDocumentos = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            /**set value to list one by one**/
            while (cursor.moveToNext()) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                ModeloLibro modelo = new ModeloLibro();
                modelo.setId(cursor.getInt(0));
                modelo.setNombre(cursor.getString(1));
                modelo.setCaratula(BitmapFactory.decodeByteArray(cursor.getBlob(4), 0, cursor.getBlob(4).length));
                modelo.setSinopsis(cursor.getString(3));
                listaDocumentos.add(modelo);
            }
        }
        App.LISTA_DOCUMENTOS = listaDocumentos;
    }

    private void getSenales() {
        Cursor cursor = DB.getInstance(this).getAll("SENALES");
        List<ModeloSenal> aux = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            /**set value to list one by one**/
            while (cursor.moveToNext()) {
                ModeloSenal modelo = new ModeloSenal();
                modelo.setId(cursor.getInt(0));
                modelo.setTipo(cursor.getString(1));
                modelo.setCaratula(BitmapFactory.decodeByteArray(cursor.getBlob(2), 0, cursor.getBlob(2).length));
                modelo.setListaTipoSenales(cargarTipoSenales(cursor.getInt(0)));
                modelo.setDescripcion(cursor.getString(3));
                aux.add(modelo);
            }
        }
        App.LISTA_SENALES = aux;
    }

    public List<ModeloTipoSenal> cargarTipoSenales(int id) {
        Cursor cursor = DB.getInstance(this).cargarTipoSenales(id);
        List<ModeloTipoSenal> listaTipoSenal = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            /**set value to list one by one**/
            while (cursor.moveToNext()) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                ModeloTipoSenal modelo = new ModeloTipoSenal();
                modelo.setId(cursor.getInt(0));
                modelo.setTipo(cursor.getString(1));
                modelo.setListaSenal(cargarImagenesSenal(cursor.getInt(0)));
                listaTipoSenal.add(modelo);
            }
        }
        return listaTipoSenal;
    }

    public List<ModeloSenal> cargarImagenesSenal(int id) {
        Cursor cursor = DB.getInstance(this).cargarImagenesSenales(id);
        List<ModeloSenal> listaTipoSenal = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            /**set value to list one by one**/
            while (cursor.moveToNext()) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                String aux;
                ModeloSenal modelo = new ModeloSenal();
                modelo.setId(cursor.getInt(0));
                if (cursor.getString(1) != null) {
                    aux = cursor.getString(1).replaceAll("\\n", " ");
                    aux = aux.toUpperCase();
                } else {
                    aux = "SIN NOMBRE";
                }
                modelo.setTipo(aux);
                modelo.setCaratula(BitmapFactory.decodeByteArray(cursor.getBlob(2), 0, cursor.getBlob(2).length));
                if (cursor.getString(4) != null) {
                    aux = cursor.getString(4).replaceAll("\\n", " ");
                } else {
                    aux = "";
                }
                modelo.setDescripcion(aux);
                listaTipoSenal.add(modelo);
            }
        }
        return listaTipoSenal;
    }

    private void getArticulos() {
        Cursor cursor = DB.getInstance(this).cargarArticulos();
        List<ModeloArticulo> listaArticulos = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            /**set value to list one by one**/
            while (cursor.moveToNext()) {
                ModeloArticulo modeloArticulo = new ModeloArticulo();
                modeloArticulo.setNombre(cursor.getInt(1));
                modeloArticulo.setDescripcion(cursor.getString(2));
                listaArticulos.add(modeloArticulo);
            }
        }
        App.LISTA_ARTICULOS = listaArticulos;
        for (int i = 0; i < 324; i++) {
            App.LISTA_ARTICULOS_LIBROS.add(listaArticulos.get(i));
        }
        for (int i = 324; i < listaArticulos.size(); i++) {
            App.LISTA_ARTICULOS_REGULACIONES.add(listaArticulos.get(i));
        }
    }

    private void getInfracciones() {
        Cursor cursor = DB.getInstance(this).cargarInfraccion();
        List<ModeloInfraccion> listaInfraccion = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            /**set value to list one by one**/
            while (cursor.moveToNext()) {
                ModeloInfraccion modeloInfraccion = new ModeloInfraccion();
                modeloInfraccion.setTipo(cursor.getString(1));
                modeloInfraccion.setListaArticulos(cargarListaInfracciones(cursor.getInt(0)));
                listaInfraccion.add(modeloInfraccion);
            }
        }
        LISTA_INFRACCION = listaInfraccion;
    }

    private List<ModeloArticulo> cargarListaInfracciones(int id) {
        Cursor cursor = DB.getInstance(this).cargarArticulosInfracciones(id);
        List<ModeloArticulo> lista = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {

            /**set value to list one by one**/
            while (cursor.moveToNext()) {
                ModeloArticulo modeloArticulo = new ModeloArticulo();
                modeloArticulo.setNombre(cursor.getInt(1));
                modeloArticulo.setDescripcion(cursor.getString(2));

                lista.add(modeloArticulo);

            }
        }
        return lista;
    }

    private void getDisposiciones() {
        Cursor cursor = DB.getInstance(this).cargarDisposiciones();
        List<String> listaDisposiciones = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            /**set value to list one by one**/
            while (cursor.moveToNext()) {
                listaDisposiciones.add(cursor.getString(2));
            }
        }
        App.LISTA_REGULACIONES = listaDisposiciones;
    }

    private void getExamenes() {
        Cursor cursor = DB.getInstance(this).getAll("EXAMENES");
        List<ModeloExamen> aux = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                ModeloExamen modelo = new ModeloExamen();
                modelo.setId(cursor.getInt(0));
                modelo.setNombre(cursor.getString(1));
                modelo.setListaPreguntas(getListaPreguntas(cursor.getInt(0)));
                aux.add(modelo);
            }
        }
        LISTA_EXAMENES = aux;
    }

    private List<ModeloPregunta> getListaPreguntas(int id) {
        Cursor cursor = DB.getInstance(this).getById("PREGUNTAS", "examen", id);
        List<ModeloPregunta> aux = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                ModeloPregunta modelo = new ModeloPregunta();
                modelo.setId(cursor.getInt(0));
                modelo.setDescripcion(cursor.getString(1));
                modelo.setImagen(BitmapFactory.decodeByteArray(cursor.getBlob(2), 0, cursor.getBlob(2).length));
                modelo.setListaRespuestas(getListaRespuestas(cursor.getInt(0)));
                aux.add(modelo);
            }
        }
        return aux;
    }

    private List<ModeloRespuesta> getListaRespuestas(int id) {
        Cursor cursor = DB.getInstance(this).getById("RESPUESTAS", "pregunta", id);
        List<ModeloRespuesta> aux = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                ModeloRespuesta modelo = new ModeloRespuesta();
                modelo.setId(cursor.getInt(0));
                modelo.setDescripcion(cursor.getString(1));
                if (cursor.getInt(3) == 1) {
                    modelo.setCorrecta(true);
                } else {
                    modelo.setCorrecta(false);
                }
                aux.add(modelo);
            }
        }
        return aux;
    }

}
