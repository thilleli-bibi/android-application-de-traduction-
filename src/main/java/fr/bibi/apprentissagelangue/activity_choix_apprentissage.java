package fr.bibi.apprentissagelangue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class activity_choix_apprentissage extends AppCompatActivity {

    private RadioButton rb_mot,rb_cat, rb_liste;
    private Button b_annuler, b_valider, b_ifsave;
    private MyDataBase mdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_apprentissage);

        mdb = new MyDataBase(this);
        rb_mot = findViewById(R.id.rb_mot);
        rb_cat = findViewById(R.id.rb_cat);
        rb_liste = findViewById(R.id.rb_liste);

        b_annuler = findViewById(R.id.b_annuler);
        b_valider = findViewById(R.id.b_valider);
        b_ifsave = findViewById(R.id.b_ifsave);


    }
    //revenir à l'activité principale
    public void annuler(View bouton)
    {
        finish();
    }
    // Accède à l'activité correspondant au choix coché
    public void valider(View bouton){
        Intent iii;
        if(rb_mot.isChecked()) {
            iii = new Intent(this, activity_apprentissage.class);
            iii.putExtra("choix","mot");
        }else if (rb_cat.isChecked())
            iii = new Intent(this,activity_apprentissage_categorie.class);
        else
            iii = new Intent(this,activity_liste_apprentissage.class);
        startActivity(iii);
    }

    // Si une liste est sauvegardé(si au moins un des valeurs a_prendre d'un mot est a vrai), on va directement à l'activité d'apprentissage
    public void ifSave(View bouton){
        if(mdb.ifSaveList()){
            Intent iii = new Intent(this, activity_apprentissage.class);
            iii.putExtra("choix","liste");
            startActivity(iii);
        } else
            Toast.makeText(this,"Aucune liste a été sauvegardée", Toast.LENGTH_LONG).show();

    }
    }