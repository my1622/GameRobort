package com.mysample.gamerobot;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.AudioManager;
import android.media.SoundPool;

import com.mysample.gamerobot.animation.Animation;
import com.mysample.gamerobot.animation.Frame;

public class Assets {
	private static SoundPool soundPool;
	public static int onJumpID,DEADID;
	public static boolean isReady = false;
	
	public static Typeface font;

	public static Bitmap Robort, grass, jump,cloud,glass,bg,background,hand,diren,enemy;
	private static Bitmap[] bitmapRobort = new Bitmap[12];
	public static ArrayList<Bitmap> enemys;
	private static Frame[] frames = new Frame[12];
	public static Animation runAnimation;

	public static Bitmap[] getBitmapRobort() {
		return bitmapRobort;
	}

	public static void setBitmapRobort(Bitmap[] bitmapRobort) {
		Assets.bitmapRobort = bitmapRobort;
	}

	public static void load() {
		/*
		 * for (int i = 0; i < bitmapRobort.length; i++) {
		 * bitmapRobort[i]=loadBitmap("robort.png",true); }
		 */
		enemys=new ArrayList<Bitmap>();
		grass = loadBitmap("grass.png", true);
		Robort = loadBitmap("robort.png", true);
		jump = loadBitmap("jump.png", true);
		cloud= loadBitmap("cloud1.png", true);
		glass=loadBitmap("plantmodify.png", true);
		bg=loadBitmap("bg.png", true);
		background=loadBitmap("background.png", true);
		hand=loadBitmap("hand.png", true);
		diren=loadBitmap("enemy.png", true);
		enemys.add(loadBitmap("enemy.png", true));
		enemys.add(loadBitmap("enemy1.png", true));
		enemys.add(loadBitmap("enemy2.png", true));
		enemys.add(loadBitmap("enemy3.png", true));
		enemys.add(loadBitmap("enemy4.png", true));
		enemys.add(loadBitmap("enemy5.png", true));
		enemys.add(loadBitmap("enemy6.png", true));
		
		

		int frameW = Robort.getWidth() / 6; // 每帧的高
		int frameH = Robort.getHeight() / 2;
		int col = Robort.getWidth() / frameW; // 得到位图的列数
		for (int currentFrame = 0; currentFrame < 12; currentFrame++) {

			int x = currentFrame % col * frameW; // 得到当前帧相对于位图的X坐标
			int y = currentFrame / col * frameH; // 得到当前帧相对于位图的Y坐标

			bitmapRobort[currentFrame] = Bitmap.createBitmap(Robort, x, y,
					frameW, frameH);
			frames[currentFrame] = new Frame(bitmapRobort[currentFrame], 0.07f);

		}
		runAnimation = new com.mysample.gamerobot.animation.Animation(frames);
		
		font = Typeface.createFromAsset(MainActivity.assetManager,
				"fonts/font2.ttf");
		onJumpID = loadSound("onjump.wav");
		DEADID= loadSound("dead.mp3");
		isReady = true;
	}

	private static Bitmap loadBitmap(String filename, boolean transparency) {
		InputStream inputStream = null;
		try {
			inputStream = MainActivity.assetManager.open(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Options options = new Options();
		if (transparency) {
			options.inPreferredConfig = Config.ARGB_8888;
		} else {
			options.inPreferredConfig = Config.RGB_565;
		}
		Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
		return bitmap;
	}

	private static int loadSound(String filename) {
		int soundID = 0;
		if (soundPool == null) {
			soundPool = new SoundPool(25, AudioManager.STREAM_MUSIC, 0);
		}
		try {
			soundID = soundPool.load(
					MainActivity.assetManager.openFd(filename), 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return soundID;
	}

	public static void playSound(int soundID) {
		soundPool.play(soundID, 1, 1, 1, 0, 1);

	}

}
