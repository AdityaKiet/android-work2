package com.teamAndAppers.womensafety;

import java.util.regex.Pattern;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.teamAndappers.womensafety.R;
import com.teamAndappers.womensafety.dto.UserDTO;

public class UpdateActivity extends Activity implements OnClickListener{
	private EditText name, address, parentContact, id, etPostalCode,etyourContact;
	private Button submit;
	private String userName, userAddress, userparent, userId, imei, email,postalCode,yourContact;
	private SharedPreferences SharedPreferences;
	private SharedPreferences.Editor sharedEditor;
	private UserDTO userDTO;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		populate();
	}
	
	private void populate() {
		name = (EditText) findViewById(R.id.nameeditText);
		address = (EditText) findViewById(R.id.addresseditText);
		parentContact = (EditText) findViewById(R.id.parentContacteditText);
		id = (EditText) findViewById(R.id.ideditText);
		etyourContact = (EditText)findViewById(R.id.yourContacteditText);
		etPostalCode = (EditText)findViewById(R.id.etPostalCode);
		submit = (Button) findViewById(R.id.Submitbutton);
		submit.setOnClickListener(this);
		SharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		sharedEditor = SharedPreferences.edit();
		
		Gson gson = new Gson();
		userDTO = gson.fromJson(SharedPreferences.getString("user", null), UserDTO.class);
		name.setText(userDTO.getUserName());
		etyourContact.setText(userDTO.getPhoneno());
		address.setText(userDTO.getUserAddress());
		parentContact.setText(userDTO.getUserparent());
		id.setText(userDTO.getUserId());
		etPostalCode.setText(userDTO.getPostalCode());
	}
	public void alert() {
		AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
		builder1.setTitle("Error Ocurred");
		builder1.setMessage("Invalid info...");
		builder1.setCancelable(true);
		builder1.setPositiveButton("Okay", null);
		AlertDialog alert11 = builder1.create();
		alert11.show();
	}

	public String getIMEI() {
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String device_id = tm.getDeviceId();
		Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
		Account[] accounts = AccountManager.get(UpdateActivity.this).getAccounts();
		for (Account account : accounts) {
		    if (emailPattern.matcher(account.name).matches()) {
		    	email = account.name;
		    }
		}
		return device_id;
	}
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.Submitbutton){
			userName = name.getText().toString();
			userAddress = address.getText().toString();
			userparent = parentContact.getText().toString();
			userId = id.getText().toString();
			yourContact = etyourContact.getText().toString();
			postalCode = etPostalCode.getText().toString();
			if (!userName.trim().equals("")&& !userAddress.trim().equals("")&& !userparent.trim().equals("")
					&& userparent.trim().length() == 10 && !userId.trim().equals("") && !postalCode.trim().equals("")) {
					imei = getIMEI();
					Gson gson = new Gson();
					userDTO.setImei(imei);
					userDTO.setUserAddress(userAddress);
					userDTO.setUserId(userId);
					userDTO.setPhoneno(yourContact);
					userDTO.setEmail(email);
					userDTO.setUserName(userName);
					userDTO.setUserparent(userparent);
					userDTO.setPostalCode(postalCode);
					String user = gson.toJson(userDTO);
					sharedEditor.putString("user", user);
					sharedEditor.commit();
					UpdateAsynTask registrationAsynTask = new UpdateAsynTask(UpdateActivity.this, userDTO);
					registrationAsynTask.execute();
			} else 
				alert();
		}
	}
}
