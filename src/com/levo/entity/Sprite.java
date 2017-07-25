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

		System.out.println("Getting sprites from " + sheetPath);
		try {
			BufferedImage spriteSheet = ImageIO.read(new File(sheetPath));
			loadSprites(spriteSheet);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void loadSprites(BufferedImage ss) {
		for (int i = 0; i < sprites.length; i++) {
			for (int j = 0; j < sprites[i].length; j++) {
				sprites[i][j] = ss.getSubimage(j * width, i * height, width, height);
			}
		}
	}

	public BufferedImage getImage() {
		System.out.println(sprites[0][0]);
		return sprites[0][0];
	}

}
