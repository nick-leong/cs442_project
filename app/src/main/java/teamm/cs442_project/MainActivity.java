package teamm.cs442_project;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{

    ImageView profileImgBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

    @Override
    public void onMapReady(GoogleMap map) {
        double currLat = 40.523317;
        double currLon = -74.44095099999998;

        map.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(currLat, currLon), 16, 0, 0)));
        map.addMarker(new MarkerOptions().position(new LatLng(currLat, currLon)).title("Marker"));
    }
}
