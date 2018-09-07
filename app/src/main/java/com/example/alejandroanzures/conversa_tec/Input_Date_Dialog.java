package com.example.alejandroanzures.conversa_tec;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Input_Date_Dialog extends DialogFragment {
    String Title="",Content="";
    Calendar date;
    Date fecha;
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
        View dialog=inflater.inflate(R.layout.date_dialog, null);


        final CalendarView tmp=(CalendarView) dialog.findViewById(R.id.calendarView1);
        tmp.setDate(fecha.getTime());
        tmp.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                date= Calendar.getInstance();
                date.set(Calendar.YEAR, year);
                date.set(Calendar.MONTH, month);
                date.set(Calendar.DATE, dayOfMonth);

            }
        });
        builder.setView(dialog)
                .setIcon(ico)
                .setTitle("Seleccione la Fecha:")
                // Add action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        try {
                            if(Open) {
                                ((Lista_Clases)padre).setDate( new SimpleDateFormat("dd/MM/yyyy").format(date.getTimeInMillis()));
                                //((Lista_Clases)padre).setDate(Date);
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

    public void setCalendarViewDate(Date fecha)
    {
        this.fecha=fecha;
    }
}
