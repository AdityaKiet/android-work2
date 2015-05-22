package com.ntes.student.logout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class Logout {
	private Context context;
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor sharedEditor;
	
	public Logout(Context context) {
		this.context = context;
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(((Activity)this.context).getBaseContext());
		sharedEditor = sharedPreferences.edit();
	}
	
	public void logout(){
		sharedEditor.putString("student", null);
	    if(sharedEditor.commit()){
	    	Intent intent = new Intent("com.ntes.MainActivity");
	    	((Activity)context).startActivity(intent);
	    	((Activity)context).finish();
	    	Toast.makeText(((Activity)context).getApplicationContext(), "Logout Successful..", Toast.LENGTH_LONG).show();
	    }else{
	    	Toast.makeText(((Activity)context).getApplicationContext(), "Logout Failed..", Toast.LENGTH_LONG).show();
	    }
	}
}
