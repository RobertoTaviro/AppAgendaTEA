package com.example.robertopc.appagendatea.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.robertopc.appagendatea.ElementosPersistentes.Tarea;
import com.example.robertopc.appagendatea.ElementosPersistentes.Usuario;
import com.example.robertopc.appagendatea.R;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Created by RobertoPC on 28/06/2017.
 */

public class RVAdapterTareaEA extends RecyclerView.Adapter<RVAdapterTareaEA.TareaEAViewHolder> {

    public static class TareaEAViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        public TextView personName;
        TextView personAge;
        ImageView personPhoto;

        TareaEAViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cvlvid);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }

    List<Tarea> tareas;

    public RVAdapterTareaEA(List<Tarea> tareas){
        this.tareas = tareas;
    }

    @Override
    public RVAdapterTareaEA.TareaEAViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_lista_vertical, parent, false);
        RVAdapterTareaEA.TareaEAViewHolder uvh = new RVAdapterTareaEA.TareaEAViewHolder(v);
        return uvh;
    }

    @Override
    public void onBindViewHolder(RVAdapterTareaEA.TareaEAViewHolder holder, int position) {
        holder.personName.setText(tareas.get(position).getNombre());
        holder.personAge.setText(tareas.get(position).getDia());
        ByteArrayInputStream imageStream = new ByteArrayInputStream(tareas.get(position).getImagen());
        Bitmap theImage= BitmapFactory.decodeStream(imageStream);
        holder.personPhoto.setImageBitmap(theImage);
    }

    @Override
    public int getItemCount() {
        return tareas.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}