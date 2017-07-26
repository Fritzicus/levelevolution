package com.levo.entity;

import java.awt.image.BufferedImage;

public class Animation {

	private BufferedImage[] frames;
	private int currentFrame = 0, frameCount;
	public int direction = 1;
	private long startTime = System.currentTimeMillis();
	
	public boolean updated = false;
	
	public Animation(Sprite sp, int r, int begin, int end) {
		frameCount = end-begin+1;
		
		frames = new BufferedImage[frameCount];
		
		currentFrame = 0;
		for(int i = begin; i < end +1; i++) {
			frames[currentFrame] = sp.getImage(r, i);
			System.out.println(frames[currentFrame]);
			currentFrame++;
		}
		currentFrame = 0;
	}
	
	public void Play() {
		if(System.currentTimeMillis() - startTime > 50) {
			if(currentFrame < frameCount - 1) {
				currentFrame++;
			}
			else
				currentFrame = 0;
			
			startTime = System.currentTimeMillis();
			updated = true;
		}
		else
			updated = false;
	}
	
	public BufferedImage getCurrentFrame() {
				return frames[currentFrame];
	}
}
