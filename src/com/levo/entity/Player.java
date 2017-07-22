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
	public static final double JUMP_VEL = -5.5;
	public static final double SPEED = 3;
	public static final double GROUND_SPEED_MULTIPLIER = 1.5;
	public static final double MAX_Y_SPEED = 15;
	public static final double JUMP_VELOCITY = -5;

	private AABB aabb;
	private Vec2 vel;
	private boolean jumpKeyDown;
	private boolean onWall;
	private boolean onGround;
	private boolean[] keyDown;
	private int noMoveCooldown;
	
	// Initialize with position
	public Player(Vec2 pos, boolean[] keyDown) {
		aabb = new AABB(pos, 10, 10); 
		vel = new Vec2(0, 0);
		onWall = false;
		onGround = false;
		this.keyDown = keyDown;
		noMoveCooldown = 0;
	}
	
	public void draw(Graphics2D g) {
		aabb.draw(g, new Color(102, 0, 204));
	}
	
	public void update() {
		if (keyDown[KeyEvent.VK_SPACE] && !jumpKeyDown) {
			if (onGround && vel.y == 0) {
				vel.y = JUMP_VELOCITY;
			} else if (onWall && keyDown[KeyEvent.VK_A]) {
				vel.y = JUMP_VELOCITY;
				vel.x = SPEED;
				noMoveCooldown = 15;
			} else if (onWall && keyDown[KeyEvent.VK_D]) {
				vel.y = JUMP_VELOCITY;
				vel.x = -SPEED;
				noMoveCooldown = 15;
			}
		} 
		jumpKeyDown = keyDown[KeyEvent.VK_SPACE];
				
		if (noMoveCooldown <= 0) {
			vel.x = 0;
			if (keyDown[KeyEvent.VK_A] || keyDown[KeyEvent.VK_LEFT]) {
				vel.x = -SPEED;
			} 
			if (keyDown[KeyEvent.VK_D] || keyDown[KeyEvent.VK_RIGHT]) {
				vel.x = SPEED;
			}
		} else {
			noMoveCooldown--;
		}
		
		aabb.addVec(vel);
		if (onWall && vel.y > 0)
			vel.add(Game.GRAVITY.scaled(.1));
		else
			vel.add(Game.GRAVITY);
		
		if (onGround) 
			vel.x *= GROUND_SPEED_MULTIPLIER;
		
		if (vel.y > MAX_Y_SPEED) {
			vel.y = MAX_Y_SPEED;
		} else if (vel.y < -MAX_Y_SPEED) {
			vel.y = -MAX_Y_SPEED;
		}
	}
	
	public void handleCollisions(List<Entity> blocks) {
		onGround = false;
		onWall = false;
		for (Entity e : blocks) {
			Block b = (Block) e;
			// Check if block is colliding with player
			if (b.getAABB().isColliding(aabb)) {
				Collision c = b.getAABB().getCollision(aabb);
				
				if (c.getNormal().y == -1) {
					vel.y = 0;
					onGround = true;
				} else if (c.getNormal().y == 1) {
					vel.y = .1;
				} else if (c.getNormal().x != 0) {
					onWall = true;
				}
				
				if (c.getDepth() >= 0) {
					aabb.addVec(c.getNormal().scaled(c.getDepth()));
				}
			}
		}
	}
	
	public Vec2 centerPoint() {
		return aabb.centerPoint();
	}
}
