package com.ntes.student.mpharm;

import com.ntes.GlobalData;
import com.ntes.uptuquestionbank.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class NotesDisplay extends Activity {
	private TextView txtTitle, txtNotes;
	private int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		position = getIntent().getExtras().getInt("position");
		setContentView(R.layout.notes_display);
		txtNotes = (TextView) findViewById(R.id.txtNotes);
		txtTitle = (TextView) findViewById(R.id.txtTitle);
		txtNotes.setText(GlobalData.notes.get(position).getNotes());
		txtTitle.setText(GlobalData.notes.get(position).getTitle());
	}

}
