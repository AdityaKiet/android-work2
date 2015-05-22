package com.ntes.discussionforum;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ntes.GlobalData;
import com.ntes.uptuquestionbank.R;

public class ExploreQuestion extends Activity{
	private int position;
	private TextView txtExploreQuestionContent;
	private EditText etAnswer;
	private Button btnSubmit;
	private LinearLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.explore_question);
		position = getIntent().getExtras().getInt("position");
		new GetAnswerAsynTask().execute();
		txtExploreQuestionContent = (TextView)findViewById(R.id.txtExploreQuestionTitle);
		etAnswer = (EditText)findViewById(R.id.etPostAnswer);
		btnSubmit = (Button)findViewById(R.id.btnPostAnswer);
		layout = (LinearLayout)findViewById(R.id.linearAnswers);
		txtExploreQuestionContent.setText(GlobalData.questions.get(position).getQuestion());
		btnSubmit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new PostAnswerAsynTask(etAnswer.getText().toString()).execute();
			}
		});
	}
	
	
	private class PostAnswerAsynTask extends AsyncTask<String, String, Void> {
		private InputStream is;
		private HttpEntity entity;
		private String result = "";	
		private String ip,sql;
		private ProgressDialog progressDialog;
		
		public PostAnswerAsynTask(String answer) {
			int id = GlobalData.questions.get(position).getId();
			sql = "insert into discussion_forum_answers(id,answer) values ('" + id+"','"+ answer + "');";
			ResourceBundle rb = ResourceBundle.getBundle("network");
			ip = rb.getString("ip");
		}

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(ExploreQuestion.this);
			progressDialog.setMessage("Please Wait.....");
			progressDialog.show();
			progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface arg0) {
					PostAnswerAsynTask.this.cancel(true);
				}
			});
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... params) {
			List<NameValuePair> list = new ArrayList<NameValuePair>(1);
			list.add(new BasicNameValuePair("sql", sql));
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(ip+"/ntes/savedata.php");
				httpPost.setEntity(new UrlEncodedFormEntity(list));
				HttpResponse httpResponse = httpClient.execute(httpPost);
				entity = httpResponse.getEntity();
				is = entity.getContent();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
				StringBuilder stringBuilder = new StringBuilder();
				String line = "";
				while ((line = bufferedReader.readLine()) != null) {
					stringBuilder.append(line);
				}
				is.close();
				result = stringBuilder.toString();
			} catch (Exception e) {
				PostAnswerAsynTask.this.cancel(true);
				Toast.makeText(getApplicationContext(),"Exception " + e.getMessage(), Toast.LENGTH_LONG).show();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void v) {
			progressDialog.dismiss();
		try{
			if(result.equals("true"))
					Toast.makeText(getApplicationContext(), "Answer Submitted", Toast.LENGTH_LONG).show();
			else
					Toast.makeText(getApplicationContext(), "Answer not Submitted", Toast.LENGTH_LONG).show();
			}
			catch(Exception e){
				PostAnswerAsynTask.this.cancel(true);
				Toast.makeText(getApplicationContext(),"Exception " + e.getMessage(), Toast.LENGTH_LONG).show();
			}
			super.onPostExecute(v);
		}


	}
	private class GetAnswerAsynTask extends AsyncTask<String, String, Void> {
		private InputStream is;
		private HttpEntity entity;
		private String result = "";	
		private String ip,sql;
		private ProgressDialog progressDialog;
		
		public GetAnswerAsynTask() {
			int id = GlobalData.questions.get(position).getId();
			sql = "select * from discussion_forum_answers where id = '" + id + "';";
			ResourceBundle rb = ResourceBundle.getBundle("network");
			ip = rb.getString("ip");
		}

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(ExploreQuestion.this);
			progressDialog.setMessage("Please Wait.....");
			progressDialog.show();
			progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface arg0) {
					GetAnswerAsynTask.this.cancel(true);
				}
			});
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... params) {
			List<NameValuePair> list = new ArrayList<NameValuePair>(1);
			list.add(new BasicNameValuePair("sql", sql));
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(ip+"/ntes/getdata.php");
				httpPost.setEntity(new UrlEncodedFormEntity(list));
				HttpResponse httpResponse = httpClient.execute(httpPost);
				entity = httpResponse.getEntity();
				is = entity.getContent();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
				StringBuilder stringBuilder = new StringBuilder();
				String line = "";
				while ((line = bufferedReader.readLine()) != null) {
					stringBuilder.append(line);
				}
				is.close();
				result = stringBuilder.toString();
			} catch (Exception e) {
				GetAnswerAsynTask.this.cancel(true);
				Toast.makeText(getApplicationContext(),"Exception " + e.getMessage(), Toast.LENGTH_LONG).show();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void v) {
			progressDialog.dismiss();
		try{
			List<String> list = new ArrayList<String>();
			if(!result.equals("false")){
					JSONArray array = new JSONArray(result);
					JSONObject jsonObject;
					for(int i = 0; i < array.length(); i++){
						jsonObject = array.getJSONObject(i);
						list.add(jsonObject.getString("answer"));
					}
					for(String answer : list){
						Log.d("log", answer);
						LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
						View view = inflater.inflate(R.layout.question_single_row, null);
						((TextView)view.findViewById(R.id.txtQuestion)).setText(answer);
						layout.addView(view);
					}
				}
			}
			catch(Exception e){
				GetAnswerAsynTask.this.cancel(true);
				Toast.makeText(getApplicationContext(),"Exception " + e.getMessage(), Toast.LENGTH_LONG).show();
			}
			super.onPostExecute(v);
		}


	}


}
