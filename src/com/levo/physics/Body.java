package com.levo.physics;

import java.awt.Graphics2D;

public abstract class Body {
	public static final double UNDEFINED_MASS = -1;
	public static final double INFINITE_MASS = 0;

	protected Material mat;
	protected Vec2 vel;
	protected Vec2 force;
	
	public Body(Material mat) {
		
	}

	public abstract void draw(Graphics2D g);
	public abstract void moveBy(Vec2 v);
	public abstract double getMass();
	public abstract double getMassInv();
	
	public void update(double dt) {
		moveBy(vel.scaled(dt));
		vel.add(force.scaled(getMassInv()));
	}
	
	public void applyForce(Vec2 f) {
		force.add(f);
	}
	
	public void resolveCollision(Collision c, Body b) {
		double velAlongNormal = b.vel.subtracted(vel).dot(c.getNormal());
		if (velAlongNormal > 0)
			return;
		
		double restitution = Math.min(mat.restitution(), b.mat.restitution());
		double magnitude = (-(1 + restitution) * velAlongNormal) / (getMassInv() + b.getMassInv());
		
		Vec2 impulse = c.getNormal().scaled(magnitude);
		vel.subtract(impulse.scaled(getMassInv()));
		b.vel.add(impulse.scaled(b.getMassInv()));
	}
}
