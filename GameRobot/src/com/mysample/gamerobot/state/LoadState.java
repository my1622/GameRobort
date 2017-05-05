package com.mysample.gamerobot.state;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

import com.mysample.gamerobot.Assets;
import com.mysample.gamerobot.MainActivity;
import com.mysample.gamerobot.R;
import com.mysample.gamerobot.util.Painter;

public class LoadState extends State {
	private int width, height;
	
	private int alpha = 1;
	private int bata = 255;

	@Override
	public void init(int width, int heigth) {
		this.width = MainActivity.GAME_WIDTH;
		this.height = MainActivity.GAME_HEIGHT;
		Assets.load();

	}

	@Override
	public void updata(float delta) {
		// TODO Auto-generated method stub
		bata -= 15;
		alpha = 255 - Math.abs(bata);
//		Log.d("Tag.app", "alpha" + alpha);

		if (Assets.isReady && alpha <= 0) {
			setCurrentState(new MenuState(), MainActivity.GAME_WIDTH, MainActivity.GAME_HEIGHT);
		}

	}

	@Override
	public void render(Painter g) {

		rederBackgrounds(g);
	}

	private void rederBackgrounds(Painter g) {

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, MainActivity.GAME_WIDTH, MainActivity.GAME_HEIGHT);
		g.setColor(0);
		g.setAlpha(alpha);

		
		if (alpha!=0) {
			g.setFont(Assets.font, 40);
			g.drawString(MainActivity.resources.getString(R.string.strWarnng1),
					height / 2 - 130, 150);
			g.drawString(MainActivity.resources.getString(R.string.strWarnng2),
					height / 2 - 130, 200);
			g.drawString(MainActivity.resources.getString(R.string.strWarnng3),
					height / 2 -130, 250);
			g.drawString(MainActivity.resources.getString(R.string.strWarnng4),
					height / 2 - 130, 300);
		}

	}

	@Override
	public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {

		return false;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
