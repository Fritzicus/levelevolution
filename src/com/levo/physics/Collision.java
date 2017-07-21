package com.levo.physics;

public class Collision {
	
	private Vec2 normal;
	private double depth;
	
	public Collision(Vec2 normal, double depth) {
		this.normal = normal;
		this.depth = depth;
	}
	
	public Vec2 getNormal() {
		return normal;
	}
	
	public double getDepth() {
		return depth;
	}
	
}
