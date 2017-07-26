package com.levo.physics;

import java.awt.Graphics2D;

import com.levo.game.Drawable;

public abstract class Body implements Drawable {
	public static final double UNDEFINED_MASS = -1;
	public static final double INFINITE_MASS = 0;

	
	protected double mass;
	protected double invMass;
	protected Material mat;
	protected Vec2 vel;
	protected Vec2 force;
	
	public Body(Material mat) {
		this.mat = mat;
		vel = new Vec2(0, 0);
		force = new Vec2(0, 0);
		
		mass = UNDEFINED_MASS;
		invMass = UNDEFINED_MASS;
	}

	public abstract void draw(Graphics2D g);
	public abstract void moveBy(Vec2 v);
	public abstract double area();
	public abstract Vec2 centerPoint();
	public abstract boolean containsPoint(Vec2 p);
	public abstract boolean isColliding(Body b);
	
	public void update(double dt) {
		moveBy(vel.scaled(dt));
		vel.add(force.scaled(getMassInv()));
		
		force.scale(0);
	}
	
	public void applyForce(Vec2 f) {
		force.add(f);
	}
	
	public double getMass() {
		if (mass == Body.UNDEFINED_MASS) {
			mass = area() * mat.density();
			System.out.println(mat + " " + mat.density() + " " + area());
		}
		return mass;
	}

	public double getMassInv() {
		if (mass == Body.UNDEFINED_MASS)
			getMass();
		if (invMass == Body.UNDEFINED_MASS) {
			if (mass == Body.INFINITE_MASS) {
				invMass = 0;
			} else {
				invMass = 1 / mass;
			}
		}
		return invMass;
	}
	
	public Material getMaterial() {
		return mat;
	}
	
}
