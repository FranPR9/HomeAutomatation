package solvit.com.scarlett.web;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Vector;

/**
 * Created by FranciscoPR on 03/02/16.
 */
public class Conector implements AsyncResponse {

    String url;
    String ip="192.168.42.1";

    private Vector<spiderWeb> progresschanged ;// vector con las llamadas de progresschanged. son varias y el vector
    // ayuda a poder matarlas si ya no se necesitan.
    Context context;

    public Conector(Context context)
    {
        url = "http://" + ip + ":2000/data?";
        this.context=context;
    }

    public void setVector()
    {
        progresschanged = new Vector<spiderWeb>();
    }

    public void ProgressChange(int progress,String device)
    {
        spiderWeb aux = new spiderWeb(context, this);
        progresschanged.add(aux);
        progresschanged.lastElement().execute(url+"device="+device+"&value="+progress);
    }

    public void FinishAsync(){

        for(int i=0;i<progresschanged.size();i++)
        {
            if(progresschanged.elementAt(i).getStatus().equals(AsyncTask.Status.RUNNING))
            {
                progresschanged.elementAt(i).cancel(true);
                Log.d("stat_spw", "" + progresschanged.elementAt(i).getStatus());
            }
        }
        //spw.cancel(true);
        Log.d("finish", "Requests stopped!");
        progresschanged.removeAllElements();
    }

    public void Calibrar()
    {
        //http://scarlett:2000/data?device=0&value=12
        String url="http://"+ip+":2000/data?device=0&value=12";
        spiderWeb aux = new spiderWeb(context,this);
        aux.execute(url);
    }

    public void ApagadorGeneral()
    {
        String url = "http://" + ip + ":2000/data?device=0&value=15";
        spiderWeb aux = new spiderWeb(context,this);
        aux.execute(url);
    }

    public void Panico(String value)
    {
        String url = "http://" + ip + ":2000/intruder?priority="+value;
        spiderWeb aux = new spiderWeb(context,this);
        aux.execute(url);

    }

    public void Alarma(String value)
    {
        String url = "http://" + ip + ":2000/data?device=0&value="+value;
        spiderWeb aux = new spiderWeb(context,this);
        aux.execute(url);
    }


    @Override
    public void processFinish(String output) {

        Log.d("output", output);
        if(progresschanged!=null){
            progresschanged.remove(0);
        }

    }
}
