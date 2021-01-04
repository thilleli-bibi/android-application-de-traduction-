package fr.bibi.apprentissagelangue;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyDataBase {
    private static String authority = "fr.bibi.mycontentprovider";
    private ContentResolver ContRes;

    public final static String DB_NAME = "base_apprendre";
    public final static String TABLE_APPRENDRE = "apprendre";
    public final static String COLONNE_MOT = "mot";
    public final static String COLONNE_TRADUCTION = "traduction";
    public final static String COLONNE_CATEGORIE = "categorie";
    public final static String COLONNE_WEB = "adresse_web";
    public final static String COLONNE_APPAREIL = "adresse_appareil";
    public final static String COLONNE_SON = "son";
    public final static String COLONNE_A_APPRENDRE = "a_apprendre";

    public MyDataBase(Context context){
        ContRes = context.getContentResolver();
    }

    //Retourne toutes les lignes de la table
    public Cursor getApprendreTable(){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(authority).appendEncodedPath("apprendre");
        Uri uri = builder.build();

        Cursor curs = ContRes.query(uri,
                new String[]{"rowid as _id",COLONNE_MOT,COLONNE_TRADUCTION,COLONNE_CATEGORIE,COLONNE_WEB,COLONNE_APPAREIL,COLONNE_SON,COLONNE_A_APPRENDRE}, //modification :ajout colonne a apprendre
                null,
                null,
                null);
        return curs;
    }
    //Ajoute un mot dans la table s'il y est pas
    public boolean ajoutDico(String mot, String traduction, String categorie, String web, String appareil, String son){
        ContentValues cv = new ContentValues();
        cv.put(COLONNE_MOT, mot);
        cv.put(COLONNE_TRADUCTION, traduction);
        cv.put(COLONNE_CATEGORIE, categorie);
        cv.put(COLONNE_WEB, web);
        cv.put(COLONNE_APPAREIL, appareil);
        cv.put(COLONNE_SON, son);
        cv.put(COLONNE_A_APPRENDRE, false);  //rajoutéé

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(authority).appendPath("apprendre");
        Uri uri = builder.build();

        uri = ContRes.insert(uri,cv);

        long res = ContentUris.parseId(uri);
        if(res == -1)
            return false;
        return true;
    }

    /*Remplissage de la base de données*/
    public void init(){
        ajoutDico("Tortue","Turtle","Animaux","https://upload.wikimedia.org/wikipedia/commons/thumb/8/8f/Tortue_g%C3%A9ante.jpg/330px-Tortue_g%C3%A9ante.jpg",null,null);
        ajoutDico("Lièvre","Hare","Animaux","https://upload.wikimedia.org/wikipedia/commons/thumb/4/42/Li%C3%A8vre_en_%C3%A9t%C3%A9.jpg/435px-Li%C3%A8vre_en_%C3%A9t%C3%A9.jpg",null,null);
        ajoutDico("Eucalyptus","Eucalyptus","Plantes","https://upload.wikimedia.org/wikipedia/commons/thumb/7/77/Eucalyptus_flowers2.jpg/435px-Eucalyptus_flowers2.jpg",null,null);
        ajoutDico("Epinard","Spinach","Légumes","https://www.marechal-fraicheur.fr/media/catalog/product/cache/7/image/1338x1338/c96a280f94e22e3ee3823dd0a1a87606/e/p/epinards.jpg",null,null);
        ajoutDico("Pomme","Apple","Fruits","https://media.gerbeaud.net/2017/01/640/pomme-detouree.jpg",null,null);
        ajoutDico("Genou","Knee","Corps humain","https://static.passeportsante.net/i74810-les-troubles-musculosquelettiques-du-genou.jpg",null,null);
        ajoutDico("Erable","Maple","Plantes","https://www.jardindupicvert.com/4239-large_default/erable-a-sucre.jpg",null,null);
        ajoutDico("Ordinateur","Computer","Objets","https://img-4.linternaute.com/-lcFal8QWpJXcGmytNG2-zuWWUw=/1240x/smart/6670675da1cc4c539926538636a0ecee/ccmcms-linternaute/10784129.jpg",null,null);
        ajoutDico("Mouchoir","Tissue","Objets","https://m.produitsentretien.fr/images/650x650/produit/_/boite-distributrice-de-100-mouchoirs-en-papier-2-epaisseurs_mouchoir-papier-boite-distributrice-a10030-1-.jpg",null,null);
        ajoutDico("Affiche","Poster","Objets",null,null,null);
        ajoutDico("Lampe","Lamp","Objets","https://cdn.habitat.fr/thumbnails/product/877/877233/box/1200/1200/80/bobby-lampe-de-bureau-en-acier-noir_877233.jpg",null,null);
        ajoutDico("Banane","Banana","Fruits","https://www.notretemps.com/cache/com_zoo_images/a4/bananes-bienfaits_1acc1fe0baad52c0a268516cac840346.jpg",null,null);
        ajoutDico("Ruban","Ribbon","Objets","https://image.shutterstock.com/image-vector/decorative-red-bow-horizontal-ribbon-260nw-724479184.jpg",null,null);
        ajoutDico("Peluche","Plush","Objets",null,null,null);
        ajoutDico("Violet","Purple","Couleurs","https://www.jerome-dreyfuss.com/media/fcustomblock/v/e/veau_violet.jpg",null,null);
        ajoutDico("Jaune","Yellow","Couleurs","https://www.apyart.com/1085-thickbox_default/peinture-pour-artiste-jaune-colza.jpg",null,null);
        ajoutDico("Aubergine","Eggplant","Légumes","https://www.biendecheznous.be/sites/default/files/styles/image_on_detailpage/public/ps_image/istock_aubergine.jpg?itok=bSr_QuF-",null,null);
        ajoutDico("Cerveau","Brain","Corps humain",null,null,null);
        ajoutDico("Peigne","Comb","Objets","https://static1.bleulibellule.com/5846-thickbox_default/peigne-a-manche-noir.jpg",null,null);
        ajoutDico("Poumon","Lung","Corps humain","https://www.pourquoidocteur.fr/media/article/ggl1200_istock-953787016-1555320517.jpg",null,null);
        ajoutDico("Renard","Fox","Animaux",null,null,null);
        ajoutDico("Oignon","Onion","Légumes",null,null,null);
        ajoutDico("Pamplemousse","Grapefruit","Fruits",null,null,null);
        ajoutDico("Pastèque","Watermelon","Fruits","https://www.lesfruitsetlegumesfrais.com/_upload/cache/ressources/produits/pasteque/pasteque_346_346_filled.jpg",null,null);
        ajoutDico("Pied","Foot","Corps humain",null,null,null);
        ajoutDico("Corne","Horn","Objets",null,null,null);
        ajoutDico("Estomac","Stomach","Corps humain",null,null,null);
        ajoutDico("Ecureil","Squirrel","Animaux","https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Nutkin.jpg/1200px-Nutkin.jpg",null,null);
        ajoutDico("Montre","Watch","Objets","https://www.bijourama.com/media/produits/maserati/img/montre-maserati--homme-r8871134003_r8871134003_680x680.jpg",null,null);
        ajoutDico("Livre","Book","Objets",null,null,null);
        ajoutDico("Elfe","Elf","Surnaturel","https://upload.wikimedia.org/wikipedia/commons/1/12/Tauriel_elf.jpg",null,null);
        ajoutDico("Licorne","Unicorn","Surnaturel",null,null,null);
        ajoutDico("Sirène","Mermaid","Surnaturel",null,null,null);
        ajoutDico("Fée","Fairy","Surnaturel","https://previews.123rf.com/images/sivanova/sivanova1701/sivanova170100026/70385500-silhouette-de-la-belle-f%C3%A9e-dessin%C3%A9-%C3%A0-la-main-isol%C3%A9-sur-fond-blanc-.jpg",null,null);
        ajoutDico("Fantome","Ghost","Surnaturel","https://gaite-lyrique.net/storage/2016/02/02/extra_2_160202_cover.jpg",null,null);
        ajoutDico("Sorcier","Wizard","Surnaturel","https://image.freepik.com/vecteurs-libre/vieux-sorcier-tenant-livre-magique_29190-4287.jpg",null,null);
        ajoutDico("Vert","Green","Couleurs",null,null,null);
        ajoutDico("Marron","Brown","Couleurs","https://www.sacres-coupons.com/images/Image/peau-veau-couleur-marron-fauve.jpg",null,null);
        ajoutDico("Manteau","Coat","Objets",null,null,null);
        ajoutDico("Pouce","Thumb","Corps humain",null,null,null);
        ajoutDico("Colle","Glue","Objets",null,null,null);
        ajoutDico("Crochet","Hook","Objets","https://images-na.ssl-images-amazon.com/images/I/3159LtPmt7L._SY300_QL70_.jpg",null,null);
        ajoutDico("Lézard","Lizard","Animaux","https://www.antiopa.info/images/publie/1466335510.jpg",null,null);
        ajoutDico("Cerise","Cherry","Fruits",null,null,null);
        ajoutDico("Poire","Perry","Fruits","https://www.lesfruitsetlegumesfrais.com/_upload/cache/ressources/produits/poire/poire_new_346_346_filled.jpg",null,null);
        ajoutDico("Vouivre","Wyvern","Surnaturel",null,null,null);
        ajoutDico("Orteil","Toe","Corps humain",null,null,null);
        ajoutDico("Coude","Elbow","Corps humain",null,null,null);
        ajoutDico("Démon","Fiend","Surnaturel","http://img.over-blog-kiwi.com/0/22/73/08/201301/ob_ae4a034bbb2b695f141177ea147e9228_demon.png",null,null);
        ajoutDico("Chien","Dog","Animaux","https://img.bfmtv.com/c/1000/600/0190734/b58def2ec4c796f65ae44d7cf.jpeg",null,null);

    }

    /*Récupère la ligne dans la table contenant le mot ou null sinon*/
    public Cursor getMot(String mot){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(authority).appendEncodedPath("apprendre");
        Uri uri = builder.build();

        Cursor curs = ContRes.query(uri,
                new String[]{COLONNE_MOT,COLONNE_TRADUCTION,COLONNE_CATEGORIE,COLONNE_WEB,COLONNE_APPAREIL,COLONNE_SON,COLONNE_A_APPRENDRE},//colonne appareil rajoutee
                COLONNE_MOT + " =?",
                new String[]{mot},
                null);
        return curs;
    }

    //mettre à jour les colonnes d'un mot null sinon
    public boolean updateMot(String mot, String traduction, String categorie, String web, String appareil, String son){
        ContentValues cv = new ContentValues();
        cv.put(COLONNE_MOT, mot);
        cv.put(COLONNE_TRADUCTION, traduction);
        cv.put(COLONNE_CATEGORIE, categorie);
        cv.put(COLONNE_WEB, web);
        cv.put(COLONNE_APPAREIL, appareil);
        cv.put(COLONNE_SON, son);

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(authority).appendPath("apprendre");
        Uri uri = builder.build();

        int up = ContRes.update(uri,cv,COLONNE_MOT + " =?", new String[]{mot});
        if(up == 0) return false;
        return true;
    }

    //ajouter une traduciton à un mot existant dans la base,renvoyer vrai si l'ajout a réussi faux sinon
    public boolean addTraduction(String mot, String traduction){
        Cursor curs = getMot(mot);
        if(curs == null) return false;
        curs.moveToFirst();
        String str = curs.getString(curs.getColumnIndexOrThrow(COLONNE_TRADUCTION));
        String[] tab = str.split(",");
        for(String trad : tab){
            if(traduction.equals(trad.trim()))   //trim = retirer les blancs du debut et fin de chaine
                return false;
        }
        ContentValues cv = new ContentValues();
        cv.put(COLONNE_MOT,mot);
        cv.put(COLONNE_TRADUCTION,curs.getString(curs.getColumnIndexOrThrow(COLONNE_TRADUCTION))+","+ traduction);
        cv.put(COLONNE_CATEGORIE,curs.getString(curs.getColumnIndexOrThrow(COLONNE_CATEGORIE)));
        cv.put(COLONNE_WEB,curs.getString(curs.getColumnIndexOrThrow(COLONNE_WEB)));
        cv.put(COLONNE_APPAREIL,curs.getString(curs.getColumnIndexOrThrow(COLONNE_APPAREIL)));
        cv.put(COLONNE_SON,curs.getString(curs.getColumnIndexOrThrow(COLONNE_SON)));

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(authority).appendPath("apprendre");
        Uri uri = builder.build();

        int up = ContRes.update(uri,cv,COLONNE_MOT + " =?", new String[]{mot});
        if(up == 0) return false;
        return true;
    }

    //supression toutes les traductions d'un mot donné en paramétre
    public boolean deleteTraduction (String mot)
    {
        Cursor curs = getMot(mot);
        if(curs == null) return false;

        curs.moveToFirst();
        ContentValues cv = new ContentValues();

        cv.put(COLONNE_MOT,mot);
        cv.put(COLONNE_TRADUCTION,(String) null);
        cv.put(COLONNE_CATEGORIE,curs.getString(curs.getColumnIndexOrThrow(COLONNE_CATEGORIE)));
        cv.put(COLONNE_WEB,curs.getString(curs.getColumnIndexOrThrow(COLONNE_WEB)));
        cv.put(COLONNE_APPAREIL,curs.getString(curs.getColumnIndexOrThrow(COLONNE_APPAREIL)));
        cv.put(COLONNE_SON,curs.getString(curs.getColumnIndexOrThrow(COLONNE_SON)));

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(authority).appendPath("apprendre");
        Uri uri = builder.build();

        int up = ContRes.update(uri,cv,COLONNE_MOT + " =?", new String[]{mot});
        if(up == 0) return false;


        return true;
    }

    //suppression d'une image sur l'appareil
    public boolean deleteImage (String mot,File dir){

        Cursor curs = getMot(mot);
        if(curs == null) return false;

        curs.moveToFirst();
        ContentValues cv = new ContentValues();


        String path = curs.getString(curs.getColumnIndexOrThrow(COLONNE_APPAREIL));

        cv.put(COLONNE_MOT,mot);
        cv.put(COLONNE_TRADUCTION,curs.getString(curs.getColumnIndexOrThrow(COLONNE_TRADUCTION)));
        cv.put(COLONNE_CATEGORIE,curs.getString(curs.getColumnIndexOrThrow(COLONNE_CATEGORIE)));
        cv.put(COLONNE_WEB,curs.getString(curs.getColumnIndexOrThrow(COLONNE_WEB)));
        //supprimer le chemin de la base
        cv.put(COLONNE_APPAREIL,(String) null);
        cv.put(COLONNE_SON,curs.getString(curs.getColumnIndexOrThrow(COLONNE_SON)));

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(authority).appendPath("apprendre");
        Uri uri = builder.build();

        //supprimer l'image de la mémoire de l'appareil
        //ce code supprime l'image des fichiers de l'emulateur

          /*String[] s = path.split("/");
          File file = new File(dir,s[s.length-1]);


          boolean deleted = file.delete();


          if (file.exists())
            {if (!deleted)
               return false;
            }

        int up = ContRes.update(uri,cv,COLONNE_MOT + " =?", new String[]{mot});
        if(up == 0) return false;*/


        return true;
    }

    //ajouter un lien vers une image sur le web
    public boolean addImageWeb(String mot, String web){
        Cursor curs = getMot(mot);
        if(curs == null) return false;
        curs.moveToFirst();
        ContentValues cv = new ContentValues();
        cv.put(COLONNE_MOT,mot);
        cv.put(COLONNE_TRADUCTION,curs.getString(curs.getColumnIndexOrThrow(COLONNE_TRADUCTION)));
        cv.put(COLONNE_CATEGORIE,curs.getString(curs.getColumnIndexOrThrow(COLONNE_CATEGORIE)));
        cv.put(COLONNE_WEB,web);
        cv.put(COLONNE_APPAREIL,curs.getString(curs.getColumnIndexOrThrow(COLONNE_APPAREIL)));
        cv.put(COLONNE_SON,curs.getString(curs.getColumnIndexOrThrow(COLONNE_SON)));

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(authority).appendPath("apprendre");
        Uri uri = builder.build();

        int up = ContRes.update(uri,cv,COLONNE_MOT + " =?", new String[]{mot});
        if(up == 0) return false;
        return true;
    }

    //ajouter le chemin vers une image sur l'appareil
    public boolean addImageAppareil(String mot, String appareil){
        Cursor curs = getMot(mot);
        if(curs == null) return false;
        curs.moveToFirst();
        ContentValues cv = new ContentValues();
        cv.put(COLONNE_MOT,mot);
        cv.put(COLONNE_TRADUCTION,curs.getString(curs.getColumnIndexOrThrow(COLONNE_TRADUCTION)));
        cv.put(COLONNE_CATEGORIE,curs.getString(curs.getColumnIndexOrThrow(COLONNE_CATEGORIE)));
        cv.put(COLONNE_WEB,curs.getString(curs.getColumnIndexOrThrow(COLONNE_WEB)));
        cv.put(COLONNE_APPAREIL,appareil);
        cv.put(COLONNE_SON,curs.getString(curs.getColumnIndexOrThrow(COLONNE_SON)));

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(authority).appendPath("apprendre");
        Uri uri = builder.build();

        int up = ContRes.update(uri,cv,COLONNE_MOT + " =?", new String[]{mot});
        if(up == 0) return false;
        return true;
    }
    //Ajoute un son present dans l'appareil pour le mot correspondant. Remplace le son s'il y en a un
    public boolean addSon(String mot, String son){
        Cursor curs = getMot(mot);
        if(curs == null) return false;
        curs.moveToFirst();
        ContentValues cv = new ContentValues();
        cv.put(COLONNE_MOT,mot);
        cv.put(COLONNE_TRADUCTION,curs.getString(curs.getColumnIndexOrThrow(COLONNE_TRADUCTION)));
        cv.put(COLONNE_CATEGORIE,curs.getString(curs.getColumnIndexOrThrow(COLONNE_CATEGORIE)));
        cv.put(COLONNE_WEB,curs.getString(curs.getColumnIndexOrThrow(COLONNE_WEB)));
        cv.put(COLONNE_APPAREIL,curs.getString(curs.getColumnIndexOrThrow(COLONNE_APPAREIL)));
        cv.put(COLONNE_SON,son);

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(authority).appendPath("apprendre");
        Uri uri = builder.build();

        int up = ContRes.update(uri,cv,COLONNE_MOT + " =?", new String[]{mot});
        if(up == 0) return false;
        return true;
    }

    //Retourne vrai si le mot est dans une catégorie
    public boolean isInCategory(String mot, String categorie){
        Cursor curseur = getMot(mot);
        if(curseur == null) return false;
        String cat = curseur.getString(curseur.getColumnIndexOrThrow(COLONNE_CATEGORIE));
        String[] tabCat = cat.split(",");
        for(String str : tabCat){
            if(categorie.equals(str.trim()))
                return true;
        }
        return false;
    }

    //Mets "a_apprendre" à vrai pour dire que le mot est sauvegarder dans la liste
    public boolean saveOnList(String mot){
        Cursor curs = getMot(mot);
        if(curs == null) return false;
        curs.moveToFirst();
        ContentValues cv = new ContentValues();
        cv.put(COLONNE_A_APPRENDRE,true);

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(authority).appendPath("apprendre");
        Uri uri = builder.build();
        int up = ContRes.update(uri,cv,COLONNE_MOT + " =?", new String[]{mot});
        if(up == 0) return false;
        return true;
    }

    //Mets "a_apprendre" à faux pour dire que le mot n'est pas sauvegarder dans la liste
    public boolean NotSaveOnList(String mot){
        Cursor curs = getMot(mot);
        if(curs == null) return false;
        curs.moveToFirst();
        ContentValues cv = new ContentValues();
        cv.put(COLONNE_A_APPRENDRE,false);

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(authority).appendPath("apprendre");
        Uri uri = builder.build();
        int up = ContRes.update(uri,cv,COLONNE_MOT + " =?", new String[]{mot});
        if(up == 0) return false;
        return true;
    }

    //Retourne vrai si le mot est bien sauvegarder dans la liste, faux sinon
    public boolean isSaved(String mot){
        Cursor curs = getMot(mot);
        curs.moveToFirst();
        return curs.getInt(curs.getColumnIndexOrThrow(COLONNE_A_APPRENDRE)) > 0;
    }

    //Retourne vrai s'il y a une liste de mot sauvegarder
    public boolean ifSaveList(){
        Cursor curs = getApprendreTable();
        boolean b = curs.moveToFirst();
        while(b){
            if(isSaved(curs.getString(curs.getColumnIndexOrThrow(COLONNE_MOT))))
                return true;
            b = curs.moveToNext();
        }
        return false;
    }


}
