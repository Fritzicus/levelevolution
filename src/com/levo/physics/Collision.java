package com.levo.physics;

// Collision contains information about a collision event that can be used to resolve collisions
public class Collision {
	public static final double POSITIONAL_CORRECTION = 0.8;
	public static final double SLOP = 0.01;
	
	// Collision normal is perpendicular to the colliding surfaces, giving the direction collision occured in
	private Vec2 normal;
	// Depth is the distance along the collision normal that two colliding objects overlap
	private double depth;
	public Body a, b;
	
	public Collision(Body a, Body b) {
		this.a = a;
		this.b = b;
		
		calcCollision();
	}
	
	public Collision(Vec2 normal, double depth) {
		this.normal = normal;
		this.depth = depth;
	}
	
	public void resolve() {
		if (depth <= 0)
			return;
		Vec2 relativeVel = b.vel.subtracted(a.vel);
		double velAlongNormal = relativeVel.dot(normal);
		if (velAlongNormal > 0)
			return;
		
		double restitution = Math.min(a.mat.restitution(), b.mat.restitution());
		double magnitude = (-(1 + restitution) * velAlongNormal) / (a.getMassInv() + b.getMassInv());
		
		Vec2 impulse = normal.scaled(magnitude);
		a.vel.subtract(impulse.scaled(a.getMassInv()));
		b.vel.add(impulse.scaled(b.getMassInv()));
		
		
		Vec2 tangent = relativeVel.subtracted(normal.scaled(normal.dot(relativeVel))).normalized();
		double velAlongTangent = relativeVel.dot(tangent);
		double tanMagnitude = (-(1 + restitution) * velAlongTangent) / (a.getMassInv() + b.getMassInv());
		double friction = Material.combinedFriction(a.getMaterial().staticFriction(), b.getMaterial().staticFriction());
		Vec2 frictionImpulse = null;
		if (Math.abs(tanMagnitude) < magnitude * friction) {
			frictionImpulse = tangent.scaled(tanMagnitude);
		} else {
			friction = Material.combinedFriction(a.getMaterial().staticFriction(), b.getMaterial().staticFriction());
			frictionImpulse = tangent.scaled(- magnitude * friction);
		}
		a.vel.subtract(frictionImpulse.scaled(a.getMassInv()));
		b.vel.add(frictionImpulse.scaled(b.getMassInv()));
	
		if (depth > SLOP) {
			Vec2 positionalCorrection = normal.scaled((depth - SLOP) * POSITIONAL_CORRECTION / (a.getMassInv() + b.getMassInv()));
			a.moveBy(positionalCorrection.scaled(-a.getMassInv()));
			b.moveBy(positionalCorrection.scaled(b.getMassInv()));
		}
	}
	
	private void calcCollision() {
		if (a instanceof AABB && b instanceof AABB) {
			AABBvsAABB((AABB) a, (AABB) b);
		} else if (a instanceof Circle && b instanceof AABB) {
			CirclevsAABB((Circle) a, (AABB) b);
		} else if (a instanceof AABB && b instanceof Circle) {
			Body temp = a;
			a = b;
			b = temp;
			CirclevsAABB((Circle) a, (AABB) b);
		} else if (a instanceof Circle && b instanceof Circle) {
			CirclevsCircle((Circle) a, (Circle) b);
		}
	}
	
	private void AABBvsAABB(AABB a, AABB b) {
		// Calculates x and y overlap between both AABBs
		double xOverlap = Math.min(b.getPosB().x - a.getPosA().x, a.getPosB().x - b.getPosA().x);
		double yOverlap = Math.min(b.getPosB().y - a.getPosA().y, a.getPosB().y - b.getPosA().y);

		// The smaller overlap is the axis the collision occurred on.
		if (xOverlap > yOverlap) {
			// Collision occurred in Y axis, now check if top or bottom
			depth = yOverlap;
			if (a.getPosA().y > b.getPosA().y) {
				normal = new Vec2(0, -1); 
			} else {
				normal = new Vec2(0, 1);
			}
		} else {
			// Collision occurred in X axis, now check if left or right
			depth = xOverlap;
			if (a.getPosA().x > b.getPosA().x) {
				normal = new Vec2(-1, 0);
			} else {
				normal = new Vec2(1, 0);
			}
		}
	}
	
	private void CirclevsAABB(Circle a, AABB b) {
		Vec2 pos = a.getPos();
		double radius = a.getRadius();
		Vec2 closestPoint = new Vec2();
		closestPoint.x = Math.max(b.getPosA().x, Math.min(pos.x, b.getPosB().x));
		closestPoint.y = Math.max(b.getPosA().y, Math.min(pos.y, b.getPosB().y));
		boolean inside = false;
		if (b.containsPoint(pos)) {
			inside = true;
			double xDiff1 = closestPoint.x - b.getPosA().x; 
			double xDiff2 = b.getPosB().x - closestPoint.x;
			double yDiff1 = closestPoint.y - b.getPosA().y;
			double yDiff2 = b.getPosB().y - closestPoint.y;
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
		depth = radius - pos.distance(closestPoint);
		if (depth <= 0)
			return;
		normal = closestPoint.subtracted(pos).normalized().scaled(inside ? -1 : 1); 
	}
	
	private void CirclevsCircle(Circle a, Circle b) {
		normal = b.getPos().subtracted(a.getPos()).normalized();
		depth = a.getRadius() + b.getRadius() - a.getPos().distance(b.getPos());
	}
	
	public Vec2 getNormal() {
		return normal;
	}
	
	public double getDepth() {
		return depth;
	}
}
