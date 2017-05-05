package com.mysample.gamerobot.model;

import java.util.Random;

import android.util.Log;

import com.mysample.gamerobot.Assets;
import com.mysample.gamerobot.MainActivity;

public class Enemy extends BaseModel {
	private int speed;
	private int no;

	public Enemy(int x, int y, int width, int height, int speed) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		this.speed = speed;

	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		// super.update(delta);
		if (getX() < -getWidth()) {
			setX(1800);
			Random r = new Random();
			setY(r.nextInt(230) + 200);
			no=r.nextInt(Assets.enemys.size());
			// setY(r.nextInt(MainActivity.GAME_HEIGHT-50)+50);
			//speed = -(r.nextInt(10) + 5);
		} else {
			setX(getX() + speed * delta);

		}
//		Log.d("tag.app","ememys.x:"+getX()+"dmemy.y:"+getY());

		updateRects();
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

}
