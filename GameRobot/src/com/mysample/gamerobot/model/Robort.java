package com.mysample.gamerobot.model;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Rect;
import android.util.Log;

import com.mysample.gamerobot.Assets;

public class Robort {

	private float x, y;
	private int width, height, velY;
	private Rect rect, duckRect, block;
	private boolean isAlive, isDucked;
	private float duckDuration;
	private static int INIT_LIFE=3,JUMP_VELOCITY = -800;

	public static int getINIT_LIFE() {
		return INIT_LIFE;
	}

	public static void setINIT_LIFE(int iNIT_LIFE) {
		INIT_LIFE = iNIT_LIFE;
	}

	public static void setJUMP_VELOCITY(int jUMP_VELOCITY) {
		JUMP_VELOCITY = jUMP_VELOCITY;
	}

	private static final int ACCEL_GRAVITY = 1800;
	private static int Speed = -150;

	public static int getSpeed() {
		return Speed;
	}

	public static void setSpeed(int speed) {
		Speed = speed;
	}

	public Robort(float x, float y, int width, int height) {

		this.x = x;
		this.y = y;
		
		this.width = width;
		this.height = height;
		block = new Rect();

		rect = new Rect();
		duckRect = new Rect();
		isAlive = true;
		isDucked = false;

	}

	public void updata(float delta, int display_height, ArrayList<Block> blocks) {
		if (y > display_height) {
			isAlive = false;
		}
		if (velY < Math.abs(JUMP_VELOCITY)) {
			velY += ACCEL_GRAVITY * delta;
		}
		for (int i = 0; i < blocks.size(); i++) {
			this.block.set((int) blocks.get(i).getX() + 10, (int) blocks.get(i)
					.getY() - 20, (int) blocks.get(i).getX()
					+ blocks.get(i).getWidth(), (int) blocks.get(i).getY()
					+ blocks.get(i).getHeight());
			Log.d("tag", "y=" + y + "      height=" + height);

			Log.d("TAG", "blocks.get(i).getY()" + blocks.get(i).getY() + "y:="
					+ y + "block.top" + block.top);
			if (isOnthisBlock()
					&& Math.abs(y + height - block.top) <= velY * delta + 25
					&& velY > 0) {
				velY = 0;
				// y = 406 - height;
				y = blocks.get(i).getY() - height + 1;
				Log.d("TAG", "blocks.get(i).getY()" + blocks.get(i).getY()
						+ "y:=" + y);
				break;

			}
		}
		if(isOnthisBlock()&& velY>0){
			x=block.left-width-8;
		}

		// y++;
		/*
		 * if (!isGrounded() && !isOnthisBlock()) { velY += ACCEL_GRAVITY *
		 * delta; y += velY * delta; } else if (isGrounded()) { y = 406 -
		 * height; velY = 0; } else if (isOnthisBlock()) { y = block.getY() -
		 * height; velY = 0; }
		 */

		y += velY * delta;
		Log.d("TAG", "velY:=" + velY + "y:=" + y + "display_height"
				+ display_height);

		if (y > display_height) {
			isAlive = false;
		}

		updateRects();

	}

	public boolean isUpthisBlock() {

		return rect.left < block.right && block.left < rect.right
				&& rect.bottom < block.top;

	}

	public boolean isOnthisBlock() {

		return Rect.intersects(rect, block);

	}

	private void updateRects() {
		rect.set((int) x + 10, (int) y, (int) x + (width - 20), (int) y
				+ height);
		duckRect.set((int) x, (int) y + 20, (int) x + width, (int) y + 20
				+ (height - 20));

	}

	public void jump() {
		if (isOnthisBlock()	&& velY == 0) {
			Assets.playSound(Assets.onJumpID);
			isDucked = false;
			duckDuration = .6f;
			y -= 10;
			velY = JUMP_VELOCITY;
			updateRects();
		}
	}

	/*
	 * public void duck() { if (isGrounded()) { isDucked = true; } }
	 * 
	 * private void updateRects() {
	 * 
	 * 
	 * 
	 * }
	 */

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

	public int getVelY() {
		return velY;
	}

	public Rect getRect() {
		return rect;
	}

	public Rect getDuckRect() {
		return duckRect;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public boolean isDucked() {
		return isDucked;
	}

	public float getDuckDuration() {
		return duckDuration;
	}

}
