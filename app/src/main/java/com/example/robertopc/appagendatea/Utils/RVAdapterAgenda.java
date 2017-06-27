package com.example.robertopc.appagendatea.Utils;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robertopc.appagendatea.ElementosPersistentes.Agenda;
import com.example.robertopc.appagendatea.R;

import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Created by RobertoPC on 26/06/2017.
 */

public class RVAdapterAgenda extends RecyclerView.Adapter<RVAdapterAgenda.AgendaViewHolder> {

    public static class AgendaViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        public TextView nombreAgenda;
        public ImageButton edit, delete;

        AgendaViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cvlvsp);
            nombreAgenda = (TextView)itemView.findViewById(R.id.nombreAgenda);
            edit = (ImageButton) itemView.findViewById(R.id.editAgendaButtonid);
            delete = (ImageButton) itemView.findViewById(R.id.deleteAgendaButtonId);

        }
    }

    List<Agenda> agendas;

    public RVAdapterAgenda(List<Agenda> agendas){
        this.agendas = agendas;
    }

    @Override
    public RVAdapterAgenda.AgendaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_lista_vertical_sin_picto, parent, false);
        RVAdapterAgenda.AgendaViewHolder uvh = new RVAdapterAgenda.AgendaViewHolder(v);
        return uvh;
    }

    @Override
    public void onBindViewHolder(RVAdapterAgenda.AgendaViewHolder holder, int position) {
        holder.nombreAgenda.setText(this.agendas.get(position).getNombre());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return this.agendas.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
