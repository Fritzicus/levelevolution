package com.levo.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import com.levo.physics.Vec2;

// Represents a solid block that the player can jump and move around on
public class Block extends Entity {
    
	private final Color color;
        
	public Block(Vec2 pos, int w, int h, Color color) {
                //Creates a new AABB object using the constructor of the superclass
		super(pos, w, h, ID.BLOCK);
		this.color = color;
	}
	
        @Override
	public void draw(Graphics2D g) {
		this.getAABB().draw(g, color);
	}
		
}
