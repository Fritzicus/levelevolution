package com.levo.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.levo.game.Game;

public class MainMenuState extends GameState {

	public MainMenuState() {
		
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.BLUE);
		Game.drawStringCentered(g, "<Press anything to continue>", 200, 370);
	}

	public void update() {
		
	}
	
	public void keyPressed(KeyEvent e) {
		exit();
		setNextState(new PlayState());
	}

}
