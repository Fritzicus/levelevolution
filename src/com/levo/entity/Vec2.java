package com.levo.entity;


public class Vec2 {
	
	public double x, y; // Normally, I don't like public instance variables, but in this case, I think it's fairly benign
	
	public Vec2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec2(double angle) {
		this.x = Math.cos(angle);
		this.y = Math.sin(angle);
	}
	
	public double angle() {
		return Math.atan2(y, x);
	}
	
	// TODO memoize
	public double length() {
		return Math.sqrt(lengthSquared());
	}
	
	public double lengthSquared() {
		return Math.pow(x, 2) + Math.pow(y, 2);
	}
	
	public double distance(Vec2 v) {
		return Math.sqrt(distanceSquared(v));
	}
	
	public double distanceSquared(Vec2 v) {
		return Math.pow(x - v.x, 2) + Math.pow(y - v.y, 2);
	}
	
	public void scale(double amt) {
		x *= amt;
		y *= amt;
	}
	
	public Vec2 scaled(double amt) {
		return new Vec2(x * amt, y * amt);
	}

	public void rotate(double angle) {
		double len = length();
		angle += angle();
		x = len * Math.cos(angle);
		y = len * Math.sin(angle);
	}
	
	public Vec2 rotated(double angle) {
		return (new Vec2(angle() + angle)).scaled(length());
	}
	
	public void add(Vec2 v) {
		x += v.x;
		y += v.y;
	}
	
	public Vec2 added(Vec2 v) {
		return new Vec2(x + v.x, y + v.y);
	}
	
	public void subtract(Vec2 v) {
		x -= v.x;
		y -= v.y;
	}
	
	public Vec2 subtracted(Vec2 v) {
		return new Vec2(x - v.x, y - v.y);		
	}
		
	public double dot(Vec2 v) {
		return v.x * x + v.y * y;
	}
	
	public double projectionLength(Vec2 v) {
		return dot(v.normalized());
	}
	
	public void normalize() {
		double len = length();
		x /= len;
		y /= len;
	}
	
	public Vec2 normalized() {
		return new Vec2(x / length(), y / length());
	}
	
	public String toString() {
		return "<" + x + ", " + y + ">";
	}
}