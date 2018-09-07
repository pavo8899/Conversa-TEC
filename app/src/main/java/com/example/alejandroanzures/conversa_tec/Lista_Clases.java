package com.example.alejandroanzures.conversa_tec;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Lista_Clases extends AppCompatActivity {
    private ListView items;
    private AdaptadorClases adaptador;
    ArrayList<itemClase> lstitem;

    Button Fecha;
    Spinner Topico;

    Lista_Clases padre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clases);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.historial);
        padre=this;

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

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        //Lista de Topicos en DropBox
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("MATEMATICAS");
        spinnerArray.add("PROGRAMACIÓN ORIENTADA A OBJETOS");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Topico = (Spinner) findViewById(R.id.textEditTopic);
        Topico.setAdapter(adapter);

        //Accediendo a Calendario
        Fecha =(Button)findViewById(R.id.textEditDate);
        Fecha.setText(new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTimeInMillis()));
        Fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Input_Date_Dialog message=new Input_Date_Dialog();
                message.setTitleMessage("","",R.drawable.ic_lista_clases,padre,true);
                try {
                    message.setCalendarViewDate((new SimpleDateFormat("dd/MM/yyyy")).parse(Fecha.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                message.show(getSupportFragmentManager(),"Información");
            }
        });
    }

    public void setDate(String date)
    {
        Fecha.setText(date);
    }

}
