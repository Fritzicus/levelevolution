package com.levo.game;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.levo.gamestates.GameState;

public class GameStateManager extends KeyAdapter implements MouseListener, MouseMotionListener {
	
	private GameState gameState;
	
	public void draw(Graphics g) {
		gameState.draw(g);
	}
	
	public void update() {
		gameState.update();
		gameState = gameState.getGameState();
	}
	
	public void dispose() {
		gameState.dispose();
	}
	
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
	
	public void keyPressed(KeyEvent e) {
		gameState.keyPressed(e);
	}
	
	public void keyReleased(KeyEvent e) {
		gameState.keyReleased(e);
	}

	public void mouseClicked(MouseEvent e) {
		gameState.mouseClicked(e);
	}

	public void mouseEntered(MouseEvent e) {
		gameState.mouseEntered(e);		
	}

	public void mouseExited(MouseEvent e) {
		gameState.mouseExited(e);		
	}

	public void mousePressed(MouseEvent e) {
		gameState.mousePressed(e);		
	}

	public void mouseReleased(MouseEvent e) {
		gameState.mouseReleased(e);		
	}

	public void mouseDragged(MouseEvent e) {
		gameState.mouseDragged(e);		
	}

	public void mouseMoved(MouseEvent e) {
		gameState.mouseMoved(e);		
	}
}
