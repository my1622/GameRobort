package com.mysample.gamerobot.model;

import java.util.Random;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Log;

public class Cloud {
	private float x, y;
	private int width, height, velY, velX;
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

	public Cloud(float x, float y, int width, int height,int degree) {

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.setDegree(degree);
		
		velX =Robort.getSpeed();
		rect = new Rect();
		duckRect = new Rect();

	}

	public void update(float delta) {
		// Log.d("Tag.app","Cloud x:"+x+"      y:"+y);
		if (x >= -width) {
			x += velX * delta;
		} else {
			Random r = new Random();
			y = r.nextInt(100) + 30;
			
			velX = -r.nextInt(Math.abs(Robort.getSpeed()));

			x = 1800;
		}
		
	/*	Matrix matrix = new Matrix();
		matrix.setTranslate(100, 100);     //设置图片的旋转中心，即绕（X,Y）这点进行中心旋转
		matrix.preRotate(30, (float)gameImage.getWidth()/2, (float)gameImage.getHeight()/2);  //要旋转的角度
		*/
		
		setDegree(getDegree() + 0.5f);
		
		updateRects();
	}

	private void updateRects() {
		
		rect.set((int) x, (int) y, (int) x + width, (int) y + height);
		
		

	}

	public float getDegree() {
		return degree;
	}

	public void setDegree(float degree) {
		this.degree = degree;
	}

}
