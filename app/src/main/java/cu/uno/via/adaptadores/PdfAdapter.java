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

import cu.uno.via.R;

/**
 * Created by Suleiman on 26-07-2015.
 */
public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.MasonryView> implements View.OnClickListener {

    private Context context;
    List<Bitmap> list;
    private View.OnClickListener listener;
    AssetManager assetManager;
    Bitmap bitmap;
    View layoutView;
    int cant;

    public PdfAdapter(Context context, List<Bitmap> lista, int cant) {
        this.context = context;
        this.list = lista;
        this.cant = cant;
    }

    @Override
    public MasonryView onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_pdf, parent, false);
        MasonryView masonryView = new MasonryView(layoutView);
        return masonryView;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(MasonryView holder, int position) {

        //holder.nombre.setText(list.get(position).getNombre());
        holder.portada.setImageBitmap(list.get(position));
        layoutView.setOnClickListener(this);
        layoutView.setTag(position);
    }



    @Override
    public int getItemCount() {
        return cant;
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
