package com.ntes.student.mtech;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ntes.GlobalData;
import com.ntes.dto.NotesDTO;
import com.ntes.uptuquestionbank.R;

public class ListAdapter extends BaseAdapter{
	private Context context;
	private List<NotesDTO> list;
	public ListAdapter(Context context) {
		this.context = context;
		list = GlobalData.notes;
	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) {

			LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			View rowView = null;
			rowView = mInflater.inflate(R.layout.all_test_single_row, null);
			TextView txtTitle = (TextView) rowView.findViewById(R.id.txtNotesTitle);
			txtTitle.setText(list.get(position).getTitle());
			TextView txtSocre = (TextView)rowView.findViewById(R.id.txtFaculty);
			txtSocre.setText(list.get(position).getFaculty());
		return rowView;
	}
}