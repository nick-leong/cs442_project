package teamm.cs442_project;

import com.facebook.Profile;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

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
import android.net.Uri;
import android.content.pm.PackageManager;
import android.content.pm.ApplicationInfo;

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
        String userid = Profile.getCurrentProfile().getId();
        Picasso.with(this).load("https://graph.facebook.com/" + userid + "/picture?type=large").into(profileImgBtn);
        profileImgBtn.setMaxHeight(10);
        profileImgBtn.setMaxWidth(10);

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
        CircleOptions options = new CircleOptions()
                .center(new LatLng(lat, lng))
                .radius(8)
                .fillColor(0x330000FF)
                .strokeColor(Color.RED)
                .strokeWidth(2);

        g_map.addCircle(options);


    /*    PolygonOptions options = new PolygonOptions()
                .add(new LatLng(lat + .000060, lng + .000020),new LatLng(lat - .000060, lng + .000020), new LatLng(lat + .000060, lng - .000020), new LatLng(lat - .000060, lng - .000020))
                .fillColor(0x330000FF)
                .strokeColor(Color.RED)
                .strokeWidth(2);
        g_map.addPolygon(options);
    */
    }

    @Override
    public void onMapReady(GoogleMap map) throws SecurityException{

        Location l = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        double currLat = l.getLatitude(); //40.523317;
        double currLon = l.getLongitude(); //-74.44095099999998;

        g_map = map;

        map.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(currLat, currLon), 16, 0, 0)));
        // map.addMarker(new MarkerOptions().position(new LatLng(currLat, currLon)).title("Marker"));
        g_map.addMarker(new MarkerOptions()
                .position(new LatLng(40.525620, -74.437874))
                .title("Starbucks")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.castle_red)));
        setPoly(40.525620, -74.437874);

        g_map.addMarker(new MarkerOptions()
                .position(new LatLng(40.523518, -74.437170))
                .title("Livingston Student Center")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.castle_blue)));
        setPoly(40.523518, -74.437170);

        g_map.addMarker(new MarkerOptions()
                .position(new LatLng(40.521871, -74.435990))
                .title("Killmer Library")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.castle_red)));
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

    public void showFBPage(View view){

    }

    public static Intent newFacebookIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                // http://stackoverflow.com/a/24547437/1048340
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

}
