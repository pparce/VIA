package cu.uno.via.actividades;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cu.uno.via.R;
import cu.uno.via.App;
import cu.uno.via.utiles.CallBacks.CallbackArbolArticulos;
import cu.uno.via.utiles.PdfViewerView;
import cu.uno.via.utiles.recyclertreeview_lib.TreeNode;
import cu.uno.via.utiles.recyclertreeview_lib.TreeViewAdapter;
import cu.uno.via.utiles.recyclertreeview_lib.app.Dir;
import cu.uno.via.utiles.recyclertreeview_lib.app.DirectoryNodeBinder;
import cu.uno.via.utiles.recyclertreeview_lib.app.File;
import cu.uno.via.utiles.recyclertreeview_lib.app.FileNodeBinder;

public class ArbolActivity extends AppCompatActivity implements CallbackArbolArticulos {
    PdfViewerView pdfViewerView;
    private PdfRenderer pdfRender;
    int posicicion = 0;
    LinearLayout menu;
    Toolbar toolbarmenu;
    SeekBar seekBarPagina;
    TextView indicadorPagina;
    boolean flag = false;
    TreeNode treeNodeBack;
    RecyclerView.ViewHolder holderBack;

    private RecyclerView rv;
    private TreeViewAdapter adapter;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        posicicion = 0;
        initTree();
    }

    private void initTree() {
        setContentView(R.layout.activity_visualizar_codigo_vial);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Código Vial");
        rv = (RecyclerView) findViewById(R.id.rv);

        initData();
    }

    private void initData() {
        List<TreeNode> nodes = new ArrayList<>();
        TreeNode<Dir> libro1 = new TreeNode<>(new Dir("Libro I. PARTE GENERAL"));
        TreeNode<Dir> libro2 = new TreeNode<>(new Dir("Libro II. DE LA VIALIDAD"));
        TreeNode<Dir> libro3 = new TreeNode<>(new Dir("Libro III. DEL USO DE LAS VIAS"));
        TreeNode<Dir> libro4 = new TreeNode<>(new Dir("Libro IV. DEL CONTROL TÉCNICO Y REGISTRO DE VEHÍCULOS"));
        TreeNode<Dir> libro5 = new TreeNode<>(new Dir("Libro V. DE LA EDUCACIÓ VIAL Y LA LICENCIA DE CONDUCCIÓN"));
        TreeNode<Dir> libro6 = new TreeNode<>(new Dir("Libro VI. DE LA RESPONSABILIDAD ADMINISTRATIVA POR INFRACCIONES  DE LAS REGULACIONES  DEL TRÁNSITO DE VEHÍCULOS NO CONSTRUCTIVAS DE DELITO"));
        TreeNode<Dir> libro7 = new TreeNode<>(new Dir("Libro VII. DE LAS COMISIONES DE SEGURIDAD VIAL"));
        TreeNode<Dir> regulaciones = new TreeNode<>(new Dir("REGULACIONES COMPLEMENTARIAS"));
        TreeNode<File> especiales = new TreeNode<>(new File("DISPOSICIONES ESPECIALES"));
        final TreeNode<File> transitorias = new TreeNode<>(new File("DISPOSICIONES TRANSITORIAS"));
        TreeNode<File> finales = new TreeNode<>(new File("REGULACIONES FINALES"));

        nodes.add(libro1);
        nodes.add(libro2);
        nodes.add(libro3);
        nodes.add(libro4);
        nodes.add(libro5);
        nodes.add(libro6);
        nodes.add(libro7);
        nodes.add(regulaciones);
        nodes.add(especiales);
        nodes.add(transitorias);
        nodes.add(finales);


        libro1.addChild(new TreeNode<>(new Dir("CAPITULO I. DE SU DENOMINACION Y OBJETO"))
                .addChild(new TreeNode<>(new File("ARTICULO 1")))
                .addChild(new TreeNode<>(new File("ARTICULO 2")))
                .addChild(new TreeNode<>(new File("ARTICULO 3")))
                .addChild(new TreeNode<>(new File("ARTICULO 4"))));

        libro2.addChild(new TreeNode<>(new Dir("TITULO I. DE LA PLANIFICACIÓN VIAL"))
                .addChild(new TreeNode<>(new Dir("CAPITULO I. DEL ESQUEMA VIAL Y LA RED VIAL NACIONA"))
                        .addChild(new TreeNode<>(new File("ARTICULO 5")))
                        .addChild(new TreeNode<>(new File("ARTICULO 6")))
                        .addChild(new TreeNode<>(new File("ARTICULO 7")))));
        libro2.addChild(new TreeNode<>(new Dir("TITULO II. DE LA CONSTRUCCIÓN Y PROTECCIÓN DE LAS VIAS"))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO I. DE LAS VIAS"))
                        .addChild(new TreeNode<>(new File("ARTICULO 8")))
                        .addChild(new TreeNode<>(new File("ARTICULO 9")))
                        .addChild(new TreeNode<>(new File("ARTICULO 10")))
                        .addChild(new TreeNode<>(new File("ARTICULO 11")))
                        .addChild(new TreeNode<>(new File("ARTICULO 12")))
                        .addChild(new TreeNode<>(new File("ARTICULO 13")))
                        .addChild(new TreeNode<>(new File("ARTICULO 14")))
                        .addChild(new TreeNode<>(new File("ARTICULO 15"))))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO II. DEL PROYECTO, CONSTRUCCIÓN Y RECONSTRUCCIÓN VIAL"))
                        .addChild(new TreeNode<>(new File("ARTICULO 16")))
                        .addChild(new TreeNode<>(new File("ARTICULO 17")))
                        .addChild(new TreeNode<>(new File("ARTICULO 18")))
                        .addChild(new TreeNode<>(new File("ARTICULO 19")))
                        .addChild(new TreeNode<>(new File("ARTICULO 20")))
                        .addChild(new TreeNode<>(new File("ARTICULO 21")))
                        .addChild(new TreeNode<>(new File("ARTICULO 22")))
                        .addChild(new TreeNode<>(new File("ARTICULO 23")))
                        .addChild(new TreeNode<>(new File("ARTICULO 24"))))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO III. DE LA CONSERVACIÓN VIAL"))
                        .addChild(new TreeNode<>(new File("ARTICULO 25")))
                        .addChild(new TreeNode<>(new File("ARTICULO 26")))
                        .addChild(new TreeNode<>(new File("ARTICULO 27")))
                        .addChild(new TreeNode<>(new File("ARTICULO 28")))
                        .addChild(new TreeNode<>(new File("ARTICULO 29")))
                        .addChild(new TreeNode<>(new File("ARTICULO 30")))
                        .addChild(new TreeNode<>(new File("ARTICULO 31"))))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO IV. DE LAS AFECTACIONES DE LAS VIA"))
                        .addChild(new TreeNode<>(new File("ARTICULO 32")))
                        .addChild(new TreeNode<>(new File("ARTICULO 33")))
                        .addChild(new TreeNode<>(new File("ARTICULO 34")))
                        .addChild(new TreeNode<>(new File("ARTICULO 35")))
                        .addChild(new TreeNode<>(new File("ARTICULO 36")))
                        .addChild(new TreeNode<>(new File("ARTICULO 37")))
                        .addChild(new TreeNode<>(new File("ARTICULO 38")))
                        .addChild(new TreeNode<>(new File("ARTICULO 39")))
                        .addChild(new TreeNode<>(new File("ARTICULO 40")))
                        .addChild(new TreeNode<>(new File("ARTICULO 41")))
                        .addChild(new TreeNode<>(new File("ARTICULO 42"))))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO V. DE LA PROTECCIÓN Y LA DEFENSA DE LAS VIAS"))
                        .addChild(new TreeNode<>(new File("ARTICULO 43")))
                        .addChild(new TreeNode<>(new File("ARTICULO 44")))
                        .addChild(new TreeNode<>(new File("ARTICULO 45")))
                        .addChild(new TreeNode<>(new File("ARTICULO 46")))
                        .addChild(new TreeNode<>(new File("ARTICULO 47")))
                        .addChild(new TreeNode<>(new File("ARTICULO 48")))));
        libro2.addChild(new TreeNode<>(new Dir("TITULO III. DE LA INGENIERIA DEL TRÁNSITO"))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO I. DE SU CONTENIDO"))
                        .addChild(new TreeNode<>(new File("ARTICULO 49"))))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO II. DE LOS ESTUDIOS SISTEMÁTICOS Y DEL DISEÑO"))
                        .addChild(new TreeNode<>(new File("ARTICULO 50")))
                        .addChild(new TreeNode<>(new File("ARTICULO 51")))
                        .addChild(new TreeNode<>(new File("ARTICULO 52")))
                        .addChild(new TreeNode<>(new File("ARTICULO 53")))
                        .addChild(new TreeNode<>(new File("ARTICULO 54")))
                        .addChild(new TreeNode<>(new File("ARTICULO 55")))
                        .addChild(new TreeNode<>(new File("ARTICULO 56")))
                        .addChild(new TreeNode<>(new File("ARTICULO 57"))))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO III. DE LA SEÑALIZACIÓN"))
                        .addChild(new TreeNode<>(new File("ARTICULO 58")))
                        .addChild(new TreeNode<>(new File("ARTICULO 59")))));

        libro3.addChild(new TreeNode<>(new Dir("TITULO I. DE LA ORGANIZACIÓN DEL TRÁNSITO"))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO I. DE LA CIRCULACIÓN GENERAL"))
                        .addChild(new TreeNode<>(new File("ARTICULO 60")))
                        .addChild(new TreeNode<>(new File("ARTICULO 61")))
                        .addChild(new TreeNode<>(new File("ARTICULO 62")))
                        .addChild(new TreeNode<>(new File("ARTICULO 63")))
                        .addChild(new TreeNode<>(new File("ARTICULO 64")))
                        .addChild(new TreeNode<>(new File("ARTICULO 65")))
                        .addChild(new TreeNode<>(new File("ARTICULO 66")))
                        .addChild(new TreeNode<>(new File("ARTICULO 67")))
                        .addChild(new TreeNode<>(new File("ARTICULO 68")))
                        .addChild(new TreeNode<>(new File("ARTICULO 69")))
                        .addChild(new TreeNode<>(new File("ARTICULO 70")))
                        .addChild(new TreeNode<>(new File("ARTICULO 71")))
                        .addChild(new TreeNode<>(new File("ARTICULO 72")))
                        .addChild(new TreeNode<>(new File("ARTICULO 73")))
                        .addChild(new TreeNode<>(new File("ARTICULO 74")))
                        .addChild(new TreeNode<>(new File("ARTICULO 75")))
                        .addChild(new TreeNode<>(new File("ARTICULO 76")))
                        .addChild(new TreeNode<>(new File("ARTICULO 77")))
                        .addChild(new TreeNode<>(new File("ARTICULO 78")))
                        .addChild(new TreeNode<>(new File("ARTICULO 79")))
                        .addChild(new TreeNode<>(new File("ARTICULO 80")))
                        .addChild(new TreeNode<>(new File("ARTICULO 81")))
                        .addChild(new TreeNode<>(new File("ARTICULO 82")))
                        .addChild(new TreeNode<>(new File("ARTICULO 83")))
                        .addChild(new TreeNode<>(new File("ARTICULO 84")))
                        .addChild(new TreeNode<>(new File("ARTICULO 85")))
                        .addChild(new TreeNode<>(new File("ARTICULO 86")))
                        .addChild(new TreeNode<>(new File("ARTICULO 87")))
                        .addChild(new TreeNode<>(new File("ARTICULO 88")))
                        .addChild(new TreeNode<>(new File("ARTICULO 89")))
                        .addChild(new TreeNode<>(new File("ARTICULO 90")))
                        .addChild(new TreeNode<>(new File("ARTICULO 91")))
                        .addChild(new TreeNode<>(new File("ARTICULO 92")))
                        .addChild(new TreeNode<>(new File("ARTICULO 93")))
                        .addChild(new TreeNode<>(new File("ARTICULO 94")))
                        .addChild(new TreeNode<>(new File("ARTICULO 95")))
                        .addChild(new TreeNode<>(new File("ARTICULO 96")))
                        .addChild(new TreeNode<>(new File("ARTICULO 97")))
                        .addChild(new TreeNode<>(new File("ARTICULO 98")))
                        .addChild(new TreeNode<>(new File("ARTICULO 99")))
                        .addChild(new TreeNode<>(new File("ARTICULO 100")))
                        .addChild(new TreeNode<>(new File("ARTICULO 101")))
                        .addChild(new TreeNode<>(new File("ARTICULO 102")))
                        .addChild(new TreeNode<>(new File("ARTICULO 103")))
                        .addChild(new TreeNode<>(new File("ARTICULO 104")))
                        .addChild(new TreeNode<>(new File("ARTICULO 105")))
                        .addChild(new TreeNode<>(new File("ARTICULO 106")))
                        .addChild(new TreeNode<>(new File("ARTICULO 107")))
                        .addChild(new TreeNode<>(new File("ARTICULO 108")))
                        .addChild(new TreeNode<>(new File("ARTICULO 109")))
                        .addChild(new TreeNode<>(new File("ARTICULO 110")))
                        .addChild(new TreeNode<>(new File("ARTICULO 111")))
                        .addChild(new TreeNode<>(new File("ARTICULO 112")))
                        .addChild(new TreeNode<>(new File("ARTICULO 113")))
                        .addChild(new TreeNode<>(new File("ARTICULO 114")))
                        .addChild(new TreeNode<>(new File("ARTICULO 115")))
                        .addChild(new TreeNode<>(new File("ARTICULO 116")))
                        .addChild(new TreeNode<>(new File("ARTICULO 117")))
                        .addChild(new TreeNode<>(new File("ARTICULO 118")))
                        .addChild(new TreeNode<>(new File("ARTICULO 119")))
                        .addChild(new TreeNode<>(new File("ARTICULO 120")))
                        .addChild(new TreeNode<>(new File("ARTICULO 121")))
                        .addChild(new TreeNode<>(new File("ARTICULO 122")))
                        .addChild(new TreeNode<>(new File("ARTICULO 123")))
                        .addChild(new TreeNode<>(new File("ARTICULO 124")))
                        .addChild(new TreeNode<>(new File("ARTICULO 125"))))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO II. DE LOS LÍMITES DE VELOCIDAD"))
                        .addChild(new TreeNode<>(new File("ARTICULO 126")))
                        .addChild(new TreeNode<>(new File("ARTICULO 127")))
                        .addChild(new TreeNode<>(new File("ARTICULO 128")))
                        .addChild(new TreeNode<>(new File("ARTICULO 129"))))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO III. DE LOS VEHÍCULOS DE CARGA Y SU UTILIZACIÓN PARA LA TRANSPORTACIÓN MASIVA DE PERSONAS"))
                        .addChild(new TreeNode<>(new File("ARTICULO 130")))
                        .addChild(new TreeNode<>(new File("ARTICULO 131")))
                        .addChild(new TreeNode<>(new File("ARTICULO 132")))
                        .addChild(new TreeNode<>(new File("ARTICULO 133")))
                        .addChild(new TreeNode<>(new File("ARTICULO 134")))
                        .addChild(new TreeNode<>(new File("ARTICULO 135")))
                        .addChild(new TreeNode<>(new File("ARTICULO 136"))))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO IV. DEL ESTACIONAMIENTO"))
                        .addChild(new TreeNode<>(new File("ARTICULO 137")))
                        .addChild(new TreeNode<>(new File("ARTICULO 138")))
                        .addChild(new TreeNode<>(new File("ARTICULO 139")))
                        .addChild(new TreeNode<>(new File("ARTICULO 140")))
                        .addChild(new TreeNode<>(new File("ARTICULO 141")))
                        .addChild(new TreeNode<>(new File("ARTICULO 142")))
                        .addChild(new TreeNode<>(new File("ARTICULO 143"))))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO V. DE LOS PEATONES Y PASAJEROS"))
                        .addChild(new TreeNode<>(new File("ARTICULO 144")))
                        .addChild(new TreeNode<>(new File("ARTICULO 145")))
                        .addChild(new TreeNode<>(new File("ARTICULO 146")))
                        .addChild(new TreeNode<>(new File("ARTICULO 147")))
                        .addChild(new TreeNode<>(new File("ARTICULO 148"))))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO VI. DE LAS SEÑALES VIALES"))
                        .addChild(new TreeNode<>(new File("ARTICULO 149")))
                        .addChild(new TreeNode<>(new File("ARTICULO 150")))
                        .addChild(new TreeNode<>(new File("ARTICULO 151")))
                        .addChild(new TreeNode<>(new File("ARTICULO 152")))
                        .addChild(new TreeNode<>(new File("ARTICULO 153")))
                        .addChild(new TreeNode<>(new File("ARTICULO 154")))
                        .addChild(new TreeNode<>(new File("ARTICULO 155")))
                        .addChild(new TreeNode<>(new File("ARTICULO 156")))
                        .addChild(new TreeNode<>(new File("ARTICULO 157")))
                        .addChild(new TreeNode<>(new File("ARTICULO 158")))
                        .addChild(new TreeNode<>(new File("ARTICULO 159")))
                        .addChild(new TreeNode<>(new File("ARTICULO 160")))
                        .addChild(new TreeNode<>(new File("ARTICULO 161")))
                        .addChild(new TreeNode<>(new File("ARTICULO 162")))
                        .addChild(new TreeNode<>(new File("ARTICULO 163")))
                        .addChild(new TreeNode<>(new File("ARTICULO 164")))
                        .addChild(new TreeNode<>(new File("ARTICULO 165")))
                        .addChild(new TreeNode<>(new File("ARTICULO 166")))
                        .addChild(new TreeNode<>(new File("ARTICULO 167")))
                        .addChild(new TreeNode<>(new File("ARTICULO 168")))
                        .addChild(new TreeNode<>(new File("ARTICULO 169")))
                        .addChild(new TreeNode<>(new File("ARTICULO 170")))
                        .addChild(new TreeNode<>(new File("ARTICULO 171")))
                        .addChild(new TreeNode<>(new File("ARTICULO 172")))
                        .addChild(new TreeNode<>(new File("ARTICULO 173")))
                        .addChild(new TreeNode<>(new File("ARTICULO 174")))
                        .addChild(new TreeNode<>(new File("ARTICULO 175")))
                        .addChild(new TreeNode<>(new File("ARTICULO 176")))
                        .addChild(new TreeNode<>(new File("ARTICULO 177")))
                        .addChild(new TreeNode<>(new File("ARTICULO 178")))
                        .addChild(new TreeNode<>(new File("ARTICULO 179")))
                        .addChild(new TreeNode<>(new File("ARTICULO 180")))));
        libro3.addChild(new TreeNode<>(new Dir("TITULO II. DEL ESTADO TÉCNICO DE LOS VEHÍCULOS"))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO I. DE LAS EXIGENCIAS TÉCNICAS"))
                        .addChild(new TreeNode<>(new File("ARTICULO 181")))
                        .addChild(new TreeNode<>(new File("ARTICULO 182")))
                        .addChild(new TreeNode<>(new File("ARTICULO 183"))))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO II. DEL SISTEMA DE LUCES"))
                        .addChild(new TreeNode<>(new File("ARTICULO 184")))
                        .addChild(new TreeNode<>(new File("ARTICULO 185")))
                        .addChild(new TreeNode<>(new File("ARTICULO 186")))
                        .addChild(new TreeNode<>(new File("ARTICULO 187")))
                        .addChild(new TreeNode<>(new File("ARTICULO 188")))
                        .addChild(new TreeNode<>(new File("ARTICULO 189")))
                        .addChild(new TreeNode<>(new File("ARTICULO 190"))))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO III. DE LOS ACCESORIOS Y OTROS ADITAMIENTOS"))
                        .addChild(new TreeNode<>(new File("ARTICULO 191")))
                        .addChild(new TreeNode<>(new File("ARTICULO 192")))
                        .addChild(new TreeNode<>(new File("ARTICULO 193")))
                        .addChild(new TreeNode<>(new File("ARTICULO 194")))
                        .addChild(new TreeNode<>(new File("ARTICULO 195")))
                        .addChild(new TreeNode<>(new File("ARTICULO 196")))
                        .addChild(new TreeNode<>(new File("ARTICULO 197")))
                        .addChild(new TreeNode<>(new File("ARTICULO 198")))
                        .addChild(new TreeNode<>(new File("ARTICULO 199")))
                        .addChild(new TreeNode<>(new File("ARTICULO 200")))
                        .addChild(new TreeNode<>(new File("ARTICULO 201")))
                        .addChild(new TreeNode<>(new File("ARTICULO 202")))));

        libro4.addChild(new TreeNode<>(new Dir("TITULO I. DEL CONTROL TÉCNICO DE LOS VEHÍCULOS"))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO I. DE SU OBJETO"))
                        .addChild(new TreeNode<>(new File("ARTICULO 203")))
                        .addChild(new TreeNode<>(new File("ARTICULO 204")))
                        .addChild(new TreeNode<>(new File("ARTICULO 205")))
                        .addChild(new TreeNode<>(new File("ARTICULO 206"))))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO II. DE LA INSPECCIÓN Y REVISIÓN TÉCNICA"))
                        .addChild(new TreeNode<>(new File("ARTICULO 207")))
                        .addChild(new TreeNode<>(new File("ARTICULO 208")))
                        .addChild(new TreeNode<>(new File("ARTICULO 209")))
                        .addChild(new TreeNode<>(new File("ARTICULO 210")))
                        .addChild(new TreeNode<>(new File("ARTICULO 211")))
                        .addChild(new TreeNode<>(new File("ARTICULO 212")))
                        .addChild(new TreeNode<>(new File("ARTICULO 213")))
                        .addChild(new TreeNode<>(new File("ARTICULO 214"))))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO III. DEL REGISTRO DE VEHÍCULOS"))
                        .addChild(new TreeNode<>(new File("ARTICULO 215")))
                        .addChild(new TreeNode<>(new File("ARTICULO 216")))
                        .addChild(new TreeNode<>(new File("ARTICULO 217")))
                        .addChild(new TreeNode<>(new File("ARTICULO 218")))
                        .addChild(new TreeNode<>(new File("ARTICULO 219")))
                        .addChild(new TreeNode<>(new File("ARTICULO 220")))
                        .addChild(new TreeNode<>(new File("ARTICULO 221")))
                        .addChild(new TreeNode<>(new File("ARTICULO 222")))
                        .addChild(new TreeNode<>(new File("ARTICULO 223")))
                        .addChild(new TreeNode<>(new File("ARTICULO 224")))
                        .addChild(new TreeNode<>(new File("ARTICULO 225")))
                        .addChild(new TreeNode<>(new File("ARTICULO 226")))
                        .addChild(new TreeNode<>(new File("ARTICULO 227")))
                        .addChild(new TreeNode<>(new File("ARTICULO 228")))
                        .addChild(new TreeNode<>(new File("ARTICULO 229")))
                        .addChild(new TreeNode<>(new File("ARTICULO 230")))
                        .addChild(new TreeNode<>(new File("ARTICULO 231")))
                        .addChild(new TreeNode<>(new File("ARTICULO 232")))
                        .addChild(new TreeNode<>(new File("ARTICULO 233")))
                        .addChild(new TreeNode<>(new File("ARTICULO 234")))
                        .addChild(new TreeNode<>(new File("ARTICULO 235")))
                        .addChild(new TreeNode<>(new File("ARTICULO 236")))
                        .addChild(new TreeNode<>(new File("ARTICULO 237")))
                        .addChild(new TreeNode<>(new File("ARTICULO 238")))));

        libro5.addChild(new TreeNode<>(new Dir("TITULO I. DE LA EDUCACIÓN VIAL"))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO I. DE LA EDUCACION VIAL"))
                        .addChild(new TreeNode<>(new File("ARTICULO 239")))
                        .addChild(new TreeNode<>(new File("ARTICULO 240")))
                        .addChild(new TreeNode<>(new File("ARTICULO 241")))
                        .addChild(new TreeNode<>(new File("ARTICULO 242")))
                        .addChild(new TreeNode<>(new File("ARTICULO 243")))
                        .addChild(new TreeNode<>(new File("ARTICULO 244")))
                        .addChild(new TreeNode<>(new File("ARTICULO 245")))
                        .addChild(new TreeNode<>(new File("ARTICULO 246")))
                        .addChild(new TreeNode<>(new File("ARTICULO 247")))
                        .addChild(new TreeNode<>(new File("ARTICULO 248"))))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO II. DE LAS ESCUELAS DE EDUCACION VIAL Y CONDUCCIÓN"))
                        .addChild(new TreeNode<>(new File("ARTICULO 249")))
                        .addChild(new TreeNode<>(new File("ARTICULO 250")))
                        .addChild(new TreeNode<>(new File("ARTICULO 251")))
                        .addChild(new TreeNode<>(new File("ARTICULO 252")))
                        .addChild(new TreeNode<>(new File("ARTICULO 253")))
                        .addChild(new TreeNode<>(new File("ARTICULO 254")))
                        .addChild(new TreeNode<>(new File("ARTICULO 255")))
                        .addChild(new TreeNode<>(new File("ARTICULO 256")))
                        .addChild(new TreeNode<>(new File("ARTICULO 257")))
                        .addChild(new TreeNode<>(new File("ARTICULO 258")))
                        .addChild(new TreeNode<>(new File("ARTICULO 259"))))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO III. DE LA LICENCIA DE CONDUCCIÓN"))
                        .addChild(new TreeNode<>(new File("ARTICULO 260")))
                        .addChild(new TreeNode<>(new File("ARTICULO 261")))
                        .addChild(new TreeNode<>(new File("ARTICULO 262")))
                        .addChild(new TreeNode<>(new File("ARTICULO 263")))
                        .addChild(new TreeNode<>(new File("ARTICULO 264")))
                        .addChild(new TreeNode<>(new File("ARTICULO 265")))
                        .addChild(new TreeNode<>(new File("ARTICULO 266")))
                        .addChild(new TreeNode<>(new File("ARTICULO 267")))
                        .addChild(new TreeNode<>(new File("ARTICULO 268")))
                        .addChild(new TreeNode<>(new File("ARTICULO 269")))
                        .addChild(new TreeNode<>(new File("ARTICULO 270")))
                        .addChild(new TreeNode<>(new File("ARTICULO 271")))
                        .addChild(new TreeNode<>(new File("ARTICULO 272")))
                        .addChild(new TreeNode<>(new File("ARTICULO 273")))
                        .addChild(new TreeNode<>(new File("ARTICULO 274")))
                        .addChild(new TreeNode<>(new File("ARTICULO 275")))
                        .addChild(new TreeNode<>(new File("ARTICULO 276")))
                        .addChild(new TreeNode<>(new File("ARTICULO 277")))
                        .addChild(new TreeNode<>(new File("ARTICULO 278")))
                        .addChild(new TreeNode<>(new File("ARTICULO 279")))
                        .addChild(new TreeNode<>(new File("ARTICULO 280")))
                        .addChild(new TreeNode<>(new File("ARTICULO 281")))
                        .addChild(new TreeNode<>(new File("ARTICULO 282")))
                        .addChild(new TreeNode<>(new File("ARTICULO 283")))
                        .addChild(new TreeNode<>(new File("ARTICULO 284")))
                        .addChild(new TreeNode<>(new File("ARTICULO 285")))
                        .addChild(new TreeNode<>(new File("ARTICULO 286")))
                        .addChild(new TreeNode<>(new File("ARTICULO 287")))
                        .addChild(new TreeNode<>(new File("ARTICULO 288")))
                        .addChild(new TreeNode<>(new File("ARTICULO 289")))
                        .addChild(new TreeNode<>(new File("ARTICULO 290")))
                        .addChild(new TreeNode<>(new File("ARTICULO 291")))
                        .addChild(new TreeNode<>(new File("ARTICULO 292")))
                        .addChild(new TreeNode<>(new File("ARTICULO 293")))
                        .addChild(new TreeNode<>(new File("ARTICULO 294")))
                        .addChild(new TreeNode<>(new File("ARTICULO 295")))
                        .addChild(new TreeNode<>(new File("ARTICULO 296")))
                        .addChild(new TreeNode<>(new File("ARTICULO 297")))
                        .addChild(new TreeNode<>(new File("ARTICULO 298")))
                        .addChild(new TreeNode<>(new File("ARTICULO 299")))
                        .addChild(new TreeNode<>(new File("ARTICULO 300")))
                        .addChild(new TreeNode<>(new File("ARTICULO 301")))
                        .addChild(new TreeNode<>(new File("ARTICULO 302"))))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO IV. DE LAS AUTORIZACIONES ESPECIALES PARA LOS CONDUCTORES DE TRACCIÓN ANIMAL Y HUMANA"))
                        .addChild(new TreeNode<>(new File("ARTICULO 303")))
                        .addChild(new TreeNode<>(new File("ARTICULO 304")))));

        libro6.addChild(new TreeNode<>(new Dir("TITULO I. DE LA RESPONSABILIDAD ADMINISTRATIVA"))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO I. DE LAS MULTAS"))
                        .addChild(new TreeNode<>(new File("ARTICULO 305")))
                        .addChild(new TreeNode<>(new File("ARTICULO 306")))
                        .addChild(new TreeNode<>(new File("ARTICULO 307")))));

        libro7.addChild(new TreeNode<>(new Dir("TITULO I. DE LA COMISIÓN NACIONAL"))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO I. DE SU INTEGRACIÓN Y FUNCIONES"))
                        .addChild(new TreeNode<>(new File("ARTICULO 308")))
                        .addChild(new TreeNode<>(new File("ARTICULO 309")))
                        .addChild(new TreeNode<>(new File("ARTICULO 310")))
                        .addChild(new TreeNode<>(new File("ARTICULO 311")))
                        .addChild(new TreeNode<>(new File("ARTICULO 312")))
                        .addChild(new TreeNode<>(new File("ARTICULO 313")))
                        .addChild(new TreeNode<>(new File("ARTICULO 314")))));
        libro7.addChild(new TreeNode<>(new Dir("TITULO II. DE LAS COMISIONES PROVINCIALES Y MUNICIPALES"))
                .addChild(new TreeNode<>(new File("ARTICULO 315")))
                .addChild(new TreeNode<>(new File("ARTICULO 316")))
                .addChild(new TreeNode<>(new File("ARTICULO 317")))
                .addChild(new TreeNode<>(new File("ARTICULO 318")))
                .addChild(new TreeNode<>(new File("ARTICULO 319")))
                .addChild(new TreeNode<>(new File("ARTICULO 320"))))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO II. DE LAS COMISIONES MUNICIPALES"))
                        .addChild(new TreeNode<>(new File("ARTICULO 321")))
                        .addChild(new TreeNode<>(new File("ARTICULO 322")))
                        .addChild(new TreeNode<>(new File("ARTICULO 323")))
                        .addChild(new TreeNode<>(new File("ARTICULO 324"))));


        regulaciones.addChild(new TreeNode<>(new Dir("CÁPITULO I. DE SU OBJETO"))
                .addChild(new TreeNode<>(new File("ARTICULO 1"))))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO II. DE LAS INFRACCIONES DE LAS REGULACIONES DEL TRÁNSITO PREVISTAS EN EL CÓDIGO DE SEGURIDAD VIAL NO CONSTITUTIVAS DE DELITO"))
                        .addChild(new TreeNode<>(new File("ARTICULO 2")))
                        .addChild(new TreeNode<>(new File("ARTICULO 3")))
                        .addChild(new TreeNode<>(new File("ARTICULO 4")))
                        .addChild(new TreeNode<>(new File("ARTICULO 5")))
                        .addChild(new TreeNode<>(new File("ARTICULO 6")))
                        .addChild(new TreeNode<>(new File("ARTICULO 7")))
                        .addChild(new TreeNode<>(new File("ARTICULO 8")))
                        .addChild(new TreeNode<>(new File("ARTICULO 9")))
                        .addChild(new TreeNode<>(new File("ARTICULO 10")))
                        .addChild(new TreeNode<>(new File("ARTICULO 11")))
                        .addChild(new TreeNode<>(new File("ARTICULO 12")))
                        .addChild(new TreeNode<>(new File("ARTICULO 13")))
                        .addChild(new TreeNode<>(new File("ARTICULO 14")))
                        .addChild(new TreeNode<>(new File("ARTICULO 15")))
                        .addChild(new TreeNode<>(new File("ARTICULO 16")))
                        .addChild(new TreeNode<>(new File("ARTICULO 17")))
                        .addChild(new TreeNode<>(new File("ARTICULO 18")))
                        .addChild(new TreeNode<>(new File("ARTICULO 19")))
                        .addChild(new TreeNode<>(new File("ARTICULO 20"))))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO III. DE LA NOTIFICACION PREVENTIVA"))
                        .addChild(new TreeNode<>(new File("ARTICULO 21")))
                        .addChild(new TreeNode<>(new File("ARTICULO 22")))
                        .addChild(new TreeNode<>(new File("ARTICULO 23")))
                        .addChild(new TreeNode<>(new File("ARTICULO 24"))))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO IV. DEL PAGO DE LOS GASTOS POR TRASLADO, DEPÓSITO Y CUSTODIA DE VEHÍCULOS"))
                        .addChild(new TreeNode<>(new File("ARTICULO 25")))
                        .addChild(new TreeNode<>(new File("ARTICULO 26"))))
                .addChild(new TreeNode<>(new Dir("CAPÍTULO III. DEL DECOMISO DE BIENES, RECURSOS Y PROCEDIMIENTOS PARA SU IMPUGNACIÓN "))
                        .addChild(new TreeNode<>(new File("ARTICULO 27")))
                        .addChild(new TreeNode<>(new File("ARTICULO 28")))
                        .addChild(new TreeNode<>(new File("ARTICULO 29")))
                        .addChild(new TreeNode<>(new File("ARTICULO 30")))
                        .addChild(new TreeNode<>(new File("ARTICULO 31")))
                        .addChild(new TreeNode<>(new File("ARTICULO 32")))
                        .addChild(new TreeNode<>(new File("ARTICULO 33")))
                        .addChild(new TreeNode<>(new File("ARTICULO 34")))
                        .addChild(new TreeNode<>(new File("ARTICULO 35")))
                        .addChild(new TreeNode<>(new File("ARTICULO 36")))
                        .addChild(new TreeNode<>(new File("ARTICULO 37")))
                        .addChild(new TreeNode<>(new File("ARTICULO 38")))
                        .addChild(new TreeNode<>(new File("ARTICULO 39")))
                        .addChild(new TreeNode<>(new File("ARTICULO 40")))
                        .addChild(new TreeNode<>(new File("ARTICULO 41")))
                        .addChild(new TreeNode<>(new File("ARTICULO 42"))));

        rv.setLayoutManager(new LinearLayoutManager(this));
        App.callbackArbolArticulo = this;
        adapter = new TreeViewAdapter(nodes, Arrays.asList(new FileNodeBinder(), new DirectoryNodeBinder()));
        // whether collapse child nodes when their parent node was close.
        adapter.ifCollapseChildWhileCollapseParent(true);
        adapter.setOnTreeNodeListener(new TreeViewAdapter.OnTreeNodeListener() {
            @Override
            public boolean onClick(TreeNode node, RecyclerView.ViewHolder holder) {
                if (!node.isLeaf()) {
                    //Update and toggle the node.
                    onToggle(!node.isExpand(), holder);

                }
                if (node.getHeight() == 0) {
                    holderBack = holder;
                }
                return false;
            }

            @Override
            public void onToggle(boolean isExpand, RecyclerView.ViewHolder holder) {


                DirectoryNodeBinder.ViewHolder dirViewHolder = (DirectoryNodeBinder.ViewHolder) holder;
                final ImageView ivArrow = dirViewHolder.getIvArrow();
                int rotateDegree = isExpand ? 90 : -90;
                ivArrow.animate().rotationBy(rotateDegree)
                        .start();
            }
        });
        rv.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {


        super.onBackPressed();

    }

    @Override
    public void onClick(String nombre, int posicion) {

        if (nombre.equals("DISPOSICIONES ESPECIALES")) {

            setDialog(nombre, App.LISTA_REGULACIONES.get(0));

        } else if (nombre.equals("DISPOSICIONES TRANSITORIAS")) {

            setDialog(nombre, App.LISTA_REGULACIONES.get(1));


        } else if (nombre.equals("REGULACIONES FINALES")) {

            setDialog(nombre, App.LISTA_REGULACIONES.get(2));

        } else {
            View view = (View) holderBack.itemView;
            TextView textView = (TextView) view.findViewById(R.id.tv_name);

            if (textView.getText().equals("REGULACIONES COMPLEMENTARIAS")) {

                String[] aux = nombre.split(" ");
                int pos = Integer.parseInt(aux[1]);

                setDialog(nombre, App.LISTA_ARTICULOS_REGULACIONES.get(pos - 1).getDescripcion());

            } else {
                String[] aux = nombre.split(" ");
                int pos = Integer.parseInt(aux[1]);

                setDialog(nombre, App.LISTA_ARTICULOS_LIBROS.get(pos - 1).getDescripcion());

            }
        }


    }

    private void setDialog(String title, String message) {

        LayoutInflater layoutInflater = LayoutInflater.from(ArbolActivity.this);
        final View myview = layoutInflater.inflate(R.layout.dialog_layout_articulo, null);
        TextView descripcion = myview.findViewById(R.id.descripcion);
        descripcion.setText(message);
        final AlertDialog.Builder builder = new AlertDialog.Builder(ArbolActivity.this);
        builder
                .setTitle(title)
                .setView(myview)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        final AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.AnimacionDialog1;
        dialog.setCancelable(true);
        dialog.show();

    }
}
