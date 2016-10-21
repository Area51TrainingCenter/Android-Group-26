package pe.area51.googlemaps;

import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MyMapFragment extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMyLocationChangeListener {

    private GoogleMap googleMap;
    private boolean cameraUpdated = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_map, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_map_normal:
                setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.action_map_hybrid:
                setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.action_map_satellite:
                setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.action_map_terrain:
                setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMyLocationEnabled(true);
        googleMap.setTrafficEnabled(true);
        googleMap.setOnMyLocationChangeListener(this);
    }

    @Override
    public void onMyLocationChange(Location location) {
        if (!cameraUpdated) {
            moveToLocation(location, true);
            cameraUpdated = true;
        }
    }

    private void moveToLocation(final Location location, boolean animate) {
        final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        final CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(20)
                .bearing(70)
                .tilt(40)
                .build();
        final CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        if (animate) {
            googleMap.animateCamera(cameraUpdate, 5000, null);
        } else {
            googleMap.moveCamera(cameraUpdate);
        }
    }

    private void setMapType(final int mapType) {
        googleMap.setMapType(mapType);
    }
}
