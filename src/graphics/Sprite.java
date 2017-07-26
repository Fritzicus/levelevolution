package graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {

	private int width, height, offset = 0;

	private BufferedImage[][] spriteFrames;

	public Sprite(int w, int h, int r, int c, String sheetPath) {
		width = w;
		height = h;
		spriteFrames = new BufferedImage[r][c];

		System.out.println("Getting spriteFrames from " + sheetPath);
		try {
			BufferedImage spriteFramesheet = ImageIO.read(new File(sheetPath));
			loadspriteFrames(spriteFramesheet);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void loadspriteFrames(BufferedImage ss) {
		for (int i = 0; i < spriteFrames.length; i++) {
			for (int j = 0; j < spriteFrames[i].length; j++) {
				spriteFrames[i][j] = ss.getSubimage(j * width, i * height, width - offset, height);
			}
		}
	}

	public BufferedImage getFrame(int row, int col) {
		//System.out.println(spriteFrames[0][0]);
		return spriteFrames[row][col];
	}
	
	public Animation makeAnimation(int row, int begin, int end) {
		return new Animation(this, row, begin, end);
	}

}
