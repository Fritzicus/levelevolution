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
	public static final double JUMP_VEL = -5;
	public static final double SPEED = 5;

	private AABB aabb;
	private Vec2 vel;
	private boolean canJump;
	
	//Initialize with position
	public Player(Vec2 pos) {
		aabb = new AABB(pos, 50, 50); 
		vel = new Vec2(0, 0);
	}
	
	public void draw(Graphics2D g) {
		aabb.draw(g, Color.GREEN);
	}
	
	public void update() {
		aabb.addVec(vel);
		vel.add(Game.GRAVITY);
	}
	
	public void handleCollisions(List<Block> blocks) {
		//Step through each block in list
		for (Block b : blocks) {
			//Check if block is colliding
			if (b.getAABB().isColliding(aabb)) {
				Collision c = b.getAABB().getCollision(aabb);
				
				System.out.println(c.getNormal() + " " + c.getDepth());
				if (c.getNormal().y == -1) {
					vel.y = 0;
					canJump = true;
				}
				
				
				if (c.getDepth() >= 0) {
					aabb.addVec(c.getNormal().scaled(c.getDepth()));
				}
			}
		}
	}
	
	// TODO change horizontal movement speed while in air by referencing an 'inAir' bool?
	public void keyPressed(int k) {
		
		if (k == KeyEvent.VK_SPACE) {
			if (canJump && vel.y == 0) {
				vel.add(new Vec2(0, -5));
				vel.x = 0;
				canJump = false;
				
			}
		} else if (k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT) {
			if(canJump)
				vel.x = -SPEED;
			else
				vel.x = -SPEED/4;
		} else if (k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT) {
			if(canJump)
				vel.x = SPEED;
			else
				vel.x = SPEED/4;
		}
	}
	
	public void keyReleased(int k) {
		if (k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT) {
			vel.x = 0;
		} else if (k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT) {
			vel.x = 0;
		}
	}
}
