package com.irfpero.mapamap;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener {
    private LatLng latLngBiru;
    private LatLng latLngKuning;
    private LatLng latLngHijau;
    LatLng lokasi;
    Double latitude, longitude;
    private GoogleMap mapku;
    private Marker marker;
    Button button;
//    Boolean a = false;

    private BroadcastReceiver ReceivefromService = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent)
        {
            latitude=intent.getDoubleExtra("Latitude",0);
            longitude=intent.getDoubleExtra("Longitude",0);
            LatLng lokasi = new LatLng(latitude, longitude);

            mapku.clear();
            mapku.addMarker(new MarkerOptions().position(lokasi).title("Lokasiku"));

            Circle circle = mapku.addCircle(new CircleOptions()
                    .center(new LatLng(latitude, longitude))
                    .radius(150)
                    .strokeColor(Color.argb(100,0, 110, 255))
                    .fillColor(Color.argb(100,0, 110, 255)));
//            marker.setPosition(lokasi);
//            if (!a) {
              mapku.animateCamera(CameraUpdateFactory.newLatLngZoom(lokasi, 17));
//                a = true;
//            }

            buatMakrker();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        startService(new Intent(this, LocationService.class));
        setContentView(R.layout.activity_main);

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Pilih Wahana/Binatang :");
        categories.add("Children Zoo");
        categories.add("Burung Pelikan");
        categories.add("Macan Tutul");
        categories.add("Kudanil");
        categories.add("Orang Utan");
        categories.add("Gajah Sumatera");
        categories.add("Beruang Madu");
        categories.add("Gorilla");
        categories.add("KAV.POLRI");
        categories.add("Pintu Barat");
        categories.add("Pintu Selatan");
        categories.add("Pintu Timur");
        categories.add("Pintu Utara");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapku=googleMap;
//        LatLng ft = new LatLng(-7.428727, 109.336334);
//        MarkerOptions options=new MarkerOptions().position(ft).title("Lokasiku");
//        marker=mapku.addMarker(options);
//        mapku.animateCamera(CameraUpdateFactory.newLatLngZoom(ft,15));



        mapku.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mapku.setTrafficEnabled(true);
        mapku.setIndoorEnabled(true);
        mapku.setBuildingsEnabled(true);
        mapku.getUiSettings().setZoomControlsEnabled(true);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startService(new Intent(this, LocationService.class));

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("gpstest");
        registerReceiver(ReceivefromService, filter);
        //the first parameter is the name of the inner class we created.
    }

    public void test(String text){

        Toast.makeText(this, text , Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.kebun,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.kebun0:
                mapku.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude),17.0f));
                break;
            case R.id.kebun1:
                mapku.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-6.711008, 106.947465),15));
                break;
            case R.id.kebun2:
                mapku.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-6.312441, 106.820176),15));
                break;
            case R.id.kebun3:
                mapku.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-7.888231, 112.529828),15));
                break;
            case R.id.kebun4:
                mapku.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-7.296172, 112.736705),15));
                break;
            case R.id.kebun5:
                mapku.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-7.804125, 110.398022),15));
                break;
            case R.id.kebun6:
                mapku.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-6.894550, 107.608257),15));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        switch (i) {
            case 0:
                //mapku.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-7.428727, 109.336334),17));
                break;
            case 1:
                mapku.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-6.306831, 106.819038),17));
                break;
            case 2:
                mapku.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-6.307702, 106.820299),17));
                break;
            case 3:
                mapku.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-6.308245, 106.821029),17));
                break;
            case 4:
                mapku.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-6.308017, 106.823233),17));
                break;
            case 5:
                mapku.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng (-6.311033, 106.822375),17));
                break;
            case 6:
                mapku.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-6.309280, 106.817928),17));
                break;
            case 7:
                mapku.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-6.316378, 106.824456),17));
                break;
            case 8:
                mapku.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-6.312795, 106.820787),17));
                break;
            case 9:
                mapku.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-6.313157, 106.818384),17));
                break;
            case 10:
                mapku.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-6.311391, 106.817703),17));
                break;
            case 11:
                mapku.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng (-6.315081, 106.819580),17));
                break;
            case 12:
                mapku.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-6.312161, 106.825618),17));
                break;
            case 13:
                mapku.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-6.305896, 106.820543),17));
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void buatMakrker(){
        //tamansafaribogor
        latLngBiru = new LatLng(-6.711008, 106.947465);
        mapku.addMarker(new MarkerOptions()
                .position(latLngBiru)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .title("Kebun Raya Bogor").snippet("Jl. Ir. Haji Djuanda No.13 Paledang Central Bogor Bogor City, West Java 16122, Indonesia"));

        //ragunan
        latLngBiru = new LatLng(-6.312441, 106.820176);
        mapku.addMarker(new MarkerOptions()
                .position(latLngBiru)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .title("Kebun Binatang Ragunan").snippet("Jl. Harsono No.1 Ragunan Pasar Minggu South Jakarta City, Jakarta, Indonesia"));

        //batu secret zoo
        latLngBiru = new LatLng(-7.888231, 112.529828);
        mapku.addMarker(new MarkerOptions()
                .position(latLngBiru)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .title("Kebun Binatang Batu ").snippet("Jl. Oro-Oro Ombo No.9 Temas Batu Sub-District Batu City, East Java 65315, Indonesia"));

        //kebun binatang surabaya
        latLngBiru = new LatLng(-7.296172, 112.736705);
        mapku.addMarker(new MarkerOptions()
                .position(latLngBiru)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .title("Kebun Binatang Surabaya").snippet("Jalan Setail No.1, Darmo, Wonokromo, Kota Surabaya"));

        //kebun binatang gembira loka
        latLngBiru = new LatLng(-7.804125, 110.398022);
        mapku.addMarker(new MarkerOptions()
                .position(latLngBiru)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .title("Kebun Binatang Gembira Loka").snippet("Jl. Kebun Raya No.2 Rejowinangun Kotagede Yogyakarta City, Special Region of Yogyakarta 55171, Indonesia"));

        //Kebun Binatang Bandung
        latLngBiru = new LatLng(-6.894550, 107.608257);
        mapku.addMarker(new MarkerOptions()
                .position(latLngBiru)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .title("Kebun Binatang Bandung").snippet("Jalan Kebun Binatang No.6, Lebak Siliwangi, Coblong, Kota Bandung"));

        //RG-children zoo
        latLngKuning = new LatLng(-6.306831, 106.819038);
        mapku.addMarker(new MarkerOptions()
                .position(latLngKuning)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.childrenzoo))
                .title("Children Zoo").snippet("Children Zoo Is a wildlife park featuring a few animals that kids love such as: giant fish arapaima gigas namely fish weighing more than 100 kg There are also a clever parrot talk, mini terrarium contained a beautiful color of condrophyton snake, kapibara are the type of rodents giant (rodent species) are equipped with the means of children’s games such as swings, cobwebs, and so forth. For the entry ticket is Rp. 2.500."));
        latLngKuning = new LatLng(-6.307702, 106.820299);
        mapku.addMarker(new MarkerOptions()
                .position(latLngKuning)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.burung))
                .title("Burung Pelikan").snippet("Burung Air Pemakan Ikan"));
        latLngKuning = new LatLng(-6.308245, 106.821029);
        mapku.addMarker(new MarkerOptions()
                .position(latLngKuning)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.macan))
                .title("Macan Tutul").snippet("Hewan Mamalia"));
        latLngKuning = new LatLng(-6.308017, 106.823233);
        mapku.addMarker(new MarkerOptions()
                .position(latLngKuning)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.kudanil))
                .title("Kudanil").snippet("Hewan Mamalia"));
        latLngKuning = new LatLng(-6.311033, 106.822375);
        mapku.addMarker(new MarkerOptions()
                .position(latLngKuning)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.orangutan))
                .title("Orang Utan").snippet("Hewan Mamalia"));
        latLngKuning = new LatLng(-6.309280, 106.817928);
        mapku.addMarker(new MarkerOptions()
                .position(latLngKuning)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.gajah))
                .title("Gajah Sumatera").snippet("Hewan Mamalia"));
        latLngKuning = new LatLng(-6.316378, 106.824456);
        mapku.addMarker(new MarkerOptions()
                .position(latLngKuning)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.beruang))
                .title("Beruang Madu").snippet("Hewan Mamalia"));
        latLngKuning = new LatLng(-6.312795, 106.820787);
        mapku.addMarker(new MarkerOptions()
                .position(latLngKuning)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.gorilla))
                .title("Gorilla").snippet("Hewan Mamalia"));
        latLngKuning = new LatLng(-6.313157, 106.818384);
        mapku.addMarker(new MarkerOptions()
                .position(latLngKuning)
                .title("KAV.POLRI").snippet("Tempat Pengaduan")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.kavpolri)));
        latLngHijau = new LatLng(-6.311391, 106.817703);
        mapku.addMarker(new MarkerOptions()
                .position(latLngHijau)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pintu))
                .title("PINTU BARAT"));
        latLngHijau = new LatLng(-6.315081, 106.819580);
        mapku.addMarker(new MarkerOptions()
                .position(latLngHijau)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pintu))
                .title("PINTU SELATAN"));
        latLngHijau = new LatLng(-6.312161, 106.825618);
        mapku.addMarker(new MarkerOptions()
                .position(latLngHijau)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pintu))
                .title("PINTU TIMUR"));
        latLngHijau = new LatLng(-6.305896, 106.820543);
        mapku.addMarker(new MarkerOptions()
                .position(latLngHijau)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pintu))
                .title("PINTU UTARA"));
    }
}
