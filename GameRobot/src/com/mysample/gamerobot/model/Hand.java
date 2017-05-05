package com.mysample.gamerobot.model;

import android.graphics.Rect;

public class Hand {
	private float x, y;
	private int width, height, velY, velX, alpha;
	private int count = 0;

	public int getAlpha() {
		return alpha;
	}

	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}

	private float degree;

	private Rect rect, duckRect;

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Hand(float x, float y, int width, int height, int alpha) {

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.setAlpha(alpha);

		velX = Robort.getSpeed();
		rect = new Rect();
		duckRect = new Rect();
	}

	public void update(float delta) {
		count += 10;
		if (count >= 200) {
			count = 0;
		}
		if (x < 650) {
			x = 600 + count / 4;
			y = 300 - Math.abs(count);
		} else {

			x = 600;
			y = 300;

		}
		if (count < 51) {
			alpha = (int) (count * 5);
		}

		updateRects();
	}

	private void updateRects() {

		rect.set((int) x, (int) y, (int) x + width, (int) y + height);

	}

}
