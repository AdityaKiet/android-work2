package com.ntes.student.mba;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ntes.student.btech.ListAdapter;
import com.ntes.uptuquestionbank.R;

public class NotesActivity extends Activity {
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notes);
		listView = (ListView)findViewById(R.id.listNotes);
		ListAdapter adapter = new ListAdapter(NotesActivity.this);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Bundle bundle = new Bundle();
				bundle.putInt("position", position);
				Intent intent = new Intent("com.ntes.student.btech.NotesDisplay");
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

}
