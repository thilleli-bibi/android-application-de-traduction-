package fr.bibi.apprentissagelangue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class activity_test_couple_mots extends AppCompatActivity {
    public MyDataBase mdb;
    private TextView resultat;

    private Spinner chooseWord;
    private Spinner chooseWord2;

    private String motChoisi;
    private String motChoisi2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_couple_mots);

        Intent intent = getIntent();

        mdb = new MyDataBase(this);

        resultat = findViewById(R.id.ed_resultat);

        chooseWord = findViewById(R.id.sp_word_mot);
        chooseWord2 = findViewById(R.id.sp_word_traduction);


        ArrayList<String> al = new ArrayList<>();
        Cursor curs = mdb.getApprendreTable();
        boolean b = curs.moveToFirst();
        while(b){
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

        ArrayList<String> lt = new ArrayList<>();
        ArrayList<String> liste = new ArrayList<>();


        Cursor curs2 = mdb.getApprendreTable();
        boolean b2 = curs2.moveToFirst();
        while(b2){
            lt.add(curs2.getString(curs2.getColumnIndexOrThrow(mdb.COLONNE_TRADUCTION)));
            b2 = curs2.moveToNext();
        }
        curs2.close();

        //construire une liste des traductions désordonnéé
        for (int i=lt.size()-1;i>0;i--)
        {
            if (!lt.get(i).equals(" ") && (lt.get(i) != null))
                    liste.add(lt.get(i));
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,liste);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseWord2.setAdapter(adapter);
        chooseWord2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                motChoisi2 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
   //verifier si la traduction chosie est correcte
    public void verifier(View bouton){


        Cursor curseur = mdb.getMot(motChoisi);
        String result = "";

             System.out.println(motChoisi2);
        System.out.println(result);

        boolean bool=false;

        if (curseur == null)
            result="erreur";
        else if (curseur.equals(" "))
            result="le mot n'existe pas dans la base";

        if (curseur.moveToFirst())
            result = curseur.getString(curseur.getColumnIndexOrThrow(mdb.COLONNE_TRADUCTION));

        String[] s = result.split(",");
        String[] sChoisi = motChoisi2.split(",");  //recuperer chaque traduction


        if (result.equals(" "))
            resultat.setText("il existe pas des traductions pour " + motChoisi);


        else
          {
              int i=0;
              int j=0;

                while (!bool && j<sChoisi.length){
                  while (!bool && i < s.length) {
                      if (s[i].equalsIgnoreCase(sChoisi[j])){
                          bool = true;
                          resultat.setText("bravo! votre réponse est juste");
                          break;
                      }
                      i++;
                  } j++;

              }

              if (!bool)
                  resultat.setText("dommage! votre réponse est fausse,réesayer");

          }

        Log.i("DATABASE","verifier_traduction invoked");

    }

}
