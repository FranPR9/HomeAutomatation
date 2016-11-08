package solvit.com.scarlett;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import solvit.com.scarlett.model.Room;

/**
 * Created by FranciscoPR on 31/10/15.
 */
public class AdapterRoomMain extends RecyclerView.Adapter<AdapterRoomMain.ViewHolder> {
    private ArrayList<Room> mDataset;
    private CuartosIndex il;



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
    public AdapterRoomMain(ArrayList<Room> rooms, CuartosIndex index) {
        mDataset = rooms;
        il=index;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AdapterRoomMain.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_cuartos_content, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TextView name = (TextView)holder.mTextView.findViewById(R.id.roommainname);
        name.setText(mDataset.get(position).nombre);
        TextView descript = (TextView)holder.mTextView.findViewById(R.id.roommaindevices);
        descript.setText("Apagadores:"+mDataset.get(position).getDevicesLightsLength()+" Persianas:"+mDataset.get(position).getDevicesBlindsLength());

        ImageView backi= (ImageView) holder.mTextView.findViewById(R.id.roomiconmain);
        backi.setImageResource(setImg(mDataset.get(position).icon));



        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("meta", "Mdataset es: " + mDataset.get(position).nombre);

                il.StartDetailActivity(mDataset.get(position));


            }
        });

    }

    private int setImg(String tipo)
    {
        switch (tipo) {

            case "recamara":
                return R.drawable.recamara;


            case "sala":
                return R.drawable.sala;


            case "comedor":
                return R.drawable.comedor;


            case "garage":
                return R.drawable.garage;

            case "cocina":
                return R.drawable.cocina;

            case "wc":
                return R.drawable.wc;

            case "estudio":
                return R.drawable.estudio;

            case "patio":
                return R.drawable.patio;

            default: return 0;

        }
    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}