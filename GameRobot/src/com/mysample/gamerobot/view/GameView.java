package com.mysample.gamerobot.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;

import com.mysample.gamerobot.Assets;
import com.mysample.gamerobot.R;
import com.mysample.gamerobot.animation.MyAnimation;
import com.mysample.gamerobot.state.LoadState;
import com.mysample.gamerobot.state.State;
import com.mysample.gamerobot.util.InputHandler;
import com.mysample.gamerobot.util.Painter;

public class GameView extends SurfaceView implements Runnable {

	private Bitmap gameImage;
	private Rect gameImageSrc;
	private Rect gameImageDst;
	private Canvas gameCanvas;
	private Canvas screen;
	private Painter graphics;
	private volatile State currentState;
	private View cloud;

	private boolean running;
	private Thread gameThread;
	private InputHandler inputHandler;

	public GameView(Context context, int gameWidth, int gameHeight) {
		super(context);
//		cloud=new View(getContext());
//		cloud.setBackgroundResource(R.drawable.ic_launcher);
//		MyAnimation myAnimation=new MyAnimation();
//		cloud.startAnimation(myAnimation.getRotateAnim());
//		cloud.
		gameImage = Bitmap.createBitmap(gameWidth, gameHeight,
				Bitmap.Config.RGB_565);
		gameImageSrc = new Rect(0, 0, gameImage.getWidth(),
				gameImage.getHeight());
		gameImageDst = new Rect();
		gameCanvas = new Canvas(gameImage);
		graphics = new Painter(gameCanvas);
		SurfaceHolder holder = getHolder();
		holder.addCallback(new Callback() {

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// pauseGame();
				running = false;

			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				initInput();
				

			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
				if (currentState == null) {
					setCurrentState(new LoadState(), width, height);
				}
				initGame();

			}
		});

	}

	protected void initGame() {
		running = true;
		gameThread = new Thread(this, "Game Thread");
		gameThread.start();

	}

	protected void initInput() {
		if (inputHandler == null) {
			inputHandler = new InputHandler();
		}
		setOnTouchListener(inputHandler);
	}
	public void setCurrentState(State newState) {
	
		
		System.gc();
		newState.init();
		currentState = newState;
		inputHandler.setCurrentState(currentState);

	}

	public void setCurrentState(State newState,int width,int height) {
	
		
		System.gc();
		newState.init( width,height);
		currentState = newState;
		inputHandler.setCurrentState(currentState);

	}
	private void updateAndRender(long delta) {
		currentState.updata(delta / 1000f);
		currentState.render(graphics);
		renderGameImage();
	}

	private void renderGameImage() {
		screen = getHolder().lockCanvas();
		if (screen != null) {
		
			screen.getClipBounds(gameImageDst);
			//screen.drawBitmap(gameImage, matrix, null);
			screen.drawBitmap(gameImage, gameImageSrc, gameImageDst,null);
			getHolder().unlockCanvasAndPost(screen);
		}
	}

	@Override
	public void run() {
		long updateDurationMillis = 0;
		long sleepDurationMillis = 0;
		while (running && Assets.isReady) {
			long beforeUpdateRender = System.nanoTime();
			long deltaMillis = sleepDurationMillis + updateDurationMillis;
			
			updateAndRender(deltaMillis);
		
/*			if (Assets.isReady) {
				Bitmap[] b = Assets.getBitmapRobort();
				//
				Canvas screen = getHolder().lockCanvas();

				graphics.drawImage(b[5], 100, 100, b[4].getWidth(),
						b[4].getHeight());
				screen.getClipBounds(gameImageDst);
				screen.drawBitmap(gameImage, gameImageSrc, gameImageDst, null);
				getHolder().unlockCanvasAndPost(screen);
			}*/
			updateDurationMillis = (System.nanoTime() - beforeUpdateRender) / 1000000L;
			//Log.d("APP.TAG","updateDurationMillis:"+updateDurationMillis+"");
			sleepDurationMillis = Math.max(20, 17 - updateDurationMillis);
			try {
				Thread.sleep(sleepDurationMillis);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	

}
