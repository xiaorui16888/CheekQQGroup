package com.joinqqgroup.utils;
import android.app.AlertDialog;
import android.content.Context;

public class ShowDialogUtils
{
	public static void showDialog(Context context,String msg){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(msg);
		builder.create().show();
	}
}
