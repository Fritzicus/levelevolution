package com.levo.gamestate;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class GameState {
	
	private boolean done;
	private GameState nextState;
	
	public GameState() {
		done = false;
		nextState = null;
	}

	public abstract void draw(Graphics2D g);
	
	public abstract void update();

	// Returning anything non null will cause that gamestate to be pushed on the gamestate stack
	public GameState nextState() {
		return nextState;
	}
	
	protected void setNextState(GameState nextState) {
		this.nextState = nextState;
	}
	
	public boolean isDone() {
		return done;
	}
	
	protected void exit() {
		done = true;
	}
	
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
