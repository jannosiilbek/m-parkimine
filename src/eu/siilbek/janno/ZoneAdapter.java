package eu.siilbek.janno;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ZoneAdapter extends ArrayAdapter<Zone> {
  private ArrayList<Zone> zones;

  public ZoneAdapter(Context context, int resource, ArrayList<Zone> objects) {
    super(context, resource, objects);
    this.zones = objects;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View v = convertView;
    if (v == null) {
      LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
          Context.LAYOUT_INFLATER_SERVICE);
      v = vi.inflate(R.layout.zone_row, null);
    }

    Zone z = zones.get(position);

    TextView tt = (TextView) v.findViewById(R.id.toptext);
    TextView bt = (TextView) v.findViewById(R.id.bottomtext);

    tt.setText(z.getName());
    bt.setText(z.getComment());

    return v;
  }
  
  
}
