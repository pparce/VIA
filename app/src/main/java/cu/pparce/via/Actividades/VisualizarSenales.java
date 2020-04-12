package cu.pparce.via.Actividades;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;


import cu.pparce.via.Adaptadores.ViewPagerSenalesAdapter;
import cu.pparce.via.Aplicacion;
import cu.pparce.via.R;
import cu.pparce.via.Utiles.OnScrollCallback;


public class VisualizarSenales extends AppCompatActivity implements OnScrollCallback {

    public static int posicion;

    TabLayout tab;
    ViewPager pager;
    ViewPagerSenalesAdapter pagerAdaptor;
    AppBarLayout appBarLayout;
    public static OnScrollCallback onScrollCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_senales);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        posicion = bundle.getInt("posicion");
        setTitle(Aplicacion.LISTA_SENALES.get(posicion).getTipo());

        Inicializar();
        onScrollCallback = this;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void Inicializar(){

        //TABLAYOUT Y VIEW PAGER
        tab = (TabLayout) findViewById(R.id.tab);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);


        for (int i = 0; i < Aplicacion.LISTA_SENALES.get(posicion).getListaTipoSenales().size(); i++) {
            tab.addTab(tab.newTab().setText(Aplicacion.LISTA_SENALES.get(posicion).getListaTipoSenales().get(i).getTipo()));
        }

        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdaptor = new ViewPagerSenalesAdapter(VisualizarSenales.this, getSupportFragmentManager(), tab.getTabCount());
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

        if (id == android.R.id.home) {
            onBackPressed();
        }


        return super.onOptionsItemSelected(item);


    }

    @Override
    public void onScroll(boolean scroll) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (scroll) {

                appBarLayout.setElevation(0f);

            } else {
                appBarLayout.setElevation(10f);
            }
        }
    }
}
