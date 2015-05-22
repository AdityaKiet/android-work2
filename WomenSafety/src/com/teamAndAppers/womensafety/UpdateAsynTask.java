package com.teamAndAppers.womensafety;

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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.teamAndappers.womensafety.dto.UserDTO;

public class UpdateAsynTask extends AsyncTask<String, String, Void>{
	private Context context;
	private InputStream is;
	private HttpEntity entity;
	private String result = "";
	private ProgressDialog progressDialog;
	private String ip;
	private String sql;
	
	public UpdateAsynTask(final Context context, UserDTO userDTO) {
		this.context = context;
	    ResourceBundle rb = ResourceBundle.getBundle("network");
		ip = rb.getString("ip");
		sql = "update `user_master` set name = '" + userDTO.getUserName() +"', contact_number = '" + userDTO.getPhoneno() 
				+ "',parent_contact_number = '" + userDTO.getUserparent() + "',address = '" + userDTO.getUserAddress() 
				+"', voter_id = '" + userDTO.getUserId() +"' where id = '" + userDTO.getId() + "';"; 
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				progressDialog.dismiss();
				this.cancel();
				((Activity)context).runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						if(null!= context && UpdateAsynTask.this.isCancelled())
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
				UpdateAsynTask.this.cancel(true);
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
			HttpPost httpPost = new HttpPost(ip+"/e_safety/register.php");
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
			UpdateAsynTask.this.cancel(true);
			Toast.makeText(context.getApplicationContext().getApplicationContext(),"Exception " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
		return null;
	}
	
	
	@Override
	protected void onPostExecute(Void v) {
		progressDialog.dismiss();
		Log.d("sql", sql);
		Log.d("log", result);
		
		try{
			if(result.equals("true")){
				AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
				builder1.setTitle("Success");
				builder1.setMessage("Congratulations ! \n Your details are successfully updated.");
				builder1.setCancelable(false);
				builder1.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent("com.teamAndappers.womensafety.ThanxActivity");
						((Activity)context).startActivity(intent);
						((Activity)context).finish();
					}
				});
				AlertDialog alert11 = builder1.create();
				alert11.show();
			}else{
				AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
				builder1.setTitle("Error");
				builder1.setMessage("Some Error Occured!!\n Please try again.");
				builder1.setCancelable(false);
				builder1.setPositiveButton("Okay", null);
				AlertDialog alert11 = builder1.create();
				alert11.show();
			}
		}catch(Exception e){
			AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
			builder1.setTitle("Error");
			builder1.setMessage("Some Error Occured!!\n Please try again.\n Error code : " + e.getMessage());
			builder1.setCancelable(false);
			builder1.setPositiveButton("Okay", null);
			AlertDialog alert11 = builder1.create();
			alert11.show();
		}
		
		super.onPostExecute(v);
	}
}
