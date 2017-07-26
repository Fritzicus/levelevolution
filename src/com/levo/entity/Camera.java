package com.levo.entity;

import java.awt.Graphics2D;
import java.util.List;

import com.levo.physics.Vec2;

public class Camera {
	// The distance the tracked entity is allowed to get away before camera moves to keep it in frame
	public static final int X_DIST_TOLERANCE = 100;
	public static final int Y_DIST_TOLERANCE = 100;

	private Entity tracking;
	private Vec2 pos;
	
	public Camera(Entity tracking) {
		this.tracking = tracking;
		pos = tracking.centerPoint();
	}
	
	public void draw(Graphics2D g, List<Entity> entities) {
		double x = -pos.x + 200;
		double y = -pos.y + 200;
		g.translate(x, y);
		for (Entity e : entities) {
			e.draw(g);
		}
		tracking.draw(g);
		g.translate(-x, -y);
	}
	
	
	
	public void update() {
		Vec2 centerPoint = tracking.centerPoint();
		if (centerPoint.x > pos.x + X_DIST_TOLERANCE)
			pos.x = centerPoint.x - X_DIST_TOLERANCE;
		if (centerPoint.x < pos.x - X_DIST_TOLERANCE)
			pos.x = centerPoint.x + X_DIST_TOLERANCE;
		if (centerPoint.y > pos.y + Y_DIST_TOLERANCE)
			pos.y = centerPoint.y - Y_DIST_TOLERANCE;
		if (centerPoint.y < pos.y - Y_DIST_TOLERANCE)
			pos.y = centerPoint.y + Y_DIST_TOLERANCE;
	}
}
