package com.levo.physics;


public class Vec2 {
	
	public double x, y; // Normally, I don't like public instance variables, but in this case, I think it's fairly benign
	
	//Initialize Vector with specified x-y values
	public Vec2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	//Initialize Vector with x-y values calculated from given angle
	public Vec2(double angle) {
		this.x = Math.cos(angle);
		this.y = Math.sin(angle);
	}
	
	//Get angle from current x-y values
	public double angle() {
		return Math.atan2(y, x);
	}
	
	// TODO memoize
	public double length() {
		return Math.sqrt(lengthSquared());
	}
	
	//Calculate length
	public double lengthSquared() {
		return Math.pow(x, 2) + Math.pow(y, 2);
	}
	
	//Calculate distance from distance^2
	public double distance(Vec2 v) {
		return Math.sqrt(distanceSquared(v));
	}
	
	//Calculate distance(^2) from different vector
	public double distanceSquared(Vec2 v) {
		return Math.pow(x - v.x, 2) + Math.pow(y - v.y, 2);
	}
	
	//Scale x-y by specified amount
	public void scale(double amt) {
		x *= amt;
		y *= amt;
	}
	
	//Create new scaled vector
	public Vec2 scaled(double amt) {
		return new Vec2(x * amt, y * amt);
	}

	//Rotate by calculating x-y from given angle of rotation
	public void rotate(double angle) {
		double len = length();
		angle += angle();
		x = len * Math.cos(angle);
		y = len * Math.sin(angle);
	}
	
	//Return new vector rotated specific angle from current vector
	public Vec2 rotated(double angle) {
		return (new Vec2(angle() + angle)).scaled(length());
	}
	
	//Add given vector to current vector
	public void add(Vec2 v) {
		x += v.x;
		y += v.y;
	}
	
	//Return vector result of adding
	public Vec2 added(Vec2 v) {
		return new Vec2(x + v.x, y + v.y);
	}
	
	//Subtract given vector from current
	public void subtract(Vec2 v) {
		x -= v.x;
		y -= v.y;
	}
	
	//Return vector result of subtracting
	public Vec2 subtracted(Vec2 v) {
		return new Vec2(x - v.x, y - v.y);		
	}
	
	public double dot(Vec2 v) {
		return v.x * x + v.y * y;
	}
	
	public double projectionLength(Vec2 v) {
		return dot(v.normalized());
	}
	
	//Normalize vector x-y
	public void normalize() {
		double len = length();
		x /= len;
		y /= len;
	}
	
	//Return normalized vector
	public Vec2 normalized() {
		return new Vec2(x / length(), y / length());
	}
	
	//Convert vector data to string
	public String toString() {
		return "<" + x + ", " + y + ">";
	}
}