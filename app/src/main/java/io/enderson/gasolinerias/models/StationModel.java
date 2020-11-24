package io.enderson.gasolinerias.models;

import android.os.Parcel;
import android.os.Parcelable;

public class StationModel implements Parcelable {
    private long id;
    private String empresa;
    private String estacion;
    private String departamento;
    private String municipio;
    private String longitud;
    private String latitud;

    protected StationModel(Parcel in) {
        id = in.readLong();
        empresa = in.readString();
        estacion = in.readString();
        departamento = in.readString();
        municipio = in.readString();
        longitud = in.readString();
        latitud = in.readString();
    }

    public StationModel (String empresa, String estacion, String departamento, String municipio, String longitud, String latitud) {
        this.empresa = empresa;
        this.estacion = estacion;
        this.departamento = departamento;
        this.municipio = municipio;
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public StationModel (long id, String empresa, String estacion, String departamento, String municipio, String longitud, String latitud) {
        this.id = id;
        this.empresa = empresa;
        this.estacion = estacion;
        this.departamento = departamento;
        this.municipio = municipio;
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public static final Creator<StationModel> CREATOR = new Creator<StationModel>() {
        @Override
        public StationModel createFromParcel(Parcel in) {
            return new StationModel(in);
        }

        @Override
        public StationModel[] newArray(int size) {
            return new StationModel[size];
        }
    };

    public StationModel(String empresa, String estacion, String departamento, String municipio) {
        this.empresa = empresa;
        this.estacion = estacion;
        this.departamento = departamento;
        this.municipio = municipio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(empresa);
        dest.writeString(estacion);
        dest.writeString(departamento);
        dest.writeString(municipio);
        dest.writeString(longitud);
        dest.writeString(latitud);
    }


    public long getId() {
        return id;
    }

    public String getEmpresa() {
        return empresa;
    }

    public String getEstacion() {
        return estacion;
    }

    public String getDepartamento() {
        return departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getLongitud() {
        return longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public void setEstacion(String estacion) {
        this.estacion = estacion;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }
}
