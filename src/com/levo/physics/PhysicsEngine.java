package com.levo.physics;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;
import java.util.List;

public class PhysicsEngine {

	private List<Body> bodies;
	private Body bodyToAdd;
	
	public PhysicsEngine(List<Body> bodies) {
		this.bodies = bodies;
		bodyToAdd = null;
	}
	
	public void update(double dt) {
		if (bodyToAdd != null) {
			bodies.add(bodyToAdd);
		}
		List<Collision> collisions = new LinkedList<Collision>();
		for (int i = 0; i < bodies.size(); i++) {
			Body b = bodies.get(i);
			//b.applyForce(new Vec2(0, 1).scaled(b.getMass()));
			b.update(dt);
			for (int j = i + 1; j < bodies.size(); j++) {
				if (b.isColliding(bodies.get(j))) {
					collisions.add(new Collision(b, bodies.get(j)));
				}
			}
		}
		
		for (Collision c : collisions) {
			c.resolve();
		}
	}
	
	public void draw(Graphics2D g) {
		for (Body b : bodies) {
			b.draw(g);
			g.fill(new Ellipse2D.Double(b.centerPoint().x - 1.5, b.centerPoint().y - 1.5, 3, 3));
		}
	}
	
	public Body getBodyAtCoord(Vec2 coord) {
		for (Body b : bodies) {
			if (b.containsPoint(coord)) {
				return b;
			}
		}
		return null;
	}
	
	public void addBody(Body b) {
		bodies.add(b);
	}
}
