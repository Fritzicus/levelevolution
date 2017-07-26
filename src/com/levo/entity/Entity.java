package com.levo.entity;

import java.awt.Graphics2D;

import com.levo.physics.Vec2;

import graphics.Sprite;

public abstract class Entity {

	// TODO, find things to be abstracted into Entity class (physics? sprite?) If nothing is found, remove entity class
	
	protected Sprite sprite;
	
	public abstract void draw(Graphics2D g);
	public void update() {	}
	
	public abstract Vec2 centerPoint();
	
}
