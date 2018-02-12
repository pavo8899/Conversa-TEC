package com.example.alejandroanzures.conversa_tec;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Alejandro Anzures on 07/11/2017.
 */

public class InputClaseName extends DialogFragment  {
    String Title="",Content="";
    int ico=0;
    AppCompatActivity padre;
    boolean Open=true;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View dialog=inflater.inflate(R.layout.clasename_dialog, null);

        final EditText tmp=(EditText) dialog.findViewById(R.id.dialogClaseNameNombre);
        final TextView tmp1=(TextView)dialog.findViewById(R.id.dialogClaseNameFecha);
        tmp1.setText(new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()));
        final TextView tmp2=(TextView)dialog.findViewById(R.id.dialogClaseNameHora);
        tmp2.setText(new SimpleDateFormat("hh:00 a").format(Calendar.getInstance().getTime()));
        builder.setView(dialog)
                .setIcon(ico)
                .setTitle("Nombre de la Clase")
                // Add action buttons
                .setPositiveButton("Crear Clase", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        try {
                            if(Open) {
                                clasesDB bd=new clasesDB(padre);
                                bd.crearClase(tmp.getText().toString(),tmp2.getText().toString());
                                Intent clase_directa = new Intent(padre, Clase_Directa.class);
                                startActivity(clase_directa);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        return builder.create();
    }

    public void setTitleMessage(String Title, String Content, int ico, AppCompatActivity padre,boolean Open)
    {
        this.ico=ico;
        this.padre=padre;
        this.Open= Open;
    }
}
