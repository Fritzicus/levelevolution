package com.levo.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.levo.entity.Camera;
import com.levo.entity.Entity;
import com.levo.entity.GenerateLevel;
import com.levo.entity.Player;
import com.levo.game.Game;
import com.levo.physics.Vec2;
import com.sun.glass.events.KeyEvent;

// The main state where the player plays the level
public class PlayState extends GameState {

	public List<Entity> blocks;
	private Player p;
	private Camera cam;
	
	public PlayState() {
		super();
		
		
		// going to add basic level generation for now
		GenerateLevel createLevel = new GenerateLevel();
		blocks = new ArrayList<Entity>();
		
		createLevel.generateArray();
		blocks = createLevel.generateTerrain();
		
		
		
		/*
		blocks = new ArrayList<Entity>();
		
		blocks.add(new Block(new Vec2(50, 380), 100, 20, Color.GREEN));
		blocks.add(new Block(new Vec2(150, 360), 1000, 40, Color.BLUE));
		blocks.add(new Block(new Vec2(50, 240), 200, 40, Color.CYAN));
		blocks.add(new Block(new Vec2(420, 140), 80, 80, Color.RED));
		*/
		
		int[] playerPos = new int[2];
		
		playerPos=createLevel.generatePlayerPos();
		
		
		p = new Player(new Vec2(playerPos[0],playerPos[1]), keyDown);

		// Initialize Camera to track the player
		cam = new Camera(p);
		
		System.out.println("PlayState initialized");
	}
	
	public void draw(Graphics2D g) {
		// Draw all entities from the cameras perspective
		cam.draw(g, blocks);
		
		// Draw a bar at the top with level number to test that camera still works
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, 400, 20);
		g.setColor(Color.BLACK);
		Game.drawStringCentered(g, "Level 1", 200, 10);
	}

	public void update() {
		// Update player, blocks, camera
		p.update();
		p.handleCollisions(blocks);
		cam.update();
		
		// On escape, close the game
		if (keyDown[KeyEvent.VK_ESCAPE]) {
			super.exit();
		}
	}
}
