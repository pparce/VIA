package cu.uno.via.fragmentos;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

import cu.uno.via.adaptadores.ViewPagerInfraccionesAdapter;
import cu.uno.via.database.modelos.ModeloArticulo;
import cu.uno.via.R;
import cu.uno.via.utiles.App;
import cu.uno.via.utiles.CallBacks.CallBackFragmentInfraccion;


public class FragmentInfracciones extends Fragment implements CallBackFragmentInfraccion, SearchView.OnQueryTextListener  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    View view;
    Context context;

    TabLayout tab;
    public static ViewPager pager;
    public static int fragment = 0;
    public static int posicion = 0;
    ViewPagerInfraccionesAdapter pagerAdaptor;
    LinearLayout contenedorBuscar;
    SearchView searchView;



    public FragmentInfracciones() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_infracciones, container, false);

        initView();
        return view;
    }
    private void initView() {

        App.callBackFragmentInfraccion = this;
//        contenedorBuscar = (LinearLayout) view.findViewById(R.id.contenedor_buscador);

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
