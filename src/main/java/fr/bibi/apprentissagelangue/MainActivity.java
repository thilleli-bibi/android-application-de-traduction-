package fr.bibi.apprentissagelangue;


import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
 private MyDataBase mdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContentResolver cr = getContentResolver();

        mdb = new MyDataBase(this);


    }
    public void ajout(View bouton) {
        System.out.println("********************************");
        System.out.println(getFilesDir());

        Intent intent = new Intent(this, activity_ajout.class);
        startActivity(intent);


    }

    public void suppression(View bouton) {
        Intent intent = new Intent(this, activity_suppression.class);
        startActivity(intent);
    }

    public void update(View bouton){
        Intent intent = new Intent(this, activity_update.class);
        startActivity(intent);
    }
    public void test (View bouton){
        Intent intent = new Intent(this, activity_test.class);
        startActivity(intent);

    }
    public void choix_apprentissage(View bouton){
        Intent intent = new Intent(this, activity_choix_apprentissage.class);
        startActivity(intent);
    }
}