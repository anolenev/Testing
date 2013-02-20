package com.example.testing;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RingtonItemAdapter extends ArrayAdapter<RingtonItem> {
	
	private ArrayList<RingtonItem>	rington;
	Activity			activity;

	public RingtonItemAdapter(Activity activity, int textViewResourceId, ArrayList<RingtonItem> rington) {
		super(activity, textViewResourceId, rington);
		this.activity = activity;
		this.rington = rington;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = vi.inflate(R.layout.rington_item_layout, null);
		final RingtonItem ringtonItem = rington.get(position);
		if (ringtonItem != null) {
			LinearLayout layout = (LinearLayout) v.findViewById(R.id.ringtonItemLayout);
			layout.setBackgroundColor((position & 1) == 0 ? Color.WHITE : Color.LTGRAY);
			TextView text = (TextView) v.findViewById(R.id.ringtonInItem);
			text.setText(ringtonItem.getText());
			OnClickListener listener = new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent resultIntent = new Intent();
					resultIntent.putExtra("text", ringtonItem.getText());
					// TODO Add extras or a data URI to this
					// intent as appropriate.
					activity.setResult(Activity.RESULT_OK, resultIntent);
					activity.finish();
				}
			};
			v.setOnClickListener(listener);
		}
		return v;
	}

}
