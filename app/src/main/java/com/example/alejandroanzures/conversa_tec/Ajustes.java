package com.example.alejandroanzures.conversa_tec;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class Ajustes extends AppCompatActivity {

    clasesDB db;

    Button BTNColorF;
    String HEXColorFondo="";

    AppCompatActivity padre=this;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db=new clasesDB(this);
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
        prefs =getSharedPreferences("ConversaTEC", Context.MODE_PRIVATE);

        HEXColorFondo=prefs.getString("colorFondo", "#e1c694");

        setColors();
    }



    public void setColors()
    {
        int ColorF= Color.parseColor(HEXColorFondo);
        BTNColorF.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(ColorF, PorterDuff.Mode.SRC));
    }

    public void setHEXColorFondo(String Color)
    {
        HEXColorFondo=Color;
    }

    public void guardarAjustes(View view)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("colorFondo", HEXColorFondo);
        editor.commit();

        Snackbar.make(view, "Guardando Cambios", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
