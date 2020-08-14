package cu.uno.via.adaptadores;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import cu.uno.via.R;
import cu.uno.via.database.modelos.InformacionModelo;

/**
 * Created by Suleiman on 26-07-2015.
 */
public class InformacionUtilAdapter extends RecyclerView.Adapter<InformacionUtilAdapter.MasonryView> implements View.OnClickListener {

    private Context context;
    List<InformacionModelo> list;
    private View.OnClickListener listener;
    AssetManager assetManager;
    Bitmap bitmap;
    View layoutView;

    public InformacionUtilAdapter(Context context, List<InformacionModelo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MasonryView onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_informacion, parent, false);
        MasonryView masonryView = new MasonryView(layoutView);
        return masonryView;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(MasonryView holder, int position) {

        holder.titulo.setText((list.get(position).getNombre()));
        holder.descripcion.setText(list.get(position).getDescripcion());
        holder.fondo.setImageResource(list.get(position).getFondo());
        layoutView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null)
            listener.onClick(view);
    }

    public void setFilter(List<InformacionModelo> list) {
        this.list = new ArrayList<>();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public List<InformacionModelo> getLista(){
        return this.list;
    }

    class MasonryView extends RecyclerView.ViewHolder {
        TextView descripcion, titulo;
        ImageView fondo;

        public MasonryView(View itemView) {
            super(itemView);

            descripcion = itemView.findViewById(R.id.descripcion);
            titulo = itemView.findViewById(R.id.titulo);
            fondo = itemView.findViewById(R.id.fondo);
        }
    }

    private SpannableString marcarBusqueda(String nombre, String busqueda){
        String nombreSinCambios = nombre;
        nombre = Normalizer.normalize(nombre, Normalizer.Form.NFD);
        busqueda = Normalizer.normalize(busqueda, Normalizer.Form.NFD);

        nombre = nombre.replaceAll("[^\\p{ASCII}]", "");
        busqueda = busqueda.replaceAll("[^\\p{ASCII}]", "");

        nombre = nombre.toLowerCase();
        busqueda = busqueda.toLowerCase();


        int pos1 = nombre.indexOf(busqueda);
        int pos2 = pos1 + busqueda.length();

        SpannableString span = new SpannableString(nombreSinCambios);
        span.setSpan(new ForegroundColorSpan(Color.BLACK),pos1,pos2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        span.setSpan(new StyleSpan(Typeface.BOLD),pos1, pos2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        //span.setSpan(new AbsoluteSizeSpan(20,true),pos1, pos2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        return span;
    }
}
