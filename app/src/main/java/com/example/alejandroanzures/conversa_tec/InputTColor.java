package com.example.alejandroanzures.conversa_tec;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

/**
 * Created by lNFORMATICA on 12/02/2018.
 */

public class InputTColor extends DialogFragment {
    String Title="",Content="";
    int ico=0;
    AppCompatActivity padre;
    boolean Open=true;
    SharedPreferences prefs;

    RadioButton rb1,rb2,rb3;

    //
    String HEXColor="#020202";
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
        View dialog=inflater.inflate(R.layout.tcolor_dialog, null);
        rb1=(RadioButton)dialog.findViewById(R.id.radioButtonFColor1);
        rb2=(RadioButton)dialog.findViewById(R.id.radioButtonFColor2);
        rb3=(RadioButton)dialog.findViewById(R.id.radioButtonFColor3);


        builder.setView(dialog)
                .setIcon(ico)
                .setTitle("Color de Texto")
                // Add action buttons
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        try {
                                //clasesDB bd=new clasesDB(padre);
                                //bd.modificarCFONDO(HEXColor);
                            if(rb1.isChecked())
                                HEXColor="#020202";
                            if(rb2.isChecked())
                                HEXColor="#353b44";

                            ((Ajustes)padre).setHEXColorTexto(HEXColor);

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
}
