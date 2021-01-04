package fr.bibi.apprentissagelangue;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class activity_ajout extends AppCompatActivity {
    public MyDataBase bd;

    private EditText mot, traduction, categorie, image_web;
    private TextView image_appareil,ref_son;
    private Button chargerImgApp, chargerSon;

    private File imgPath;
    private File soundPath;

    private Bitmap imgbm;

    private static final int PICK_IMAGE = 1;
    private static final int PICK_SOUND = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);

        bd = new MyDataBase(this);
        mot = findViewById(R.id.ed_mot);
        traduction = findViewById(R.id.ed_traduction1);
        categorie = findViewById(R.id.ed_categorie);
        image_web = findViewById(R.id.ed_image_web1);
        image_appareil = findViewById(R.id.uri_image_appareil);
        ref_son = findViewById(R.id.ref_son);
        chargerImgApp = findViewById(R.id.b_charger_image);
        chargerSon = findViewById(R.id.b_charger_son);
    }
   //charger une image de l'appareil
    public void chargerImgApp(View bouton){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE);
    }
   //charger un son de l'appareil
    public void chargerSon(View bouton){
        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_SOUND);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK) {//image
            Uri uri = data.getData();
            String src = uri.toString();
            image_appareil.setText(src);

            // Partie pour sauvegarder dans la mémoire interne
            imgbm = null;
            try {
                imgbm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Create imageDir
            String name = "img_";
            for (int i = 0; i < 10; i++)
                name += new Random().nextInt(10);
            name += ".png";

            File directory = getFilesDir();
            File imgDir = new File(directory.getAbsolutePath()+File.separator+"Images");
            if(!imgDir.exists())
                imgDir.mkdir();
            imgPath = new File(imgDir+File.separator+name);
            image_appareil.setText(imgPath.toString());
        }
        if(requestCode == PICK_SOUND && resultCode == RESULT_OK) {//son
            Uri uri = data.getData();
            String src = uri.toString();
            ref_son.setText(src);

             /*
            File source = new File(src);
            String name = "sound_";
            for (int i = 0; i < 10; i++)
                name += new Random().nextInt(10);
            name += ".3gp";
            File soundDir = new File(getFilesDir().getAbsolutePath()+File.separator+"Sounds");
            if(!soundDir.exists())
                soundDir.mkdir();
            soundPath = new File(soundDir+File.separator+name);
            ref_son.setText(soundPath.toString());*/
        }
        }


    public void ajouter (View bouton){
        if(mot.getText().toString().length() == 0) {
            Toast.makeText(this, "Veuillez écrire le mot à ajouter !", Toast.LENGTH_LONG).show();
            return;
        }
        String trad = traduction.getText().toString();
        String cat = categorie.getText().toString();
        String imgWeb = image_web.getText().toString();
        String imgApp = image_appareil.getText().toString();
        String sonApp = ref_son.getText().toString();
        if(trad.length() == 0)
            trad = null;
        if(cat.length() == 0)
            cat = null;
        if(imgWeb.length() == 0)
            imgWeb = null;
        if(imgApp.length() > 0) {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(imgPath);
                // Use the compress method on the BitMap object to write image to the OutputStream
                imgbm.compress(Bitmap.CompressFormat.PNG, 90, fos);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else imgApp = null;
        if(sonApp.length() > 0) {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(soundPath);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else sonApp = null;
       boolean bool = bd.ajoutDico(mot.getText().toString(),trad,cat,imgWeb,imgApp,sonApp);
        if (bool)
            Toast.makeText(this,"L'insertion a réussi", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this,"L'insertion a échoué", Toast.LENGTH_LONG).show();
    }

    public void preRemplir (View bouton){
        bd.init();
        Toast.makeText(this, "Base initialisée",  Toast.LENGTH_LONG).show();
    }
}
