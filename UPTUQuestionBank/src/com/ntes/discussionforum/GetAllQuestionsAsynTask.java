package com.ntes.discussionforum;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

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
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ntes.GlobalData;
import com.ntes.dto.QuestionDTO;

public class GetAllQuestionsAsynTask extends AsyncTask<String, String, Void> {
	private Context context;
	private InputStream is;
	private HttpEntity entity;
	private String result = "";	
	private String ip,sql;
	private ProgressDialog progressDialog;
	
	public GetAllQuestionsAsynTask(final Context context) {
		this.context = context;
		sql = "select * from discussion_forum_questions";
		ResourceBundle rb = ResourceBundle.getBundle("network");
		ip = rb.getString("ip");
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				progressDialog.dismiss();
				this.cancel();
				((Activity)context).runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if(null != context && GetAllQuestionsAsynTask.this.isCancelled())
							Toast.makeText(context.getApplicationContext().getApplicationContext(),"Connection failed..", Toast.LENGTH_LONG).show();
					}
				});
			}
		};
		
		Timer timer = new Timer();
		timer.schedule(task, 15000);
	}

	@Override
	protected void onPreExecute() {
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("Please Wait.....");
		progressDialog.show();
		progressDialog.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface arg0) {
				GetAllQuestionsAsynTask.this.cancel(true);
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
			GetAllQuestionsAsynTask.this.cancel(true);
			Toast.makeText(context.getApplicationContext().getApplicationContext(),"Exception " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void v) {
		progressDialog.dismiss();
		Log.d("log", result);
		try{
		if(result.equals("false"))
			Toast.makeText(context.getApplicationContext(), "No Question Found", Toast.LENGTH_LONG).show();
		else{
			JSONArray jsonArray = new JSONArray(result);
			JSONObject jsonObject;
			QuestionDTO questionDTO;
			List<QuestionDTO> list = new ArrayList<QuestionDTO>();
			for(int i = 0;i < jsonArray.length(); i++){
				jsonObject = jsonArray.getJSONObject(i);
				questionDTO = new QuestionDTO();
				questionDTO.setId(jsonObject.getInt("id"));
				questionDTO.setQuestion(jsonObject.getString("question"));
				list.add(questionDTO);
			}
			GlobalData.questions = list;
		    Intent intent = new Intent("com.ntes.discussionforum.AllQuestionActivity");
			context.startActivity(intent);
			((Activity)context).finish();
			}
		
		}
		catch(Exception e){
			GetAllQuestionsAsynTask.this.cancel(true);
			Toast.makeText(context.getApplicationContext().getApplicationContext(),"Exception " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
		super.onPostExecute(v);
	}

}
