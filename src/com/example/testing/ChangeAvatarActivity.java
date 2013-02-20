package com.example.testing;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ListView;

public class ChangeAvatarActivity extends Activity {

	private ListView		avatars;
	private ArrayList<AvatarItem>	avatarList	= new ArrayList<AvatarItem>();
	ProgressDialog	mDialog;
	Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.activity = this;
		setContentView(R.layout.change_avatar_layout);
		avatars = (ListView) findViewById(R.id.avatarsListView);
		showPopup(this);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				avatarList = getAvatarList();
				avatars.post(new Runnable() {
					
					@Override
					public void run() {
						avatars.setAdapter(new AvatarItemAdapter(activity, R.layout.avatar_item_layout, avatarList));
						mDialog.dismiss();
					}
				});				
			}
		}).start();
	}

	public ArrayList<AvatarItem> getAvatarList() {
		ArrayList<AvatarItem>	avatars	= new ArrayList<AvatarItem>();
		for (int i = 1; i <= 13; i++) {
			if (i < 10)
				avatars.add(new AvatarItem("http://onoapps.com/Dev/avatars/a0" + i +  ".png", i, getApplicationContext()));
			else 
				avatars.add(new AvatarItem("http://onoapps.com/Dev/avatars/a" + i +  ".png", i, getApplicationContext()));
		}
		return avatars;
	}
	
	private void showPopup(final Activity context) {
		mDialog = new ProgressDialog(context);
		mDialog.setMessage("Loading...");
		mDialog.setCancelable(false);
		mDialog.show();
	}

}
