package cu.pparce.via.Adaptadores;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cu.pparce.via.Aplicacion;
import cu.pparce.via.DataBase.ModeloArticulo;
import cu.pparce.via.DataBase.ModeloLibro;
import cu.pparce.via.DataBase.ModeloSenal;
import cu.pparce.via.DataBase.ModeloTipoSenal;
import cu.pparce.via.R;

/**
 * Created by Suleiman on 26-07-2015.
 */
public class BuscarSenalesAdapter extends RecyclerView.Adapter<BuscarSenalesAdapter.MasonryView> implements View.OnClickListener {

    private Context context;
    List<ModeloSenal> list;
    private View.OnClickListener listener;
    AssetManager assetManager;
    Bitmap bitmap;
    View layoutView;

    public BuscarSenalesAdapter(Context context, List<ModeloSenal> lista) {
        this.context = context;

        this.list = lista;
    }

    public void setFilter(List<ModeloSenal> list) {
        this.list = new ArrayList<>();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public List<ModeloSenal> getLista(){
        return this.list;
    }

    @Override
    public MasonryView onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_buscar_senales, parent, false);
        MasonryView masonryView = new MasonryView(layoutView);
        return masonryView;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(MasonryView holder, int position) {

        holder.nombre.setText(list.get(position).getTipo());
        holder.portada.setImageBitmap(list.get(position).getCaratula());
        holder.descripcion.setText(list.get(position).getDescripcion());
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

    class MasonryView extends RecyclerView.ViewHolder {
        TextView nombre, descripcion;
        ImageView portada;

        public MasonryView(View itemView) {
            super(itemView);

            nombre = (TextView) itemView.findViewById(R.id.titulo);
            descripcion = (TextView) itemView.findViewById(R.id.descripcion);
            portada = (ImageView) itemView.findViewById(R.id.imageView);

        }
    }
}
