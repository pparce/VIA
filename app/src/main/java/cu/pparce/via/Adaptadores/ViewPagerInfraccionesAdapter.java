package cu.pparce.via.Adaptadores;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import cu.pparce.via.Fragmentos.FragmentoArticulosInfracciones;
import cu.pparce.via.Fragmentos.FragmentoBuscarArticulos;
import cu.pparce.via.Fragmentos.FragmentoBuscarSenales;


public class ViewPagerInfraccionesAdapter extends FragmentStatePagerAdapter {

    int tabCount;
    Context context;

    public ViewPagerInfraccionesAdapter(Context context, FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = new FragmentoArticulosInfracciones();
        Bundle bundle = new Bundle();
        bundle.putInt("posicion", position);
        fragment.setArguments(bundle);

        return fragment;

    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

