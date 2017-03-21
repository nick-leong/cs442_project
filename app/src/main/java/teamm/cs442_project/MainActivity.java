package teamm.cs442_project;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{

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

    }

    @Override
    public void onMapReady(GoogleMap map) throws SecurityException{

        Location l = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        double currLat = l.getLatitude(); //40.523317;
        double currLon = l.getLongitude(); //-74.44095099999998;

        map.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(currLat, currLon), 16, 0, 0)));
        map.addMarker(new MarkerOptions().position(new LatLng(currLat, currLon)).title("Marker"));
    }
}
