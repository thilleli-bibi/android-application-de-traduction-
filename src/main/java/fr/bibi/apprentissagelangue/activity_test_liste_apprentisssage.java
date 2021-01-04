package fr.bibi.apprentissagelangue;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class activity_test_liste_apprentisssage extends AppCompatActivity {
    private EditText mot;
    private EditText traduction;
    public MyDataBase mdb;
    private TextView resultat;

    private Spinner chooseWord;

    private String motChoisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_liste_apprentisssage);

        mdb = new MyDataBase(this);
        traduction = findViewById(R.id.ed_traduction_test_liste_apprentissage);

        resultat = findViewById(R.id.ed_resultat_test_liste_apprentissage);
        chooseWord = findViewById(R.id.sp_word_test_liste_apprentissage);



        //la liste il faut  qu'il sera recuperee a partir de la liste d'apprentissage(activite precedente)
        ArrayList<String> al = new ArrayList<>();
        Cursor curs = mdb.getApprendreTable();
        boolean b = curs.moveToFirst();
        if (mdb.ifSaveList()) {
            while (b) {
                String apprendre = curs.getString(curs.getColumnIndexOrThrow(mdb.COLONNE_MOT));
                if (mdb.isSaved(apprendre))
                    al.add(curs.getString(curs.getColumnIndexOrThrow(mdb.COLONNE_MOT)));
                b = curs.moveToNext();
            }
            curs.close();
        }
        else
            resultat.setText("Vous avez pas choisi une liste d'apprentissage" + "\n" + "vous pouvez choisir une liste en accedant à <choix apprentissage>");


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

    public void verifier_traduction_app(View bouton) {

        //mot.getText().toString();
        //Cursor curseur = mdb.getMot(mot.getText().toString());
        Cursor curseur = mdb.getMot(motChoisi);
        String result = "";


        if (curseur == null)
            result = "erreur";
        else if (curseur.equals(" "))
            result = "le mot n'existe pas dans la base";

        if (curseur.moveToFirst())
            result = curseur.getString(curseur.getColumnIndexOrThrow(mdb.COLONNE_TRADUCTION));


        String[] s = result.split(",");
        boolean bool = false;

        if (result.equals(" "))
            resultat.setText("il existe pas des traductions pour " + motChoisi);

        else
        {
            int i=0;
             while (!bool && i<s.length) {
                if (s[i].equalsIgnoreCase(traduction.getText().toString())) {
                bool = true;
            }
            i++;
            }
            if(bool)
            resultat.setText("bravo! votre réponse est juste");
            else
            resultat.setText("dommage! votre réponse est fausse,réesayer");

    }


        Log.i("DATABASE", "verifier_traduction invoked");
    }
    }
