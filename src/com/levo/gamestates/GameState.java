package com.levo.gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class GameState {

	private GameState gameState;
	
	public GameState() {
		gameState = this;
	}
	
	protected void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public GameState getGameState() {
		return gameState;
	}

	public abstract void draw(Graphics g);
	
	public abstract void update();
	
	public void dispose() {};

	public void keyPressed(KeyEvent e) {}

	public void keyReleased(KeyEvent e) {}
	
	public void mouseClicked(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}
	
	public void mouseReleased(MouseEvent e) {}

	public void mouseDragged(MouseEvent e) {}

	public void mouseMoved(MouseEvent e) {}
	
}
