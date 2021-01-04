package fr.bibi.apprentissagelangue;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class activity_liste_apprentissage extends ListActivity {

    private SimpleCursorAdapter listAdapt;
    private ArrayList<String> selectedList;
    private Button b_save;
    private MyDataBase mdb;

    private static final String authority = "fr.bibi.mycontentprovider";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_apprentissage);

        selectedList = new ArrayList<>();
        b_save = findViewById(R.id.b_save);
        mdb = new MyDataBase(this);

        listAdapt = new SimpleCursorAdapter(
                this, // Context.
                android.R.layout.simple_list_item_1,
                null,
                new String[]{"mot"},
                new int[]{android.R.id.text1}, 0);
        setListAdapter(listAdapt);

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(authority).appendPath("apprendre");
        final Uri uri = builder.build();
        Bundle bundle = new Bundle();
        bundle.putStringArray("projection", new String[]{"rowid as _id", "mot"});
        int NUMLOADER = 0;
        getLoaderManager().initLoader(NUMLOADER, bundle,
                new LoaderManager.LoaderCallbacks<Cursor>() {
                    @Override
                    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                        String[] projecion = args.getStringArray("projection");
                        return new CursorLoader(getApplicationContext(),
                                uri, projecion, null, null, null);
                    }

                    @Override
                    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                        listAdapt.swapCursor(data);
                        listAdapt.notifyDataSetChanged();
                    }

                    @Override
                    public void onLoaderReset(Loader<Cursor> loader) {
                        listAdapt.swapCursor(null);
                        listAdapt.notifyDataSetChanged();
                    }
                });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Cursor c = (Cursor) getListAdapter().getItem(position);
        String paysChoisi = (c.getString(c.getColumnIndex("mot"))).trim();
        if(!selectedList.contains(paysChoisi)) {
            v.setBackgroundColor(getResources().getColor(R.color.colorSelected));
            selectedList.add(paysChoisi);
        }else {
            v.setBackgroundColor(getResources().getColor(R.color.colorDeselected));
            selectedList.remove(paysChoisi);
        }
    }
    // Sauvegarde la liste en mettant à jour la colonne a_apprendre de la base
    public void Sauvegarder(View bouton){
        Intent iii = new Intent(this, activity_apprentissage.class);
        Cursor curseur = mdb.getApprendreTable();
        boolean b = curseur.moveToFirst();
        while(b){
            String mot = curseur.getString(curseur.getColumnIndexOrThrow(mdb.COLONNE_MOT));
            if(selectedList.contains(mot))
                mdb.saveOnList(mot);
            else
                mdb.NotSaveOnList(mot);
            b = curseur.moveToNext();
        }
        if(selectedList.size() == 0)
            iii.putExtra("choix","mot");
        else
            iii.putExtra("choix","liste");
        startActivity(iii);
    }
}