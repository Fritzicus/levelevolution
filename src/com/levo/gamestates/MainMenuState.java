package com.levo.gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class MainMenuState extends GameState {

	public MainMenuState() {
		
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		g.drawRect(50, 50, 100, 100);
		
		g.setColor(Color.YELLOW);
		g.drawOval(300, 170, 80, 200);
	}

	public void update() {
		
	}
	
	public void keyPressed(KeyEvent e) {
		exit();
		setNextState(new PlayState());
	}

}
