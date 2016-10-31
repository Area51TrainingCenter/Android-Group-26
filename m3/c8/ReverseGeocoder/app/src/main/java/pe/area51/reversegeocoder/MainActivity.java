package pe.area51.reversegeocoder;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private TextView locationResultTextView;
    private Location currentLocation;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationResultTextView = (TextView) findViewById(R.id.textview_location_result);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (final String locationProvider : locationManager.getAllProviders()) {
            locationManager.requestLocationUpdates(locationProvider, 0, 0, this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_get_address:
                if (currentLocation != null) {
                    onGetAddress(currentLocation);
                } else {
                    showError(getString(R.string.no_location));
                }
                return true;
            default:
                return false;
        }
    }

    private void onGetAddress(final Location location) {
        Request<Address> getAddressRequest = RequestFactory.doReverseGeocodingRequest(location.getLatitude(), location.getLongitude());
        getAddressRequest.doAsync(new Request.RequestCallback<Address>() {
            @Override
            public void onResponse(ResponseObject<Address> responseObject) {
                if (responseObject.hasError()) {
                    showError(getString(responseObject.getErrorMessageId()));
                } else {
                    showAddress(responseObject.getResponseBody());
                }
            }
        });
    }

    private void showProgress() {

    }

    private void dismissProgress() {

    }

    private void showAddress(final Address address) {
        locationResultTextView.setText(address.toString());
    }

    private void showError(final String errorMessage) {
        Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
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
