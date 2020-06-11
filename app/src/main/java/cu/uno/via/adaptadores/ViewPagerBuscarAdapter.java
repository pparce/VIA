package cu.uno.via.adaptadores;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import cu.uno.via.fragmentos.FragmentoBuscarArticulos;
import cu.uno.via.fragmentos.FragmentoBuscarSenales;

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

