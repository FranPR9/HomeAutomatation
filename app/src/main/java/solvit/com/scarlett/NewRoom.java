package solvit.com.scarlett;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import solvit.com.scarlett.database.Queries;


public class NewRoom extends AppCompatActivity implements View.OnClickListener {

    String[] rooms = {"Selecionar...","recamara", "comedor", "sala","cocina","patio","garage","estudio","wc"};
    // Declaring the Integer Array with resourse Id's of Images for the Spinners
    Integer[] images = {0, R.drawable.recamara, R.drawable.comedor,
            R.drawable.sala, R.drawable.cocina , R.drawable.patio,R.drawable.garage,R.drawable.estudio,R.drawable.wc };
    Queries query;
    Spinner iconos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_room);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//se agrega el boton de regresar.

        iconos = (Spinner)findViewById(R.id.room_spinner);
        MyAdapter myAdapter = new MyAdapter(this,R.layout.room_spinner_cell,rooms);
        iconos.setAdapter(myAdapter);

        Button create = (Button)findViewById(R.id.create_new_room);
        create.setOnClickListener(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewRoom.this.onBackPressed();
            }
        });


        query= new Queries(this);

    }

    @Override
    public void onClick(View v) {

        TextView nombre = (TextView) findViewById(R.id.nombre_room_new);

        if(nombre.getText().toString().length()>0) {

            TextView tv = (TextView)iconos.getSelectedView().findViewById(R.id.weekoftheday);
            if(tv.getText().equals("Selecionar..."))
            {
                Toast t = Toast.makeText(this, "Por favor elije tipo", Toast.LENGTH_SHORT);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.show();
            }
            else{
                int newroom=Nuevo_Cuarto(nombre.getText().toString(),tv.getText().toString(),tv.getText().toString());
                Intent intent = new Intent();
                intent.putExtra("id",newroom+"");
                setResult(RESULT_OK, intent);
                finish();


            }

        }
        else
        {
            Toast t = Toast.makeText(this, "Por favor Escribe un Nombre", Toast.LENGTH_SHORT);
            t.setGravity(Gravity.CENTER, 0, 0);
            t.show();
        }

    }

    /**
     *
     * @param nombre
     * @param tipo
     * @param img
     *
     */
    public int Nuevo_Cuarto(String nombre, String tipo, String img)
    {
        return query.newRoom(nombre,tipo,img);

    }

    public class MyAdapter extends ArrayAdapter {

        public MyAdapter(Context context, int textViewResourceId,
                         String[] objects) {
            super(context, textViewResourceId, objects);
        }

        public View getCustomView(int position, View convertView,
                                  ViewGroup parent) {

// Inflating the layout for the custom Spinner
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.room_spinner_cell, parent, false);

// Declaring and Typecasting the textview in the inflated layout
            TextView tvLanguage = (TextView) layout
                    .findViewById(R.id.weekoftheday);

// Setting the text using the array
            tvLanguage.setText(rooms[position]);

// Setting the color of the text
            //tvLanguage.setTextColor(Color.rgb(75, 180, 225));

// Declaring and Typecasting the imageView in the inflated layout
            ImageView img = (ImageView) layout.findViewById(R.id.icon);

// Setting an image using the id's in the array
            img.setImageResource(images[position]);

// Setting Special atrributes for 1st element
            if (position == 0) {
// Removing the image view
                img.setVisibility(View.GONE);
// Setting the size of the text
                tvLanguage.setTextSize(20f);
// Setting the text Color
                tvLanguage.setTextColor(Color.BLACK);

            }

            return layout;
        }

        // It gets a View that displays in the drop down popup the data at the specified position
        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        // It gets a View that displays the data at the specified position
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            return getCustomView(position, convertView, parent);
        }
    }


}
