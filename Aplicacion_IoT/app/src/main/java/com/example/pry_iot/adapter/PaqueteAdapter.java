package com.example.pry_iot.adapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pry_iot.R;
import com.example.pry_iot.model.Paquete;
import com.example.pry_iot.view.paquete;
import com.example.pry_iot.view.rvhistorial_paq;

import java.util.ArrayList;
import java.util.List;
public class PaqueteAdapter extends RecyclerView.Adapter<PaqueteAdapter.ViewHolder> {
    private ArrayList<Paquete> paquetes;
    public PaqueteAdapter(ArrayList<Paquete> paquetes) {
        this.paquetes = paquetes;
    }
    // Otros métodos del adaptador
    public void setPaquetes(ArrayList<Paquete> paquetes) {
        this.paquetes = paquetes;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public PaqueteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.item, parent, false);

        return new ViewHolder(v); //constructor
    }
    @Override
    public void onBindViewHolder(@NonNull PaqueteAdapter.ViewHolder holder, int position) {
        Paquete P = paquetes.get(position);
        holder.setData(P);
    }
    @Override
    public int getItemCount() {
        return paquetes.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tvpaquete, tvlugar, tvestado, tvid;
        private Paquete paquete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvpaquete = itemView.findViewById(R.id.tvpaquete);
            tvlugar = itemView.findViewById(R.id.tvlugar);
            tvestado = itemView.findViewById(R.id.tvestado);
            tvid = itemView.findViewById(R.id.tvid);
            itemView.setOnClickListener(this);
        }
        public void setData(Paquete p) {
            this.paquete = p;
            tvid.setText(String.valueOf(p.getId()));
            tvpaquete.setText(p.getNombre_paquete());
            tvlugar.setText(p.getLugar_paquete());
            tvestado.setText("Estatus: " + String.valueOf(p.getStatus()));
        }
        @Override
        public void onClick(View v) {
            Intent i = new Intent(itemView.getContext(), com.example.pry_iot.view.paquete.class);
            i.putExtra("paquete", paquete);
            itemView.getContext().startActivity(i);
            itemView.getContext();
            //((Activity) itemView.getContext()).finish();
        }

    }
}