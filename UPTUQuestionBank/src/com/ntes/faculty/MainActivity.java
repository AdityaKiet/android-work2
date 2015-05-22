package com.ntes.faculty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ntes.faculty.logout.Logout;
import com.ntes.uptuquestionbank.R;

public class MainActivity extends Activity implements OnClickListener{
	private Button btnBtech, btnMtech, btnMba, btnMca, btnBpharm, btnMpharm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_course);
		populate();
	}

	private void populate() {
		btnBpharm = (Button)findViewById(R.id.btnSelectbharm);
		btnBtech = (Button)findViewById(R.id.btnSelectBtech);
		btnMtech = (Button)findViewById(R.id.btnSelectMtech);
		btnMba = (Button)findViewById(R.id.btnSelectMba);
		btnMca = (Button)findViewById(R.id.btnSelectMca);
		btnMpharm = (Button)findViewById(R.id.btnSelectmpharm);
		
		btnBpharm.setOnClickListener(this);
		btnBtech.setOnClickListener(this);
		btnMtech.setOnClickListener(this);
		btnMba.setOnClickListener(this);
		btnMca.setOnClickListener(this);
		btnMpharm.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.student_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_aboutus :
			Intent intent = new Intent("com.ntes.discussionforum.MainActivity");
			startActivity(intent);
			break;
		case R.id.logout:
			Logout logout = new Logout(MainActivity.this);
			logout.logout();
			break;
		default:
			break;
			
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch(v.getId()){
		case R.id.btnSelectBtech:
			intent = new Intent("com.ntes.faculty.btech.BranchActivity");
			startActivity(intent);
			break;
		case R.id.btnSelectMtech:
			intent = new Intent("com.ntes.faculty.mtech.BranchActivity");
			startActivity(intent);
			break;
		case R.id.btnSelectMba:
			intent = new Intent("com.ntes.faculty.mba.NotesUploadActivity");
			startActivity(intent);
			break;
		case R.id.btnSelectMca:
			intent = new Intent("com.ntes.faculty.mca.NotesUploadActivity");
			startActivity(intent);
			break;
		case R.id.btnSelectbharm:
			intent = new Intent("com.ntes.faculty.bpharm.NotesUploadActivity");
			startActivity(intent);
			break;
		case R.id.btnSelectmpharm:
			intent = new Intent("com.ntes.faculty.mpharm.NotesUploadActivity");
			startActivity(intent);
			break;
		}
		
	}
}
