package cu.uno.via;

import android.app.Application;
import android.database.Cursor;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cu.uno.via.database.Basedatos;
import cu.uno.via.database.DB;
import cu.uno.via.database.modelos.ArticuloModelo;
import cu.uno.via.database.modelos.ExamenModelo;
import cu.uno.via.database.modelos.InfraccionModelo;
import cu.uno.via.database.modelos.LibroModelo;
import cu.uno.via.database.modelos.PreguntaModelo;
import cu.uno.via.database.modelos.RespuestaModelo;
import cu.uno.via.database.modelos.ResultadoModelo;
import cu.uno.via.database.modelos.SenalModelo;
import cu.uno.via.database.modelos.ModeloTipoSenal;
import cu.uno.via.utiles.CallBacks.CallBackFragmentInfraccion;
import cu.uno.via.utiles.CallBacks.CallBackPrincipaToInicio;
import cu.uno.via.utiles.CallBacks.CallbackArbolArticulos;
import cu.uno.via.utiles.CallBacks.CallbackFragmentBuscar;
import cu.uno.via.utiles.CallBacks.CallbackFragmentBuscarSenales;
import cu.uno.via.utiles.CallBacks.CallbackLibro;

public class App extends Application {
    public static List<LibroModelo> LISTA_DOCUMENTOS;
    public static List<SenalModelo> LISTA_SENALES;
    public static List<ArticuloModelo> LISTA_ARTICULOS_LIBROS;
    public static List<ArticuloModelo> LISTA_ARTICULOS_REGULACIONES;
    public static List<ArticuloModelo> LISTA_ARTICULOS;
    public static List<ExamenModelo> LISTA_EXAMENES;
    public static List<ResultadoModelo> LISTA_RESULTADOS;
    public static List<InfraccionModelo> LISTA_INFRACCION;
    public static List<String> LISTA_REGULACIONES;

    public static CallbackLibro callbackLibro;
    public static CallbackArbolArticulos callbackArbolArticulo;
    public static CallBackPrincipaToInicio callBackPrincipaToInicio;
    public static CallbackFragmentBuscar callbackFragmentBuscar;
    public static List<CallbackFragmentBuscar> listaCallBack;
    public static CallbackFragmentBuscarSenales callbackFragmentBuscarSenales;
    public static CallBackFragmentInfraccion callBackFragmentInfraccion;

    public static String FECHA;
    public static Boolean COMPRADO;
    public static Random random = new Random();

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
        getResultados();
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
        LISTA_RESULTADOS = new ArrayList<>();
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
        List<LibroModelo> listaDocumentos = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            /**set value to list one by one**/
            while (cursor.moveToNext()) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                LibroModelo modelo = new LibroModelo();
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
        List<SenalModelo> aux = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            /**set value to list one by one**/
            while (cursor.moveToNext()) {
                SenalModelo modelo = new SenalModelo();
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

    public List<SenalModelo> cargarImagenesSenal(int id) {
        Cursor cursor = DB.getInstance(this).cargarImagenesSenales(id);
        List<SenalModelo> listaTipoSenal = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            /**set value to list one by one**/
            while (cursor.moveToNext()) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                String aux;
                SenalModelo modelo = new SenalModelo();
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
        List<ArticuloModelo> listaArticulos = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            /**set value to list one by one**/
            while (cursor.moveToNext()) {
                ArticuloModelo articuloModelo = new ArticuloModelo();
                articuloModelo.setNombre(cursor.getInt(1));
                articuloModelo.setDescripcion(cursor.getString(2));
                listaArticulos.add(articuloModelo);
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
        List<InfraccionModelo> listaInfraccion = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            /**set value to list one by one**/
            while (cursor.moveToNext()) {
                InfraccionModelo infraccionModelo = new InfraccionModelo();
                infraccionModelo.setTipo(cursor.getString(1));
                infraccionModelo.setListaArticulos(cargarListaInfracciones(cursor.getInt(0)));
                listaInfraccion.add(infraccionModelo);
            }
        }
        LISTA_INFRACCION = listaInfraccion;
    }

    private List<ArticuloModelo> cargarListaInfracciones(int id) {
        Cursor cursor = DB.getInstance(this).cargarArticulosInfracciones(id);
        List<ArticuloModelo> lista = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {

            /**set value to list one by one**/
            while (cursor.moveToNext()) {
                ArticuloModelo articuloModelo = new ArticuloModelo();
                articuloModelo.setNombre(cursor.getInt(1));
                articuloModelo.setDescripcion(cursor.getString(2));

                lista.add(articuloModelo);

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
        List<ExamenModelo> aux = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                ExamenModelo modelo = new ExamenModelo();
                modelo.setId(cursor.getInt(0));
                modelo.setNombre(cursor.getString(1));
                modelo.setListaPreguntas(getListaPreguntas(cursor.getInt(0)));
                aux.add(modelo);
            }
        }
        LISTA_EXAMENES = aux;
    }

    private List<PreguntaModelo> getListaPreguntas(int id) {
        Cursor cursor = DB.getInstance(this).getById("PREGUNTAS", "examen", id);
        List<PreguntaModelo> aux = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                PreguntaModelo modelo = new PreguntaModelo();
                modelo.setId(cursor.getInt(0));
                modelo.setDescripcion(cursor.getString(1));
                modelo.setImagen(BitmapFactory.decodeByteArray(cursor.getBlob(2), 0, cursor.getBlob(2).length));
                modelo.setListaRespuestas(getListaRespuestas(cursor.getInt(0)));
                aux.add(modelo);
            }
        }
        return aux;
    }

    private List<RespuestaModelo> getListaRespuestas(int id) {
        Cursor cursor = DB.getInstance(this).getById("RESPUESTAS", "pregunta", id);
        List<RespuestaModelo> aux = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                RespuestaModelo modelo = new RespuestaModelo();
                modelo.setId(cursor.getInt(0));
                modelo.setDescripcion(cursor.getString(2));
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

    private void getResultados() {
        Cursor cursor = DB.getInstance(this).getAll("RESULTADO");
        List<ResultadoModelo> aux = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                ResultadoModelo modelo = new ResultadoModelo();
                modelo.setId(cursor.getInt(0));
                modelo.setFecha(cursor.getString(1));
                modelo.setResultado(cursor.getInt(2));
                aux.add(modelo);
            }
        }
        LISTA_RESULTADOS = aux;
    }
}
