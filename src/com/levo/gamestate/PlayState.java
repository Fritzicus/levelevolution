package com.levo.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.levo.entity.Block;
import com.levo.entity.Player;
import com.levo.entity.Vec2;

public class PlayState extends GameState {

	private List<Block> blocks;
	private Player p;
	
	public PlayState() {
		blocks = new ArrayList<Block>();
		blocks.add(new Block(new Vec2(50, 380), 200, 20, Color.YELLOW));
		
		p = new Player();
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.GRAY);
		g.fillRect(50, 50, 300, 300);
		
		p.draw(g);
	}

	public void update() {
		
	}
	
	public void keyPressed(KeyEvent e) {
		p.keyPressed(e);
	}
	
	public void keyReleased(KeyEvent e) {
		p.keyReleased(e);
	}

}
