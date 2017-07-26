package com.levo.physics;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Circle extends Body {
	private Vec2 pos;
	private double radius;

	public Circle(Vec2 pos, double radius, Material mat) {
		super(mat);
		this.pos = pos;
		this.radius = radius;
	}

	public void draw(Graphics2D g) {
		g.setColor(mat.color());
		g.draw(new Ellipse2D.Double(pos.x - radius, pos.y - radius, 2 * radius, 2 * radius));
	}

	public void moveBy(Vec2 v) {
		pos.add(v);
	}

	public double area() {
		return Math.PI * radius * radius;
	}

	public Vec2 centerPoint() {
		return pos;
	}

	public boolean containsPoint(Vec2 p) {
		return pos.distanceSquared(p) < radius * radius;
	}

	public boolean isColliding(Body b) {
		if (b instanceof Circle) {
			Circle other = (Circle) b;
			return other.pos.distanceSquared(pos) < (other.radius + radius) * (other.radius + radius);
		} else if (b instanceof AABB) {
			return true;
		}
		return false;
	}

	public Collision getCollision(Body b) {
		if (b instanceof Circle) {
			Circle other = (Circle) b;
			return new Collision(other.pos.subtracted(pos).normalized(), radius + other.radius - pos.distance(other.pos));
		} else if (b instanceof AABB) {
			AABB other = (AABB) b;
			Vec2 closestPoint = new Vec2();
			closestPoint.x = Math.max(other.getPosA().x, Math.min(pos.x, other.getPosB().x));
			closestPoint.y = Math.max(other.getPosA().y, Math.min(pos.y, other.getPosB().y));
			boolean inside = false;
			if (other.containsPoint(pos)) {
				inside = true;
				double xDiff1 = closestPoint.x - other.getPosA().x; 
				double xDiff2 = other.getPosB().x - closestPoint.x;
				double yDiff1 = closestPoint.y - other.getPosA().y;
				double yDiff2 = other.getPosB().y - closestPoint.y;
				if (Math.min(yDiff1, yDiff2) < Math.min(xDiff1, xDiff2)) {
					if (xDiff1 < xDiff2) {
						closestPoint.x -= xDiff1;
					} else {
						closestPoint.x += xDiff2;
					}
				} else {
					if (yDiff1 < yDiff2) {
						closestPoint.y -= yDiff1;
					} else {
						closestPoint.y += yDiff2;
					}
				}
			}
			double distance = radius - pos.distance(closestPoint);
			if (distance < 0) {
				return null;
			}
			return new Collision(closestPoint.subtracted(pos).normalized().scaled(inside ? -1 : 1), distance);
		}
		return null;
	}
	
	

}
