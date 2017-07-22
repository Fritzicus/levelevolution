package com.levo.entity;

import java.awt.Color;
import java.util.ArrayList;

import com.levo.physics.Vec2;

public class GenerateLevel {
	private int levelX;
	private int levelY;
	public int[][] levelMap;
	public GenerateLevel() {
		this.levelX = 30;
		this.levelY = 30;
		this.levelMap = new int[levelX][levelY];
	}
	public GenerateLevel(int[] genome) {
		this.levelX = 10;
		this.levelY = 10;
		this.levelMap = new int[levelX][levelY];
	}
	public GenerateLevel(int levelX, int levelY) {
		this.levelX = levelX;
		this.levelY = levelY;
		this.levelMap = new int[levelX][levelY];
	}
	
	public ArrayList<Entity> generateTerrain() {
		ArrayList<Entity> levelGrid = new ArrayList<Entity>();
		for(int i = 0 ; i<levelX; i++) {
			for(int j = 0; j++ <levelY; j++ ) {
				levelMap[i][j]=0;
				levelMap[3][j]=1;
			}
		}
		
		for(int i = 0 ; i<levelX; i++) {
			levelMap[i][3]=1;
			levelMap[i][2]=1;
			for(int j = 0; j++ <levelY; j++ ) {
				if(levelMap[i][j]==1) {
					levelGrid.add(new Block(new Vec2(i*30, j*30), 30, 30, Color.GREEN));
				}
			}
		}
	
		return levelGrid;
	}
}
