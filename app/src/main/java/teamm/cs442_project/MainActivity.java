package teamm.cs442_project;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{

    GoogleMap g_map;

    ImageView profileImgBtn;
    float radius;
    LocationManager locationManager;
    FELocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) throws SecurityException{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radius = 1000;

        profileImgBtn = (ImageView) findViewById(R.id.profileImgBtn);

        profileImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ProfileActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //check for GPS on then prompt to turn on

        locationListener = new FELocationListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000L, radius, locationListener);

        final Handler handler = new Handler();
        final Runnable map_updater = new Runnable() {
            @Override
            public void run() {
                //while(true){
                    //updateMap();
                    handler.postDelayed(this, 1000);
                //}
            }
        };
        handler.postDelayed(map_updater, 1000);
    }

    private void setPoly(double lat, double lng){
        drawCircle(new LatLng(lat, lng));
    }
    private Circle drawCircle(LatLng latLng){
        CircleOptions options = new CircleOptions()
                .center(latLng)
                .radius(30)
                .fillColor(0x33FF0000)
                .strokeColor(Color.BLUE)
                .strokeWidth(3);

        return g_map.addCircle(options);
    }

    @Override
    public void onMapReady(GoogleMap map) throws SecurityException{

        Location l = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        double currLat = l.getLatitude(); //40.523317;
        double currLon = l.getLongitude(); //-74.44095099999998;

        g_map = map;

        map.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(currLat, currLon), 16, 0, 0)));
        // map.addMarker(new MarkerOptions().position(new LatLng(currLat, currLon)).title("Marker"));
        g_map.addMarker(new MarkerOptions().position(new LatLng(40.525761, -74.437889)).title("Starbucks Marker"));
        setPoly(40.525761, -74.437889);

        g_map.addMarker(new MarkerOptions().position(new LatLng(40.523518, -74.437170)).title("Livingston Student Center Marker"));
        setPoly(40.523518, -74.437170);

        g_map.addMarker(new MarkerOptions().position(new LatLng(40.521871, -74.435990)).title("Killmer Library Marker"));
        setPoly(40.521871, -74.435990);


    }

    private void updateMap() throws SecurityException{
        Location l = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        double currLat = l.getLatitude(); //40.523317;
        double currLon = l.getLongitude(); //-74.44095099999998;

        if(g_map != null){
            g_map.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(currLat, currLon), 16, 0, 0)));


        }
    }
}
