package com.example.testing;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	ImageView	avatar;
	TextView	rington;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setAvatarClickListener();
		setShareClickListener();
		setRingtonClickListener();
	}

	private void setAvatarClickListener() {
		avatar = (ImageView) findViewById(R.id.avatar);
		avatar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplication(), ChangeAvatarActivity.class);
				startActivityForResult(i, 0);
			}
		});
	}

	private void setRingtonClickListener() {
		rington = (TextView) findViewById(R.id.ringron);
		rington.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplication(), ChangeRingronActivity.class);
				startActivityForResult(i, 1);
			}
		});
	}

	private void setShareClickListener() {
		Button share = (Button) findViewById(R.id.share);
		final EditText firstName = (EditText) findViewById(R.id.firstName);
		final EditText lastName = (EditText) findViewById(R.id.lastName);
		final EditText phoneNumber = (EditText) findViewById(R.id.phoneNumber);
		share.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("message/rfc822");
				i.putExtra(Intent.EXTRA_EMAIL, new String[] { "welcome@onoapps.com" });
				i.putExtra(Intent.EXTRA_SUBJECT, "Share contact");
				String emailText = "Name: " + firstName.getText() + " " + lastName.getText() + "\n";
				emailText += "Phone: " + phoneNumber.getText();
				i.putExtra(Intent.EXTRA_TEXT, emailText);
				avatar.buildDrawingCache();
				Bitmap bmap = avatar.getDrawingCache();
				String path = Images.Media.insertImage(getContentResolver(), bmap, "title", null);
				Uri imageUri = Uri.parse(path);
				i.putExtra(Intent.EXTRA_STREAM, imageUri);
				i.setType("image/png");
				try {
					startActivity(Intent.createChooser(i, "Send mail..."));
				} catch (android.content.ActivityNotFoundException ex) {
					Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
				}

			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, final Intent data) {
		if (resultCode == RESULT_OK && requestCode == 0) {
			Log.e("here", "here");
			Bitmap b = BitmapFactory.decodeByteArray(data.getByteArrayExtra("byteArray"), 0, data.getByteArrayExtra("byteArray").length);
			avatar.setImageBitmap(b);
		} else if (resultCode == RESULT_OK && requestCode == 1) {
			rington.setText(data.getCharSequenceExtra("text"));
		}
	}

}