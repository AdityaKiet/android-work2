package com.ntes.student.register;

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
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ntes.dto.RegisterDTO;
import com.ntes.dto.SessionDTO;
import com.ntes.security.Encryptor;

public class RegisterGetIDAsynTask extends AsyncTask<String, String, Void>{
	private RegisterDTO registerDTO;
	private Context context;
	private InputStream is;
	private HttpEntity entity;
	private String result = "";
	private SessionDTO sessionDTO;
	private ProgressDialog progressDialog;
	private String ip;
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor sharedEditor;
	
	public RegisterGetIDAsynTask(RegisterDTO registerDTO, final Context context) {
		this.registerDTO = registerDTO;
		this.context = context;
	    ResourceBundle rb = ResourceBundle.getBundle("network");
		ip = rb.getString("ip");
		
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(((Activity)this.context).getBaseContext());
		sharedEditor = sharedPreferences.edit();
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				progressDialog.dismiss();
				this.cancel();
				((Activity)context).runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						if(null != context && RegisterGetIDAsynTask.this.isCancelled())
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
				RegisterGetIDAsynTask.this.cancel(true);
			}
		});
		super.onPreExecute();
	}
	
	@Override
	protected Void doInBackground(String... params) {
		List<NameValuePair> list = new ArrayList<NameValuePair>(1);
		list.add(new BasicNameValuePair("email", registerDTO.getEmail()));
		list.add(new BasicNameValuePair("password", Encryptor.encryptSHA512(registerDTO.getPassword())));
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(ip+"/ntes/register_get_id.php");
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
			RegisterGetIDAsynTask.this.cancel(true);
			Toast.makeText(context.getApplicationContext().getApplicationContext(),"Exception " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
		return null;
	}
	@Override
	protected void onPostExecute(Void v) {
		progressDialog.dismiss();
				try{
					sessionDTO = new SessionDTO();
					JSONArray jsonArray = new JSONArray(result);
					JSONObject jsonObject = jsonArray.getJSONObject(0);
					sessionDTO.setName(registerDTO.getName());
					sessionDTO.setEmail(registerDTO.getEmail());
					sessionDTO.setId(jsonObject.getInt("id"));
					Gson gson = new Gson();
					String json = gson.toJson(sessionDTO);
				    sharedEditor.putString("student", json);
				    sharedEditor.putBoolean("isStudentLogin", true);
				    sharedEditor.commit();
				    Intent intent = new Intent("com.ntes.student.MainActivity");
					context.startActivity(intent);
					((Activity)context).finish();
				}
				catch(Exception e){
					Toast.makeText(context.getApplicationContext(),"Please login again.. " + e.getMessage(), Toast.LENGTH_LONG).show();
				}
		super.onPostExecute(v);
	}
}
