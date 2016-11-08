package solvit.com.scarlett.model;

import android.content.Context;
import android.util.Log;

import solvit.com.scarlett.web.Conector;

/**
 * Created by FranciscoPR on 19/01/16.
 */
public class Blind {

    public int id;
    public String nombre;
    int estado;
    double porcentaje;
    int numguia=0;// explicacion en Light.java
    Context context;
    Conector conector;
    String device;

    public Blind(int id, String nombre, int estado, double porcentaje,int numguia,String device,Context context)
    {
        this.id=id;
        this.nombre=nombre;
        this.estado=estado;
        this.porcentaje=porcentaje;
        this.numguia=numguia;
        this.context=context;
        this.device=device;
        conector = new Conector(context);
        conector.setVector();
    }
    public void changeEstado(int progress)
    {
        //valor_seekbar + (64*numguia)
        estado=progress+(64*numguia);
        Log.d("estandochange", "EstadoPersiana:" + estado);
        conector.ProgressChange(estado,device);
    }
    public void FinishAsync()
    {
        conector.FinishAsync();
    }

}
