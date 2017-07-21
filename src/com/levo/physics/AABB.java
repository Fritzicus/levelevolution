package com.levo.physics;

import java.awt.Color;
import java.awt.Graphics2D;

// TODO Hopefully deprecate this class when more serious physics comes along

//(Axis Aligned Bounding Box)
public class AABB {
	
	public Vec2 posA;
	private Vec2 posB;
	private double width, height;
	
	//Initialize AABB with given vectors
	public AABB(Vec2 posA, Vec2 posB) {
		this.posA = posA;
		this.posB = posB;
		
		this.width = posB.x - posA.x;
		this.height = posB.y - posA.y;
	}
	
	//Initialize AABB with given location vector and width/height values
	public AABB(Vec2 posA, int w, int h) {
		this(posA, new Vec2(posA.x + w, posA.y + h));
	}
	
	//Calculate fill area and draw object
	public void draw(Graphics2D g, Color c) {
		g.setColor(c);		
		g.fillRect((int) posA.x, (int) posA.y, (int) width, (int) height);
	}
	
	//Add vectors
	public void addVec(Vec2 v) {
		posA.add(v);
		posB.add(v);
	}
	
	//Test if box is colliding
	public boolean isColliding(AABB other) {
		return !(other.posB.y < posA.y || other.posB.x < posA.x || other.posA.x > posB.x || other.posA.y > posB.y);

	}

	//
	public Collision getCollision(AABB other) {
		double xOverlap1 = other.posB.x - posA.x;
		double xOverlap2 = posB.x - other.posA.x;
		double yOverlap1 = other.posB.y - posA.y;
		double yOverlap2 = posB.y - other.posA.y;
		
		if (Math.max(yOverlap1, yOverlap2) < Math.max(xOverlap1, xOverlap2)) {
			if (yOverlap1 >= 0) {
				return new Collision(new Vec2(3 * Math.PI / 2), yOverlap1);
			} else {
				return new Collision(new Vec2(Math.PI / 2), yOverlap2);
			}
		} else {
			if (xOverlap1 >= 0) {
				return new Collision(new Vec2(Math.PI), xOverlap1);
			} else {
				return new Collision(new Vec2(0), xOverlap2);
			}
		}
	}
}
