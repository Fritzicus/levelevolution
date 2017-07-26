package com.levo.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import com.levo.entity.Entity;
import com.levo.game.Camera;
import com.levo.physics.AABB;
import com.levo.physics.Body;
import com.levo.physics.Circle;
import com.levo.physics.Collision;
import com.levo.physics.Material;
import com.levo.physics.Vec2;
import com.sun.glass.events.KeyEvent;

public class PhysicsState extends GameState {

	private List<Body> bodies;
	private Camera cam;
	private Entity viewer;
	private Body selected;
	private Vec2 mouseCoord;
	
	public PhysicsState() {
		bodies = new ArrayList<Body>();
		bodies.add(new AABB(new Vec2(-200, 380), 800, 80, Material.IMMOVEABLE));
		bodies.add(new AABB(new Vec2(-280, 300), 80, 160, Material.IMMOVEABLE));
		bodies.add(new AABB(new Vec2(600, 300), 80, 160, Material.IMMOVEABLE));
		for (int i = 0; i < 10; i++) {
			bodies.add(new AABB(new Vec2(370 * Math.random(), 330 * Math.random()), 30, 30, Material.WOOD));
			bodies.add(new Circle(new Vec2(10 + 380 * Math.random(), 10 + Math.random() * 300), 10, Material.RUBBER));
		}
		
		viewer = new Entity() {
			private static final int SPEED = 150;
			
			private Vec2 pos = new Vec2(200, 200);
			private boolean[] kd = keyDown;

			public void draw(Graphics2D g) { }
			public void update(double dt) {
				if (kd[KeyEvent.VK_W])
					pos.add(new Vec2(0, -SPEED * dt));
				if (kd[KeyEvent.VK_A])
					pos.add(new Vec2(-SPEED * dt, 0));
				if (kd[KeyEvent.VK_S])
					pos.add(new Vec2(0, SPEED * dt));
				if (kd[KeyEvent.VK_D])
					pos.add(new Vec2(SPEED * dt, 0));
			}
			
			public Vec2 centerPoint() {
				return pos;
			}
		};
		cam = new Camera(viewer);
	}
	
	public void draw(Graphics2D g) {
		cam.activate(g);
		for (Body b : bodies) {
			b.draw(g);
		}
		if (selected != null) {
			g.setColor(Color.GREEN);
			g.fill(new Ellipse2D.Double(selected.centerPoint().x - 1.5, selected.centerPoint().y - 1.5, 3, 3));
			g.draw(new Line2D.Double(selected.centerPoint().x, selected.centerPoint().y, mouseCoord.x, mouseCoord.y));
		}
		cam.deactivate(g);
	}

	public void update(double dt) {
		cam.update(dt);
		viewer.update(dt);
		
		if (selected != null) {
			selected.applyForce(mouseCoord.subtracted(selected.centerPoint()).scaled(150));
		}
		
		for (Body b : bodies) {
			b.applyForce(new Vec2(0, 1).scaled(b.getMass()));
			b.update(dt);
			for (Body other : bodies) {
				if (b != other && b.isColliding(other)) {
					Collision c = b.getCollision(other);
					b.resolveCollision(c, other);
				}
			}
		}
	}
	
	public void mousePressed(MouseEvent e) {
		Vec2 v = cam.getCoordinate(e);
		for (Body b : bodies) {
			if (b.containsPoint(v)) {
				selected = b;
				mouseCoord = v;
				break;
			}
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		if (selected != null) {
			mouseCoord = cam.getCoordinate(e);
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		selected = null;
		mouseCoord = null;
	}
}
