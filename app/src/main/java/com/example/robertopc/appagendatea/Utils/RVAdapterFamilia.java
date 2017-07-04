package com.example.robertopc.appagendatea.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.robertopc.appagendatea.ElementosPersistentes.Familia;
import com.example.robertopc.appagendatea.ElementosPersistentes.Pictograma;
import com.example.robertopc.appagendatea.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RobertoPC on 16/06/2017.
 */

public class RVAdapterFamilia extends RecyclerView.Adapter<RVAdapterFamilia.FamiliaViewHolder> implements AdaptadorListener{
    List<Familia> familias;

    private OnAdapterListener onAdapterListener;

    public void setOnAdapterListener(OnAdapterListener onAdapterListener) {
        this.onAdapterListener = onAdapterListener;
    }

    @Override
    public void onFondoClicked(int position) {
        ArrayList<Pictograma> pictos = familias.get(position).getPictogramas();
        onAdapterListener.onFamClicked(position, pictos);
    }


    public RVAdapterFamilia(List<Familia> familias, OnAdapterListener onAdapterListener){
        this.familias = familias;
        this.onAdapterListener = onAdapterListener;
    }

    @Override
    public RVAdapterFamilia.FamiliaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_lista_horizontal_con_picto, parent, false);
        RVAdapterFamilia.FamiliaViewHolder uvh = new RVAdapterFamilia.FamiliaViewHolder(v, this);
        return uvh;
    }

    @Override
    public void onBindViewHolder(final RVAdapterFamilia.FamiliaViewHolder holder, int position) {

        holder.personName.setText(familias.get(position).getNombre());
        try {
            ByteArrayInputStream imageStream = new ByteArrayInputStream(familias.get(position).getImagen());
            Bitmap theImage= BitmapFactory.decodeStream(imageStream);
            holder.personPhoto.setImageBitmap(theImage);
        } catch (Exception e){
            Log.i("ByteArrayImputStream", "No se ha capturado imagen, debe capturarse antes de guardar o no podrá cargar su elemento. Reinstala APP.");
        }

    }


    @Override
    public int getItemCount() {
        return familias.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }




    public class FamiliaViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        public TextView personName;
        ImageView personPhoto;

        FamiliaViewHolder(View itemView, final AdaptadorListener adaptadorListener) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cvlhcpid);
            personName = (TextView)itemView.findViewById(R.id.cvlhtextfid);
            personPhoto = (ImageView)itemView.findViewById(R.id.cvlhpictofid);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION){
                        adaptadorListener.onFondoClicked(position);
                    }
                }
            });

        }

    }




}
