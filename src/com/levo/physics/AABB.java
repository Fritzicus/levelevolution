package com.levo.physics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
// TODO Hopefully deprecate this class when more serious physics comes along

// (Axis Aligned Bounding Box)
public class AABB extends Body {
	// Position A is a point vector for the top left of the rectangle
	private Vec2 posA;
	// Position B is a point vector for the bottom right of the rectangle
	private Vec2 posB;
	// Represent the width and the height of the rectangle
	private double width, height;

	// Initialize AABB with given vectors
	public AABB(Vec2 posA, Vec2 posB, Material mat) {
		super(mat);
		this.posA = posA;
		this.posB = posB;
		this.width = posB.x - posA.x;
		this.height = posB.y - posA.y;
	}

	// Initialize AABB with given location vector and width/height values
	public AABB(Vec2 posA, int width, int height, Material mat) {
		this(posA, new Vec2(posA.x + width, posA.y + height), mat);
	}

	// Calculate fill area and draw object
	public void draw(Graphics2D g, Color c) {
		g.setColor(c);
		g.draw(new Rectangle2D.Double(posA.x, posA.y, width, height));
	}

	public void draw(Graphics2D g) {
		g.setColor(mat.color());
		g.draw(new Rectangle2D.Double(posA.x, posA.y, width, height));
	}

	// Shift AABB by a vector
	public void moveBy(Vec2 v) {
		posA.add(v);
		posB.add(v);
	}

	public boolean containsPoint(Vec2 p) {
		return (p.x > posA.x && p.x < posB.x && p.y > posA.y && p.y < posB.y);
	}
	
	// Test if box is colliding
	public boolean isColliding(Body b) {
		if (b instanceof AABB) {
			AABB other = (AABB) b;
			return !(other.posB.y <= posA.y || other.posB.x <= posA.x || other.posA.x >= posB.x
					|| other.posA.y >= posB.y);
		} else {
			return false;
		}
	}

	// Gets the collision object for a collision with another AABB
	public Collision getCollision(Body b) {
		if (b instanceof AABB) {
			AABB other = (AABB) b;
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
		} else {
			return null;
		}
	}

	public double area() {
		return width * height;
	}

	// Returns a Vec2 representing the center of the AABB
	public Vec2 centerPoint() {
		return posA.added(new Vec2(width / 2, height / 2));
	}
	
	public Vec2 getPosA() {
		return posA;
	}
	
	public Vec2 getPosB() {
		return posB;
	}
}
