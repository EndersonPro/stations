package io.enderson.gasolinerias.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.enderson.gasolinerias.models.StationModel;

public class StationDatabaseController {
    private SQLiteSimpleDatabase db;
    public static final String db_name = "station_db";

    public StationDatabaseController(Context context) {
        db = new SQLiteSimpleDatabase(context, db_name, StationTable.create_query, 8);
    }

    public List<StationModel> list () {
        try {
            SQLiteDatabase sql = db.getReadableDatabase();
            Cursor cursor = sql.query(StationTable.table_name, null, null,
                    null, null, null, null, null);
            List<StationModel> stations = new ArrayList<>();
            while(cursor.moveToNext()){
                long id = cursor.getLong(0);
                String empresa = cursor.getString(1);
                String estacion = cursor.getString(2);
                String departamento = cursor.getString(3);
                String municipio = cursor.getString(4);
                String longitud = cursor.getString(5);
                String latitud = cursor.getString(6);
                stations.add(new StationModel(id, empresa, estacion, departamento, municipio, longitud, latitud));
            }
            cursor.close();
            return stations;
        }catch (Exception e){
            Log.e(getClass().getName(), Log.getStackTraceString(e));
            return null;
        }
    }

    public StationModel add (StationModel station) {
        try {
            SQLiteDatabase sql = db.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(StationTable.col_empresa, station.getEmpresa());
            values.put(StationTable.col_estacion, station.getEstacion());
            values.put(StationTable.col_departamento, station.getDepartamento());
            values.put(StationTable.col_municipio, station.getMunicipio());
            values.put(StationTable.col_longitud, station.getLongitud());
            values.put(StationTable.col_latitud, station.getLatitud());
            long id = sql.insert(StationTable.table_name, null, values);
            station.setId(id);
            return station;
        } catch (Exception e) {
            Log.e(getClass().getName(), Log.getStackTraceString(e));
            return null;
        }
    }
}
