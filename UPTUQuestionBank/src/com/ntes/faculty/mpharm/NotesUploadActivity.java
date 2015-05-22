package com.ntes.faculty.mpharm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ntes.dto.SessionDTO;
import com.ntes.uptuquestionbank.R;

public class NotesUploadActivity extends Activity implements OnClickListener{

	private String branch, notes;
	private EditText etTitle, etNotes,etBranch;
	private Button btnSubmit;
	private List<File> files;
	private int selectedItem = -1;
	private String[] fileNames;
	private String[] filePaths;
	private SharedPreferences shared;
	private SessionDTO sessionDTO;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_other_notes);
		shared = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		Gson gson = new Gson();
		sessionDTO  = gson.fromJson(shared.getString("faculty", null),SessionDTO.class);
		populate();
	}

	private void populate() {
		etNotes = (EditText)findViewById(R.id.etTxtFile);
		etTitle = (EditText)findViewById(R.id.etTitle);
		etBranch = (EditText)findViewById(R.id.etBranch);
		btnSubmit = (Button)findViewById(R.id.btnPostNotes);
		etNotes.setOnClickListener(this);
		btnSubmit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.etTxtFile){
			files = new ArrayList<File>();
			scandir(Environment.getExternalStorageDirectory());
			fileNames = new String[files.size()];
			filePaths = new String[files.size()];
			
			for(int i = 0; i < files.size() ; i++){
				fileNames[i] = files.get(i).getName();
				filePaths[i] = files.get(i).getAbsolutePath();
			}
			AlertDialog.Builder builder = new AlertDialog.Builder(NotesUploadActivity.this);
			builder.setSingleChoiceItems(fileNames, -1, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						selectedItem = which;
					}
			});
			builder.setPositiveButton("Okay ", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					if(selectedItem == -1)
						Toast.makeText(getApplicationContext(), "Select any file", Toast.LENGTH_LONG).show();
					else{
						etNotes.setText(fileNames[selectedItem]);
					}
				}
			});
			builder.setNegativeButton("Cancel", null);
			builder.setTitle("Select a File");
			builder.show();
		}else if (v.getId() == R.id.btnPostNotes){
			if(etTitle.getText().toString().trim().equals("") || etNotes.getText().toString().equals(""))
				Toast.makeText(getApplicationContext(), "Fields can't be blank", Toast.LENGTH_LONG).show();
			else{
				try {
					File file = new File(filePaths[selectedItem]);
					FileInputStream stream = new FileInputStream(file);
					BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
					String row = "";
					String buffer = "";
					while ((row = reader.readLine()) != null) { 
						buffer += row;
						buffer += "\n";
						}
					notes = buffer;
					reader.close();
					
				} catch (Exception e) {
					Toast.makeText(getBaseContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
				}
				branch = etBranch.getText().toString();
				new NotesUploadAsncTask().execute();
			}
		}
	}

	private void scandir(File dir) {
	    String txtPattern = ".txt";

	    File listFile[] = dir.listFiles();

	    if (listFile != null) {
	        for (int i = 0; i < listFile.length; i++) {

	            if (listFile[i].isDirectory()) {
	                scandir(listFile[i]);
	            } else {
	              if (listFile[i].getName().endsWith(txtPattern)){
	                                files.add(listFile[i]);

	              }
	            }
	        }
	    }
	}

private class NotesUploadAsncTask extends AsyncTask<String, String, Void>{
	private InputStream is;
	private HttpEntity entity;
	private String result = "";
	private ProgressDialog progressDialog;
	private String ip,sql;
	
	public NotesUploadAsncTask() {
	    ResourceBundle rb = ResourceBundle.getBundle("network");
		ip = rb.getString("ip");
		sql = "insert into notes (course,branch,title,faculty,notes) values ('M.Pharm','" + 
		branch + "','" + etTitle.getText().toString() + "','" + sessionDTO.getName() + "','" + notes +"');";
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				progressDialog.dismiss();
				this.cancel();
				(NotesUploadActivity.this).runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						if(null != NotesUploadActivity.this && NotesUploadAsncTask.this.isCancelled())
							Toast.makeText(getApplicationContext(),"Connection failed..", Toast.LENGTH_LONG).show();
					}
				});
			}
		};
		
		Timer timer = new Timer();
		timer.schedule(task, 15000);
	}
	@Override
	protected void onPreExecute() {
		progressDialog = new ProgressDialog(NotesUploadActivity.this);
		progressDialog.setTitle("Loading.....");
		progressDialog.setMessage("Please Wait.....");
		progressDialog.show();
		progressDialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface arg0) {
				NotesUploadAsncTask.this.cancel(true);
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
			NotesUploadAsncTask.this.cancel(true);
			Toast.makeText(getApplicationContext(),"Exception " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
		return null;
	}
	@Override
	protected void onPostExecute(Void v) {
		progressDialog.dismiss();
				try{
					if(result.equals("true"))
						Toast.makeText(getApplicationContext(),"Notes uploaded sucessfully", Toast.LENGTH_LONG).show();
					else
						Toast.makeText(getApplicationContext(),"Notes not uploaded sucessfully", Toast.LENGTH_LONG).show();
				}
				catch(Exception e){
					Toast.makeText(getApplicationContext(),"Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
				}
		super.onPostExecute(v);
	}
}
}