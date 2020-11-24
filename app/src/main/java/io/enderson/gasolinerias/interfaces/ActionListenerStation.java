package io.enderson.gasolinerias.interfaces;

import android.widget.Button;

import androidx.annotation.NonNull;

import io.enderson.gasolinerias.models.StationModel;

public interface ActionListenerStation {
    void onSave(int index, StationModel station);
    void onDelete(int index, StationModel station);
    void getLocationGPS(@NonNull Button btn);
    void onCancel();
}
