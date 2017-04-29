package com.mysample.gamerobot.model;

public class BackGround extends BaseModel {
	int width;
	public BackGround(float x, float y, int width, int height) {
		this.width=width;
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);

	}
	@Override
	public void update(float delta) {
		if (getX()<=-width){
			setX(width-10);
		}
		else{
		setX(getX()-1);}
		
		
		
	}
	

}
