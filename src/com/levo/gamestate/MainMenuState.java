package com.levo.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;

import com.levo.game.Game;

public class MainMenuState extends GameState {
	// TODO, give users options such as New Game, Online, Load Game, Settings, etc.

	public MainMenuState() {
		
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.BLUE);
		Game.drawStringCentered(g, "<Press anything to continue>", 200, 370);
	}

	public void update(double dt) {
		// Currently exits Main menu and starts a PlayState when any key is pressed
		for (boolean b : keyDown) {
			if (b) {
				exit();
				setNextState(new PlayState());
			}
		}
	}
}
