package com.example.alejandroanzures.conversa_tec;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class listadeclases extends AppCompatActivity {
    private ListView items;
    private AdaptadorClases adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listadeclases);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_lista_clases);

        clasesDB db=new clasesDB(this);
        ArrayList<itemClase> lstitem=db.listadeClases();
        items=(ListView) findViewById(R.id.listaclases);
        adaptador=new AdaptadorClases(this,lstitem);
        items.setAdapter(adaptador);
    }

}
