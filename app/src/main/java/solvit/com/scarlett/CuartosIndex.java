package solvit.com.scarlett;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Switch;

import java.util.ArrayList;

import solvit.com.scarlett.database.Queries;
import solvit.com.scarlett.model.Room;
import solvit.com.scarlett.web.Conector;

public class CuartosIndex extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    ArrayList<Room> cuartos;
    Queries query;
    RecyclerView mRecyclerView;
    RecyclerView mRecyclerNav;
    Switch general,panico, alarm;
    Conector conector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuartos_index);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //icono de app
        ImageView scicon=new ImageView(this);
        scicon.setImageResource(R.drawable.scarlett);
        //scicon.setLayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,100));
        scicon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        scicon.setPadding(0, 10, 0, 0);
        // Handle Toolbar
        toolbar.addView(scicon);
        toolbar.setTitle("");
        //toolbar.setPadding(0, 10, 0, 0);
        setSupportActionBar(toolbar);

        conector= new Conector(this);

        general = (Switch)findViewById(R.id.general);
        general.setOnClickListener(this);
        panico = (Switch)findViewById(R.id.panico);
        panico.setOnClickListener(this);
        alarm = (Switch)findViewById(R.id.alarm);
        alarm.setOnClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(),NewRoom.class);
                startActivityForResult(intent, 1);


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclrmainrooms);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerNav = (RecyclerView) findViewById(R.id.recyclernavdrawerrooms);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(this);
        mRecyclerNav.setLayoutManager(mLayoutManager1);
        query= new Queries(this);
        cuartos= new ArrayList<Room>();
        getCuartos();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.cuarto_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        /*
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void getCuartos(){
        Log.d("getc", "getcuartos!!!");
        cuartos= query.getCuartos();
        Log.d("cuartos", "cuartos:"+cuartos.size());
        for(int i=0;i<cuartos.size();i++)
        {
            cuartos.get(i).printinfo();
        }
        setRecyclerCuartosMain();
        setRecyclerCuartosNav();
    }

    private void setRecyclerCuartosMain(){
        AdapterRoomMain mAdapter = new AdapterRoomMain(cuartos,this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
    private void setRecyclerCuartosNav(){
        AdapterRoomNav mAdapter = new AdapterRoomNav(cuartos,this);
        mRecyclerNav.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    public void StartDetailActivity(Room room)
    {
        Intent i= new Intent(this,CuartoDetail.class);
        i.putExtra("idcuarto",room.id);
        i.putExtra("nombre",room.nombre);
        i.putExtra("icon",room.icon);
        i.putExtra("tipo",room.tipo);
        i.putExtra("orden", room.ordernum);
        startActivityForResult(i, 2);
    }

    public void Apagador_General()
    {
        conector.ApagadorGeneral();
    }

    public void Alarma()
    {
        if(alarm.isChecked()){
            conector.Alarma("47");// alarm on
        }
        else {
            conector.Alarma("31");
        }

    }

    public void Panico()
    {
        conector.Panico("true");
        print_Panic_D();
    }



    public void print_Panic_D()
    {
        Dialog dialog_panico;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Panico Activado");
        builder.setMessage("¿Deseas desactivarlo?")
                .setPositiveButton("Desactivar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        //Borrar();
                        conector.Panico("false");
                        panico.setChecked(false);
                    }
                });
                /*.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });*/

        dialog_panico = builder.create();
        dialog_panico.setCancelable(false);
        dialog_panico.show();
    }


    @Override
    public void onClick(View v) {


        if(v.getId()==general.getId())
        {
            Apagador_General();
        }
        if(v.getId()==panico.getId())
        {
            Panico();
        }
        if(v.getId()==alarm.getId())
        {
            Alarma();
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                cuartos.add(query.getCuarto(data.getStringExtra("id")));
                mRecyclerView.getAdapter().notifyDataSetChanged();
                mRecyclerNav.getAdapter().notifyDataSetChanged();
            }
        }
        if (requestCode == 2) {
            if(resultCode == RESULT_OK){
                cuartos.remove(data.getIntExtra("id",0)-1);
                mRecyclerView.getAdapter().notifyDataSetChanged();
                mRecyclerNav.getAdapter().notifyDataSetChanged();
            }
        }
    }
}
