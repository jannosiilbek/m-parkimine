package eu.siilbek.janno;

import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ParkingActivity extends Activity {
  private static final int MENU_OPTIONS = 0;
  private static final String PARKING_ACTIVE = "parking_active";
  private static final String START_TIME = "start_time";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if ("false".equals(pref(PARKING_ACTIVE, "false"))) {
      drawZones();
    } else {
      drawStop();
    }
  }

  static String add_zero(String s) {
    if (s.length() == 1) {
      return "0" + s;
    }
    return s;
  }

  private void drawStop() {
    setContentView(R.layout.stop_parking);

    TextView parkingTimer = (TextView) findViewById(R.id.parkingTimer);

    long pStart = new Long(pref(START_TIME, new Long(SystemClock
        .elapsedRealtime()).toString()));

    long pDuration = SystemClock.elapsedRealtime() - pStart;

    long time = pDuration / 1000;

    String hours = add_zero(Integer.toString((int) (time / 3600)));
    String minutes = add_zero(Integer.toString((int) ((time % 3600) / 60)));
    String seconds = add_zero(Integer.toString((int) (time % 60)));

    parkingTimer.setText(hours + ":" + minutes);

    Button btnStopParking = (Button) findViewById(R.id.btnStopParking);
    btnStopParking.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
            + "1903"));
        startActivity(callIntent);
        ParkingActivity.this.set_pref(PARKING_ACTIVE, "false");
        ParkingActivity.this.finish();

      }
    });
  }

  private void set_pref(String key, String value) {
    SharedPreferences prefs = PreferenceManager
        .getDefaultSharedPreferences(this);
    Editor editor = prefs.edit();
    editor.putString(key, value);
    editor.commit();
  }

  private String pref(String key, String def) {
    SharedPreferences prefs = PreferenceManager
        .getDefaultSharedPreferences(this);

    return prefs.getString(key, def);
  }

  private void drawZones() {
    setContentView(R.layout.start_parking);
    ListView lv = (ListView) findViewById(R.id.zoneListView);
    InputStream is = getApplication().getResources().openRawResource(R.raw.zones);

    final ZoneList zl = new ZoneList(is);
    lv.setAdapter(new ZoneAdapter(this, R.layout.zone_row, zl));
    lv.setOnItemClickListener(new OnItemClickListener() {
      public void onItemClick(AdapterView<?> av, View v, int p, long a) {
        ((Zone) zl.get(p)).park(ParkingActivity.this);
      }
    });

    /**
     * Button btnLocate = (Button) findViewById(R.id.btnLocate);
     * btnLocate.setOnClickListener(new OnClickListener() { public void
     * onClick(View v) { Intent mapIntent = new Intent(ParkingActivity.this,
     * ZoneMapActivity.class); startActivity(mapIntent); } });
     **/

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    menu.add(0, MENU_OPTIONS, 0, "Seaded");
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    case MENU_OPTIONS:
      Intent settingsIntent = new Intent(this, SettingsActivity.class);
      startActivity(settingsIntent);
    }
    return true;
  }
}