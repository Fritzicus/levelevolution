package com.levo.entity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {

	private int width, height;
	
	private BufferedImage[][] sprites;
	
	public Sprite(int w, int h, int r, int c, String sheetPath) {
		width = w;
		height = h;
		sprites = new BufferedImage[r][c];
		
		try {
			BufferedImage spriteSheet = ImageIO.read(new File(sheetPath));
			loadSprites(spriteSheet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void loadSprites(BufferedImage ss) {
		for(int i = 0; i < sprites.length-1; i++) {
			for(int j = 0; j < sprites[i].length-1; j++) {
				sprites[i][j] = ss.getSubimage(i * width, j * height, width, height);
			}
		}
	}
	
	public BufferedImage getImage() {
		return sprites[0][0];
	}
	
	
}
