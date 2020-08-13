package cu.uno.via.actividades;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.google.android.material.appbar.AppBarLayout;

import cu.uno.via.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PdfViewActivity extends AppCompatActivity {
    String titulo = "";
    int page = 0;
    Toolbar toolbar;
    AppBarLayout appBarLayout;
    boolean flag = true;
    Runnable runnable;
    Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfview);
        toolbar = findViewById(R.id.toolbar);
        appBarLayout = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        titulo = getIntent().getStringExtra("LIBRO");
        SharedPreferences preferences = getSharedPreferences("posiciones_libros", Context.MODE_PRIVATE);
        page = preferences.getInt(titulo, 0);
        getSupportActionBar().setTitle(titulo.substring(0, (titulo.length() - 4)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        ESTE CODIGO ES PARA CERRAR AUTOMATICAMENTE EL MENU
        runnable = new Runnable() {
            @Override
            public void run() {
                hideMenu();
            }
        };
        handler = new Handler();

        final PDFView pdfView = findViewById(R.id.pdfView);
        pdfView
                .fromAsset(titulo)
                .defaultPage(page)
                .onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {
                        SharedPreferences preferences = getSharedPreferences("posiciones_libros", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt(titulo, page);
                        editor.commit();
                    }
                })
                .onPageScroll(new OnPageScrollListener() {
                    @Override
                    public void onPageScrolled(int page, float positionOffset) {

                    }
                })
                .onTap(new OnTapListener() {
                    @Override
                    public boolean onTap(MotionEvent e) {

                        if (flag) {
                            hideMenu();
                        } else {
                            showMenu();
                            handler.removeCallbacks(runnable);
                            handler.postDelayed(runnable, 3000);
                        }
                        return true;
                    }
                })
                .load();



        new Handler().postDelayed(runnable, 3000);
    }

    private void showMenu() {
        appBarLayout.animate().translationY(0).setDuration(300).start();
//        menu.animate().translationY(0).setDuration(300).start();
        flag = true;
    }

    private void hideMenu() {
        appBarLayout.animate().translationY(-toolbar.getHeight() + (-150)).setDuration(300).start();
//        menu.animate().translationY(menu.getHeight()).setDuration(300).start();
        flag = false;
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pdfview, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}