package com.levo.physics;

// Collision contains information about a collision event that can be used to resolve collisions
public class Collision {
	
	// Collision normal is perpendicular to the colliding surfaces, giving the direction collision occured in
	private final Vec2 normal;
	// Depth is the distance along the collision normal that two colliding objects overlap
	private final double depth;
	
	// Class is immutable, only carrying information about a collision to be resolved by physics engine
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
