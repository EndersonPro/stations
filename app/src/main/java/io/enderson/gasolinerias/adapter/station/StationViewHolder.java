package io.enderson.gasolinerias.adapter.station;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;

import io.enderson.gasolinerias.R;
import io.enderson.gasolinerias.adapter.GenericViewHolder;

public class StationViewHolder extends GenericViewHolder {

    private TextView txEstacion, txEmpresa, txDepartamento, txMunicipio, txLogitud, txLatitud;

    public StationViewHolder(@NonNull View itemView) {
        super(itemView);
        txEstacion = itemView.findViewById(R.id.txEstacion);
        txEmpresa = itemView.findViewById(R.id.txEmpresa);
        txDepartamento = itemView.findViewById(R.id.txDepartamento);
        txMunicipio = itemView.findViewById(R.id.txMunicipio);
        //txLogitud = itemView.findViewById(R.id.txtLogitud);
        //txLatitud = itemView.findViewById(R.id.txtLatitud);
    }

    public TextView getTxEstacion() {
        return txEstacion;
    }

    public void setTxEstacion(TextView txEstacion) {
        this.txEstacion = txEstacion;
    }

    public TextView getTxEmpresa() {
        return txEmpresa;
    }

    public void setTxEmpresa(TextView txEmpresa) {
        this.txEmpresa = txEmpresa;
    }

    public TextView getTxDepartamento() {
        return txDepartamento;
    }

    public void setTxDepartamento(TextView txDepartamento) {
        this.txDepartamento = txDepartamento;
    }

    public TextView getTxMunicipio() {
        return txMunicipio;
    }

    public void setTxMunicipio(TextView txMunicipio) {
        this.txMunicipio = txMunicipio;
    }

    public TextView getTxLogitud() {
        return txLogitud;
    }

    public void setTxLogitud(TextView txLogitud) {
        this.txLogitud = txLogitud;
    }

    public TextView getTxLatitud() {
        return txLatitud;
    }

    public void setTxLatitud(TextView txLatitud) {
        this.txLatitud = txLatitud;
    }
}
