package com.example.alejandroanzures.conversa_tec;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class Ajustes extends AppCompatActivity {

    clasesDB db;

    Button BTNColorF;
    Button BTNColorT;
    Button BTNColorP;
    Button BTNTamaT;

    String HEXColorFondo="";
    String HEXColorTexto="";
    String HEXColorPregunta="";
    String TamañoTexto="";
    SharedPreferences prefs;
    AppCompatActivity padre=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_configuracion);

        BTNColorF=(Button)findViewById(R.id.buttonAjCFondo);
        BTNColorF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputFColor message=new InputFColor();
                //InfoDialogMainActivityFragment message=new InfoDialogMainActivityFragment();
                message.setTitleMessage("","",R.drawable.ic_configuracion,padre,true);
                message.show(getSupportFragmentManager(),"Información");
            }
        });
        BTNColorT=(Button)findViewById(R.id.buttonAjCTexto);
        BTNColorT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputTColor message=new InputTColor();
                //InfoDialogMainActivityFragment message=new InfoDialogMainActivityFragment();
                message.setTitleMessage("","",R.drawable.ic_configuracion,padre,true);
                message.show(getSupportFragmentManager(),"Información");
            }
        });
        BTNColorP=(Button)findViewById(R.id.buttonAjCPregunta);
        BTNColorP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputPColor message=new InputPColor();
                //InfoDialogMainActivityFragment message=new InfoDialogMainActivityFragment();
                message.setTitleMessage("","",R.drawable.ic_configuracion,padre,true);
                message.show(getSupportFragmentManager(),"Información");
            }
        });
        BTNTamaT=(Button)findViewById(R.id.buttonAjTTexto);
        BTNTamaT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogoTexto();
            }
        });

        prefs =getSharedPreferences("ConversaTEC", Context.MODE_PRIVATE);
        HEXColorFondo=prefs.getString("colorFondo", "#e1c694");
        HEXColorTexto=prefs.getString("colorTexto", "#020202");
        HEXColorPregunta=prefs.getString("colorPregunta", "#c62828");
        TamañoTexto=prefs.getString("tamañoTexto", "20");
        setColors();
    }



    public void setColors()
    {
        int ColorF= Color.parseColor(HEXColorFondo);
        BTNColorF.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(ColorF, PorterDuff.Mode.SRC));
        BTNColorF.setBackgroundColor(ColorF);
        ColorF= Color.parseColor(HEXColorTexto);
        BTNColorT.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(ColorF, PorterDuff.Mode.SRC));
        BTNColorT.setBackgroundColor(ColorF);
        ColorF= Color.parseColor(HEXColorPregunta);
        BTNColorP.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(ColorF, PorterDuff.Mode.SRC));
        BTNColorP.setBackgroundColor(ColorF);

        BTNTamaT.setText(TamañoTexto);
    }

    public void setHEXColorFondo(String Color)
    {
        HEXColorFondo=Color;
    }
    public void setHEXColorTexto(String Color)
    {
        HEXColorTexto=Color;
    }
    public void setHEXColorPregunta(String Color)
    {
        HEXColorPregunta=Color;
    }

    public void guardarAjustes(View view)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("colorFondo", HEXColorFondo);
        editor.putString("colorTexto", HEXColorTexto);
        editor.putString("colorPregunta", HEXColorPregunta);
        editor.putString("tamañoTexto", TamañoTexto);
        editor.commit();

        Snackbar.make(view, "Guardando Cambios", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void dialogoTexto()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_configuracion);
        builder.setTitle("Tamaño de Texto");


        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setText(TamañoTexto);
        builder.setView(input);


        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TamañoTexto = input.getText().toString();
                setColors();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
