package teamm.cs442_project;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
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

import android.content.DialogInterface;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;



import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{

    GoogleMap g_map;
    user curr_user;
    AccessTokenTracker att;
    ProfileTracker profileTracker;

    ImageView profileImgBtn;
    Button clan_standing;
    Button connect;
    float radius;
    LocationManager locationManager;
    FELocationListener locationListener;

    //Button find_us;

    @Override
    protected void onCreate(Bundle savedInstanceState) throws SecurityException{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String temp_id = "";
        String temp_name = "";
        if(Profile.getCurrentProfile() != null){
            temp_id = Profile.getCurrentProfile().getId();
            temp_name = Profile.getCurrentProfile().getName();
        }

        curr_user = new user(temp_id,
                temp_name,
                "",
                "",
                "None"
        );

        radius = 1000;

        profileImgBtn = (ImageView) findViewById(R.id.profileImgBtn);

        if(isLoggedIn()){
            if(Profile.getCurrentProfile() != null){
                String userid = Profile.getCurrentProfile().getId();
                Picasso.with(this).load("https://graph.facebook.com/" + userid + "/picture?type=large").into(profileImgBtn);
            }
        }
        profileImgBtn.setMaxHeight(10);
        profileImgBtn.setMaxWidth(10);

        profileImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ProfileActivity.class);
                myIntent.putExtra("faction", curr_user.getClan());
                MainActivity.this.startActivityForResult(myIntent, 209);
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

        clan_standing = (Button) findViewById(R.id.clan_standing);
        clan_standing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scoreIntent = new Intent(MainActivity.this, ScoreActivity.class);
                MainActivity.this.startActivity(scoreIntent);
            }
        });

        connect = (Button) findViewById(R.id.connect);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                char a = getTeamChar(curr_user.getClan());
                if(a == 'z'){
                    showDialog("Please join a faction first");
                }else{
                    SocketAsync dataTask = new SocketAsync(""+a);
                    dataTask.execute();
                    showDialog("Your response has been recorded!");
                }
            }
        });

        att = new AccessTokenTracker(){
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken){
                if(currentAccessToken != null){
                    if(Profile.getCurrentProfile() != null){
                        String userid = Profile.getCurrentProfile().getId();
                        curr_user.setId(userid);
                        curr_user.setUsername(Profile.getCurrentProfile().getName());
                        Picasso.with(MainActivity.this).load("https://graph.facebook.com/" + userid + "/picture?type=large").into(profileImgBtn);
                    }
                }
            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile profile, Profile profile1) {
                if(Profile.getCurrentProfile() != null){
                    String userid = Profile.getCurrentProfile().getId();
                    curr_user.setId(userid);
                    curr_user.setUsername(Profile.getCurrentProfile().getName());
                    Picasso.with(MainActivity.this).load("https://graph.facebook.com/" + userid + "/picture?type=large").into(profileImgBtn);
                }
            }
        };
        profileTracker.startTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 209) {
            if (resultCode == RESULT_OK) {
                if(data != null){
                    curr_user.setClan(data.getStringExtra("faction_name"));
                }
            }
        }
    }

    public char getTeamChar(String clan){
        if(clan.equals("Gold")){
            return 'g';
        }else if(clan.equals("Blue")){
            return 'b';
        }else if(clan.equals("Green")){
            return 'r';
        }else if(clan.equals("Orange")){
            return 'o';
        }else{
            return 'z';
        }
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
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
                .title("Kilmer Library")
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
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/426253597411506"));
            startActivity(intent);
        } catch(Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/RUFE-Game-Page-1241076769323661")));
        }
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

    public void showDialog(String msg){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(msg);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Continue",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
