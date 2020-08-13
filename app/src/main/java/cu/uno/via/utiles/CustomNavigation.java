package cu.uno.via.utiles;

import android.annotation.SuppressLint;
import android.content.Context;

import com.google.android.material.internal.NavigationMenuPresenter;
import com.google.android.material.navigation.NavigationView;

public class CustomNavigation extends NavigationView {
    @SuppressLint("RestrictedApi")
    private final NavigationMenuPresenter mPresenter = new NavigationMenuPresenter();
    public CustomNavigation(Context context) {
        super(context);
    }


}
