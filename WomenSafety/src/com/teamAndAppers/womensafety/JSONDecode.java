package com.teamAndAppers.womensafety;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.teamAndappers.womensafety.dto.UserDTO;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class JSONDecode {

	private String result;
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor sharedEditor;
	
	public JSONDecode(Context context, String result) {
		this.result = result;
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(((Activity)context).getBaseContext());
		sharedEditor = sharedPreferences.edit();
	}

	public void decode() throws JSONException {
		JSONArray jsonArray = new JSONArray(result);
		JSONObject jsonObject = null;
		int id = 0;
		for(int i = 0 ; i< jsonArray.length(); i++){
			jsonObject = jsonArray.getJSONObject(i);
			id = jsonObject.getInt("id");
		}
		Gson gson = new Gson();
		UserDTO userDTO = gson.fromJson(sharedPreferences.getString("user", null), UserDTO.class);
		userDTO.setId(id);
		sharedEditor.putString("user", gson.toJson(userDTO));
		sharedEditor.commit();
	}
}
