package com.ntes.discussionforum;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ntes.uptuquestionbank.R;

public class MainActivity extends Activity{
	private EditText etQuestion;
	private Button btnSubmit, btnOldQuestions;
	private String question;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_question);
		btnOldQuestions = (Button)findViewById(R.id.btnPrevioisQuestions);
		etQuestion = (EditText)findViewById(R.id.etQuestionContent);
		btnSubmit = (Button)findViewById(R.id.btnSubmitQuestion);
		btnSubmit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				question = etQuestion.getText().toString();
				if(question.trim().equals(""))
					Toast.makeText(getApplicationContext(), "Field can't be blank", Toast.LENGTH_LONG).show();
				else{
					new PostQuestionAsynTask(MainActivity.this, question).execute();
				}
			}
		});
		btnOldQuestions.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new GetAllQuestionsAsynTask(MainActivity.this).execute();
			}
		});
	}

}
