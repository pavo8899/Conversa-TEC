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
    private static final String SQL_CREAR = "create table " + TABLA_SPEECHCLASE
            + "(" + COLUMNA_ID  + " integer primary key autoincrement, "
            + COLUMNA_CLASE + " text,"
            + COLUMNA_HORA + " text,"
            + COLUMNA_SPEECH + " text,"
            + COLUMNA_FECHA +" default CURRENT_DATE);";

    public clasesDB(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(SQL_CREAR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
    }

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
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_SPEECH, Speech);
        db.update(TABLA_SPEECHCLASE,values,"_id="+getID(),null);
        db.close();
    }

    public List<String> listadeClases()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMNA_ID, COLUMNA_CLASE,COLUMNA_HORA,COLUMNA_SPEECH,COLUMNA_FECHA};

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

        List<String> speech=new ArrayList<String>();
        if(cursor.moveToFirst())
        {
            do {
                speech.add(cursor.getString(1));
            }while (cursor.moveToNext());
        }
        db.close();
        return speech;

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
}

