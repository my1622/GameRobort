package com.mysample.gamerobot.model;

import java.util.Random;

import android.graphics.Rect;

public class Block {
	private float x, y;
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getVelX() {
		return velX;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	private int width, height, velY, velX;

	private Rect rect, duckRect;

	public Block(float x, float y, int width, int height) {

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		velX = Robort.getSpeed();

		rect = new Rect();
		duckRect = new Rect();

	}

	public void update(float delta,float speed) {
		// Log.d("Tag.app","Cloud x:"+x+"      y:"+y);
		if (x >= -width) {
			x += speed * delta;
		} else {
			Random r = new Random();
			//y = r.nextInt(160) + 270;

			x = width-30;
		}
		updateRects();
	}

	private void updateRects() {
		rect.set((int) x, (int) y, (int) x + width, (int) y + height);

	}

}
