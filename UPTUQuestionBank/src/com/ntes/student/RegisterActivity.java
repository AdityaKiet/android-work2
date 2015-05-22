package com.ntes.student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ntes.dto.RegisterDTO;
import com.ntes.security.Validation;
import com.ntes.student.register.RegisterAsynTask;
import com.ntes.uptuquestionbank.R;

public class RegisterActivity extends Activity implements OnClickListener{

	private EditText etName, etEmail, etPassword, etConfirmPassword;
	private Button btnRegister,btnLogin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_activity);
		populate();
	}
	private void populate() {
		etName = (EditText)findViewById(R.id.etRegisterName);
		etPassword = (EditText)findViewById(R.id.etRegisterPassword);
		etEmail = (EditText)findViewById(R.id.etRegisterEmail);
		etConfirmPassword  = (EditText)findViewById(R.id.etRegisterConfirmPassword);
		btnRegister = (Button)findViewById(R.id.btnRegister);
		btnLogin = (Button)findViewById(R.id.btnLoginPage);
		btnLogin.setOnClickListener(this);
		btnRegister.setOnClickListener(this);
	}
	public void onClick(View v) {
		if(v.getId() == R.id.btnRegister){
			Validation validation = new Validation();
			if(etConfirmPassword.getText().toString().trim().equals("")||etEmail.getText().toString().trim().equals("")||etName.getText().toString().trim().equals("")||etPassword.getText().toString().trim().equals(""))
				Toast.makeText(getApplicationContext().getApplicationContext(),"Fields can't be blank", Toast.LENGTH_LONG).show();
			else if(!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())){
				Toast.makeText(getApplicationContext().getApplicationContext(),"Password not matched", Toast.LENGTH_LONG).show();
			}else if(!validation.validateEmail((etEmail.getText().toString()))){
				Toast.makeText(getApplicationContext().getApplicationContext(),"E-mail not valid", Toast.LENGTH_LONG).show();
			}else if(!validation.validatePassword(etPassword.getText().toString())){
				Toast.makeText(getApplicationContext().getApplicationContext(),"Password not valid", Toast.LENGTH_LONG).show();
			}else{
				RegisterDTO registerDTO = new RegisterDTO();
				registerDTO.setName(etName.getText().toString());
				registerDTO.setEmail(etEmail.getText().toString());
				registerDTO.setPassword(etPassword.getText().toString());
				RegisterAsynTask registerAsynTask = new RegisterAsynTask(registerDTO, RegisterActivity.this);
				registerAsynTask.execute();
			}
		}
		else if (v.getId() == R.id.btnLoginPage){
			Intent intent = new Intent("com.ntes.student.LoginActivity");
			startActivity(intent);
			RegisterActivity.this.finish();
		}
		}
}
