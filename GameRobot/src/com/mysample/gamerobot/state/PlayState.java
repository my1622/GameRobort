package com.mysample.gamerobot.state;

import java.util.ArrayList;
import java.util.Random;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.MotionEvent;

import com.mysample.gamerobot.Assets;
import com.mysample.gamerobot.MainActivity;
import com.mysample.gamerobot.animation.MyAnimation;
import com.mysample.gamerobot.model.BackGround;
import com.mysample.gamerobot.model.Block;
import com.mysample.gamerobot.model.Cloud;
import com.mysample.gamerobot.model.Robort;
import com.mysample.gamerobot.util.Painter;

public class PlayState extends State {
	private Robort robort;
	private Cloud cloud1, cloud2;
	private BackGround background1, background2;
	private ArrayList<BackGround> backGrounds;
	private long beforeAction_dwon = 1000000000000000l;;
	private int height;

	private ArrayList<Block> blocks;
	private int highScore = 0;

	@Override
	public void init(int width, int height) {
		this.height = height;
		robort = new Robort(100, 100, Assets.getBitmapRobort()[0].getWidth(),
				Assets.getBitmapRobort()[0].getHeight());
		cloud1 = new Cloud(400, 50, Assets.cloud.getWidth(),
				Assets.cloud.getHeight(), 0);
		// cloud2 = new Cloud(900, 65, Assets.cloud.getWidth(),
		// Assets.cloud.getHeight());

		backGrounds = new ArrayList<BackGround>();
		for (int i = 0; i < 2; i++) {

			BackGround background = new BackGround(i * Assets.bg.getWidth(), 0,
					Assets.bg.getWidth(), Assets.bg.getHeight());
			backGrounds.add(background);

		}

		blocks = new ArrayList<Block>();

		for (int i = 0; i < 2; i++) {
			Random r = new Random();

			Block block = new Block(i * 700 + r.nextInt(33), 405,
					Assets.block.getWidth(), Assets.block.getHeight());
			blocks.add(block);
		}

	}

	@Override
	public void updata(float delta) {
		if (!robort.isAlive() && Robort.getINIT_LIFE() >= 0) {
			if (Robort.getINIT_LIFE() > 0) {
				Robort.setINIT_LIFE(Robort.getINIT_LIFE() - 1);
			}
			setCurrentState(new HighScoreState(highScore / 10),
					MainActivity.GAME_WIDTH, MainActivity.GAME_HEIGHT);

		} else {

			Assets.runAnimation.updata(delta);

			cloud1.update(delta);
			// cloud2.update(delta);
			uddateBackgournd(delta);

			robort.updata(delta, height, blocks);
			updateBlocks(delta);

		}
	}

	private void uddateBackgournd(float delta) {
		for (BackGround b : backGrounds) {
			b.update(delta);
		}

	}

	private void updateBlocks(float delta) {
		for (Block b : blocks) {
			b.update(delta, robort.getSpeed());
			/*
			 * if (b.isVisible()) { if (player.isDucked() &&
			 * Rect.intersects(b.getRect(), player.getDuckRect())) {
			 * b.onCollide(player); } else if (!player.isDucked() &&
			 * Rect.intersects(b.getRect(), player.getRect())) {
			 * b.onCollide(player); } }
			 */
		}

	} 

	@Override
	public void render(Painter g) {
		g.setColor(Color.rgb(158, 194, 197));
		g.fillRect(0, 0, MainActivity.GAME_WIDTH, MainActivity.GAME_HEIGHT);
		rederBackgrounds(g);

		rederCloud(g);
		renderBlocks(g);
		renderPlayer(g);

		// g.drawImage(Assets.grass, 0, 405);
		renderScore(g);
		if (highScore==0){
			renderHit(g);
			
			
		}

	}  

	private void renderHit(Painter g) {
		g.setColor(Color.BLACK);
		g.setAlpha(30);
		g.fillRect(0, 0, MainActivity.GAME_WIDTH, MainActivity.GAME_HEIGHT);
		
	}

	private void renderScore(Painter g) {
		g.setFont(Typeface.SANS_SERIF, 25);
		g.setColor(Color.GRAY);
		g.drawString("Score:" + highScore / 10, MainActivity.GAME_WIDTH - 100,
				30);
	}

	private void rederBackgrounds(Painter g) {
		for (BackGround bg : backGrounds) {
			g.drawImage(Assets.bg, (int) bg.getX(), (int) bg.getY(),
					bg.getWidth(), bg.getHeight());

		}

	}

	private void renderBlocks(Painter g) {
		for (Block block : blocks) {

			g.drawImage(Assets.block, (int) block.getX(), (int) block.getY(),
					block.getWidth(), block.getHeight());

		}

	}

	private void rederCloud(Painter g) {

		g.drawImage(Assets.cloud, (int) cloud1.getX(), (int) cloud1.getY(),
				cloud1.getWidth(), cloud1.getHeight(), cloud1.getDegree());
		// g.drawImage(Assets.cloud, (int) cloud2.getX(), (int) cloud2.getY(),
		// cloud2.getWidth(), cloud2.getHeight());

	}

	private void renderPlayer(Painter g) {
		if (robort.isOnthisBlock()) {
			if (robort.isDucked()) {
				g.drawImage(Assets.getBitmapRobort()[0], (int) robort.getX(),
						(int) robort.getY());
			} else {
				Assets.runAnimation.render(g, (int) robort.getX(),
						(int) robort.getY(), robort.getWidth(),
						robort.getHeight());
			}
		} else if ((!robort.isOnthisBlock())) {
			g.drawImage(Assets.jump, (int) robort.getX(), (int) robort.getY(),
					robort.getWidth(), robort.getHeight());
		}
	}

	@Override
	public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {

		if (e.getAction() == MotionEvent.ACTION_DOWN) {
			beforeAction_dwon = System.nanoTime();

		}
		if (e.getAction() == MotionEvent.ACTION_UP) {
			Log.d("tagapp", "System.nanoTime()=" + System.nanoTime());
			Log.d("tagapp", "beforeAction_dwon=" + beforeAction_dwon);
			long longPressTime = (System.nanoTime() - beforeAction_dwon) / 100000000;
			Log.d("tag", "longPressTime=" + longPressTime);
			if (longPressTime > 1.4) {
				Robort.setJUMP_VELOCITY(-1000);

			} else {
				Robort.setJUMP_VELOCITY(-800);
			}
			robort.jump();
			highScore++;

		}

		return true;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
