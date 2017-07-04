package com.example.robertopc.appagendatea.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.robertopc.appagendatea.ElementosPersistentes.Pictograma;
import com.example.robertopc.appagendatea.R;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Created by RobertoPC on 01/07/2017.
 */

public class RVAdapterPictogram extends RecyclerView.Adapter<RVAdapterPictogram.PictogramViewHolder> {
    private OnFirstPictogramGaleriaClick onPictogramListener;

    public void setOnPictogramListener(OnFirstPictogramGaleriaClick onPictogramListener) {
        this.onPictogramListener = onPictogramListener;
    }


    List<Pictograma> pictogramas;

    public RVAdapterPictogram(List<Pictograma> pictogramas, OnFirstPictogramGaleriaClick onPictogramListener){
        this.pictogramas = pictogramas;
        this.onPictogramListener = onPictogramListener;
    }

    @Override
    public RVAdapterPictogram.PictogramViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_lista_pictos, parent, false);
        RVAdapterPictogram.PictogramViewHolder uvh = new RVAdapterPictogram.PictogramViewHolder(v, onPictogramListener);
        return uvh;
    }

    @Override
    public void onBindViewHolder(RVAdapterPictogram.PictogramViewHolder holder, int position) {
        if (pictogramas.size()>0){
            holder.personName.setText(pictogramas.get(position).getNombre());
            ByteArrayInputStream imageStream = new ByteArrayInputStream(pictogramas.get(position).getImagen());
            Bitmap theImage= BitmapFactory.decodeStream(imageStream);
            holder.personPhoto.setImageBitmap(theImage);
        }
        else
            Log.d("OnBindViewHolder", "El array de pictogramas está vacío");

    }

    @Override
    public int getItemCount() {
        return pictogramas.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }




    public static class PictogramViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        public TextView personName;
        ImageView personPhoto;

        PictogramViewHolder(View itemView, final OnFirstPictogramGaleriaClick onPictogramListener) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cvlpid);
            personName = (TextView)itemView.findViewById(R.id.cvlptv);
            personPhoto = (ImageView)itemView.findViewById(R.id.cvlpiv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION){
                        onPictogramListener.onFirstPictogramGaleriaClick(position);
                    }

                }
            });
        }
    }


}
