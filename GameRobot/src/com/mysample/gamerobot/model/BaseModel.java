package com.mysample.gamerobot.model;

import android.graphics.Rect;

public class BaseModel {
	private float x, y;
	private int width, height, velY, velX;

	public int getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}

	public int getVelX() {
		return velX;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public Rect getRect() {
		return rect;
	}

	public void setRect(Rect rect) {
		this.rect = rect;
	}

	public Rect getDuckRect() {
		return duckRect;
	}

	public void setDuckRect(Rect duckRect) {
		this.duckRect = duckRect;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}
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
	
	public void update(float delta) {
		updateRects();
	}
	private void updateRects() {
		rect.set((int) x, (int) y, (int) x + width, (int) y + height);

	}

}
