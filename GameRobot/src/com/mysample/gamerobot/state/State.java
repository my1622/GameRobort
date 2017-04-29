package com.mysample.gamerobot.state;

import android.view.MotionEvent;

import com.mysample.gamerobot.MainActivity;
import com.mysample.gamerobot.util.Painter;

public abstract class State {

	public void setCurrentState(State newState) {
		MainActivity.gameView.setCurrentState(newState);
	}

	public void setCurrentState(State newState, int width, int heigth) {
		MainActivity.gameView.setCurrentState(newState, width, heigth);
	}

	public abstract void init();

	public abstract void init(int width, int height);

	public abstract void updata(float delta);

	public abstract void render(Painter g);

	public abstract boolean onTouch(MotionEvent e, int scaledX, int scaledY);

}