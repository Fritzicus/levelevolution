package com.levo.physics;

import java.awt.Color;

public enum Material {
	WOOD (5, .2, new Color(130, 82, 1)),
	RUBBER (10, .8, new Color(11, 38, 81)),
	IMMOVEABLE (0, .5, Color.DARK_GRAY);

	private double density;
	private double restitution;
	private Color color;
	
	Material(double density, double restitution, Color color) {
		this.density = density;
		this.restitution = restitution;
		this.color = color;
	}
	
	public double density() {
		return density;
	}
	
	public double restitution() {
		return restitution;
	}
	
	public Color color() {
		return color;
	}
}
