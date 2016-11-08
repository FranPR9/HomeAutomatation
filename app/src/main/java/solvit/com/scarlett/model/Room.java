package solvit.com.scarlett.model;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import solvit.com.scarlett.database.Queries;

/**
 * Created by FranciscoPR on 19/01/16.
 */
public class Room {

    public int id;
    public String nombre;
    public String icon;
    public String tipo;
    public int ordernum;
    public ArrayList<Device> devices;
    Queries query;

    public Room(int id, String nombre, String icon,String tipo, int ordernum,Context Context)
    {
        query= new Queries(Context);
        this.id=id;
        this.nombre=nombre;
        this.icon=icon;
        this.tipo=tipo;
        this.ordernum=ordernum;
        getDevices(id);

    }

    public ArrayList<Device> getDevices(int roomid)
    {
        devices=query.getDevices(roomid);
        return devices;
    }

    public int getDevicesLightsLength()
    {
        int length=0;
        for(int i=0;i<devices.size();i++)
        {
            length+=devices.get(i).getLightslength();
        }
        return length;
    }
    public int getDevicesBlindsLength()
    {
        int length=0;
        for(int i=0;i<devices.size();i++)
        {
            length+=devices.get(i).getBlindsLength();
        }
        return length;
    }

    public void printinfo()
    {
        Log.d("room","id:"+id+" nombre:"+nombre+" icon:"+icon+" tipo:"+tipo+" ordennum:"+ordernum+" ");
    }

    public void RoomGeneralFocos(int estado)
    {
        for(int i=0;i<devices.size();i++)
        {
           devices.get(i).GeneralLights(estado);
        }
    }
    public void RoomGeneralPersianas(int estado)
    {
        for(int i=0;i<devices.size();i++)
        {
            devices.get(i).GeneralPersianas(estado);
        }
    }

    public void FinishAsyncPersianas()
    {
        for(int i=0;i<devices.size();i++)
        {
            devices.get(i).FinishAsyncPersianas();
        }
    }
    public void FinishAsyncFocos()
    {
        for(int i=0;i<devices.size();i++)
        {
            devices.get(i).FinishAsyncFocos();
        }
    }

    public void newDevice(String name,int focos,int persianas)
    {
        Device aux=query.NewDevice(name, id);
        devices.add(aux);
        for (int i=0;i<focos;i++){
            aux.newLight(i);
        }
        for (int i=0;i<persianas;i++){
            aux.newBlind(i);
        }
    }


}
