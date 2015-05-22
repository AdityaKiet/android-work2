package com.wage;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.wageworkerapplication.R;

public class MainActivity extends Activity implements OnClickListener{
	
	private Button btnPostJob, btnPreviousJob;
	private EditText etDisc, etStartDate, etEndDate, etRenum, etLocation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		popultae();
	}

	private void popultae() {
		btnPostJob = (Button)findViewById(R.id.btnPost);
		btnPreviousJob = (Button)findViewById(R.id.btnOldJobs);
		etDisc = (EditText)findViewById(R.id.etJobDisc);
		etEndDate = (EditText)findViewById(R.id.etJobEndDate);
		etStartDate = (EditText)findViewById(R.id.etJobStartDate);
		etRenum = (EditText)findViewById(R.id.etremuneration);
		etLocation = (EditText)findViewById(R.id.etLocation);
		btnPostJob.setOnClickListener(this);
		btnPreviousJob.setOnClickListener(this);
		etStartDate.setOnClickListener(this);
		etEndDate.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnOldJobs :
			
			break;
		case R.id.btnPost :
			
			break;
		case R.id.etJobStartDate:
			DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
				public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					StringBuffer dobBuffer = new StringBuffer();
					dobBuffer.append(year + "-"+ (monthOfYear+1) +"-"+dayOfMonth);
					etStartDate.setText(dobBuffer.toString());
				}
			};

			final Calendar calendar = Calendar.getInstance();
			DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, datePickerListener,calendar.get(Calendar.YEAR) ,calendar.get(Calendar.MONTH) , calendar.get(Calendar.DAY_OF_MONTH));
			datePickerDialog.setCancelable(true);
			datePickerDialog.show();
			break;

		case R.id.etJobEndDate:
			DatePickerDialog.OnDateSetListener datePickerListener1 = new DatePickerDialog.OnDateSetListener() {
				public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					StringBuffer dobBuffer = new StringBuffer();
					dobBuffer.append(year + "-"+ (monthOfYear+1) +"-"+dayOfMonth);
					etEndDate.setText(dobBuffer.toString());
				}
			};
			final Calendar calendar1 = Calendar.getInstance();
			DatePickerDialog datePickerDialog1 = new DatePickerDialog(MainActivity.this, datePickerListener1,calendar1.get(Calendar.YEAR) ,calendar1.get(Calendar.MONTH) , calendar1.get(Calendar.DAY_OF_MONTH));
			datePickerDialog1.setCancelable(true);
			datePickerDialog1.show();
			break;
		}
	}

}
