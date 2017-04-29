package com.mysample.gamerobot.animation;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

public class MyAnimation {
	private Animation anim=null;
	public Animation getRotateAnim() {
		
		anim =new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		anim.setFillAfter(true); // 设置保持动画最后的状态
		anim.setDuration(3000); // 设置动画时间
		anim.setInterpolator(new AccelerateInterpolator()); // 设置插入器
		
		return anim;
		
	}

}
