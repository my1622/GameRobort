package com.mysample.gamerobot.animation;

import com.mysample.gamerobot.MainActivity;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class myTextView extends TextView {

	public myTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public myTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public myTextView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		// TODO Auto-generated method stub

		AssetManager assMager = context.getAssets();
		Typeface font = Typeface.createFromAsset(assMager, "fonts/font.ttf");
		setTypeface(font);

	}

}
