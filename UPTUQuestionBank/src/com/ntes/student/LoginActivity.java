package com.ntes.student;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.ntes.security.Encryptor;
import com.ntes.student.login.LoginAsynTask;
import com.ntes.student.login.LoginDTO;
import com.ntes.uptuquestionbank.R;

public class LoginActivity extends Activity implements OnClickListener{
	private EditText etEmailId, etPassword;
	private Button btnLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.student_login);
		btnLogin = (Button) findViewById(R.id.btnGuestLogin);
		etEmailId = (EditText) findViewById(R.id.etGuestID);
		etPassword = (EditText) findViewById(R.id.etGuestPassword);
		btnLogin.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnGuestLogin:
			final LoginDTO loginDTO = new LoginDTO();
			loginDTO.setId(etEmailId.getText().toString());
			loginDTO.setPassword(Encryptor.encryptSHA512(etPassword.getText().toString()));
			LoginAsynTask loginAsynTask = new LoginAsynTask(LoginActivity.this, loginDTO);
			loginAsynTask.execute();
			break;
		
		default:
			break;
		}
	}

}
