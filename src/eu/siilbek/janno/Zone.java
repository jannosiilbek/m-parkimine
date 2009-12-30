package eu.siilbek.janno;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.telephony.gsm.SmsManager;

public class Zone {
  public String city, key, provider, address, desc;

  public String getName() {
    return provider + " " + key;
  }

  public String getComment() {
    if (notEmpty(address)) {
      return address + " " + desc;
    }
    return desc;
  }

  static boolean notEmpty(String s) {
    if (s == null) {
      return false;
    }

    return s.length() > 0;
  }

  public void park(final Activity ctx) {
    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ctx);
    final String regno = pref("regno", "", ctx);
    final String msg = "Alustan parkimist s›idukile " + regno + " tsoonis "
        + key;

    alertBuilder.setMessage(msg + "?").setCancelable(false).setPositiveButton(
        "Jah", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int which) {
            String msg = regno + " " + key;
            SmsManager sm = SmsManager.getDefault();
            sm.sendTextMessage("1902", null, msg, null, null);
            send_notification("Parkimine alustatud ", "s›iduk " + regno
                + " tsoon " + key, ctx);
            set_pref("parking_active","true", ctx);
            set_pref("start_time", new Long(SystemClock.elapsedRealtime()).toString(), ctx);
            ctx.finish();
          }
        }).setNegativeButton("Ei", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
      }
    });

    alertBuilder.create();
    alertBuilder.show();
  }

  static void send_notification(String title, String msg, Context ctx) {
    String ns = Context.NOTIFICATION_SERVICE;
    NotificationManager mNotificationManager = (NotificationManager) ctx
        .getSystemService(ns);
    int icon = R.drawable.icon;
    Notification notification = new Notification(icon, "Mobiilne parkimine",
        System.currentTimeMillis());
    Intent notificationIntent = new Intent(ctx, ParkingActivity.class);
    PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,
        notificationIntent, 0);

    notification.setLatestEventInfo(ctx, title, msg, contentIntent);
    mNotificationManager.notify(101, notification);
  }

  static void set_pref(String key, String value, Context ctx) {
    SharedPreferences prefs = PreferenceManager
        .getDefaultSharedPreferences(ctx);
    Editor editor = prefs.edit();
    editor.putString(key, value);
    editor.commit();
  }

  static String pref(String key, String def, Context ctx) {
    SharedPreferences prefs = PreferenceManager
        .getDefaultSharedPreferences(ctx);

    return prefs.getString(key, def);
  }
}
