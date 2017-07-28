package com.levo.entity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
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

	public GenerateLevel(int levelX, int levelY) {
		this.levelX = levelX;
		this.levelY = levelY;
		this.levelMap = new int[levelX][levelY];
	}

	public ArrayList<Entity> generateTerrain() {
		ArrayList<Entity> levelGrid = new ArrayList<Entity>();
		Random rand = new Random();
		int roomsFactor = 20;
		int maxNumberOfRooms = (levelX * levelY) / roomsFactor; // This will be adjusted based on generation
		int roomsCompleted = 0;
		int errors = 0;
		int roomID = 0;
		int blockScaleFactor = 5;
		int [][]overlayMap = new int[levelX][levelY]; 
		
		ArrayList<Room> roomList = new ArrayList<Room>();
		for (int i = 0; i < levelX; i++) {
			for (int j = 0; j < levelY; j++) {
				levelMap[i][j] = 1;
				overlayMap[i][j]= 0;
			}
		}
		
		while (roomsCompleted < maxNumberOfRooms) {
			int sizeX = rand.nextInt(5) + 3;
			int sizeY = rand.nextInt(3) + 1;
			int posX = rand.nextInt(levelX - 9) + 1;
			int posY = rand.nextInt(levelX - 5) + 1;
			
			Room newRoom = new Room(posX, posY,sizeX, sizeY, roomID, 0);
			double maxOverlap = 0.0;
			for(Room i : roomList ) {
				double currentOverlap = i.calculateOverlap(newRoom);
				if(maxOverlap < currentOverlap){
					maxOverlap = currentOverlap;
				}
			}
			if(maxOverlap == 0.0 ) {
				roomsCompleted++;
				roomList.add(newRoom);
				roomID++;
				for (int i = 0; i < sizeX; i++) {
					for (int j = 0; j < sizeY; j++) {
						levelMap[i+posX][j+posY]=0;
						overlayMap[i+posX][j+posY]=roomID;
					}
				}
				
			}else {
				errors++;
			}
			if(errors > 100000) { // prevents the room generation from potentially getting stuck in an infinite loop.
				errors = 0;
				System.out.println("Lots of errors");
				roomsCompleted++;
			}
			// System.out.println(maxOverlap);
			maxOverlap = 0.0;
		}
		/// this segment is intended to simplify geometry to single blocks instead of
		/// multiple blocks

		
		for (int i = 0; i < levelX; i++) {
			for (int j = 0; j < levelY; j++) {
				String temp = String.format("%2d", overlayMap[j][i]);
				System.out.print("[" + temp + "]");
			}
			System.out.println("");
		}
		
		/*

		for (int i = 0; i < levelX; i++) {
			int adjacency = 0;
			for (int j = 0; j < levelY; j++) {
				if (levelMap[i][j] == 1)
					adjacency++;
				else {
					if (adjacency != 0)
						levelGrid.add(new Block(new Vec2(i * BlockScaleFactor, (j - adjacency) * BlockScaleFactor),
								BlockScaleFactor, BlockScaleFactor * (adjacency), Color.GREEN));
					adjacency = 0;
				}
			}
			if (adjacency > 1)
				levelGrid.add(new Block(new Vec2(i * BlockScaleFactor, 0), BlockScaleFactor,
						BlockScaleFactor * (adjacency + 1), Color.GREEN));
			adjacency = 0;
		}
        */
		// the original block implementation
		
		 for(int i = 0 ; i<levelX; i++) { 
			 for(int j = 0; j <levelY; j++ ) {
				 if(levelMap[i][j]==1) 
					 levelGrid.add(new Block(new Vec2(i*blockScaleFactor, j*blockScaleFactor), blockScaleFactor, blockScaleFactor, Color.GREEN)); 
			 }
		 }
		return levelGrid;
	}
}
