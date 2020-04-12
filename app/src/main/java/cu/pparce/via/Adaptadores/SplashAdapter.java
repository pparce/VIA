package cu.pparce.via.Adaptadores;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

import cu.pparce.via.R;


public class SplashAdapter extends PagerAdapter implements View.OnClickListener{

    public List<String> list;
    public TextView textoInicial, ocupacion;
    private Context context;
    View view;
    View.OnClickListener onClickListener;

    public SplashAdapter(Context context, List lista){
        this.list=lista;
        this.context=context;
    }

    @Override
    public int getCount() {
    return list.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView(((RelativeLayout)object));

    }

    @Override
    public void finishUpdate(ViewGroup container) {

    }

    @Override
    public Object instantiateItem(final View container, int position) {
        LayoutInflater inflater=(LayoutInflater)container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (position == list.size()-1){

            view = inflater.inflate(R.layout.splash_viewpager_ventana_final,null);

            Button empezar = (Button) view.findViewById(R.id.comenzar_splash);
            empezar.setVisibility(View.VISIBLE);
            empezar.setOnClickListener(this);
        } else {
            view = inflater.inflate(R.layout.splash_viewpager_ventana,null);


            textoInicial =(TextView)view.findViewById(R.id.viewpagerTexto);
            textoInicial.setText(list.get(position));

        }
        ((ViewPager)container).addView(view,0);

        return view;
    }


    @Override
    public Parcelable saveState() {
        return super.saveState();
    }

    @Override
    public void startUpdate(View container) {

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==((RelativeLayout)object);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.onClickListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (onClickListener != null)
            onClickListener.onClick(view);
    }
}
