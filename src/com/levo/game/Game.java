package com.levo.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.levo.gamestates.MainMenuState;

public class Game extends JPanel implements Runnable {
	private static final long serialVersionUID = 30L;

	private Font font;
	private boolean running = false;
	private GameStateManager gsm;
	
	public Game() {
		Dimension size = new Dimension(800, 800);
				
		gsm = new GameStateManager();
		gsm.setGameState(new MainMenuState());
		
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setBackground(Color.BLACK);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		addKeyListener(gsm);
		addMouseListener(gsm);
		addMouseMotionListener(gsm);
		
		JFrame f = new JFrame("Level Evolution :)") {
			private static final long serialVersionUID = 1L;
			
			public void dispose() {
				gsm.dispose();
				super.dispose();
				System.exit(0);
			}
			
		};
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.add(this);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		
		font = new Font("Courier", Font.PLAIN, 25);

		running = true;
	}
	
	public void paintComponent(Graphics g) {
		double width = getSize().getWidth(), height = getSize().getHeight();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, (int) width, (int) height);
		
		if (width > height) {
			g2d.translate((width - height) / 2, 0);
		} else {
			g2d.translate(0, (height - width) / 2);			
		}
		
		g2d.scale(Math.min(width, height) / 400, Math.min(width, height) / 400);
		g2d.setFont(font);
		gsm.draw(g);
		
		g2d.setColor(Color.BLACK);
		if (width > height) {
			g2d.fillRect((int) -width, 0, (int) width, 400);
			g2d.fillRect(400, 0, (int) width, 400);
		} else {
			g2d.fillRect(0, (int) -height, 400, (int) height);
			g2d.fillRect(0, 400, 400, (int) height);
		}
	}
	
	public void update() {
		gsm.update();
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				delta--;
			}
			repaint();

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
			}
		}
	}	
}
