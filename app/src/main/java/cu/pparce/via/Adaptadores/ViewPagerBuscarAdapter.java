package cu.pparce.via.Adaptadores;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import cu.pparce.via.Fragmentos.Fragmento;
import cu.pparce.via.Fragmentos.FragmentoBuscarArticulos;
import cu.pparce.via.Fragmentos.FragmentoBuscarSenales;


public class ViewPagerBuscarAdapter extends FragmentStatePagerAdapter {

    int tabCount;
    Context context;

    public ViewPagerBuscarAdapter(Context context, FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();

        switch (position) {
            case 0:
                fragment = new FragmentoBuscarArticulos();
                break;
            case 1:
                fragment = new FragmentoBuscarSenales();
                break;

        }

        return fragment;

    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

