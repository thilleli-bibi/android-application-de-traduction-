package fr.bibi.apprentissagelangue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class activity_test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void test_libre(View bouton){
        Intent intent = new Intent(this, activity_test_libre.class);
        startActivity(intent);
    }
    public void test_liste_apprentissage(View bouton){
        Intent intent = new Intent(this, activity_test_liste_apprentisssage.class);
        startActivity(intent);
    }
    public void test_couple_de_mots(View bouton){
        Intent intent = new Intent(this, activity_test_couple_mots.class);
        startActivity(intent);
    }
}
