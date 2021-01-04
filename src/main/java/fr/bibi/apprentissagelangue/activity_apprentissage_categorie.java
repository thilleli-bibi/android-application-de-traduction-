package fr.bibi.apprentissagelangue;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class activity_apprentissage_categorie extends AppCompatActivity {

    private MyDataBase mdb;

    private String motChoisi;
    private String catChoisie;
    private Spinner chooseword;
    private Spinner chooseCat;

    private TextView trad, cat, img;
    private ImageView image;
    private WebView img_web;
    private MediaPlayer mpson;

    private String son;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apprentissage_categorie);

        mdb = new MyDataBase(this);
        chooseCat = findViewById(R.id.sp_categorie);
        chooseword = findViewById(R.id.sp_mot_cat);

        trad = findViewById(R.id.trad_cat);
        cat = findViewById(R.id.cat_cat);
        image = findViewById(R.id.imgv_photoapp);
        img_web = findViewById(R.id.photoweb_cat);

        img = findViewById(R.id.img_cat);


        ArrayList<String> al = new ArrayList<>();
        Cursor curs = mdb.getApprendreTable();
        boolean b = curs.moveToFirst();

        while (b) {
            al.add(curs.getString(curs.getColumnIndexOrThrow(mdb.COLONNE_CATEGORIE)));
            b = curs.moveToNext();
        }
        curs.close();
        ArrayList<String> al2 = new ArrayList<>();
        for (int i = 0; i < al.size(); i++) {
            if (!al.get(i).equals("") && !al2.contains(al.get(i)))
                al2.add(al.get(i));
        }

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, al2);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseCat.setAdapter(aa);
        chooseCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                catChoisie = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                catChoisie = "animaux";
            }
        });


    }

    public void voirinfo(View bouton) {
        image.setImageResource(0);
        img_web.loadUrl("about:blank");
        String word = motChoisi.trim();
        Cursor curseur = mdb.getMot(word);
        if (word.equals("")) {
            Toast.makeText(this, "Veuillez choisir un mot s'il vous plait", Toast.LENGTH_LONG).show();
            return;
        }
        if (curseur == null) {
            Toast.makeText(this, "Le mot n'existe pas dans la base. Veuillez choisir un autre mot.", Toast.LENGTH_LONG).show();
            return;
        }
        curseur.moveToFirst();
        String traduction = "Traduction(s): " + curseur.getString(curseur.getColumnIndex(mdb.COLONNE_TRADUCTION));
        String categorie = "Categorie(s): " + curseur.getString(curseur.getColumnIndex(mdb.COLONNE_CATEGORIE));
        System.out.println();
        trad.setText(traduction);
        cat.setText(categorie);
        String photoweb = curseur.getString(curseur.getColumnIndexOrThrow(mdb.COLONNE_WEB));
        String photodevice = curseur.getString(curseur.getColumnIndexOrThrow(mdb.COLONNE_APPAREIL));
        if (photodevice != null && !photodevice.equals("")) {
            Bitmap bm;
            try {
                bm = BitmapFactory.decodeFile(photodevice);
                image.setImageBitmap(bm);
                img.setText("Image issue de l'appareil:");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (photoweb != null && !photoweb.equals("")) {
            img_web.setWebViewClient(new WebViewClient());
            img.setText("Image issue d'internet:");
            img_web.loadUrl(photoweb);
        }
        else
            img.setText("il exite pas d'image pour :" + motChoisi);

        son = curseur.getString(curseur.getColumnIndexOrThrow(mdb.COLONNE_SON));
        Log.i("DATABASE","infoBase invoked");
        curseur.close();

    }


    //valider la categorie pour afficher les mots associes dans le spinner
    public void validercat (View bouton){

        Toast.makeText(this, "vous avez choisi la catégorie : " + catChoisie, Toast.LENGTH_LONG).show();

        ArrayList<String> liste_mot = new ArrayList<>();
        ArrayList<String> liste_mot_cat = new ArrayList<>();

        Cursor curs_mot = mdb.getApprendreTable();
        boolean b2 = curs_mot.moveToFirst();

        while (b2) {
            liste_mot.add(curs_mot.getString(curs_mot.getColumnIndexOrThrow(mdb.COLONNE_MOT)));
            b2 = curs_mot.moveToNext();
        }
        curs_mot.close();
        String cat;

        for (int i = 0; i < liste_mot.size(); i++) {
            Cursor curs_get_mot = mdb.getMot(liste_mot.get(i));
            boolean bool = curs_get_mot.moveToFirst();
            if (bool) {
                cat = curs_get_mot.getString(curs_get_mot.getColumnIndexOrThrow(mdb.COLONNE_CATEGORIE));
                System.out.println("******************************");
                System.out.println(cat + " : " + catChoisie);

                if (cat.equalsIgnoreCase(catChoisie)) {
                    liste_mot_cat.add(liste_mot.get(i));
                }

            }
            curs_get_mot.close();
        }
        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, liste_mot_cat);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseword.setAdapter(a);
        chooseword.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                motChoisi = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public void play(View bouton){
        if(containsSon()) {
            if (mpson == null)
                mpson = MediaPlayer.create(this, Uri.parse(son));
            mpson.start();
        }
    }

    public void pause(View bouton){
        if(containsSon()) {
            if (mpson != null)
                mpson.pause();
        }
    }

    public void stop(View bouton){
        if(containsSon()) {
            if (mpson != null) {
                mpson.release();
                mpson = null;
                Toast.makeText(this, "Le son a été stoppé", Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean containsSon(){ return son != null;
    }
}


