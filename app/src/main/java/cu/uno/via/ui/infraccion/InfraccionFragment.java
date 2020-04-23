package cu.uno.via.ui.infraccion;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import cu.uno.via.Adaptadores.ViewPagerInfraccionesAdapter;
import cu.uno.via.DataBase.ModeloArticulo;
import cu.uno.via.R;
import cu.uno.via.utiles.App;
import cu.uno.via.utiles.CallBacks.CallBackFragmentInfraccion;

public class InfraccionFragment extends Fragment implements CallBackFragmentInfraccion, SearchView.OnQueryTextListener{

    private InfraccionViewModel galleryViewModel;

    View view;
    Context context;
    TabLayout tab;
    public static ViewPager pager;
    public static int fragment = 0;
    public static int posicion = 0;
    ViewPagerInfraccionesAdapter pagerAdaptor;
    LinearLayout contenedorBuscar;
    SearchView searchView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(InfraccionViewModel.class);
        view = inflater.inflate(R.layout.fragment_infraccion, container, false);
        initView();
        return view;
    }

    private void initView() {

        App.callBackFragmentInfraccion = this;
        contenedorBuscar = (LinearLayout) view.findViewById(R.id.contenedor_buscador);

        //TABLAYOUT Y VIEW PAGER
        tab = (TabLayout) view.findViewById(R.id.tab);

        for (int i = 0; i < App.LISTA_INFRACCION.size(); i++) {
            tab.addTab(tab.newTab().setText(App.LISTA_INFRACCION.get(i).getTipo()));
        }


        pager = (ViewPager) view.findViewById(R.id.pager);
        pagerAdaptor = new ViewPagerInfraccionesAdapter(context, getActivity().getSupportFragmentManager(), tab.getTabCount());
        pager.setAdapter(pagerAdaptor);
        pager.setOffscreenPageLimit(pagerAdaptor.getCount() > 1 ? pagerAdaptor.getCount() - 1 : 1);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));


        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
                posicion = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        searchView = (SearchView) view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
        searchView.requestFocus();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public void showBuscador() {
        if (contenedorBuscar.getVisibility() == View.GONE) {
            mostrarBuscador();
        } else {
            ocultarBuscador();
        }
    }

    private void mostrarBuscador(){
        contenedorBuscar.setVisibility(View.VISIBLE);
        int height = (int) getResources().getDimension(R.dimen.cajon_buscar) + 20;

        final ViewGroup.LayoutParams params = contenedorBuscar.getLayoutParams();
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(0, height);
        valueAnimator.setDuration(300);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator animator) {

                params.height = (int) animator.getAnimatedValue();

                contenedorBuscar.setLayoutParams(params);

            }
        });

        valueAnimator.start();
    }

    private void ocultarBuscador(){
        final ViewGroup.LayoutParams params = contenedorBuscar.getLayoutParams();
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(contenedorBuscar.getLayoutParams().height, 0);
        valueAnimator.setDuration(300);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator animator) {

                params.height = (int) animator.getAnimatedValue();

                contenedorBuscar.setLayoutParams(params);

            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                contenedorBuscar.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        valueAnimator.start();
    }

    private List<ModeloArticulo> filter(List<ModeloArticulo> articulos, String texto){

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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {


        for (int i = 0; i < App.listaCallBack.size(); i++) {
            App.listaCallBack.get(i).setBusquedaArticulos(newText);
        }
        return true;
    }

}
