package fr.bibi.apprentissagelangue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import static android.os.Environment.DIRECTORY_PICTURES;

public class activity_suppression extends AppCompatActivity {

    private TextView mot ;
    public MyDataBase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppression);

        Intent intent = getIntent();

        mot=findViewById(R.id.ed_mot2);
        bd = new MyDataBase(this);
    }


    public void supprimerTraduction (View bouton){
        String str = mot.getText().toString();
        Cursor c = bd.getMot(str);
        if(!c.moveToFirst()){
            Toast.makeText(this,"Le mot n'existe pas dans la base de données", Toast.LENGTH_LONG).show();
            c.close();
            return;
        }
        boolean bool =bd.deleteTraduction(mot.getText().toString());

        if (bool)
            Toast.makeText(this,"supression de traduction réussie", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this,"supression de traduction echouée, le mot n'existe pas dans la base", Toast.LENGTH_LONG).show();





    }
    public void supprimerImage (View bouton){

        System.out.println("********************************************");
        System.out.println("supprimerImage is invoked");

        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        System.out.println(dir);
       //si le mot n'existe pas dans la base
        String str = mot.getText().toString();
        Cursor c = bd.getMot(str);
        if(!c.moveToFirst()){
            Toast.makeText(this,"Le mot n'existe pas dans la base de données", Toast.LENGTH_LONG).show();
            c.close();
            return;
        }
        boolean bool =bd.deleteImage(mot.getText().toString(),dir);
        if (bool)
        {Toast.makeText(this,"supression de l'image réussie", Toast.LENGTH_LONG).show();
           System.out.println("supression de l'image reusie");}
        else
        {Toast.makeText(this,"supression de l'image echouée", Toast.LENGTH_LONG).show();
            System.out.println("supression de l'image echouée");}
    }
}