package com.levo.entity;

import java.awt.Color;
import java.awt.Graphics2D;

public class Block extends Entity {

	private AABB aabb;
	private Color color;
	
	public Block(Vec2 pos, int w, int h, Color color) {
		aabb = new AABB(pos, new Vec2(pos.x + w, pos.y + h));
		this.color = color;
	}
	
	public void draw(Graphics2D g) {
		aabb.draw(g, color);
	}

}
