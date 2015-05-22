package com.ntes;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ntes.uptuquestionbank.R;

public class MainActivity extends Activity implements OnClickListener{

	private Button btnStudent, btnTeacher;
	private SharedPreferences sharedPreferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		populate();
	}
	private void populate() {
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		btnStudent = (Button)findViewById(R.id.btnSelectStudent);
		btnTeacher = (Button)findViewById(R.id.btnSelectTeacher);
		btnStudent.setOnClickListener(this);
		btnTeacher.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnSelectStudent:
			if(sharedPreferences.getString("student", null) == null){
				Intent intent = new Intent("com.ntes.student.RegisterActivity");
				startActivity(intent);
			}else{
				Intent intent = new Intent("com.ntes.student.MainActivity");
				startActivity(intent);
				MainActivity.this.finish();
			}
			break;
		case R.id.btnSelectTeacher:
			if(sharedPreferences.getString("faculty", null) == null){
				Intent intent = new Intent("com.ntes.faculty.RegisterActivity");
				startActivity(intent);
			}else{
				Intent intent = new Intent("com.ntes.faculty.MainActivity");
				startActivity(intent);
				MainActivity.this.finish();
			}
			break;
		default:
			break;
		}
	}

}
