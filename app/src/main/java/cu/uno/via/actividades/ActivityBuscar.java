package cu.uno.via.actividades;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.MenuItem;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import cu.uno.via.adaptadores.ViewPagerBuscarAdapter;
import cu.uno.via.database.modelos.ModeloArticulo;
import cu.uno.via.R;
import cu.uno.via.utiles.App;
import cu.uno.via.utiles.CallBacks.CallBackBuscar;


public class ActivityBuscar extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public static SearchView searchView;
    TabLayout tab;
    ViewPager pager;
    ViewPagerBuscarAdapter pagerAdaptor;

    public static String marcado = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Inicializar();
    }


    @SuppressLint("WrongConstant")
    private void Inicializar() {
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
        searchView.requestFocus();

        //TABLAYOUT Y VIEW PAGER
        tab = findViewById(R.id.tab);
        tab.addTab(tab.newTab().setText("Artículos"));
        tab.addTab(tab.newTab().setText("Señales"));

        pager = findViewById(R.id.pager);
        pagerAdaptor = new ViewPagerBuscarAdapter(ActivityBuscar.this, getSupportFragmentManager(), tab.getTabCount());
        pager.setAdapter(pagerAdaptor);
        pager.setOffscreenPageLimit(pagerAdaptor.getCount() > 1 ? pagerAdaptor.getCount() - 1 : 1);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));

        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {


        App.callbackFragmentBuscarSenales.setBusquedaSenales(newText);
        App.callbackFragmentBuscar.setBusquedaArticulos(newText);



        return true;
    }

    private List<ModeloArticulo> filter(List<ModeloArticulo> articulos, String texto){

        marcado = texto;
        List<ModeloArticulo> list = new ArrayList<>();
        try {
            texto = texto.toLowerCase().trim();
            for (ModeloArticulo articulo : articulos){
                String descripcion = articulo.getDescripcion().toLowerCase();
                String nombre = "Articulo ".toLowerCase() + articulo.getNombre();
                if (descripcion.contains(texto)) {
                    list.add(articulo);
                }
                if (nombre.contains(texto) && texto.length() != 0) {
                    list.add(articulo);
                }
            }

        } catch (Exception e){


        }


        return list;
    }



    public class BuscarAsync extends AsyncTask<Void, Void, List<ModeloArticulo>> {

        String texto;
        List<ModeloArticulo> listaArticulos;
        CallBackBuscar callBackBuscar;

        public BuscarAsync(CallBackBuscar callBackBuscar, String texto, List<ModeloArticulo> listaArticulos) {
            this.callBackBuscar = callBackBuscar;
            this.texto = texto;
            this.listaArticulos = listaArticulos;
            execute();
        }

        @Override
        protected List<ModeloArticulo> doInBackground(Void... voids) {

            List<ModeloArticulo> listafiltrada = filter(listaArticulos, texto);
            return listafiltrada;
        }

        @Override
        protected void onPostExecute(List<ModeloArticulo> modeloArticulos) {
            callBackBuscar.pasarAdaptador(modeloArticulos);
        }
    }
}
