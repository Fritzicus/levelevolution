package com.levo.entity;

import java.awt.Color;
import java.awt.Graphics2D;

// TODO Hopefully deprecate this class when more serius physics comes along
public class AABB {
	
	private Vec2 posA;
	private Vec2 posB;
	
	public AABB(Vec2 posA, Vec2 posB) {
		this.posA = posA;
		this.posB = posB;
	}
	
	public void draw(Graphics2D g, Color c) {
		g.setColor(c);
		g.fillRect((int) posA.x, (int) posA.y, (int) (posB.x - posA.x), (int) (posB.y - posA.y));
	}
	
}
