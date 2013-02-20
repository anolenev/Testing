package com.example.testing;

import java.io.InputStream;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class AvatarItem {
	
	Drawable image;
	Integer number;
	
	public AvatarItem (String url, Integer number, Context context) {
		Log.e("url", "" + url);
		this.image = LoadImageFromWebOperations(url, context);
		this.number = number;
	}
	
	public Drawable getImage() {
		return this.image;
	}
	
	public void setImage(Drawable image) {
		this.image = image;
	}
	
	public Integer getNumber() {
		return this.number;
	}
	
	public void setNumbetr(Integer numaber) {
		this.number = number;
	}
	
	public static Drawable LoadImageFromWebOperations(String url, Context context) {
		try {
			InputStream is = (InputStream) new URL(url).getContent();
			Drawable drawable = Drawable.createFromStream(is, "src name");
			is.close();
			Log.e("asd" ,"" + drawable.toString());
			return drawable;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
