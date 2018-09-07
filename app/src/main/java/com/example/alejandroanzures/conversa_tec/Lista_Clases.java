package com.example.alejandroanzures.conversa_tec;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;

import java.util.ArrayList;

public class Lista_Clases extends AppCompatActivity {
    private ListView items;
    private AdaptadorClases adaptador;
    ArrayList<itemClase> lstitem;

    CalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_de_clases);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.historial);

        clasesDB db=new clasesDB(this);
        lstitem=db.listadeClases();
        items=(ListView) findViewById(R.id.listaclases);
        adaptador=new AdaptadorClases(this,lstitem);
        items.setAdapter(adaptador);

        items.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,View view, int Position, long id)
            {
                Intent verclase=new Intent(Lista_Clases.this,VerClase.class);
                verclase.putExtra("objetoData",lstitem.get(Position));
                startActivity(verclase);
            }
        });


    }

}
