package com.levo.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.levo.entity.Camera;
import com.levo.entity.Entity;
import com.levo.entity.GenerateLevel;
import com.levo.entity.Player;
import com.levo.game.Game;
import com.levo.physics.Vec2;
import com.sun.glass.events.KeyEvent;

public class PlayState extends GameState {

	public List<Entity> blocks;
	private Player p;
	private Camera cam;
	
	public PlayState() {
		super();
		
		// going to add basic level generation for now
		GenerateLevel createLevel = new GenerateLevel();
		blocks = new ArrayList<Entity>();
		blocks = createLevel.generateTerrain();
		/*
		blocks = new ArrayList<Entity>();
		
		blocks.add(new Block(new Vec2(50, 380), 100, 20, Color.GREEN));
		blocks.add(new Block(new Vec2(150, 360), 1000, 40, Color.BLUE));
		blocks.add(new Block(new Vec2(50, 240), 200, 40, Color.CYAN));
		blocks.add(new Block(new Vec2(420, 140), 80, 80, Color.RED));
		*/
		p = new Player(new Vec2(10, 10), keyDown);

		cam = new Camera(p);
		
		System.out.println("PlayState initialized");
	}
	
	public void draw(Graphics2D g) {
		cam.draw(g, blocks);
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, 400, 20);
		g.setColor(Color.BLACK);
		Game.drawStringCentered(g, "Level 1", 200, 10);
	}

	public void update() {
		p.update();
		p.handleCollisions(blocks);
		cam.update();
		
		if (keyDown[KeyEvent.VK_ESCAPE]) {
			super.exit();
		}
	}
}
