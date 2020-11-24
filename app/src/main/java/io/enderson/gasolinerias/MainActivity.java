package io.enderson.gasolinerias;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.enderson.gasolinerias.adapter.GenericAdapter;
import io.enderson.gasolinerias.adapter.station.StationViewHolder;
import io.enderson.gasolinerias.dialogs.StationDialog;
import io.enderson.gasolinerias.interfaces.ActionListenerStation;
import io.enderson.gasolinerias.models.StationModel;
import io.enderson.gasolinerias.sqlite.StationDatabaseController;

public class MainActivity extends AppCompatActivity {

    private StationDialog addStation;
    private FloatingActionButton fabAdd;
    double latitude, longitude;
    LocationManager locationManager;
    Button btnLocation;
    private List<StationModel> stations;
    StationDatabaseController stationDatabaseController;
    private RecyclerView recStations;
    private GenericAdapter<StationViewHolder> adapterStations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        addStation = new StationDialog(this);
        fabAdd = findViewById(R.id.fabAdd);
        stationDatabaseController = new StationDatabaseController(this);
        stations = new ArrayList<>();
        recStations = (RecyclerView) findViewById(R.id.recStations);

        addStation.setActionListener(new ActionListenerStation() {
            @Override
            public void onSave(int index, StationModel station) {
                station.setLatitud(String.valueOf(latitude));
                station.setLongitud(String.valueOf(longitude));
                StationModel stationRecord = stationDatabaseController.add(station);
                if (stationRecord != null) {
                    stations.add(stationRecord);
                    adapterStations.notifyItemInserted(stations.size() - 1);
                }
                addStation.close();
            }
            @Override
            public void onDelete(int index, StationModel station) {
                // handle Delete
            }

            @Override
            public void getLocationGPS(@NonNull Button btn) {
                Log.d("Location", "Metodo llamado");
                btnLocation = btn;
                if (!checkLocation()) return;
                if (btn.getText().equals(getResources().getString(R.string.ubicacion))) {
                    btn.setText(R.string.getUbicacion);
                    btn.setBackgroundColor(R.color.design_default_color_primary);
                }
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // FIXME: Existe un bug en esta parte, se sospecha que el primer parametro causa el problema.
                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    }, 1);
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2 * 20 * 1000, 10, locationListenerGPS);
            }
            @Override
            public void onCancel() {
                // handle cancel
            }
        });
        fabAdd.setOnClickListener((v) -> {
            addStation.show();
        });

        adapterStations = new GenericAdapter<>(this, R.layout.view_holder_station, new GenericAdapter.InstanceCallback<StationViewHolder>() {
            @Override
            public StationViewHolder onCreateViewHolder(View view) {
                return new StationViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull StationViewHolder viewHolder, int i) {
                final StationModel stationModel = stations.get(viewHolder.getAdapterPosition());

                viewHolder.getTxEmpresa().setText(stationModel.getEmpresa());
                viewHolder.getTxDepartamento().setText(stationModel.getDepartamento());
                viewHolder.getTxEstacion().setText(stationModel.getEstacion());
                viewHolder.getTxMunicipio().setText(stationModel.getMunicipio());

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("clic", "Le estoy dando clic cole");
                        Intent intent = new Intent(getBaseContext(), MapActivity.class);
                        intent.putExtra("latitud", stationModel.getLatitud());
                        intent.putExtra("longitud", stationModel.getLongitud());
                        intent.putExtra("estacion", stationModel.getEstacion());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return stations.size();
            }
        });

        recStations.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && fabAdd.isShown())
                    fabAdd.hide();
            }
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    fabAdd.show();
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        adapterStations.setOn(recStations, false);

        stations = stationDatabaseController.list();
    }

    private boolean checkLocation() {
        final boolean isLocationEnable = isLocationEnabled();
        if (isLocationEnable) return true;
        showAlert();
        return false;
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Activar ubicación")
                .setMessage("Su ubicación esta desactivada.\npor favor active su ubicación " +
                        "usa esta app")
                .setPositiveButton("Configuración de ubicación", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private final LocationListener locationListenerGPS = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            btnLocation.setText(R.string.ubicacionObtenida);
            btnLocation.setBackgroundColor(R.color.colorAccent);
            Log.d("Longitude", location.getLongitude()+"");
            Log.d("Latitute", location.getLatitude()+"");
            runOnUiThread(() -> {
                //longitudeValueGPS.setText(longitudeGPS + "");
                //latitudeValueGPS.setText(latitudeGPS + "");
                Toast.makeText(MainActivity.this, "Ubicacion obtenida", Toast.LENGTH_SHORT).show();
            });
        }
        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }
        @Override
        public void onProviderDisabled(String s) {
        }
    };
}