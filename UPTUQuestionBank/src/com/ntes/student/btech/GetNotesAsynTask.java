package com.ntes.student.btech;

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

import com.ntes.GlobalData;
import com.ntes.dto.NotesDTO;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.widget.Toast;

public class GetNotesAsynTask extends AsyncTask<String, String, Void>{
	private Context context;
	private InputStream is;
	private HttpEntity entity;
	private String result = "";
	private String sql;
	private ProgressDialog progressDialog;
	private String ip;
	
	public GetNotesAsynTask(String branch, final Context context) {
		this.context = context;
		sql = "select * from notes where branch = '" + branch + "' and course = 'B.Tech';";
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
						if(null!= context && GetNotesAsynTask.this.isCancelled())
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
		progressDialog.setTitle("Loading.....");
		progressDialog.setMessage("Please Wait.....");
		progressDialog.show();
		progressDialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface arg0) {
				GetNotesAsynTask.this.cancel(true);
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
			GetNotesAsynTask.this.cancel(true);
			Toast.makeText(context.getApplicationContext().getApplicationContext(),"Exception " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
		return null;
	}
	@Override
	protected void onPostExecute(Void v) {
		progressDialog.dismiss();
		try{
			if("false".equals(result))
				Toast.makeText(context.getApplicationContext(),"Notes not found", Toast.LENGTH_LONG).show();
			else{
				JSONArray array = new JSONArray(result);
				JSONObject jsonObject;
				List<NotesDTO> list = new ArrayList<NotesDTO>();
				NotesDTO dto;
				for(int i = 0;i < array.length(); i++){
					jsonObject = array.getJSONObject(i);
					dto = new NotesDTO();
					dto.setBranch(jsonObject.getString("branch"));
					dto.setCourse(jsonObject.getString("course"));
					dto.setFaculty(jsonObject.getString("faculty"));
					dto.setId(jsonObject.getInt("id"));
					dto.setNotes(jsonObject.getString("notes"));
					dto.setTitle(jsonObject.getString("title"));
					list.add(dto);
				}
				GlobalData.notes = list;
				Intent intent = new Intent("com.ntes.student.btech.NotesActivity");
				context.startActivity(intent);
			}
		}
		catch(Exception e){
			Toast.makeText(context.getApplicationContext(),"Exception " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
		super.onPostExecute(v);
	}
}
