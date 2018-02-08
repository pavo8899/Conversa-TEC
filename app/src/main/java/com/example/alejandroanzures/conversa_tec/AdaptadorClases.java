package com.example.alejandroanzures.conversa_tec;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lNFORMATICA on 08/02/2018.
 */

public class AdaptadorClases extends BaseAdapter{
    private Context context;
    private ArrayList<itemClase> listItems;

    public AdaptadorClases(Context context, ArrayList<itemClase> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int i) {
        return listItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        itemClase item=(itemClase)getItem(i);

        view= LayoutInflater.from(context).inflate(R.layout.item_clase,null);
        TextView txt1=(TextView)view.findViewById(R.id.textViewitemclase1);
        TextView txt2=(TextView)view.findViewById(R.id.textViewitemclase2);
        TextView txt3=(TextView)view.findViewById(R.id.textViewitemclase3);
        TextView txt4=(TextView)view.findViewById(R.id.textViewitemclase4);

        txt1.setText(item.getCOLUMNA_ID());
        txt2.setText(item.getCOLUMNA_CLASE());
        txt3.setText(item.getCOLUMNA_FECHA());
        txt4.setText(item.getCOLUMNA_HORA());
        return view;
    }


}
