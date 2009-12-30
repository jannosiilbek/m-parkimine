package eu.siilbek.janno;

import android.os.Bundle;

public class SettingsActivity extends
    android.preference.PreferenceActivity {
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    addPreferencesFromResource(R.xml.preferences);
  }
}
