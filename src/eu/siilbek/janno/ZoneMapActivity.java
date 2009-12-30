package eu.siilbek.janno;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.app.Activity;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class ZoneMapActivity extends MapActivity implements LocationListener {
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.map);

    MapView mv = (MapView) findViewById(R.id.zoneMap);
    mv.setBuiltInZoomControls(true);
    MapController control = mv.getController();
    GeoPoint p = new GeoPoint((int) (59.438862 * 1000000),
        (int) (24.754472 * 1000000));
    control.animateTo(p);
    control.setZoom(16);

    LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);

    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
  }

  @Override
  protected boolean isRouteDisplayed() {
    return false;
  }

  public void onLocationChanged(Location l) {
    MapView mv = (MapView) findViewById(R.id.zoneMap);
    MapController control = mv.getController();
    GeoPoint p = new GeoPoint((int) (l.getLatitude() * 1E6), (int) (l
        .getLongitude() * 1E6));
    control.animateTo(p);
    mv.invalidate();

    Toast.makeText(
        getBaseContext(),
        "Location changed : Lat: " + l.getLatitude() + " Lng: "
            + l.getLongitude(), Toast.LENGTH_SHORT).show();

  }

  public void onProviderDisabled(String arg0) {
    // TODO Auto-generated method stub

  }

  public void onProviderEnabled(String arg0) {
    // TODO Auto-generated method stub

  }

  public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
    // TODO Auto-generated method stub

  }
}
