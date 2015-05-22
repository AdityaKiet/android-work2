package com.o9pathshala.student.slidingmenu.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.o9paathshala.R;
import com.o9pathshala.global.GlobalData;
import com.o9pathshala.profile.dto.ProfileDTO;
import com.o9pathshala.profile.dto.SessionDTO;
import com.o9pathshala.student.test.dto.TestDTO;
import com.o9pathshala.student.test.tabs.AllTestsAdapter;
import com.o9pathshala.test.leaderboard.GetAllTestAsynTask;
import com.o9pathshala.test.database.SqlConstants;

public class FragmentLeaderboard extends Fragment implements SqlConstants{
	private SharedPreferences sharedPreferences;
	private SessionDTO sessionDTO;
	private ProfileDTO profileDTO;
	private ListView listView;
	private RelativeLayout relativeLayout;
	private EditText editText;
	private List<TestDTO> tests;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
		relativeLayout = (RelativeLayout) inflater.inflate(R.layout.leaderboard_list,container, false);
		populate();
        return relativeLayout;
    }
	private void populate() {
		
		editText = (EditText)relativeLayout.findViewById(R.id.etLeaderBoardInputSearch);
		listView = (ListView)relativeLayout.findViewById(R.id.listLeaderBoardTests);
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
		Gson gson = new Gson();
		String json = sharedPreferences.getString("profileDTO", null);
	    profileDTO = gson.fromJson(json, ProfileDTO.class);
	    json = sharedPreferences.getString("session", null);
	    sessionDTO = gson.fromJson(json, SessionDTO.class);
		String query = GET_STUDENT_TEST;
		query = query.replaceAll("INSTITUTE_ID", String.valueOf(sessionDTO.getCurrentInstitutesId()));
		query = query.replaceAll("ACTIVATION", "1");
		query = query.replaceAll("BATCH_ID", String.valueOf(profileDTO.getBatch_id()));
		
		GetAllTestAsynTask getAllTestListAsynTask = new GetAllTestAsynTask(getActivity(),query, relativeLayout);
		getAllTestListAsynTask.execute();
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(null == tests){
				Intent intent = new Intent("com.o9pathshala.test.leaderboard.LeaderBoardRankDisplayActivity");
				intent.putExtra("test_id", GlobalData.allTests.get(position).getId());
				intent.putExtra("test_name", GlobalData.allTests.get(position).getTestName());
				startActivity(intent);
			}else{
				Intent intent = new Intent("com.o9pathshala.test.leaderboard.LeaderBoardRankDisplayActivity");
				intent.putExtra("test_id", tests.get(position).getId());
				intent.putExtra("test_name", tests.get(position).getTestName());
				startActivity(intent);
			}
			}
		});
		
		
		editText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String text = editText.getText().toString().toLowerCase(Locale.getDefault());
				if(text.trim().length() == 0){
					AllTestsAdapter allTestsAdapter = new AllTestsAdapter(getActivity(), GlobalData.allTests);
					listView.setAdapter(allTestsAdapter);
					tests = null;
				}
				
				else{
					tests = new ArrayList<TestDTO>();
					for(TestDTO testDTO : GlobalData.allTests){
						if(testDTO.getTestName().toLowerCase(Locale.getDefault()).contains(text))
							tests.add(testDTO);
					}
				AllTestsAdapter allTestsAdapter = new AllTestsAdapter(getActivity(), tests);
				listView.setAdapter(allTestsAdapter);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		}
		
}
