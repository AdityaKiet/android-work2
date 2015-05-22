package com.teamAndappers.womensafety.util;

import com.teamAndappers.womensafety.dto.AlertDialogDTO;

import android.app.AlertDialog;
import android.content.DialogInterface;

public class AlertDialogActivity {
	IAlertLogic iAlertLogic;

	public void alertDialogDisplay(AlertDialogDTO alertDialogDTO,final IAlertLogic iAlertLogic) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(alertDialogDTO.getContext());
		builder.setMessage(alertDialogDTO.getMessage());
		builder.setTitle(alertDialogDTO.getTitle());
		if (null != alertDialogDTO.getPositiveButon()) {
			if (null == iAlertLogic)
				builder.setPositiveButton(alertDialogDTO.getPositiveButon(),null);
			else {
				builder.setPositiveButton(alertDialogDTO.getPositiveButon(),new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int which) {
								iAlertLogic.positiveButton();
							}
						});
			}
			
		}
		if (null != alertDialogDTO.getNegativeButton()) {
			if (null == iAlertLogic)
				builder.setNegativeButton(alertDialogDTO.getNegativeButton(),null);
			else {
				builder.setNegativeButton(alertDialogDTO.getNegativeButton(),new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int which) {
								iAlertLogic.negativeButton();
							}
						});
			}
		}
		builder.setCancelable(true);
		builder.show();
	}
}
