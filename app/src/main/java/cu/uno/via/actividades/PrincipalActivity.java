package cu.uno.via.actividades;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Timer;
import java.util.TimerTask;

import cu.uno.via.R;

public class PrincipalActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    AppBarLayout appBarLayout;
    DrawerLayout drawer;
    NavController navController;
    NavigationView navigationView;
    public static View focusedView;
    String title = "Inicio";
    int navId = R.id.nav_home;
    Menu menu;
    int atras = 0;
    boolean clickMenu = true;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("navId", navId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        if (savedInstanceState != null) {
            navId = savedInstanceState.getInt("navId");
        }
        initView();
        setNavigation(navId);
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        appBarLayout = findViewById(R.id.app_bar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.menu_home));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                closeKeyBoard();
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                if (clickMenu) {
                    setNavigation(navId);
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setNavigation(final int id) {
        if (id == R.id.nav_info) {
            startActivity(new Intent(PrincipalActivity.this, InfoActivity.class));
        } else if (id == R.id.nav_setting) {
            startActivity(new Intent(PrincipalActivity.this, ConfigActivity.class));
        } else {
            NavOptions navOptions = new NavOptions.Builder()
                    .setEnterAnim(R.animator.fade_in)
                    .setExitAnim(R.animator.fade_out)
                    .build();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (navId == R.id.nav_infraccion) {
                    appBarLayout.setElevation(0);
                } else {
                    appBarLayout.setElevation(10);
                }
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getSupportActionBar().setTitle(navigationView.getMenu().findItem(id).getTitle());
                }
            }, 200);
            navController.navigate(id, null, navOptions);
            navId = id;
        }
        clickMenu = false;
    }

    private void closeKeyBoard() {
        if (focusedView != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_buscar) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            startActivity(new Intent(this, BuscarActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (navId != menuItem.getItemId()) {
            clickMenu = true;
        } else if (menuItem.getItemId() == R.id.nav_setting || menuItem.getItemId() == R.id.nav_info) {
            clickMenu = true;
        }
        navId = menuItem.getItemId();
        title = menuItem.getTitle().toString();


        drawer.closeDrawers();
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (navId != R.id.nav_home) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    navId = R.id.nav_home;
                    setNavigation(R.id.nav_home);
                    navigationView.setCheckedItem(R.id.nav_home);
                } else {
                    if (atras == 1) {
                        finish();
                    }
                    if (atras == 0) {
                        atras = 1;
                        Toast.makeText(PrincipalActivity.this, "Presione una vez mas para cerrar ", Toast.LENGTH_SHORT).show();
                        TimerTask timerTask = new TimerTask() {
                            @Override
                            public void run() {
                                atras = 0;
                            }
                        };
                        Timer timer = new Timer();
                        timer.schedule(timerTask, 2000);
                    }
                }
            } else {
                if (atras == 1) {
                    finish();
                }
                if (atras == 0) {
                    atras = 1;
                    Toast.makeText(PrincipalActivity.this, "Presione una vez mas para cerrar ", Toast.LENGTH_SHORT).show();
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            atras = 0;
                        }
                    };
                    Timer timer = new Timer();
                    timer.schedule(timerTask, 2000);
                }
            }
        }
    }
}
