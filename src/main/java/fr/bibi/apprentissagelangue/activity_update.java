package fr.bibi.apprentissagelangue;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static android.os.Environment.DIRECTORY_MUSIC;
import static android.os.Environment.DIRECTORY_PICTURES;

public class activity_update extends AppCompatActivity {
    private MyDataBase mdb;

    private TextView uriAppareil,uriSon;
    private EditText edtraduction,edImageWeb;
    private Button b_trad,b_imgweb,b_imgapp,b_son;
    private Spinner chooseWord;

    private String motChoisi;
    private String pathImg;

    private File imgPath;
    private File soundPath;

    private Bitmap imgbm;
    private static final int PICK_IMAGE = 1;
    private static final int PICK_SOUND = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        mdb = new MyDataBase(this);
        uriAppareil = findViewById(R.id.uriAppareil);
        uriAppareil.setText("Coucou");
        uriSon = findViewById(R.id.uriSon);
        edtraduction = findViewById(R.id.ed_traduction2);
        edImageWeb = findViewById(R.id.ed_image_web2);

        b_trad = findViewById(R.id.b_traduction);
        b_imgweb = findViewById(R.id.b_image_web);
        b_imgapp = findViewById(R.id.b_image_appareil);
        b_son = findViewById(R.id.b_son);
        chooseWord = findViewById(R.id.sp_word);

        ArrayList<String> al = new ArrayList<>();
        Cursor curs = mdb.getApprendreTable();
        boolean b = curs.moveToFirst();
        while (b) {
            al.add(curs.getString(curs.getColumnIndexOrThrow(mdb.COLONNE_MOT)));
            b = curs.moveToNext();
        }
        curs.close();
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, al);
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

    public void ajouterTraduction(View bouton){

        boolean bool = mdb.addTraduction(motChoisi,edtraduction.getText().toString());
        if (bool)
            Toast.makeText(this,"traduction ajoutée", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this,"erruer!", Toast.LENGTH_LONG).show();



    }

    // Ajout(s'il y a rien) ou modification de la référence web de l'image
    public void ajouterImageWeb(View bouton){

       boolean bool = false;
       EditText imageweb = findViewById(R.id.ed_image_web2);

       if (!imageweb.getText().toString().equals(" "))
          bool = mdb.addImageWeb(motChoisi,imageweb.getText().toString());
       else
           Toast.makeText(this,"veuillez saisir une URL", Toast.LENGTH_LONG).show();

       if(bool)
           Toast.makeText(this,"URL web ajoutée à l'image", Toast.LENGTH_LONG).show();
       else
           Toast.makeText(this,"erreur, réesayer", Toast.LENGTH_LONG).show();


    }
    // Cherche une image dans l'appareil
    public void chercherImage(View bouton){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(intent, PICK_IMAGE);
    }
    // Gère l'insertion de la référence en fonction du request code
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if(requestCode == PICK_IMAGE){
            Uri uri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (FileNotFoundException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
            ContextWrapper cw = new ContextWrapper(this);
            File directory = getExternalFilesDir(DIRECTORY_PICTURES);

            // Create imageDir
            String alpha = "azertyuiopqsdfghjklmwxcvbn1234567890";
            String name = "";
            for(int i = 0; i< 8; i++)
                name += alpha.charAt(new Random().nextInt(alpha.length()));
            name += ".png";

            File mypath=new File(directory,name);

            System.out.println(mypath.toString());
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(mypath);
                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmap.compress(Bitmap.CompressFormat.PNG,90,fos);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            uriAppareil.setText(mypath.toString());
        }//*/
            if(requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
                Uri uri = data.getData();
                uriAppareil.setText(uri.toString());

                // Partie pour l'ajout dans la mémoire interne
            /*
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
            uriAppareil.setText(imgPath.toString());*/
            }
            if(requestCode == PICK_SOUND && resultCode == RESULT_OK) {
                Uri uri = data.getData();
                String src = uri.toString();
                uriSon.setText(src);

                // Partie pour l'ajout dans la mémoire interne
            /*
            Uri uri = data.getData();
            String src = uri.getPath();
            File source = new File(src);
            String name = "sound_";
            for (int i = 0; i < 10; i++)
                name += new Random().nextInt(10);
            name += ".3gp";
            File soundDir = new File(getFilesDir().getAbsolutePath()+File.separator+"Sounds");
            if(!soundDir.exists())
                soundDir.mkdir();
            soundPath = new File(soundDir+File.separator+name);
            uriSon.setText(soundPath.toString());*/
            }
        }

    // Ajout(s'il y a rien) ou modification de la référence de l'image dans la base et dans la mémoire interne
    public void ajouterImageAppareil(View bouton){

        /*
        String str = uriAppareil.getText().toString();
        File img = new File(getFilesDir().getAbsolutePath()+File.separator+"Images");
        File[] dir = img.listFiles();
        for(int i = dir.length-1; i>=0; i--) {
            String selectedFilePath = dir[i].toString();
            File file = new File(selectedFilePath);
            file.delete();
        }
        str = str.trim();
        if(str.length() == 0){
            Toast.makeText(this, "Veuillez choisir une image a ajouter !",Toast.LENGTH_LONG).show();
            return;
        }
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

        }*/

        String str = uriAppareil.getText().toString();
        if(mdb.addImageAppareil(motChoisi,str))
            Toast.makeText(this, "L'image de l'appareil a bien été rajoutée",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Ajout de l'image de l'appareil échoué",Toast.LENGTH_LONG).show();
    }


    // Cherche un son dans l'appareil
    public void chercherSon(View bouton){
        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(intent, PICK_SOUND);
    }

    // Ajout(s'il y a rien) ou modification de la référence du son dans la base et dans la mémoire interne
    public void ajouterSon(View bouton){
        /*
        String str = uriSon.getText().toString();
        File img = new File(getFilesDir().getAbsolutePath()+File.separator+"Sounds");
        File[] dir = img.listFiles();
        for(int i = dir.length-1; i>=0; i--) {
            String selectedFilePath = dir[i].toString();
            File file = new File(selectedFilePath);
            file.delete();
        }
        str = str.trim();
        if(str.length() == 0){
            Toast.makeText(this, "Veuillez choisir une image a ajouter !",Toast.LENGTH_LONG).show();
            return;
        }
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
        */

        String str = uriSon.getText().toString();
        if(mdb.addSon(motChoisi,str))
            Toast.makeText(this, "Le son de l'appareil a bien été rajoutée",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Ajout du son échoué",Toast.LENGTH_LONG).show();
    }


}
