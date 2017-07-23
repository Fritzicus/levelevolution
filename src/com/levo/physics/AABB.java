package com.levo.physics;

import java.awt.Color;
import java.awt.Graphics2D;

// TODO Hopefully deprecate this class when more serious physics comes along

// (Axis Aligned Bounding Box)
public class AABB {
	
	// Position A is a point vector for the top left of the rectangle
	public Vec2 posA;
	// Position B is a point vector for the bottom right of the rectangle
	private Vec2 posB;
	// Represent the width and the height of the rectangle
	private double width, height;
	
	//Initialize AABB with given vectors
	public AABB(Vec2 posA, Vec2 posB) {
		this.posA = posA;
		this.posB = posB;
		this.width = posB.x - posA.x;
		this.height = posB.y - posA.y;
	}
	
	// Initialize AABB with given location vector and width/height values
	public AABB(Vec2 posA, int width, int height) {
		this(posA, new Vec2(posA.x + width, posA.y + height));
	}
	
	// Calculate fill area and draw object
	public void draw(Graphics2D g, Color c) {
		g.setColor(c);		
		g.fillRect((int) posA.x, (int) posA.y, (int) width, (int) height);
	}
	
	// Shift AABB by a vector
	public void addVec(Vec2 v) {
		posA.add(v);
		posB.add(v);
	}
	
	// Test if box is colliding
	public boolean isColliding(AABB other) {
		return !(other.posB.y <= posA.y || other.posB.x <= posA.x || other.posA.x >= posB.x || other.posA.y >= posB.y);

	}

	// Gets the collision object for a collision with another AABB
	public Collision getCollision(AABB other) {
		// Calculates x and y overlap between both AABBs
		double xOverlap = Math.min(other.posB.x - posA.x, posB.x - other.posA.x);
		double yOverlap = Math.min(posB.y - other.posA.y, other.posB.y - posA.y);
		
		// The smaller overlap is the axis the collision occurred on.
		if (xOverlap > yOverlap) {
			// Collision occurred in Y axis, now check if top or bottom
			if (posA.y > other.posA.y) {
				return new Collision(new Vec2(3 * Math.PI / 2), yOverlap);
			} else {
				return new Collision(new Vec2(Math.PI / 2), yOverlap);
			}
		} else {
			// Collision occurred in X axis, now check if left or right
			if (posA.x > other.posA.x) {
				return new Collision(new Vec2(Math.PI), xOverlap);
			} else {
				return new Collision(new Vec2(0), xOverlap);
			}
		}
	}
	
	// Returns a Vec2 representing the center of the AABB
	public Vec2 centerPoint() {
		return posA.added(new Vec2(width / 2, height / 2));
	}
}
