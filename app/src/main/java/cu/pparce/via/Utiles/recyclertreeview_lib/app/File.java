package cu.pparce.via.Utiles.recyclertreeview_lib.app;


import cu.pparce.via.R;
import cu.pparce.via.Utiles.recyclertreeview_lib.LayoutItemType;

/**
 * Created by tlh on 2016/10/1 :)
 */

public class File implements LayoutItemType {
    public String fileName;

    public File(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_file;
    }
}
