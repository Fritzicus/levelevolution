package com.levo.gamestate;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

// Top level class representing a game state such as the main menu, a level, or paused state
public abstract class GameState {
	
	// Signifies that this gamestate is finished executing and to go the the previus game state
	private boolean done;
	// Tells Game what GameState to enter next
	private GameState nextState;
	
	// Map of key codes to booleans representing whether a key is pressed
	protected boolean[] keyDown;
	
	public GameState() {
		done = false;
		nextState = null;
		
		// Initialize keyDown with 256 keys that can be pressed or not pressed (true and false)
		// There are some keyCodes outside the 256 range, which will be ignored
		keyDown = new boolean[256];
	}

	// Abstract methods to be implemented by concrete gamestates suchas PlayState or MainMenuState
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
	
	// Event handler for when a key is pressed, sets keyDown to true at appropriate index
	public void keyPressed(int k) {
		if (k < keyDown.length)
			keyDown[k] = true;
	}

	// Event hendler for when a key is released, sets keyDown to false at appropriate index
	public void keyReleased(int k) {
		if (k < keyDown.length)
			keyDown[k] = false;
	}
	
	// Other unused event handlers
	public void mouseClicked(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}
	
	public void mouseReleased(MouseEvent e) {}

	public void mouseDragged(MouseEvent e) {}

	public void mouseMoved(MouseEvent e) {}
	
}
