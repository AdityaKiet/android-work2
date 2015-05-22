package com.teamAndappers.womensafety;

import java.lang.reflect.Method;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.WindowManager;

public class Splash extends Activity {
	private SharedPreferences sp = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
				| WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		final Thread timer = new Thread() {
			public void run() {
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					if (null == sp.getString("user", null)) {
						Intent intent = new Intent("com.teamAndappers.womensafety.WelcomeActivity");
						startActivity(intent);
					} else {
						Intent intent = new Intent("com.teamAndappers.womensafety.ControllerActivity");
						startActivity(intent);
					}
				}
			}
		};
		
		
		final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Alert");
		dialog.setMessage("Do you want to lauch this application ?");
		dialog.setCancelable(false);
		dialog.setPositiveButton("Confirm ",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				if (isNetworkAvailable()) 
						timer.start();
				else {
					if (null == sp.getString("user", null)) {
						Intent intent = new Intent("com.teamAndappers.womensafety.WelcomeActivity");
						startActivity(intent);
					} else {
								autoDataConnection();
								timer.start();
							}
						}
					}
				});
		dialog.setNegativeButton("No !!",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						Splash.this.finish();
					}
				});
		if (null != sp.getString("user", null)) {
		dialog.setNeutralButton("Update", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent("com.teamAndAppers.womensafety.UpdateActivity");
				startActivity(intent);
				Splash.this.finish();
			}
		});
		}
		final AlertDialog alert = dialog.create();
		alert.show();
		
		final Handler handler = new Handler();
		final Runnable runnable = new Runnable() {
			@Override
			public void run() {
				if (alert.isShowing()) {
					alert.dismiss();
					if (isNetworkAvailable()) {
						timer.start();
					} else {
						if (null == sp.getString("user", null)) {
							Intent intent = new Intent("com.teamAndappers.womensafety.WelcomeActivity");
							startActivity(intent);
						} else {
							autoDataConnection();
							timer.start();
						}
					}

				} else {
					if (null == sp.getString("user", null)) {
						Thread timer1 = new Thread() {
							public void run() {
								try 
									{sleep(1000);}
								catch (InterruptedException e) 
									{e.printStackTrace();}
								finally {
									Intent intent = new Intent("com.teamAndappers.womensafety.WelcomeActivity");
									startActivity(intent);
								}
							}
						};
						timer1.start();
					} else {
						autoDataConnection();
						timer.start();
					}

				}
			}
		};
		alert.setOnDismissListener(new DialogInterface.OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface arg0) {
				handler.removeCallbacks(runnable);
			}
		});
		handler.postDelayed(runnable, 5000);
		}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

	@Override
	public void onBackPressed() {

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if (Intent.ACTION_MAIN.equals(intent.getAction())) {
		}
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	/*public void autoCall() {
		Uri uri = Uri.fromParts("tel", sp.getString("key1", null), null);
		Intent callIntent = new Intent(Intent.ACTION_CALL, uri);
		callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(callIntent);
	}*/
	public void autoDataConnection(){
		ConnectivityManager dataManager;
		dataManager  = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		Method dataMtd = null;
		try {
			dataMtd = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", boolean.class);
			dataMtd.setAccessible(true);
			dataMtd.invoke(dataManager, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
}
