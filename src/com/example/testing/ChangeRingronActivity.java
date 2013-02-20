package com.example.testing;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class ChangeRingronActivity extends Activity {
	Activity			activity;
	private ListView		ringtons;
	ProgressDialog			mDialog;
	private ArrayList<RingtonItem>	ringtonList	= new ArrayList<RingtonItem>();
	JSONArray ringtonsArray = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.activity = this;
		setContentView(R.layout.change_rington_layout);
		ringtons = (ListView) findViewById(R.id.ringronsListView);
		showPopup(this);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				ringtonList = getRingtonList();	
				ringtons.post(new Runnable() {
					
					@Override
					public void run() {
						ringtons.setAdapter(new RingtonItemAdapter(activity, R.layout.rington_item_layout, ringtonList));
						mDialog.dismiss();						
					}
				});
			}
		}).start();
	}
	
	private static final String TAG_NAME = "ringName";
	private static final String TAG_RINGTONES = "ringtones";
	private static final String TAG_RINGDEC = "ringDec";

	public ArrayList<RingtonItem> getRingtonList() {
		// Creating JSON Parser instance
		JSONParser jParser = new JSONParser();

		// getting JSON string from URL
		JSONObject json = jParser.getJSONFromUrl("http://onoapps.com/Dev/ringtones.txt");
		ArrayList<RingtonItem> ringtons = new ArrayList<RingtonItem>();

		try {
			// Getting Array of Contacts
			ringtonsArray = json.getJSONArray(TAG_RINGTONES);

			// looping through All Contacts
			for (int i = 0; i < ringtonsArray.length(); i++) {
				JSONObject c = ringtonsArray.getJSONObject(i);

				// Storing each json item in variable
				String name = c.getString(TAG_NAME);
				String ringDec = c.getString(TAG_RINGDEC);
				ringtons.add(new RingtonItem(name + " - " + ringDec));
				Log.e("text" ,name + " - " + ringDec);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ringtons;
	}

	private void showPopup(final Activity context) {
		mDialog = new ProgressDialog(context);
		mDialog.setMessage("Loading...");
		mDialog.setCancelable(false);
		mDialog.show();
	}

}
