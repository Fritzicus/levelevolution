package com.levo.physics;

// Represents a two dimensional vector with cartesian coordinates
public class Vec2 {
	// The minimum value either component of a vector can attain. Helps with optimization
	public static final double MIN_VALUE_THRESHOLD = .000001;
	
	public double x, y; // Normally, I don't like public instance variables, but in this case, I think it's fairly benign
	
	// Initialize Vector with specified x-y values
	public Vec2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	// Initialize Vector with x-y values calculated from given angle
	public Vec2(double angle) {
		x = Math.cos(angle);
		y = Math.sin(angle);
		if (Math.abs(x) < MIN_VALUE_THRESHOLD)
			x = 0;
		if (Math.abs(y) < MIN_VALUE_THRESHOLD)
			y = 0;
	}
	
	public Vec2() {
		this(0, 0);
	}
	
	// Get angle from current x-y values
	public double angle() {
		return Math.atan2(y, x);
	}
	
	// TODO memoize
	public double length() {
		return Math.sqrt(lengthSquared());
	}
	
	// Calculate length squared (useful as sqrt operation is costly and sometimes unnecessary.
	public double lengthSquared() {
		return Math.pow(x, 2) + Math.pow(y, 2);
	}
	
	// Calculate distance from another vector
	public double distance(Vec2 v) {
		return Math.sqrt(distanceSquared(v));
	}
	
	// Calculate squared distance from another vector
	public double distanceSquared(Vec2 v) {
		return Math.pow(x - v.x, 2) + Math.pow(y - v.y, 2);
	}
	
	// Scale vector length by specified amount
	public void scale(double amt) {
		x *= amt;
		y *= amt;
	}
	
	// Create new scaled vector
	public Vec2 scaled(double amt) {
		return new Vec2(x * amt, y * amt);
	}

	// Rotate by calculating x-y from given angle of rotation
	public void rotate(double angle) {
		double len = length();
		angle += angle();
		x = len * Math.cos(angle);
		y = len * Math.sin(angle);
	}
	
	// Return new vector rotated specific angle from current vector
	public Vec2 rotated(double angle) {
		return (new Vec2(angle() + angle)).scaled(length());
	}
	
	// Add given vector to current vector
	public void add(Vec2 v) {
		x += v.x;
		y += v.y;
	}
	
	// Return vector result of adding
	public Vec2 added(Vec2 v) {
		return new Vec2(x + v.x, y + v.y);
	}
	
	// Subtract given vector from current
	public void subtract(Vec2 v) {
		x -= v.x;
		y -= v.y;
	}
	
	// Return vector result of subtracting
	public Vec2 subtracted(Vec2 v) {
		return new Vec2(x - v.x, y - v.y);		
	}
	
	// Gives the dot product of this vector with vector v
	public double dot(Vec2 v) {
		return v.x * x + v.y * y;
	}
	
	// Gives the vector-vector cross product in 2D
	public double cross(Vec2 v) {
		return x * v.y - y * v.x;
	}
	
	// Gives the vector scalar cross product in 2D
	public Vec2 cross(double d) {
		return new Vec2(d * y, -d * x);
	}
	
	// Gives the length of another vector projected on this one
	public double projectionLength(Vec2 v) {
		return dot(v.normalized());
	}
	
	// Normalize vector (Creates a length 1 vector)
	public void normalize() {
		double len = length();
		if (len == 0) {
			x = 1;
		} else {
			x /= len;
			y /= len;
		}
	}
	
	// Return normalized vector
	public Vec2 normalized() {
		double len = length();
		if (len == 0) {
			return new Vec2(1, 0);
		}
		return new Vec2(x / len, y / len);
	}
	
	// Convert vector data to string
	public String toString() {
		return "<" + x + ", " + y + ">";
	}
}