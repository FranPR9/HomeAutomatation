package solvit.com.scarlett;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import solvit.com.scarlett.model.Device;

/**
 * Created by FranciscoPR on 31/10/15.
 */
public class AdapterDevices extends RecyclerView.Adapter<AdapterDevices.ViewHolder> {
    private ArrayList<Device> mDataset;
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
    public AdapterDevices(ArrayList<Device> devices, CuartoDetail index) {
        mDataset = devices;
        il=index;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AdapterDevices.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_devices_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Log.d("Devices", "Mdataset es: " + mDataset.get(position).name+" length:"+mDataset.get(position).getLightslength());
        RecyclerView mRecyclerdevices = (RecyclerView) holder.mTextView.findViewById(R.id.recyclerfocos);
        RecyclerView.LayoutManager layoutManager = new CustomLinearLayoutManager(il);
        mRecyclerdevices.setLayoutManager(layoutManager);
        Log.d("lights","length:"+mDataset.get(position).lights.size());
        AdapterLights mAdapter = new AdapterLights(mDataset.get(position).lights,il);
        mRecyclerdevices.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        RecyclerView mRecyclerdevices1 = (RecyclerView) holder.mTextView.findViewById(R.id.recyclerpersianas);
        RecyclerView.LayoutManager layoutManager1 = new CustomLinearLayoutManager(il);
        mRecyclerdevices1.setLayoutManager(layoutManager1);
        Log.d("lights","length:"+mDataset.get(position).lights.size());
        AdapterBlinds mAdapter1 = new AdapterBlinds(mDataset.get(position).blinds,il);
        mRecyclerdevices1.setAdapter(mAdapter1);
        mAdapter1.notifyDataSetChanged();


        /*holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("meta", "Mdataset es: " + mDataset.get(position).nombre);

                //il.StartLugar(mDataset[position]);



            }
        });*/

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