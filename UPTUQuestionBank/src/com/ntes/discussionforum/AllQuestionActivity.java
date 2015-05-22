package com.ntes.discussionforum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ntes.GlobalData;
import com.ntes.uptuquestionbank.R;

public class AllQuestionActivity extends Activity {
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_questions);
		listView = (ListView)findViewById(R.id.listQuestions);
		String[] list = new String[GlobalData.questions.size()];
		for(int i = 0; i  < GlobalData.questions.size(); i++){
			list[i] = GlobalData.questions.get(i).getQuestion();
		}
		ListAdapter adapter = new ListAdapter(AllQuestionActivity.this);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Bundle bundle = new Bundle();
			bundle.putInt("position", position);
			Intent intent = new Intent("com.ntes.discussionforum.ExploreQuestion");
			intent.putExtras(bundle);
			startActivity(intent);
		}
	});
	}

}
