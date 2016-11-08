package solvit.com.scarlett.web;

/**
 * Created by Fran on 3/5/15.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class spiderWeb extends AsyncTask<String, Void, String> {

    private String dataField;
    private Context context;
    private boolean connected;

    public AsyncResponse callback=null;

    public spiderWeb(Context context,AsyncResponse callback) {
        this.context = context;
        this.callback = callback;
         connected=false;
    }


    //check Internet conenction.
    private void checkInternetConenction(){
        ConnectivityManager check = (ConnectivityManager) this.context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        if (check != null)
        {
            NetworkInfo[] info = check.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i <info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        connected=true;
                       // Toast.makeText(context, "Internet is connected",
                         //       Toast.LENGTH_SHORT).show();
                    }

        }
        else{
            //Toast.makeText(context, "not connected to internet",
            //        Toast.LENGTH_SHORT).show();
            connected=false;
        }
    }

    @Override
    protected String doInBackground(String... arg0) {



        if(connected==true){

            try{
                String link = (String)arg0[0];
                Log.d("link", link);
                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(500);
                conn.setConnectTimeout(1000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader
                        (is, "UTF-8") );
                String data = null;
                String webPage = "";
                while ((data = reader.readLine()) != null){
                    webPage += data + "\n";
                }
                return webPage;
            }catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }

        }
         return "";
    }

    @Override
    protected void onPostExecute(String result){
        callback.processFinish(result);
    }

    protected void onPreExecute(){
        checkInternetConenction();
    }
}