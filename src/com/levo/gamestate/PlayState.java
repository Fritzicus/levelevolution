package com.levo.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.levo.entity.Block;
import com.levo.entity.Player;
import com.levo.physics.Vec2;

public class PlayState extends GameState {

	private List<Block> blocks;
	private Player p;
	
	public PlayState() {
		super();
		
		blocks = new ArrayList<Block>();
		blocks.add(new Block(new Vec2(50, 380), 100, 20, Color.BLUE));
		blocks.add(new Block(new Vec2(150, 360), 200, 40, Color.BLUE));
		blocks.add(new Block(new Vec2(50, 240), 200, 40, Color.cyan));
		
		p = new Player(new Vec2(40, 250));
		
		System.out.println("PlayState initialized");
	}
	
	public void draw(Graphics2D g) {
		for (Block b : blocks) {
			b.draw(g);
		}
		
		p.draw(g);
	}

	public void update() {
		p.update();
		p.handleCollisions(blocks);
		p.updateWithControls(keyDown);
	}
}
