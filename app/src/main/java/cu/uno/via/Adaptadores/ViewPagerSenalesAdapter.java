package cu.uno.via.Adaptadores;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import cu.uno.via.fragmentos.Fragmento;


public class ViewPagerSenalesAdapter extends FragmentStatePagerAdapter {

    int tabCount;
    Context context;

    public ViewPagerSenalesAdapter(Context context, FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        Fragmento fragmento = new Fragmento();
        Bundle bundle = new Bundle();
        bundle.putInt("posicion", position);
        fragmento.setArguments(bundle);

        return fragmento;

    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

