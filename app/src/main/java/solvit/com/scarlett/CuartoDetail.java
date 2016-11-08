package solvit.com.scarlett;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import solvit.com.scarlett.database.Queries;
import solvit.com.scarlett.model.Room;
import solvit.com.scarlett.web.Conector;

public class CuartoDetail extends AppCompatActivity {

    Room actualroom;
    Dialog dialog1;
    Conector conector;
    AdapterDevices mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuarto_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        conector = new Conector(this);

        Bundle obj = getIntent().getExtras();
        if(obj!=null){
            int idroom=obj.getInt("idcuarto");
            String nombre=obj.getString("nombre");
            String icon=obj.getString("icon");
            String tipo=obj.getString("tipo");
            int orden=obj.getInt("orden");

            toolbar.setTitle(nombre);// nombre de cuarto en toolbar
            setSupportActionBar(toolbar);// se agrega la toolbar
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);//se agrega el boton de regresar.
            // Handle Back Navigation :D
            actualroom = new Room(idroom,nombre,tipo,icon,orden,this);// se inicializa la variable cuarto que meneja esta interfaz
            actualroom.printinfo();// debug para ver que tiene el cuarto
            RecyclerView mRecyclerdevices = (RecyclerView) findViewById(R.id.devicesrecycler);
            LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(this);
            mRecyclerdevices.setLayoutManager(mLayoutManager1);
            mAdapter = new AdapterDevices(actualroom.devices,this);
            mRecyclerdevices.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

            TextView textView = (TextView) findViewById(R.id.roomname);// el nombre del cuarto que aparece por encima de la imagen
            textView.setText(actualroom.nombre);// se pone el nombre del cuarto en la view
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CuartoDetail.this.onBackPressed();
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //CALIBRAR
            conector.Calibrar();
            return true;
        }
        if (id == R.id.editar) {
            //Pedir_contrasena(2);

            //Editar();
            return true;
        }
        if (id == R.id.borrar) {

            Pedir_contrasena(1);

        }
        if (id == R.id.manual) {


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.dialog_loading, null));
            builder.setMessage("Agrega Dispositivo")
                    .setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!

                            EditText edt = (EditText) dialog1.findViewById(R.id.focos_e);
                            String fcs = edt.getText().toString();
                            edt = (EditText) dialog1.findViewById(R.id.persianas_e);
                            String perss = edt.getText().toString();
                            edt = (EditText) dialog1.findViewById(R.id.id_dev);
                            String id_dev = edt.getText().toString();
                            if (perss.length() <= 0) perss = "0";
                            if (fcs.length() <= 0) fcs = "0";
                            if (id_dev.length() > 0) {
                                actualroom.newDevice(id_dev, Integer.parseInt(fcs), Integer.parseInt(perss));
                                mAdapter.notifyDataSetChanged();
                            } else {
                                Toast t = Toast.makeText(dialog1.getContext(), "¡Dispositivo No Agregado! ,escribe el id del dispositivo", Toast.LENGTH_SHORT);
                                t.show();
                            }
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });


            dialog1 = builder.create();
            dialog1.show();
            //Editar();
            return true;
        }

        if (id == R.id.automatico) {
            final ProgressDialog dialog = ProgressDialog.show(this, "",
                    "Buscando...", true);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    dialog.dismiss();


                    AlertDialog d;
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(dialog.getContext());
                    //          LayoutInflater inflater = getLayoutInflater();
//           builder.setView(inflater.inflate(R.layout.dialog_loading, null));
                    builder1.setMessage("No se Encontraron Dispositivos")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                }
                            });

                    d = builder1.create();
                    d.show();


                }}, 5000);


            //Editar();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Pedir_contrasena(final int cual){

        final Dialog dialog = new Dialog(this);
        boolean result=false;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.dialog_contrasena);


        //para que el dialog ocupe el todo el ancho
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        //boton para cambiar el nombre
        Button dialogButton = (Button) dialog.findViewById(R.id.ok);

        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText etxt = (EditText) dialog.findViewById(R.id.password);
                String password = etxt.getText().toString();
                Log.d("pass", password);

                if(password.equals("1234")){// password actual 1234

                    if(cual==1)
                    {
                        pregunta_Borrar();

                    }

                }
                else{

                    Toast t = Toast.makeText(dialog.getContext(),"¡Contraseña no valida!", Toast.LENGTH_SHORT);
                    t.show();

                }

            }
        });



        Button cancelar = (Button) dialog.findViewById(R.id.cancelar);
        // if button is clicked, close the custom dialog
        cancelar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });


        dialog.show();

        dialog.getWindow().setAttributes(lp);




    }

    public void pregunta_Borrar()
    {
        final Queries query = new Queries(this);
        //layout.FinishAsync();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Seguro que quieres Borrar este cuarto?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        query.DeleteRoom(actualroom.id+"");
                        Intent intent = new Intent();
                        intent.putExtra("id",actualroom.id);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cuarto_detail, menu);
        return true;
    }

}
