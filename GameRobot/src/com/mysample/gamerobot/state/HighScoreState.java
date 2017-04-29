package com.mysample.gamerobot.state;

import android.graphics.Color;
import android.view.MotionEvent;

import com.mysample.gamerobot.Assets;
import com.mysample.gamerobot.MainActivity;
import com.mysample.gamerobot.model.Robort;
import com.mysample.gamerobot.util.Painter;

public class HighScoreState extends State {

	private int count;
	private int alpha = 1;
	private int bata = 255;
	private int hightScore;
	public HighScoreState(int hightScroe) {
		this.hightScore=hightScroe;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updata(float delta) {
		// TODO Auto-generated method stub
		count += 3;
		bata -= 15;
		alpha = 255 - Math.abs(bata);
		if (Assets.isReady && (count > 255) && (Robort.getINIT_LIFE() != 0)) {
			setCurrentState(new PlayState(), MainActivity.GAME_WIDTH,
					MainActivity.GAME_HEIGHT);
		}

	}

	@Override
	public void render(Painter g) {
		// TODO Auto-generated method stub
		renderBackground(g);

	}

	private void renderBackground(Painter g) {
		// TODO Auto-generated method stub
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, MainActivity.GAME_WIDTH, MainActivity.GAME_HEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(Assets.font, 27);
		
		if (hightScore>MainActivity.getHightScore()){
			MainActivity.setHightScore(hightScore);
		}

		g.drawString("Welcome to MyRobortWorld!       HighScore: "+MainActivity.getHightScore(), 50, 50);
		g.drawImage(Assets.getBitmapRobort()[0],
				MainActivity.GAME_WIDTH / 2 - 100,
				MainActivity.GAME_HEIGHT / 2 - 80);
		g.drawString("x " + Robort.getINIT_LIFE(),
				MainActivity.GAME_WIDTH / 2 - 10,
				MainActivity.GAME_HEIGHT / 2 - 20);

		g.setFont(Assets.font, 70);
		g.setAlpha(alpha);
		if (Robort.getINIT_LIFE() == 0) {
			g.drawString("Game Over", MainActivity.GAME_WIDTH - 450,
					MainActivity.GAME_HEIGHT - 70);

		} else {
			g.drawString("Let's Play! ", MainActivity.GAME_WIDTH - 450,
					MainActivity.GAME_HEIGHT - 70);
		}

	}

	@Override
	public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {

		if (e.getAction() == MotionEvent.ACTION_UP) {
			if (Robort.getINIT_LIFE() == 0) {
				setCurrentState(new MenuState(), MainActivity.GAME_WIDTH,
						MainActivity.GAME_HEIGHT);

			}
		}

		return true;
	}

}
