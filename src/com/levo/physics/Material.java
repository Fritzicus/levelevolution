package com.levo.physics;

import java.awt.Color;

public enum Material {
	WOOD (5, 1, .05, new Color(130, 82, 1)),
	RUBBER (10, 1, .1, new Color(11, 38, 81)),
	IMMOVEABLE (0, .2, .6, Color.DARK_GRAY);

	private double density;
	private double restitution;
	private double staticFriction;
	private Color color;
	
	Material(double density, double restitution, double staticFriction, Color color) {
		this.density = density;
		this.restitution = restitution;
		this.staticFriction = staticFriction;
		this.color = color;
	}
	
	public double density() {
		return density;
	}
	
	public double restitution() {
		return restitution;
	}
	
	public double staticFriction() {
		return staticFriction;
	}
	
	public double dynamicFriction() {
		return 0.1 * staticFriction();
	}
	
	public Color color() {
		return color;
	}
	
	public static double combinedFriction(double f1, double f2) {
		return f1 + f2 / 2;
	}
}
