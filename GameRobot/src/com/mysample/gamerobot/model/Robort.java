package com.mysample.gamerobot.model;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Rect;
import android.util.Log;

import com.mysample.gamerobot.Assets;

public class Robort {

	private float x, y;
	private int width, height, velY, keyPressCounter = 0;
	private Rect rect, duckRect, glass, enemy;
	private boolean isAlive, isDucked;
	private float duckDuration;
	private static int INIT_LIFE = 3, JUMP_VELOCITY = -500;

	private byte[] lock = new byte[0]; // 特殊的instance变量
	private boolean canDoubleJump = false;

	public static int getINIT_LIFE() {
		return INIT_LIFE;
	}

	public static void setINIT_LIFE(int iNIT_LIFE) {
		INIT_LIFE = iNIT_LIFE;
	}

	public static void setJUMP_VELOCITY(int jUMP_VELOCITY) {
		JUMP_VELOCITY = jUMP_VELOCITY;
	}

	private static final int ACCEL_GRAVITY = 1500;
	private static int Speed = -300;

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
		glass = new Rect();
		enemy = new Rect();

		rect = new Rect();
		duckRect = new Rect();
		isAlive = true;
		isDucked = false;

	}

	public void updata(float delta, int display_height,
			ArrayList<Glass> glasses, ArrayList<Enemy> enemys) {
		if (y > display_height) {
			isAlive = false;
			Assets.playSound(Assets.DEADID);
		}

		if (velY < Math.abs(JUMP_VELOCITY)) {
			velY += ACCEL_GRAVITY * delta;
		}
		for (int i = 0; i < enemys.size(); i++) {

			this.enemy.set((int) enemys.get(i).getX() + 10, (int) enemys.get(i)
					.getY() + 20, (int) enemys.get(i).getX()
					+ enemys.get(i).getWidth() - 10, (int) enemys.get(i).getY()
					+ enemys.get(i).getHeight() - 20);
			if (isOnthisEnemy()) {
				isAlive = false;
				Assets.playSound(Assets.DEADID);
			}

		}
		for (int i = 0; i < glasses.size(); i++) {
			this.glass.set((int) glasses.get(i).getX() + 20,
					(int) glasses.get(i).getY() - 20, (int) glasses.get(i)
							.getX() + glasses.get(i).getWidth() - 170,
					(int) glasses.get(i).getY() + glasses.get(i).getHeight());
			// Log.d("tag", "y=" + y + "      height=" + height);

			// Log.d("TAG", "blocks.get(i).getY()" + blocks.get(i).getY() +
			// "y:="
			// + y + "block.top" + block.top);
			if (isOnthisBlock()
					&& Math.abs(y + height - glass.top) <= velY * delta + 25
							+ 30 && velY > 0) {
				velY = 0;
				keyPressCounter = 0;
				// y = 406 - height;
				y = glasses.get(i).getY() - height + 30;
				// Log.d("TAG", "blocks.get(i).getY()" + blocks.get(i).getY()
				// + "y:=" + y);
				break;

			}
		}

		y += velY * delta;
		// Log.d("TAG", "velY:=" + velY + "y:=" + y + "display_height"
		// + display_height);

		if (y > display_height) {
			isAlive = false;
			Assets.playSound(Assets.DEADID);
		}

		updateRects();

	}

	public boolean isOnthisEnemy() {
		// TODO Auto-generated method stub
		return Rect.intersects(rect, enemy);
	}

	public boolean isUpthisBlock() {

		return rect.left < glass.right && glass.left < rect.right
				&& rect.bottom < glass.top;

	}

	public boolean isOnthisBlock() {

		return Rect.intersects(rect, glass);

	}

	private void updateRects() {
		rect.set((int) x + 20, (int) y, (int) x + (width - 20), (int) y
				+ height);
		duckRect.set((int) x, (int) y + 20, (int) x + width, (int) y + 20
				+ (height - 20));

	}

	public synchronized void jump() {
		Log.d("tag.app1", "jumpstate:" + keyPressCounter);

		synchronized (lock) {

			if (++keyPressCounter == 1) {// 防止连发

				// 更新状态为准备起跳

				// playerState = 'readyToJump';
				// jumping = true; //跳跃中
				// onGround = false; //离地

				Assets.playSound(Assets.onJumpID);
				isDucked = false;
				duckDuration = .6f;
				y -= 10;
				velY = JUMP_VELOCITY; // 跳跃初始速度
				canDoubleJump = true;

				updateRects();

				/*
				 * if (isOnthisBlock() && velY == 0) {
				 * 
				 * }
				 */
			} else {
				if (canDoubleJump) {
					// 二段跳进行后禁止再次跳跃
					canDoubleJump = false;
					// 重新设置起跳速度
					velY += JUMP_VELOCITY * 1.1;
				}
			}
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

	public int getJumpstate() {
		return keyPressCounter;
	}

	public void setJumpstate(int jumpstate) {
		this.keyPressCounter = jumpstate;
	}

}
