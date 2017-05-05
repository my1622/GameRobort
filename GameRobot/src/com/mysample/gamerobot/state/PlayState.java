package com.mysample.gamerobot.state;

import java.util.ArrayList;
import java.util.Random;

import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Message;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.MotionEvent;

import com.mysample.gamerobot.Assets;
import com.mysample.gamerobot.MainActivity;
import com.mysample.gamerobot.animation.MyAnimation;
import com.mysample.gamerobot.model.BackGround;
import com.mysample.gamerobot.model.Block;
import com.mysample.gamerobot.model.Cloud;
import com.mysample.gamerobot.model.Enemy;
import com.mysample.gamerobot.model.Glass;
import com.mysample.gamerobot.model.Hand;
import com.mysample.gamerobot.model.Robort;
import com.mysample.gamerobot.util.Painter;
import com.mysample.gamerobot.view.GameView;

public class PlayState extends State {

	private byte[] lock = new byte[0]; // 特殊的instance变量
	private int count = 0;
	private Robort robort;
	private Hand hand;
	private Cloud cloud1, cloud2;
	private ArrayList<Enemy> enemys;
	private BackGround background1, background2;
	private ArrayList<BackGround> backGrounds;

	private int height;
	private Random random = new Random();

	private ArrayList<Glass> glasses;
	private int highScore = 0;
	private long durntime = 0;
	private float recentTouchY;
	private Painter panter;

	@Override
	public void init(int width, int height) {

		this.height = height;
		enemys = new ArrayList<Enemy>();

		cloud1 = new Cloud(400, -60, Assets.cloud.getWidth(),
				Assets.cloud.getHeight(), 0);
		// cloud2 = new Cloud(900, 65, Assets.cloud.getWidth(),
		// Assets.cloud.getHeight());

		hand = new Hand(400, 300, 70, 70, 0);

		backGrounds = new ArrayList<BackGround>();
		for (int i = 0; i < 2; i++) {

			BackGround background = new BackGround(i * Assets.bg.getWidth(),
					0 - 50, Assets.bg.getWidth(), Assets.bg.getHeight());
			backGrounds.add(background);

		}

		glasses = new ArrayList<Glass>();

		for (int i = 0; i < 2; i++) {

			Glass glass = new Glass(i * Assets.glass.getWidth() + 1, 300,
					Assets.glass.getWidth(), Assets.glass.getHeight());
			glasses.add(glass);

		}
		enemys.add(new Enemy((int) random.nextInt(33) + Assets.glass.getWidth()
				* 3, 405 - 150, Assets.diren.getWidth(), Assets.diren
				.getHeight(), Robort.getSpeed() * 2));
		robort = new Robort(100, 200, Assets.getBitmapRobort()[0].getWidth(),
				Assets.getBitmapRobort()[0].getHeight());

	}

	@Override
	public void updata(float delta) {

		if (!robort.isAlive() && Robort.getINIT_LIFE() >= 0) {
			if (Robort.getINIT_LIFE() > 0) {
				Robort.setINIT_LIFE(Robort.getINIT_LIFE() - 1);
			}

			for (int i = 0; i < 3; i++) {
				// TODO animation dead
				render(panter);
			}

			setCurrentState(new HighScoreState(highScore / 10),
					MainActivity.GAME_WIDTH, MainActivity.GAME_HEIGHT);

		} else if ((highScore == 0 && robort.isOnthisBlock())
				&& MainActivity.getHightScore() == 0) {
			hand.update(delta);

		} else {

			Assets.runAnimation.updata(delta);

			cloud1.update(delta);
			// cloud2.update(delta);
			uddateBackgournd(delta);
			for (int i = 0; i < enemys.size(); i++) {

				enemys.get(i).update(delta);

			}

			robort.updata(delta, height, glasses, enemys);

			updateGlass(delta);
		}

		count++;

		if (count == 2000) {
			count = 0;
			enemys.add(new Enemy((int) random.nextInt(33)
					+ Assets.glass.getWidth() * 3, 405 - 150, Assets.diren
					.getWidth(), Assets.diren.getHeight(),
					Robort.getSpeed() * 2));
		}

	}

	private void uddateBackgournd(float delta) {
		for (BackGround b : backGrounds) {
			b.update(delta);
		}

	}

	private void updateGlass(float delta) {
		for (Glass glass : glasses) {
			glass.update(delta, robort.getSpeed());
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
		this.panter = g;
		g.setColor(Color.rgb(158, 194, 197));
		g.fillRect(0, 0, MainActivity.GAME_WIDTH, MainActivity.GAME_HEIGHT);
		rederBackgrounds(g);

		rederCloud(g);
		renderGlasses(g);
		renderPlayer(g);
		renderDiren(g);

		// g.drawImage(Assets.grass, 0, 405);
		renderScore(g);
		if (highScore == 0
				&& (robort.isOnthisBlock() || robort.isOnthisEnemy())
				&& MainActivity.getHightScore() == 0) {
			renderHit(g);

		}

	}

	private void renderDiren(Painter g) {
		for (int i = 0; i < enemys.size(); i++) {
			g.drawImage(Assets.enemys.get(enemys.get(i).getNo()), (int) enemys
					.get(i).getX(), (int) enemys.get(i).getY(), enemys.get(i)
					.getWidth(), enemys.get(i).getHeight());

		}

	}

	private void renderHit(Painter g) {
		g.setColor(Color.BLACK);
		g.setAlpha(180);
		g.fillRect(0, 0, MainActivity.GAME_WIDTH, MainActivity.GAME_HEIGHT);

		g.drawImage(Assets.hand, (int) hand.getX(), (int) hand.getY(),
				hand.getWidth(), hand.getHeight(), hand.getAlpha());

	}

	private void renderScore(Painter g) {
		g.setFont(Typeface.SANS_SERIF, 25);
		g.setColor(Color.GRAY);
		// g.drawString("Score:" + highScore / 10, MainActivity.GAME_WIDTH -
		// 100,30);
		g.drawString("Score:" + highScore / 10 + "count:" + count,
				MainActivity.GAME_WIDTH - 300, 30);
	}

	private void rederBackgrounds(Painter g) {
		for (BackGround bg : backGrounds) {
			g.drawImage(Assets.bg, (int) bg.getX(), (int) bg.getY(),
					bg.getWidth(), bg.getHeight());

		}

	}

	private void renderGlasses(Painter g) {
		for (Glass glass : glasses) {

			g.drawImage(Assets.glass, (int) glass.getX(), (int) glass.getY(),
					glass.getWidth(), glass.getHeight());

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
		long durningtime = 1000l;
		long begingtime;
		if (e.getAction() == MotionEvent.ACTION_DOWN) {
			recentTouchY = scaledY;

		}
		if (e.getAction() == MotionEvent.ACTION_UP) {
			if (robort.isAlive() && scaledY - recentTouchY < -50) {

				
				
				
				if (durningtime > (1000 / GameView.FPS)) {
					durningtime=0;
					begingtime = System.currentTimeMillis();

					synchronized (lock) {
						Message msg = new Message();
						msg.what = 1;

						robort.jump();
						highScore++;
					}
					durningtime = System.currentTimeMillis() - begingtime;
				}

			}
		}

		return true;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
