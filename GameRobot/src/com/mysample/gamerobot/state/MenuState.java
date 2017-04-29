package com.mysample.gamerobot.state;

import android.graphics.Color;
import android.view.MotionEvent;

import com.mysample.gamerobot.Assets;
import com.mysample.gamerobot.MainActivity;
import com.mysample.gamerobot.model.Robort;
import com.mysample.gamerobot.util.Painter;

public class MenuState extends State {
	private int width, height;

	@Override
	public void init() {
		// TODO Auto-generated method stub
		

	}

	@Override
	public void init(int width, int height) {
		// TODO Auto-generated method stub
		this.width = width;
		this.height = height;

	}

	@Override
	public void updata(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Painter g) {
		renderBackgournd(g);

	}

	private void renderBackgournd(Painter g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, MainActivity.GAME_WIDTH, MainActivity.GAME_HEIGHT);

		g.drawImage(Assets.background, 0, 0, width, height);

	}

	@Override
	public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
		if (e.getAction() == MotionEvent.ACTION_UP) {
			Robort.setINIT_LIFE(3);
			setCurrentState(new HighScoreState(MainActivity.getHightScore()), width, height);
		}
		return true;
	}

}
