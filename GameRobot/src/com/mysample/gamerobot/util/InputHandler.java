package com.mysample.gamerobot.util;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.mysample.gamerobot.MainActivity;
import com.mysample.gamerobot.state.State;

public class InputHandler implements OnTouchListener {

	private State currentState;

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	public boolean onTouch(View v, MotionEvent event) {
//		int scaledX = (int) ((event.getX() / v.getWidth()) * MainActivity.GAME_WIDTH);
//		int scaledY = (int) ((event.getY() / v.getHeight()) * MainActivity.GAME_HEIGHT);
		int scaledX = (int) event.getX();
		int scaledY = (int) event.getY();
		return currentState.onTouch(event, scaledX, scaledY);
	}

}
