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
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.teamAndappers.womensafety.dto.UserDTO;

public class RegistrationAsynTask extends AsyncTask<String, String, Void>{
	private Context context;
	private InputStream is;
	private HttpEntity entity;
	private String result = "",result1 = "";
	private ProgressDialog progressDialog;
	private String ip;
	private String sql,sql1;
	
	public RegistrationAsynTask(final Context context, UserDTO userDTO) {
		this.context = context;
	    ResourceBundle rb = ResourceBundle.getBundle("network");
		ip = rb.getString("ip");
		sql = "insert into `user_master`(name,contact_number,parent_contact_number,address,voter_id,imei,email,postal_code) values ('" + userDTO.getUserName()
				+"','" + userDTO.getPhoneno() + "','" + userDTO.getUserparent() + "','" + userDTO.getUserAddress()
				+"','" + userDTO.getUserId() + "','" + userDTO.getImei() + "','" + userDTO.getEmail() +"','"+userDTO.getPostalCode()+"');"; 
		sql1 = "select id,email from `user_master` where `imei` = '" + userDTO.getImei() + "';";
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				progressDialog.dismiss();
				this.cancel();
				((Activity)context).runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						if(null!= context && RegistrationAsynTask.this.isCancelled())
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
				RegistrationAsynTask.this.cancel(true);
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
			
			list = new ArrayList<NameValuePair>(1);
			list.add(new BasicNameValuePair("sql", sql1));
			httpPost = new HttpPost(ip+"/e_safety/get_id.php");
			httpPost.setEntity(new UrlEncodedFormEntity(list));
			
			httpResponse = httpClient.execute(httpPost);
			entity = httpResponse.getEntity();
			is = entity.getContent();
			bufferedReader = new BufferedReader(new InputStreamReader(is));
			stringBuilder = new StringBuilder();
			line = "";
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
			is.close();
			result1 = stringBuilder.toString();
			
		} catch (Exception e) {
			RegistrationAsynTask.this.cancel(true);
			Toast.makeText(context.getApplicationContext().getApplicationContext(),"Exception " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
		return null;
	}
	
	
	@Override
	protected void onPostExecute(Void v) {
		progressDialog.dismiss();
		try{
			if(result.equals("true")){
				JSONDecode jsonDecode = new JSONDecode(context,result1);
				jsonDecode.decode();
				AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
				builder1.setTitle("Success");
				builder1.setMessage("Congratulations ! \n You are successfully registered.");
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
