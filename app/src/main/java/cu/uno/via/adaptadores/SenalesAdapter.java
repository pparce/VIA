package cu.uno.via.adaptadores;

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

import java.util.List;

import cu.uno.via.database.modelos.ModeloSenal;
import cu.uno.via.R;
import cu.uno.via.utiles.App;

/**
 * Created by Suleiman on 26-07-2015.
 */
public class SenalesAdapter extends RecyclerView.Adapter<SenalesAdapter.MasonryView> implements View.OnClickListener {

    private Context context;
    List<ModeloSenal> list;
    private View.OnClickListener listener;
    AssetManager assetManager;
    Bitmap bitmap;
    View layoutView;

    public SenalesAdapter(Context context) {
        this.context = context;
        this.list = App.LISTA_SENALES;
    }

    @Override
    public MasonryView onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_senales, parent, false);
        MasonryView masonryView = new MasonryView(layoutView);
        return masonryView;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(MasonryView holder, int position) {

        holder.nombre.setText(list.get(position).getTipo());

        Bitmap bitmap = list.get(position).getCaratula();
        holder.imageView.setImageBitmap(bitmap);
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
        ImageView imageView;

        public MasonryView(View itemView) {
            super(itemView);

            nombre = (TextView) itemView.findViewById(R.id.titulo);
            descripcion = (TextView) itemView.findViewById(R.id.descripcion);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);

        }
    }
}
