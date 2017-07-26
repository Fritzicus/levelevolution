package com.levo.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import com.levo.physics.AABB;
import com.levo.physics.Material;
import com.levo.physics.Vec2;

// Represents a solid block that the player can jump and move around on
// Currently is just an entity wrapping an AABB, but may have more functionality later
public class Block extends Entity {

	private AABB aabb;
	private Color color;
	
	public Block(Vec2 pos, int w, int h, Color color) {
		aabb = new AABB(pos, w, h, Material.WOOD);
		this.color = color;
	}
	
	public void draw(Graphics2D g) {
		aabb.draw(g, color);
	}
	
	public AABB getAABB() {
		return aabb;
	}
	
	public Vec2 centerPoint() {
		return aabb.centerPoint();
	}
}
