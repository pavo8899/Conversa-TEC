package com.example.alejandroanzures.conversa_tec;

import java.io.Serializable;

/**
 * Created by lNFORMATICA on 08/02/2018.
 */

public class itemClase implements Serializable {
    public  String COLUMNA_CLASE;
    public  String COLUMNA_ID;
    public  String COLUMNA_HORA;
    public  String COLUMNA_FECHA;

    public itemClase(String COLUMNA_ID,String COLUMNA_CLASE, String COLUMNA_HORA, String COLUMNA_FECHA) {
        this.COLUMNA_ID = COLUMNA_ID;
        this.COLUMNA_CLASE = COLUMNA_CLASE;
        this.COLUMNA_HORA = COLUMNA_HORA;
        this.COLUMNA_FECHA = COLUMNA_FECHA;
    }

    public String getCOLUMNA_CLASE()
    {
        return COLUMNA_CLASE;
    }

    public String getCOLUMNA_ID() {
        return COLUMNA_ID;
    }

    public String getCOLUMNA_HORA() {
        return COLUMNA_HORA;
    }

    public String getCOLUMNA_FECHA() {
        return COLUMNA_FECHA;
    }
}
