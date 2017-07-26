package com.levo.game;

import java.awt.Color;
import java.util.ArrayList;

import com.levo.entity.Block;
import com.levo.entity.Entity;
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
			for(int j = 0; j <levelY; j++ ) {
				levelMap[i][j]=0;
				
			//	levelMap[3][j]=1;
			}
		}
		
		for(int j = 0; j <levelY; j++ ) {
			levelMap[0][j]=1;
			levelMap[j][0]=1;
			levelMap[levelX-1][j]=2;
			levelMap[j][levelY-1]=2;
		}

		levelMap[0][1]=0;
		for(int i = 0 ; i<levelX; i++) {
			
			for(int j = 0; j <levelY; j++ ) {
				if(levelMap[i][j]==1)
					levelGrid.add(new Block(new Vec2(j*30, i*30), 30, 30, Color.GREEN));				
				if(levelMap[i][j]==2)
					levelGrid.add(new Block(new Vec2(j*30, i*30), 30, 30, Color.RED));
			}
		}
	
		return levelGrid;
	}
}
