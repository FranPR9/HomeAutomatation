package solvit.com.scarlett.model;

import android.content.Context;
import android.util.Log;

import solvit.com.scarlett.web.Conector;

/**
 * Created by FranciscoPR on 19/01/16.
 */
public class Light {

    public int id;
    public String nombre;
    int estado;// EL ESTADO AUN NO SE GUARDA EN LA BASE DE DATOS, NO SE SABE SI ES NECESARIO 03/02/16
    double porcentaje;
    public int numguia=0;//numero de foco 1º,2º
    // este numero nos ayudara a multiplicar el estado del seekbar
    //el seekbar solo va de 0-63 para el 1er foco entonces se hace
    // (valor_seekbar)+(64*1); ver metodo changeEstado();
    public Conector conector;
    public Context context;
    String device;

    public Light(int id, String nombre, int estado, double porcentaje, int numguia,String device,Context context)
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
        Log.d("estandochange","Estado:"+estado);
        conector.ProgressChange(estado,device);
    }

    public void FinishAsync()
    {
        conector.FinishAsync();
    }

}
