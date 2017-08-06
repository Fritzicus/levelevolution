package com.levo.physics;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.Arrays;

public class Polygon extends Body {

	private Vec2 pos;
	private Vec2[] vertices;
	private Vec2[] normals; // Inner normals
	
	public Polygon(Vec2[] vertices, Material mat) {
		super(mat);
		this.vertices = vertices;
		calcNormals();
	}
	
	public Polygon(Vec2 pos, double radius, int numSides, Material mat) {
		super(mat);
		this.pos = pos;
		
		vertices = new Vec2[numSides];
		for (int i = 0; i < numSides; i++) 
			vertices[i] = new Vec2(i * 2 * Math.PI / numSides).scaled(radius);
		calcNormals();
	}

	private void calcNormals() {
		normals = new Vec2[vertices.length];
		
		Vec2 first = vertices[0];
		Vec2 prev = first;
		for (int i = 1; i < vertices.length; i++) {
			Vec2 vertex = vertices[i];
			normals[i] = vertex.subtracted(prev).cross(1).normalized();
			prev = vertex;
		}
		normals[0] = first.subtracted(prev).cross(1).normalized();
		System.out.println(Arrays.toString(normals));
	}
	
	public void draw(Graphics2D g) {
		g.setColor(mat.color());
		Vec2 first = vertices[0].added(pos);
		Vec2 prev = first;
		for (int i = 1; i < vertices.length; i++) {
			Vec2 vertex = vertices[i].added(pos);
			g.draw(new Line2D.Double(prev.x, prev.y, vertex.x, vertex.y));
			g.draw(new Line2D.Double(vertex.x, vertex.y, vertex.x + normals[i].x * 15, vertex.y + normals[i].y * 15));
			prev = vertex;
		}
		g.draw(new Line2D.Double(first.x, first.y, prev.x, prev.y));
		g.draw(new Line2D.Double(first.x, first.y, first.x + normals[0].x * 15, first.y + normals[0].y * 15));

	}

	public void moveBy(Vec2 v) {
		pos.add(v);
	}
	
	public void rotateBy(double angle) {
		for (Vec2 v : vertices)
			v.rotate(angle);
		for (Vec2 v : normals)
			v.rotate(angle);
	}

	public double area() {
		double area = 0;
		Vec2 first = vertices[0];
		Vec2 prev = first;
		for (int i = 1; i < vertices.length; i++) {
			Vec2 point = vertices[i];
			area += Math.abs(point.cross(prev) / 2);
			prev = point;
		}
		System.out.println(area);
		area += Math.abs(prev.cross(first) / 2);
		System.out.println(area);
		return area;
	}

	public Vec2 centerPoint() {
		return pos;
	}

	public boolean containsPoint(Vec2 point) {
		int[] x = new int[vertices.length];
		int[] y = new int[vertices.length];
		for (int i = 0; i < vertices.length; i++) {
			x[i] = (int) (vertices[i].x + pos.x);
			y[i] = (int) (vertices[i].y + pos.y);
		}
		
		java.awt.Polygon p = new java.awt.Polygon(x, y, vertices.length);
		return p.contains((int) point.x, (int) point.y);
	}

	public boolean isColliding(Body b) {
		Polygon p = (Polygon) b;
		for (int i = 0; i < normals.length; i++) {
			if (normals[i].dot(p.getSupport(normals[i].scaled(-1)).subtracted(vertices[i].added(pos))) > 0)
				return false;
		}
		for (int i = 0; i < p.normals.length; i++) {
			if (p.normals[i].dot(getSupport(p.normals[i].scaled(-1)).subtracted(p.vertices[i].added(p.pos))) > 0)
				return false;
		}
		return true;
	}
	
	public Vec2 getSupport(Vec2 dir) {
		double bestProjection = -Double.MAX_VALUE;
		Vec2 bestVec = null;
		for (int i = 0; i < vertices.length; i++) {
			double projection = vertices[i].dot(dir);
			if (projection > bestProjection) {
				bestProjection = projection;
				bestVec = vertices[i];
			}
		}
		return bestVec.added(pos);
	}
	
	public int getNumSides() {
		return vertices.length;
	}
	
	public Vec2 getVertex(int idx) {
		return vertices[idx].added(pos);
	}
	
	public Vec2 getNormal(int idx) {
		return normals[idx];
	}
}
