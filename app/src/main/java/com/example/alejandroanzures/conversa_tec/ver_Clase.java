package com.example.alejandroanzures.conversa_tec;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

public class ver_Clase extends AppCompatActivity {

    private itemClase Item;

    //Elementos del layout
    TextView txtvCurrentSpeech;
    //Colores
    String ColorPregunta;
    String HEXColorFondo="";
    String HEXColorTexto="";
    String HEXColorPregunta="";
    String TamañoTexto="";
    SharedPreferences prefs;

    clasesDB DB,db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver__clase);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_clase_directa);

        Item= (itemClase) getIntent().getSerializableExtra("objetoData");

        //Elementos del Layout
        txtvCurrentSpeech=(TextView) findViewById(R.id.txtvCurrentSpeech);
        txtvCurrentSpeech.setMovementMethod(new ScrollingMovementMethod());
        //Base de Datos
        DB=new clasesDB(this);
        setTitle("Conversa-TEC: "+DB.getNombreClasebyId(Item.getCOLUMNA_ID()));
        txtvCurrentSpeech.setText(Html.fromHtml(DB.getSpeechClasebyId(Item.getCOLUMNA_ID())));

        SetSettings();
    }

    public void SetSettings()
    {
        prefs =getSharedPreferences("ConversaTEC", Context.MODE_PRIVATE);
        HEXColorFondo=prefs.getString("colorFondo", "#e1c694");
        HEXColorTexto=prefs.getString("colorTexto", "#020202");
        HEXColorPregunta=prefs.getString("colorPregunta", "#c62828");
        TamañoTexto=prefs.getString("tamañoTexto", "20");

        int color= Color.parseColor(HEXColorFondo);
        txtvCurrentSpeech.setBackgroundColor(color);

        color= Color.parseColor(HEXColorTexto);
        txtvCurrentSpeech.setTextColor(color);

        txtvCurrentSpeech.setTextSize(Float.parseFloat(TamañoTexto));

        ColorPregunta=HEXColorPregunta;
    }
}
