package com.example.testing;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AvatarItemAdapter extends ArrayAdapter<AvatarItem> {
	private ArrayList<AvatarItem>	avatar;
	Activity			activity;

	public AvatarItemAdapter(Activity activity, int textViewResourceId, ArrayList<AvatarItem> avatar) {
		super(activity, textViewResourceId, avatar);
		this.activity = activity;
		this.avatar = avatar;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = vi.inflate(R.layout.avatar_item_layout, null);
		final AvatarItem avatarItem = avatar.get(position);
		if (avatarItem != null) {
			LinearLayout layout = (LinearLayout) v.findViewById(R.id.avatarItemLayout);
			layout.setBackgroundResource((position & 1) == 0 ? R.drawable.background : R.drawable.background_xml);
			ImageView avatarImage = (ImageView) v.findViewById(R.id.avatarInItem);
			avatarImage.setImageDrawable(avatarItem.getImage());
			OnClickListener listener = new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent resultIntent = new Intent();
					Bitmap b = drawableToBitmap(avatarItem.getImage());
					ByteArrayOutputStream bs = new ByteArrayOutputStream();
					b.compress(Bitmap.CompressFormat.PNG, 50, bs);
					resultIntent.putExtra("byteArray", bs.toByteArray());
					// TODO Add extras or a data URI to this
					// intent as appropriate.
					activity.setResult(Activity.RESULT_OK, resultIntent);
					activity.finish();
					Log.e("finish", "finish");
				}
			};
			avatarImage.setOnClickListener(listener);
		}
		return v;
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}

		int width = drawable.getIntrinsicWidth();
		width = width > 0 ? width : 1;
		int height = drawable.getIntrinsicHeight();
		height = height > 0 ? height : 1;

		Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);

		return bitmap;
	}
}
