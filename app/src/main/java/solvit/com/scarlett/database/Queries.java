package solvit.com.scarlett.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import solvit.com.scarlett.model.Blind;
import solvit.com.scarlett.model.Device;
import solvit.com.scarlett.model.Light;
import solvit.com.scarlett.model.Room;

/**
 * Created by FranciscoPR on 19/01/16.
 */
public class Queries {


    Database b;
    Context context;
    public Queries(Context context)
    {

        this.context=context;
        b = new Database(context);


    }

    public ArrayList<Room> getCuartos()
    {
        ArrayList<Room> rooms= new ArrayList<Room>();
        SQLiteDatabase base = b.getReadableDatabase();
        Cursor cursor = base.query(true,"Cuarto",null,"cuartoid!=0",null,null,null,null,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            Room r= new Room(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),context);
            r.printinfo();
            rooms.add(r);
            cursor.moveToNext();
        }
        cursor.close();
        return rooms;
    }
    public ArrayList<Device> getDevices(int roomid)
    {
        ArrayList<Device> devices= new ArrayList<Device>();
        SQLiteDatabase base = b.getReadableDatabase();
        Cursor cursor = base.query(true,"Device",null,"idcuarto="+roomid,null,null,null,null,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            Device d= new Device(cursor.getInt(0),cursor.getString(1),context);
            devices.add(d);
            cursor.moveToNext();
        }
        cursor.close();

        return devices;
    }

    public  ArrayList<Light>  getFocos(int deviceid,String device)
    {
        ArrayList<Light> lights= new ArrayList<Light>();
        SQLiteDatabase base = b.getReadableDatabase();
        Cursor cursor = base.query(true,"Focos",null,"device="+deviceid,null,null,null,null,null);

        cursor.moveToFirst();
        int i=1;
        while (!cursor.isAfterLast()) {

            Light l= new Light(cursor.getInt(0),cursor.getString(3),cursor.getInt(1),0,i,device,context);
            lights.add(l);
            cursor.moveToNext();
            i++;
        }
        cursor.close();
        return lights;
    }

    public ArrayList<Blind> getPersianas(int deviceid, String device)
    {
        ArrayList<Blind> blinds= new ArrayList<Blind>();
        SQLiteDatabase base = b.getReadableDatabase();
        Cursor cursor = base.query(true, "Persianas", null, "device=" + deviceid, null, null, null, null, null);
        int i=1;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            Blind b= new Blind(cursor.getInt(0),cursor.getString(3),cursor.getInt(1),0,i,device,context);
            blinds.add(b);
            cursor.moveToNext();
            i++;
        }
        cursor.close();
        return blinds;
    }

    public Device NewDevice(String name, int cuarto)
    {
        SQLiteDatabase base = b.getWritableDatabase();
        ContentValues contentvalues= new ContentValues();
        contentvalues.put("nombre_device",name);
        contentvalues.put("idcuarto", cuarto);
        int inserted =(int) base.insert("Device", null ,contentvalues);
        Device d= new Device(inserted,name,context);

        return d;
    }
    public Light NewLight(String device,int numguia, int deviceid)
    {
        SQLiteDatabase base = b.getWritableDatabase();
        ContentValues contentvalues= new ContentValues();
        contentvalues.put("estado",0);
        contentvalues.put("device", deviceid);
        contentvalues.put("nombre", "Apagador1");
        int inserted =(int) base.insert("Focos", null ,contentvalues);
        Light light = new Light(inserted,"Apagador1",0,0,numguia,device,context);

        return light;
    }

    public Blind newBlind(String device,int numguia, int deviceid)
    {
        SQLiteDatabase base = b.getWritableDatabase();
        ContentValues contentvalues= new ContentValues();
        contentvalues.put("estado",0);
        contentvalues.put("device", deviceid);
        contentvalues.put("nombre", "Persiana1");
        int inserted =(int) base.insert("Persianas", null ,contentvalues);
        Blind blind = new Blind(inserted,"Persiana1",0,0,numguia,device,context);

        return blind;
    }

    public int newRoom(String nombre, String tipo, String img)
    {
        SQLiteDatabase base = b.getWritableDatabase();
        ContentValues contentvalues= new ContentValues();
        contentvalues.put("nombre",nombre);
        contentvalues.put("tipo", tipo);
        contentvalues.put("img", img);
        contentvalues.put("orden", 0);// aun no se hace nada con el orden
        int inserted =(int) base.insert("Cuarto", null ,contentvalues);
        Log.d("inserted", "" + inserted);//por mero debugeo
        return inserted;
    }

    public Room getCuarto(String idroom)
    {

        SQLiteDatabase base = b.getReadableDatabase();
        Cursor cursor = base.query(true, "Cuarto", null, "cuartoid=" + idroom, null, null, null, null, null);
        Room r=null;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            r = new Room(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),context);
            r.printinfo();
            cursor.moveToNext();
        }
        cursor.close();
        return r;
    }

    public void DeleteRoom(String id)
    {
        SQLiteDatabase base = b.getWritableDatabase();
        base.execSQL("DElETE From Cuarto Where cuartoid=" + id);
    }

    public void SetNombreFoco(String name,String idcuarto)
    {
        SQLiteDatabase base = b.getWritableDatabase();
        base.execSQL("UPDATE Focos SET nombre='"+name+"' Where focoid="+idcuarto);
    }
    public void SetNombrePersianas(String name,String idcuarto)
    {
        SQLiteDatabase base = b.getWritableDatabase();
        base.execSQL("UPDATE Persianas SET nombre='"+name+"' Where persianaid="+idcuarto);
    }




}
