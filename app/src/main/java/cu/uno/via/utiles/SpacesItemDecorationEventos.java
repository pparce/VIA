package cu.uno.via.utiles;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Suleiman on 26-07-2015.
 */
public class SpacesItemDecorationEventos extends RecyclerView.ItemDecoration {
    private final int leftRight;
    private final int topBottom;

    public SpacesItemDecorationEventos(int leftRight, int topBottom) {
        this.leftRight = leftRight;
        this.topBottom = topBottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = topBottom;
        outRect.bottom = topBottom;
        outRect.left = leftRight;
        outRect.right = leftRight;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildAdapterPosition(view) == 0)
            outRect.top = topBottom;
    }
}