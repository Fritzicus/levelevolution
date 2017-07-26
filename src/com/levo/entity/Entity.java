package com.levo.entity;

import java.awt.Graphics2D;

import com.levo.game.Drawable;
import com.levo.physics.Vec2;

// Top level entity class for anything which is displayed on screen and interacts with other entities (i.e. Player, Blocks, Enemies, Particles, etc.)
public abstract class Entity implements Drawable {

	// TODO, find things to be abstracted into Entity class (physics? sprite?) If nothing is found, remove entity class
	
	public abstract void draw(Graphics2D g);
	public void update(double dt) {	}
	
	public abstract Vec2 centerPoint();
}
