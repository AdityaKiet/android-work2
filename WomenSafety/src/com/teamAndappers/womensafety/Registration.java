package com.teamAndappers.womensafety;

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
import com.teamAndAppers.womensafety.RegistrationAsynTask;
import com.teamAndappers.womensafety.dto.UserDTO;

public class Registration extends Activity implements OnClickListener{
	private EditText name, address, parentContact, id, etPostalCode, etyourContact;
	private Button submit;
	private String userName, userAddress, userparent, userId, imei, email, postalCode, yourContact;
	private SharedPreferences SharedPreferences;
	private SharedPreferences.Editor sharedEditor;

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
		submit = (Button) findViewById(R.id.Submitbutton);
		etyourContact = (EditText)findViewById(R.id.yourContacteditText);
		etPostalCode = (EditText)findViewById(R.id.etPostalCode);
		submit.setOnClickListener(this);
		SharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		sharedEditor = SharedPreferences.edit();
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
		Pattern emailPattern = Patterns.EMAIL_ADDRESS;
		Account[] accounts = AccountManager.get(Registration.this).getAccounts();
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
			yourContact = etyourContact.getText().toString();
			userId = id.getText().toString();
			postalCode = etPostalCode.getText().toString();
			if (!userName.trim().equals("")&& !userAddress.trim().equals("")&& !userparent.trim().equals("")
					&& userparent.trim().length() == 10 && !userId.trim().equals("") && !postalCode.trim().equals("") && !yourContact.trim().equals("")
					&& yourContact.trim().length() == 10) {
					imei = getIMEI();
					UserDTO dto = new UserDTO();
					dto.setImei(imei);
					dto.setUserAddress(userAddress);
					dto.setUserId(userId);
					dto.setPhoneno(yourContact);
					dto.setEmail(email);
					dto.setUserName(userName);
					dto.setUserparent(userparent);
					dto.setPostalCode(postalCode);
					Gson gson = new Gson();
					String user = gson.toJson(dto);
					sharedEditor.putString("user", user);
					sharedEditor.commit();
					RegistrationAsynTask registrationAsynTask = new RegistrationAsynTask(Registration.this, dto);
					registrationAsynTask.execute();
			} else 
				alert();
		}
	}

}
