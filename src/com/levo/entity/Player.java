package com.levo.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.List;

import com.levo.game.Game;
import com.levo.physics.AABB;
import com.levo.physics.Collision;
import com.levo.physics.Vec2;

public class Player extends Entity {
	// The Speed of the player moving on the X axis
	public static final double SPEED = 2.5;
	// Multiplier for speed boost when player is on the ground
	public static final double GROUND_SPEED_MULTIPLIER = 1.5;
	// Maximum speed in the Y axis for the player
	public static final double MAX_Y_SPEED = 15;
	// The Y-Velocity the player instantaneously obtains when jumping
	public static final double JUMP_VELOCITY = -5.5;
	// Wall jump movement cooldown to make sure player moves away from wall before going back to it
	public static final int WALL_JUMP_COOLDOWN = 15;
	// Multiplier on gravity to be used during a wall slide to reduce slide speed
	public static final double WALL_SLIDE_GRAVITY = .2;
	
	public static final int WIDTH = 15;
	public static final int HEIGHT = 20;
	public static final Color COLOR = new Color(102, 0, 150);

	// AABB representing the players position and size (Player is a rectangle)
	private AABB aabb;
	// Players velocity. Updated when keys are pressed, collisions occur, and with gravity
	private Vec2 vel;
	// Boolean representing whether or not the jump key is being pressed. Used track changes in jump key being pressed.
	private boolean jumpKeyDown;
	// True when player is actively walking into a wall while moving left
	private boolean onLeftWall;
	// True when player is actively walking into a wall while moving right
	private boolean onRightWall;
	// True when player is standing on something
	private boolean onGround;
	// Array with an index for every key code. keyDown[any key code] is true when that key is being pressed
	private boolean[] keyDown;
	// Integer representing the number of ticks the player cannot move left and right
	private int noMoveCooldown;
	
	// Initialize with starting position. 
	// Also passes a reference to keyDown array so when keyDown is updated in GameState.java, 
	// the player keyDown array is effected
	public Player(Vec2 pos, boolean[] keyDown) {
		aabb = new AABB(pos, WIDTH, HEIGHT); 
		vel = new Vec2(0, 0);
		jumpKeyDown = false;
		onLeftWall = false;
		onRightWall = false;
		onGround = false;
		this.keyDown = keyDown;
		noMoveCooldown = 0;
	}
	
	// Draws the player currently as a rectangle with a purple color
	public void draw(Graphics2D g) {
		aabb.draw(g, COLOR);
	}
	
	public void update() {
		// Tests if the jump key is down and we haven't already recorded it. 
		// This ensures that jump only triggers when jump key is initially pressed, not if held down
		if (keyDown[KeyEvent.VK_SPACE] && !jumpKeyDown) {
			if (onGround && vel.y == 0) {
				// A regular jump is possible and will occur
				vel.y = JUMP_VELOCITY;
			} else if (onLeftWall) {
				// A wall jump has happened on a wall to the left
				vel.y = JUMP_VELOCITY;
				vel.x = SPEED;
				noMoveCooldown = WALL_JUMP_COOLDOWN; // A cooldown is necessary so the player moves away from the wall
			} else if (onRightWall) {
				// A wall jump has happened on a wall to the right
				vel.y = JUMP_VELOCITY;
				vel.x = -SPEED;
				noMoveCooldown = WALL_JUMP_COOLDOWN;
			}
		} 
		jumpKeyDown = keyDown[KeyEvent.VK_SPACE];
				
		// Allow player to move unless noMoveCooldown is positive, otherwise decrement it
		if (noMoveCooldown <= 0) {
			vel.x = 0;
			// Check player movement keys and set speed accordingly
			if (keyDown[KeyEvent.VK_A] || keyDown[KeyEvent.VK_LEFT]) {
				vel.x = -SPEED;
			} 
			if (keyDown[KeyEvent.VK_D] || keyDown[KeyEvent.VK_RIGHT]) {
				vel.x = SPEED;
			}
		} else {
			noMoveCooldown--;
		}
		
		// Apply ground movement speed bonus
		if (onGround) 
			vel.x *= GROUND_SPEED_MULTIPLIER;
		
		aabb.addVec(vel); // Move position by vel vector
		
		// If on wall, lower gravity to give a sliding down effect
		if ((onLeftWall || onRightWall) && vel.y > 0)
			vel.add(Game.GRAVITY.scaled(WALL_SLIDE_GRAVITY));
		else
			vel.add(Game.GRAVITY);
		
		// Keep y velocity within maximum bounds
		if (vel.y > MAX_Y_SPEED) {
			vel.y = MAX_Y_SPEED;
		} else if (vel.y < -MAX_Y_SPEED) {
			vel.y = -MAX_Y_SPEED;
		}
	}
	
	public void handleCollisions(List<Entity> blocks) {
		// Make these checks false and set them to true they are detected
		onGround = false;
		onLeftWall = false;
		onRightWall = false;
		for (Entity e : blocks) {
			Block b = (Block) e; // TODO check entity type before casting and doing special routines
			// Check if block is colliding with player
			if (b.getAABB().isColliding(aabb)) {
				Collision c = b.getAABB().getCollision(aabb);
				
				// Check collision normal to know what direction collision occured in
				if (c.getNormal().y == -1) {
					vel.y = 0;
					onGround = true;
				} else if (c.getNormal().y == 1) {
					vel.y = .1;
				} else if (c.getNormal().x == 1) {
					onLeftWall = true;
				} else if (c.getNormal().x == -1) {
					onRightWall = true;
				}
				
				if (c.getDepth() >= 0) {
					// Resolve collision by moving player back in the direction of the normal
					aabb.addVec(c.getNormal().scaled(c.getDepth()));
				}
			}
		}
	}
	
	// Returns players center, useful for camera positioning
	public Vec2 centerPoint() {
		return aabb.centerPoint();
	}
}
