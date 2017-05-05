package com.mysample.gamerobot.model;

import java.util.Random;

public class Glass extends BaseModel {
	private int width;

	public Glass(float x, float y, int width, int height) {
		setX(x);
		setY(y);
		this.width=width;
		setWidth(width);
		setHeight(height);

	}

	public void update(float delta, int speed) {
		if (getX() <= -getWidth()) {
			setX(width-10);
			

		} else {

			setX(getX() + delta * speed);
		}

		super.update(delta);
	}

}
