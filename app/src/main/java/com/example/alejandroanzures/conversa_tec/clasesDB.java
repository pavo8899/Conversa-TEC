package com.example.alejandroanzures.conversa_tec;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.Calendar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lNFORMATICA on 08/11/2017.
 */

public class clasesDB extends SQLiteOpenHelper
{

    //Version y nombre de la Base de Datos
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "clasesConversa-TEC.db";

    //Tabla Clase
    public static final String TABLA_SPEECHCLASE = "speechclase";
    public static final String COLUMNA_ID = "_id";
    public static final String COLUMNA_CLASE = "clase";
    public static final String COLUMNA_HORA = "hora";
    public static final String COLUMNA_SPEECH = "speech";
    public static final String COLUMNA_FECHA = "fecha";
    private static final String SQL_CREAR_SPEECHCLASE = "create table " + TABLA_SPEECHCLASE
            + "(" + COLUMNA_ID  + " integer primary key autoincrement, "
            + COLUMNA_CLASE + " text,"
            + COLUMNA_HORA + " text,"
            + COLUMNA_SPEECH + " text,"
            + COLUMNA_FECHA +" default CURRENT_DATE);";

    //Tabla Clase
    public static final String TABLA_AJUSTES = "ajustes";
    public static final String COLUMNA_A_CFONDO = "cfondo";
    public static final String COLUMNA_A_CFUENTE = "cfuente";
    public static final String COLUMNA_A_CPREGUNTA = "cpregunta";
    public static final String COLUMNA_A_TTEXTO = "ttexto";
    private static final String SQL_CREAR_AJUSTES = "create table " + TABLA_AJUSTES
            + "(" + COLUMNA_A_CFONDO  + " text, "
            + COLUMNA_A_CFUENTE + " text,"
            + COLUMNA_A_CPREGUNTA + " text,"
            + COLUMNA_A_TTEXTO + " text);";

    public clasesDB(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(SQL_CREAR_SPEECHCLASE);
        sqLiteDatabase.execSQL(SQL_CREAR_AJUSTES);

        /*SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMNA_A_CFONDO, "#E1C694");
        values.put(COLUMNA_A_CFUENTE, "#020202");
        values.put(COLUMNA_A_CPREGUNTA, "#c62828");
        values.put(COLUMNA_A_TTEXTO, "20sp");

        db.insert(TABLA_AJUSTES, null,values);
        db.close();*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
    }

    //Metodos TABLA CLASE
    public void crearClase(String CLASE,String HORA)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        //db.execSQL("Insert into "+TABLA_SPEECHCLASE+"("+COLUMNA_SPEECH+","+COLUMNA_FECHA+") "+);
        values.put(COLUMNA_CLASE, CLASE);
        values.put(COLUMNA_HORA, HORA);
        values.put(COLUMNA_SPEECH, "");
        //values.put(COLUMNA_FECHA,String.format());

        db.insert(TABLA_SPEECHCLASE, null,values);
        db.close();
    }

    public void insertSpeech(String Speech)
    {
        int id=getID();

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMNA_SPEECH, Speech);

        db.update(TABLA_SPEECHCLASE,values,"_id="+id,null);
        db.close();
    }

    public ArrayList<itemClase> listadeClases()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMNA_ID, COLUMNA_CLASE,COLUMNA_HORA,COLUMNA_FECHA};

        Cursor cursor =
                db.query(
                        TABLA_SPEECHCLASE,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        COLUMNA_ID+" desc",
                        null);

        ArrayList<itemClase> listaclases=new ArrayList<itemClase>();
        if(cursor.moveToFirst())
        {
            do {
                //speech.add(cursor.getString(1));
                listaclases.add(
                  new itemClase(
                          cursor.getString(0),
                          cursor.getString(1),
                          cursor.getString(2),
                          cursor.getString(3)
                          )
                );
            }while (cursor.moveToNext());
        }
        db.close();
        return listaclases;

    }

    public int getID()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMNA_ID};

        Cursor cursor =
                db.query(
                        TABLA_SPEECHCLASE,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        COLUMNA_ID+" desc",
                        "1");

        int Id=0;
        if(cursor.moveToFirst())
        {
            Id= cursor.getInt(0);
        }
        else
        {

            Id= -1;
        }
        db.close();
        return  Id;
    }

    public String getNombreClase()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMNA_CLASE};

        Cursor cursor =
                db.query(
                        TABLA_SPEECHCLASE,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        COLUMNA_ID+" desc",
                        "1");

        String Clase="";
        if(cursor.moveToFirst())
        {
            Clase= cursor.getString(0);
        }
        else
        {

            Clase= "";
        }
        db.close();
        return  Clase;
    }

    public String getSpeechClase()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMNA_SPEECH};

        Cursor cursor =
                db.query(
                        TABLA_SPEECHCLASE,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        COLUMNA_ID+" desc",
                        "1");

        String Clase="";
        if(cursor.moveToFirst())
        {
            Clase= cursor.getString(0);
        }
        else
        {

            Clase= "";
        }
        db.close();
        return  Clase;
    }

    //METODOS TABLA AJUSTES

    public void modificarCFONDO(String CFONDO)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMNA_A_CFONDO, CFONDO);

        db.update(TABLA_AJUSTES,values,null,null);
        db.close();
    }

    public String getCFONDO()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMNA_A_CFONDO};

        Cursor cursor =
                db.query(
                        TABLA_AJUSTES,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        null,
                        "1");

        String HEXColor="";
        if(cursor.moveToFirst())
        {
            HEXColor= cursor.getString(0);
        }
        else
        {

            HEXColor= "#ffffff";
        }
        db.close();
        return  HEXColor;
    }
}
