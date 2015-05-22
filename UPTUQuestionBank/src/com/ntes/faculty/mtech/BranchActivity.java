package com.ntes.faculty.mtech;

import com.ntes.uptuquestionbank.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BranchActivity extends Activity implements OnClickListener {
	private Button btnCSE, btnIT, btnECE, btnEN, btnEI, btnCivil, btnME;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.btech_branches);
		populate();
	}

	private void populate() {
		btnCSE = (Button) findViewById(R.id.btnCSE);
		btnIT = (Button) findViewById(R.id.btnIt);
		btnECE = (Button) findViewById(R.id.btnEce);
		btnEN = (Button) findViewById(R.id.btnEn);
		btnEI = (Button) findViewById(R.id.btnEi);
		btnCivil = (Button) findViewById(R.id.btnCivil);
		btnME = (Button) findViewById(R.id.btnMe);

		btnCSE.setOnClickListener(this);
		btnIT.setOnClickListener(this);
		btnECE.setOnClickListener(this);
		btnEN.setOnClickListener(this);
		btnEI.setOnClickListener(this);
		btnCivil.setOnClickListener(this);
		btnME.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
			Button btn = (Button)findViewById(v.getId());
			Intent intent = new Intent("com.ntes.faculty.mtech.NotesUploadActivity");
			Bundle bundle = new Bundle();
			bundle.putString("branch", btn.getText().toString());
			intent.putExtras(bundle);
			startActivity(intent);
	}

}
