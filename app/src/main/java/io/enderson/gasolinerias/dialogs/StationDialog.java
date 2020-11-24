package io.enderson.gasolinerias.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialog;

import com.google.android.material.textfield.TextInputLayout;

import io.enderson.gasolinerias.R;
import io.enderson.gasolinerias.interfaces.ActionListenerStation;
import io.enderson.gasolinerias.models.StationModel;

public class StationDialog extends AppCompatDialog {

    private ActionListenerStation actionListener;
    private Button btnLocation, btnGuardar;
    private TextInputLayout txtEstacion, txtEmpresa, txtDepartamento, txtMunicipio;

    public StationDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_gasolineria);

        txtEstacion = findViewById(R.id.txtEstacion);
        txtEmpresa = findViewById(R.id.txtEmpresa);
        txtDepartamento = findViewById(R.id.txtDepartamento);
        txtMunicipio = findViewById(R.id.txtMunicipio);

        btnLocation = findViewById(R.id.btnLocation);
        btnGuardar = findViewById(R.id.btnGuardar);

        btnLocation.setOnClickListener((v) -> {
           if (actionListener != null) {
               actionListener.getLocationGPS(btnLocation);
           }
        });

        btnGuardar.setOnClickListener((v) -> {
            String estacion = txtEstacion.getEditText().getText().toString();
            String empresa = txtEmpresa.getEditText().getText().toString();
            String departamento = txtDepartamento.getEditText().getText().toString();
            String municipio = txtMunicipio.getEditText().getText().toString();

            if (!TextUtils.isEmpty(estacion) && !TextUtils.isEmpty(empresa) && !TextUtils.isEmpty(departamento) && !TextUtils.isEmpty(municipio)) {
                if (actionListener != null) {
                    actionListener.onSave(0, new StationModel(empresa, estacion, departamento, municipio));
                }
            } else {
                Toast.makeText(getContext(), "Se han ingresado campos vacios", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setActionListener(ActionListenerStation actionListener) {
        this.actionListener = actionListener;
    }

    public void close(){
        cancel();
    }


}
