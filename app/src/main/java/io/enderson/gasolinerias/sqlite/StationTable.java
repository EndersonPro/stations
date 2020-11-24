package io.enderson.gasolinerias.sqlite;

public class StationTable {
    public static final String table_name = "stations";
    public static final String col_id = "id";
    public static final String col_empresa = "empresa";
    public static final String col_estacion = "estacion";
    public static final String col_departamento = "departamento";
    public static final String col_municipio = "municipio";
    public static final String col_longitud = "longitud";
    public static final String col_latitud = "latitud";

    public static final String create_query = "CREATE TABLE IF NOT EXISTS " + StationTable.table_name + " ( " +
            StationTable.col_id +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            StationTable.col_empresa + " TEXT," +
            StationTable.col_estacion + " TEXT," +
            StationTable.col_departamento + " TEXT," +
            StationTable.col_municipio + " TEXT," +
            StationTable.col_longitud + " TEXT," +
            StationTable.col_latitud + " TEXT" +
            ");";
}
