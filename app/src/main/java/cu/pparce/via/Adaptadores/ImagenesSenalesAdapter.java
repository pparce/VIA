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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cu.pparce.via.Actividades.VisualizarSenales;
import cu.pparce.via.Aplicacion;
import cu.pparce.via.DataBase.ModeloSenal;
import cu.pparce.via.R;

/**
 * Created by Suleiman on 26-07-2015.
 */
public class ImagenesSenalesAdapter extends RecyclerView.Adapter<ImagenesSenalesAdapter.MasonryView> implements View.OnClickListener, View.OnLongClickListener {

    private Context context;
    List<ModeloSenal> list;
    List<Integer> imagenes;
    private View.OnClickListener onClickListener;
    private View.OnLongClickListener onLongClickListener;

    AssetManager assetManager;
    Bitmap bitmap;
    View layoutView;
    int pos;

    public ImagenesSenalesAdapter(Context context, int pos) {
        this.context = context;
        this.list = Aplicacion.LISTA_SENALES.get(VisualizarSenales.posicion).getListaTipoSenales().get(pos).getListaSenal();
        this.pos = pos;
        //this.imagenes = Imagenes();
    }

    @Override
    public MasonryView onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_imagenes, parent, false);
        MasonryView masonryView = new MasonryView(layoutView);

        LinearLayout linearLayout = (LinearLayout) layoutView;
        linearLayout.getChildAt(0).setOnClickListener(this);
        return masonryView;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(MasonryView holder, int position) {

        Bitmap bitmap = list.get(position).getCaratula();

        holder.imageView.setImageBitmap(bitmap);
        holder.titulo.setText(list.get(position).getTipo());

        //holder.imageView.setImageResource(imagenes.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.onClickListener = listener;
    }

    public void setOnLongClickListener(View.OnLongClickListener listener) {
        this.onLongClickListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (onClickListener != null)
            onClickListener.onClick(view);
    }

    @Override
    public boolean onLongClick(View view) {
        if (onLongClickListener != null)
            onLongClickListener.onLongClick(view);
        return true;
    }

    class MasonryView extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titulo;

        public MasonryView(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            titulo = (TextView) itemView.findViewById(R.id.titulo);

        }
    }
}
