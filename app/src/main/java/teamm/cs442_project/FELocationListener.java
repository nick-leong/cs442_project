package teamm.cs442_project;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by nickl on 3/21/2017.
 */

public class FELocationListener implements LocationListener {

    @Override
    public void onLocationChanged(Location location){
        Log.d("LOCATION_LISTENER", "onLocationChanged: "+location);
    }

    @Override
    public void onProviderDisabled(String provider){

    }

    @Override
    public void onProviderEnabled(String provider){

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras){

    }
}
