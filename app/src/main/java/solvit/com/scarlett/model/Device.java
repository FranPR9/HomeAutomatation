package solvit.com.scarlett.model;

import android.content.Context;

import java.util.ArrayList;

import solvit.com.scarlett.database.Queries;

/**
 * Created by FranciscoPR on 19/01/16.
 */
public class Device {

    int id;
    public String name;
    public ArrayList<Light> lights;
    public ArrayList<Blind> blinds;
    Queries query;

    public Device(int id,String name,Context context)
    {
        query= new Queries(context);
        this.id=id;
        this.name=name;
        getLights(id);
        getBlinds(id);
    }

    private void getLights(int deviceid)
    {
       lights= query.getFocos(deviceid,name);

    }
    private void getBlinds(int deviceid)
    {
       blinds= query.getPersianas(deviceid, name);
    }
    public int getLightslength(){

        return  lights.size();
    }

    public int getBlindsLength()
    {
        return blinds.size();
    }

    public void GeneralLights(int estado)
    {
        for(int i=0;i<lights.size();i++)
        {
            if(estado==1)estado=63;//porque 63 es el estado de maximo encendido. en light cambia segun el numero de foco 63 + 64 * numguia
            lights.get(i).changeEstado(estado);
        }
    }

    public void GeneralPersianas(int estado)
    {
        for(int i=0;i<blinds.size();i++)
        {
            if(estado==1)estado=63;//porque 63 es el estado de maximo encendido. en blind cambia segun el numero de foco 63 + 64 * numguia
            blinds.get(i).changeEstado(estado);
        }
    }
    public void FinishAsyncPersianas()
    {
        for(int i=0;i<lights.size();i++)
        {
            lights.get(i).FinishAsync();
        }
    }

    public void FinishAsyncFocos()
    {
        for(int i=0;i<blinds.size();i++)
        {
            blinds.get(i).FinishAsync();
        }
    }

    public void newLight(int guia){
        Light aux = query.NewLight(name,guia,id);
        lights.add(aux);

    }
    public void newBlind(int guia){
        Blind aux = query.newBlind(name,guia,id);
        blinds.add(aux);

    }



}
