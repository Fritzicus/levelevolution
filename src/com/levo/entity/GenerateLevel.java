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
			for(int j = 0; j <levelY; j++ ) {
				levelMap[i][j]=0;
			}
		}
		
		/// This segment will generate the actual level blocks
		
		/***  THIS SEGMENT IS ONLY FOR TEST PURPOSES, WILL BE REMOVED AT A LATER TIME***/

		for(int k = 0; k <levelY; k++ ) {
			levelMap[0][k]=1;			
			levelMap[levelY-1][k]=1;
			levelMap[k][levelY-1]=2;
			levelMap[k][0]=2;
		}
		levelMap[1][0]=0;
		
		levelMap[2][2]=1;
		
		levelMap[3][3]=1;
		levelMap[3][4]=1;
		
		/*** THIS IS THE END OF THE TEST SEGMENT***/
		
		/// this segment is intended to simplify geometry to single blocks instead of multiple blocks
 		
		for(int i = 0 ; i<levelX; i++) {
			int adjacency = 0;			
			for(int j = 0; j <levelY; j++ ) {
				if(levelMap[i][j]==1) 
					adjacency++;
				else {
					if(adjacency != 0)
						levelGrid.add(new Block(new Vec2(i*30, (j-adjacency)*30), 30, 30 * (adjacency), Color.GREEN));	
					adjacency = 0;
				}
			}
			if(adjacency > 1 ) 
				levelGrid.add(new Block(new Vec2(i*30, 0), 30, 30 * (adjacency + 1), Color.GREEN));		
			adjacency = 0;
		}
		
		// the original block implementation
		for(int i = 0 ; i<levelX; i++) {			
			for(int j = 0; j <levelY; j++ ) {			
				if(levelMap[i][j]==2)
					levelGrid.add(new Block(new Vec2(i*30, j*30), 30, 30, Color.RED));
			}
		}

		return levelGrid;
	}
}
