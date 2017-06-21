package com.example.robertopc.appagendatea.Utils;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.robertopc.appagendatea.ElementosPersistentes.Familia;
import com.example.robertopc.appagendatea.ElementosPersistentes.Usuario;
import com.example.robertopc.appagendatea.R;

import java.util.List;

/**
 * Created by RobertoPC on 16/06/2017.
 */

public class RVAdapterFamilia extends RecyclerView.Adapter<RVAdapterFamilia.FamiliaViewHolder> {

    public static class FamiliaViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        public TextView personName;
        ImageView personPhoto;

        FamiliaViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cvlhcpid);
            personName = (TextView)itemView.findViewById(R.id.cvlhtextfid);
            personPhoto = (ImageView)itemView.findViewById(R.id.cvlhpictofid);
        }
    }

    List<Familia> familias;

    public RVAdapterFamilia(List<Familia> familias){
        this.familias = familias;
    }

    @Override
    public RVAdapterFamilia.FamiliaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_lista_horizontal_con_picto, parent, false);
        RVAdapterFamilia.FamiliaViewHolder uvh = new RVAdapterFamilia.FamiliaViewHolder(v);
        return uvh;
    }

    @Override
    public void onBindViewHolder(RVAdapterFamilia.FamiliaViewHolder holder, int position) {
        holder.personName.setText(familias.get(position).getNombre());
        holder.personPhoto.setImageBitmap(familias.get(position).getImagen());
    }

    @Override
    public int getItemCount() {
        return familias.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}
