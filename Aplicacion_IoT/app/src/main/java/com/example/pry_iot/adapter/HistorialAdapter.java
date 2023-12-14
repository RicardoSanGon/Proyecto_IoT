package com.example.pry_iot.adapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pry_iot.R;
import com.example.pry_iot.model.Historial_paquete;
import com.example.pry_iot.view.paquete;

import java.text.DecimalFormat;
import java.util.List;
public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.ViewHolder>{
    List<Historial_paquete> historial_paquetes;
    public HistorialAdapter(List<Historial_paquete> historial_paquetes) {
        this.historial_paquetes = historial_paquetes;
    }
    @NonNull
    @Override
    public HistorialAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.itemhp, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull HistorialAdapter.ViewHolder holder, int position) {
        holder.bind(historial_paquetes.get(position));
    }
    @Override
    public int getItemCount() {
        return historial_paquetes.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvph,tvcond,tvtemp,tvniv,tvturb,tvfecha;
        Button btnsalir;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvph = itemView.findViewById(R.id.dataph);
            tvcond = itemView.findViewById(R.id.datacond);
            tvtemp = itemView.findViewById(R.id.datatemp);
            tvniv = itemView.findViewById(R.id.dataniv);
            tvturb = itemView.findViewById(R.id.datatur);
            tvfecha = itemView.findViewById(R.id.fechahora);

        }
        public void bind(Historial_paquete historialPaquete) {
            tvph.setText(formatDecimal(historialPaquete.getDatos_sensor_ph()));
            tvcond.setText(formatDecimal(historialPaquete.getDatos_sensor_conductividad()));
            tvtemp.setText(formatDecimal(historialPaquete.getDatos_sensor_temperatura()));
            tvniv.setText(formatDecimal(historialPaquete.getDatos_sensor_nivel_agua()));
            tvturb.setText(formatDecimal(historialPaquete.getDatos_sensor_turbidez()));
            tvfecha.setText(historialPaquete.getFecha_hora());
        }
        private String formatDecimal(double number)
        {
            // Definir el formato deseado
            DecimalFormat formato = new DecimalFormat("0.###");
            // Formatear el número
            String numeroFormateado = formato.format(number);
            // Regresa numero formateado
            return numeroFormateado;
        }

    }
}
