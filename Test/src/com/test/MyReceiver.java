package com.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {

		Log.v("#@%@%#", "Power button is pressed.");

		Toast.makeText(arg0, "power button clicked", Toast.LENGTH_LONG).show();

	}
}