package cu.uno.via.utiles.recyclertreeview_lib.app;

import android.view.View;
import android.widget.TextView;

import cu.uno.via.R;
import cu.uno.via.App;
import cu.uno.via.utiles.recyclertreeview_lib.TreeNode;
import cu.uno.via.utiles.recyclertreeview_lib.TreeViewBinder;


/**
 * Created by tlh on 2016/10/1 :)
 */

public class FileNodeBinder extends TreeViewBinder<FileNodeBinder.ViewHolder> {
    @Override
    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindView(ViewHolder holder, final int position, final TreeNode node) {
        final File fileNode = (File) node.getContent();
        holder.tvName.setText(fileNode.fileName);
        View view = (View) holder.tvName.getParent();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.e("..........", "" + position);
                App.callbackArbolArticulo.onClick(fileNode.fileName, position);
            }
        });
    }


    @Override
    public int getLayoutId() {
        return R.layout.item_file;
    }

    public class ViewHolder extends TreeViewBinder.ViewHolder {
        public TextView tvName;

        public ViewHolder(View rootView) {
            super(rootView);
            this.tvName = (TextView) rootView.findViewById(R.id.tv_name);
        }

    }
}
