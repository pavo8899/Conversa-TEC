package com.example.alejandroanzures.conversa_tec;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class VerClase extends AppCompatActivity {

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
        setContentView(R.layout.activity_ver_clase);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Item= (itemClase) getIntent().getSerializableExtra("objetoData");

        //Elementos del Layout
        txtvCurrentSpeech=(TextView) findViewById(R.id.txtvCurrentSpeech);
        txtvCurrentSpeech.setMovementMethod(new ScrollingMovementMethod());
        //Base de Datos
        DB=new clasesDB(this);
        setTitle("CT: "+DB.getNombreClasebyId(Item.getCOLUMNA_ID()));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_libreta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            Toast toast = Toast.makeText(this.getApplicationContext(), "Are you sure?", Toast.LENGTH_SHORT);
            toast.show();
            return true;
        }
        if (id == R.id.action_pdf) {
            Toast toast = Toast.makeText(this.getApplicationContext(), "Guardar como PDF?", Toast.LENGTH_SHORT);
            toast.show();
            return true;
        }
        if (id == R.id.action_rtf) {
            Toast toast = Toast.makeText(this.getApplicationContext(), "Guardar como RTF?", Toast.LENGTH_SHORT);
            toast.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
