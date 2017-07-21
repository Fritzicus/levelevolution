package com.levo.entity;

import java.awt.Graphics2D;

public abstract class Entity {

	// TODO, find things to be abstracted into Entity class (physics? sprite?) If nothing is found, remove entity class
	
	public abstract void draw(Graphics2D g);
	public void update() {	}
}
