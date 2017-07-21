package com.levo.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import com.levo.physics.AABB;
import com.levo.physics.Vec2;

public class Block extends Entity {

	private AABB aabb;
	private Color color;
	
	public Block(Vec2 pos, int w, int h, Color color) {
		aabb = new AABB(pos, w, h);
		this.color = color;
	}
	
	public void draw(Graphics2D g) {
		aabb.draw(g, color);
	}

	public AABB getAABB() {
		return aabb;
	}
}
