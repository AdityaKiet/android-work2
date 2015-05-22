package com.teamAndappers.womensafety;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity extends Activity {
	private TextView txt;
	private Button btnnext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		txt=(TextView)findViewById(R.id.welcmtextView);
		btnnext=(Button)findViewById(R.id.welcmbtn);
		btnnext.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			Intent intent=new Intent("com.teamAndappers.womensafety.Registration");
			startActivity(intent);
			WelcomeActivity.this.finish();
		}
	});
		
}
}
