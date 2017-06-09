package com.example.robertopc.appagendatea.Utils;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.robertopc.appagendatea.ElementosPersistentes.Usuario;
import com.example.robertopc.appagendatea.R;

import java.util.List;

/**
 * Created by RobertoPC on 09/06/2017.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.UsuarioViewHolder> {

    public static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        public TextView personName;
        TextView personAge;
        ImageView personPhoto;

        UsuarioViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cvlvid);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }

    List<Usuario> usuarios;

    public RVAdapter(List<Usuario> usuarios){
        this.usuarios = usuarios;
    }

    @Override
    public RVAdapter.UsuarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_lista_vertical, parent, false);
        UsuarioViewHolder uvh = new UsuarioViewHolder(v);
        return uvh;
    }

    @Override
    public void onBindViewHolder(RVAdapter.UsuarioViewHolder holder, int position) {
        holder.personName.setText(usuarios.get(position).getNombre());
        holder.personAge.setText(usuarios.get(position).getFechaNacimiento());
        holder.personPhoto.setImageBitmap(usuarios.get(position).getImagen());
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}
