package solvit.com.scarlett;

import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import solvit.com.scarlett.database.Queries;
import solvit.com.scarlett.model.Light;
import solvit.com.scarlett.web.Conector;

/**
 * Created by FranciscoPR on 31/10/15.
 */
public class AdapterLights extends RecyclerView.Adapter<AdapterLights.ViewHolder> {
    private ArrayList<Light> mDataset;
    private CuartoDetail il;



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View mTextView;
        public ViewHolder(View v) {
            super(v);
            mTextView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterLights(ArrayList<Light> lights, CuartoDetail index) {
        mDataset = lights;
        il=index;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AdapterLights.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.focos_persianas_cell, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Log.d("lights", "Mdataset es: " + mDataset.get(position).nombre);
        TextView name = (TextView)holder.mTextView.findViewById(R.id.foconame);
        name.setText(mDataset.get(position).nombre);

        final SeekBar seekBar = (SeekBar) holder.mTextView.findViewById(R.id.seekbar_foco_persiana);
        seekBar.setMax(63);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress % 3 == 0)//para no enviar tantas requests
                    mDataset.get(position).changeEstado(seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }
        });


        ImageButton ib = (ImageButton) holder.mTextView.findViewById(R.id.imgbottonfocopersiana);
        ib.setImageResource(R.drawable.foco1);
        ib.setScaleType(ImageView.ScaleType.CENTER_CROP);

        ib.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("button", "ibutton es: " + mDataset.get(position).nombre);

                ClickonIButton(mDataset.get(position), seekBar);


            }
        });

        name.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                ChangeName(mDataset.get(position), v);
                return false;
            }
        });



    }

    public void ClickonIButton(Light light,SeekBar sb)
    {

        if(sb.getProgress()==sb.getMax())
        {
            light.FinishAsync();
            sb.setProgress(0);
            //light.changeEstado(0);
        } else
        {
            light.FinishAsync();
            sb.setProgress(sb.getMax());
            //light.changeEstado(63);
        }

    }

    public void ChangeName(final Light light, final View nombrelightview)
    {
        final Dialog dialog = new Dialog(il);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_nombre_foco);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        //boton para cambiar el nombre
        Button dialogButton = (Button) dialog.findViewById(R.id.ok);

        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditText t1 = (EditText) dialog.findViewById(R.id.nombre_nuevo);//.getText();
                String nombre = t1.getText().toString();
                if (nombre.equals("")) {

                    Toast t = Toast.makeText(dialog.getContext(), "No CREADO, falto Nombre", Toast.LENGTH_SHORT);
                    t.show();

                } else {

                    Queries query = new Queries(il);
                    query.SetNombreFoco(nombre, light.id + "");
                    ((TextView)nombrelightview).setText(nombre);
                    light.nombre=nombre;
                    dialog.dismiss();
                }
            }

        });


            Button cancelar = (Button) dialog.findViewById(R.id.cancelar);
            // if button is clicked, close the custom dialog
            cancelar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick (View v){

                dialog.dismiss();

                }
            });

            dialog.show();

            dialog.getWindow().setAttributes(lp);
        }


                // Return the size of your dataset (invoked by the layout manager)
        @Override
    public int getItemCount() {
        return mDataset.size();
    }
}