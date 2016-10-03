package pe.area51.locationapp;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager;
    private TextView locationInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationInfoTextView = (TextView) findViewById(R.id.textview_location_info);
    }

    private void startLocationUpdates() {
        final List<String> providers = locationManager.getAllProviders();
        for (final String provider : providers) {
            locationManager.requestLocationUpdates(provider, 0, 0, this);
        }
    }

    private void stopLocationUpdates() {
        locationManager.removeUpdates(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void showLocation(final Location location) {
        final String provider = getString(R.string.provider, location.getProvider());
        final String latitude = getString(R.string.latitude, location.getLatitude());
        final String longitute = getString(R.string.longitude, location.getLongitude());
        final String altitude = getString(R.string.altitude, location.getAltitude());
        locationInfoTextView.setText(provider + "\n" + latitude + "\n" + longitute + "\n" + altitude);
    }

    @Override
    public void onLocationChanged(Location location) {
        showLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
