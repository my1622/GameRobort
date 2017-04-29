package com.mysample.gamerobot;


import com.mysample.gamerobot.util.InputHandler;
import com.mysample.gamerobot.view.GameView;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;


public class MainActivity extends Activity {

	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 450;
	public static GameView gameView;
	public static AssetManager assetManager;
	public static Resources resources;
	private static int HightScore=0;
	private InputHandler inputHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		assetManager=getAssets();
		gameView = new GameView(this, GAME_WIDTH, GAME_HEIGHT);
		resources=getResources();
		setContentView(gameView);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	public static int getHightScore() {
		return HightScore;
	}

	public static void setHightScore(int hightScore) {
		HightScore = hightScore;
	}

}
