package fr.bibi.apprentissagelangue;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/*public class activity_apprentissage extends AppCompatActivity {

    private MyDataBase bd;
    private EditText mot;
    private TextView listtrad,listcat,tv_image,tv_son;
    private WebView wb_photo;
    private ImageView imgv_photoapp;

    private MediaPlayer mpson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apprentissage);


        wb_photo = findViewById(R.id.wv_photoweb);
        wb_photo.getSettings().setJavaScriptEnabled(true);
        wb_photo.setWebViewClient(new WebViewClient());

        imgv_photoapp = findViewById(R.id.imgv_photoapp);

        mot = findViewById(R.id.ed_motappr);
        bd = new MyDataBase(this);

        listtrad = findViewById(R.id.tv_listtrad);
        listcat = findViewById(R.id.tv_listcat);
        tv_image = findViewById(R.id.tv_img);

    }

    public void infoBase(View Bouton){
        String word = mot.getText().toString().trim();
        Cursor curseur = bd.getMot(word);
        if(word.equals("")){
            Toast.makeText(this,"Veuillez écrire un mot s'il vous plait",Toast.LENGTH_LONG).show();
            return;
        }
        if (curseur == null) {
            Toast.makeText(this,"Le mot n'existe pas dans la base. Veuillez choisir un autre mot.",Toast.LENGTH_LONG).show();
            return;
        }
        curseur.moveToFirst();
        String traduction = "Traduction(s): " + curseur.getString(curseur.getColumnIndex(bd.COLONNE_TRADUCTION));
        String categorie = "Categorie(s): "+ curseur.getString(curseur.getColumnIndex(bd.COLONNE_CATEGORIE));
        System.out.println();
        listtrad.setText(traduction);
        listcat.setText(categorie);

        String photoweb = curseur.getString(curseur.getColumnIndexOrThrow(bd.COLONNE_WEB));
        String photodevice = curseur.getString(curseur.getColumnIndexOrThrow(bd.COLONNE_APPAREIL));

        if(photodevice != null && !photodevice.equals("")) {
            Bitmap bm;
            try{
                bm = BitmapFactory.decodeFile(photodevice);
                imgv_photoapp.setImageBitmap(bm);
                tv_image.setText("Image issue de l'appareil:");
            } catch (Exception e){
                e.printStackTrace();
            }
        } else if(photoweb != null && !photoweb.equals("")) {
            wb_photo.loadUrl(photoweb);
            tv_image.setText("Image issue d'internet:");

        }
        else if (photodevice == null && photoweb == null)
            System.out.println("pas de photo pour ce mot");
        String son = curseur.getString(curseur.getColumnIndexOrThrow(bd.COLONNE_SON));

        if(son != null)
            mpson = MediaPlayer.create(this, Uri.parse(son));
        Log.i("DATABASE","infoBase invoked");
    }
}*/

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
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

import java.util.ArrayList;

public class activity_apprentissage extends AppCompatActivity {

    private MyDataBase mdb;
    private TextView tv_choix,listtrad,listcat,tv_image,tv_son;
    private WebView wb_photo;
    private ImageView imgv_photoapp;
    private boolean b_mot, b_categorie, b_liste = false;
    private Spinner chooseWord;
    private MediaPlayer mpson;
    private String motChoisi,categorie,son;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apprentissage);

        wb_photo = findViewById(R.id.wv_photoweb);
        wb_photo.getSettings().setJavaScriptEnabled(true);
        wb_photo.setWebViewClient(new WebViewClient());

        imgv_photoapp = findViewById(R.id.imgv_photoapp);

        chooseWord = findViewById(R.id.sp_chooseWord);
        mdb = new MyDataBase(this);

        listtrad = findViewById(R.id.tv_listtrad);
        listcat = findViewById(R.id.tv_listcat);
        tv_image = findViewById(R.id.tv_img);
        tv_choix = findViewById(R.id.tv_choixApprentissage);
        tv_son = findViewById(R.id.tv_son);

         mpson =  null;

        Intent iii=getIntent();
        String choix=iii.getStringExtra("choix");
        if(choix.equals("mot")){
            b_mot = true;
            tv_choix.setText("Apprentissage basique:");
        } else if (choix.equals("categorie")) {
            categorie = (iii.getStringExtra("categorie")).trim();
            b_categorie = true;
            tv_choix.setText("Apprentissage par catégorie de mot:");
        } else if(choix.equals("liste")){
            b_liste = true;
            tv_choix.setText("Apprentissage par liste de mots:");
        }

        Cursor curs = mdb.getApprendreTable();
        ArrayList<String> listMot = new ArrayList<>();
        boolean b = curs.moveToFirst();
        while (b){
            String str = curs.getString(curs.getColumnIndexOrThrow("mot"));
            if(b_mot)
                listMot.add(str);
            if(b_categorie){
                if(mdb.isInCategory(str,categorie))  //categorie?
                    listMot.add(str);
            }
            if(b_liste){
                if(mdb.isSaved(str))
                    listMot.add(str);
            }
            b = curs.moveToNext();
        }
        curs.close();
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listMot);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseWord.setAdapter(aa);
        chooseWord.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                motChoisi = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void infoBase(View Bouton){
        imgv_photoapp.setImageResource(0);
        wb_photo.loadUrl("about:blank");  //effacer les images déja existantes sur la page
        Cursor curseur = mdb.getMot(motChoisi);
        curseur.moveToFirst();
        String traduction = "Traduction(s): " + curseur.getString(curseur.getColumnIndex(mdb.COLONNE_TRADUCTION));
        String categorie = "Categorie(s): "+ curseur.getString(curseur.getColumnIndex(mdb.COLONNE_CATEGORIE));
        System.out.println();
        listtrad.setText(traduction);
        listcat.setText(categorie);
        String photoweb = curseur.getString(curseur.getColumnIndexOrThrow(mdb.COLONNE_WEB));
        String photodevice = curseur.getString(curseur.getColumnIndexOrThrow(mdb.COLONNE_APPAREIL));

        if(photodevice != null){// && !photodevice.equals("")) {
            Bitmap bm;
            try{
                bm = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.parse(photodevice)));
                imgv_photoapp.setImageBitmap(bm);
                tv_image.setText("Image issue de l'appareil:");
            } catch (Exception e){
                e.printStackTrace();
            }
        } else if(photoweb != null){//&& !photoweb.equals("")) {
            wb_photo.loadUrl(photoweb);
            tv_image.setText("Image issue d'internet:");
        }
        else
            tv_image.setText("il exite pas d'image pour : " + motChoisi);

        son = curseur.getString(curseur.getColumnIndexOrThrow(mdb.COLONNE_SON));
        curseur.close();

        Log.i("DATABASE","infoBase invoked");
    }
    //lire le son
    public void play(View bouton){
        if(containsSon()) {
            if (mpson == null)
                mpson = MediaPlayer.create(this, Uri.parse(son));
            mpson.start();
        }
    }
    //mettre en pause la lecture du son
    public void pause(View bouton){
        if(containsSon()) {
            if (mpson != null)
                mpson.pause();
        }
    }
   //arreter la lecture du son
    public void stop(View bouton){
        if(containsSon()) {
            if (mpson != null) {
                mpson.release();
                mpson = null;
                Toast.makeText(this, "Le son a été stoppé", Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean containsSon(){
        return son != null;
    }


}

