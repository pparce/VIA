package cu.pparce.via.Utiles;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by Administrador on 10/04/2019.
 */
public class VerticalPageTrasnformer implements ViewPager.PageTransformer  {
    @Override
    public void transformPage(View page, float position) {
        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            page.setAlpha(0);

        } else if (position <= 1) { // [-1,1]
            page.setAlpha(1);

            // Counteract the default slide transition
            page.setTranslationX(page.getWidth() * -position);

            //set Y position to swipe in from top
            float yPosition = position * page.getHeight();
            page.setTranslationY(yPosition);

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            page.setAlpha(0);
        }
    }
}
