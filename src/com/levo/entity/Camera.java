package com.levo.entity;

import java.awt.Graphics2D;
import java.util.List;

import com.levo.physics.Vec2;

public class Camera {

	private Entity tracking;
	
	public Camera(Entity tracking) {
		this.tracking = tracking;
	}
	
	public void draw(Graphics2D g, List<Entity> entities) {
		Vec2 trackingCenter = tracking.centerPoint();
		double x = -trackingCenter.x + 200;
		double y = -trackingCenter.y + 200;
		g.translate(x, y);
		for (Entity e : entities) {
			e.draw(g);
		}
		tracking.draw(g);
		g.translate(-x, -y);
	}
	
}
