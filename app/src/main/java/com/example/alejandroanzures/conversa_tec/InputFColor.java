package com.example.alejandroanzures.conversa_tec;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by lNFORMATICA on 12/02/2018.
 */

public class InputFColor extends DialogFragment {
    String Title="",Content="";
    int ico=0;
    AppCompatActivity padre;
    boolean Open=true;
    SharedPreferences prefs;

    RadioButton rb1,rb2,rb3;

    //
    String HEXColor="#e1c694";
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Inicializando variables
        Context context = getActivity();
        prefs =context.getSharedPreferences("ConversaTEC", Context.MODE_PRIVATE);

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View dialog=inflater.inflate(R.layout.fcolor_dialog, null);
        rb1=(RadioButton)dialog.findViewById(R.id.radioButtonFColor1);
        rb2=(RadioButton)dialog.findViewById(R.id.radioButtonFColor2);
        rb3=(RadioButton)dialog.findViewById(R.id.radioButtonFColor3);


        builder.setView(dialog)
                .setIcon(ico)
                .setTitle("Color de Fondo")
                // Add action buttons
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        try {
                                //clasesDB bd=new clasesDB(padre);
                                //bd.modificarCFONDO(HEXColor);
                            if(rb1.isChecked())
                                HEXColor="#e1c694";
                            if(rb2.isChecked())
                                HEXColor="#C5D5E2";
                            if(rb3.isChecked())
                                HEXColor="#FaFaFa";

                            ((Ajustes)padre).setHEXColorFondo(HEXColor);

                            ((Ajustes)padre).setColors();

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

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButtonFColor1:
                HEXColor="#e1c694";
                    break;
            case R.id.radioButtonFColor2:
                HEXColor="#C5D5E2";
                break;
            case R.id.radioButtonFColor3:
                HEXColor="#F6F6F6";
                break;
        }
    }
}
