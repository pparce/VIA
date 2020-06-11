package cu.uno.via.adaptadores;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cu.uno.via.database.modelos.ModeloNotas;
import cu.uno.via.R;

/**
 * Created by Suleiman on 26-07-2015.
 */
public class NotasAdapter extends RecyclerView.Adapter<NotasAdapter.MasonryView> implements View.OnClickListener, View.OnLongClickListener
{

    private Context context;
    List<ModeloNotas> list;
    private View.OnClickListener listener;
    private View.OnLongClickListener onLong;

    View layoutView;

    public NotasAdapter(Context context, List<ModeloNotas> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MasonryView onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_notas, parent, false);
        MasonryView masonryView = new MasonryView(layoutView);
        return masonryView;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(MasonryView holder, int position) {

        holder.descripcion.setText(list.get(position).getDescripcion());

        layoutView.setOnClickListener(this);
        layoutView.setOnLongClickListener(this);
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

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.onLong = onLongClickListener;
    }

    @Override
    public boolean onLongClick(View view) {
        if (onLong != null)
            onLong.onLongClick(view);
        return true;
    }


    class MasonryView extends RecyclerView.ViewHolder {
        TextView descripcion, titulo;
        ImageView portada;

        public MasonryView(View itemView) {
            super(itemView);

            descripcion = (TextView) itemView.findViewById(R.id.descripcion);


        }
    }

}
