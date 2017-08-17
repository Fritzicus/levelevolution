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
		int roomID = 1;
		int blockScaleFactor = 5;
		int[][] overlayMap = new int[levelX][levelY];
		
		ArrayList<Integer> adjacentRooms = new ArrayList<Integer>();
		ArrayList<Integer> simplifyRooms = new ArrayList<Integer>();
		

		ArrayList<ArrayList<Room>> roomList = new ArrayList<ArrayList<Room>>();

		for (int i = 0; i < levelX; i++) {
			for (int j = 0; j < levelY; j++) {
				levelMap[i][j] = 1;
				overlayMap[i][j] = 0;
			}
		}

		while (roomsCompleted < maxNumberOfRooms) {
			int sizeX = rand.nextInt(5) + 3; // Generates new room dimensions and placement at random
			int sizeY = rand.nextInt(3) + 1;
			int posX = rand.nextInt(levelX - 9) + 1;
			int posY = rand.nextInt(levelX - 5) + 1;
			int maxOverlap = 0;
			Room newRoom = new Room(posX, posY, sizeX, sizeY, roomID, 0); // instantiates a new room object
			adjacentRooms.clear();
			simplifyRooms.clear();
			//System.out.println("For room: " + roomID);
			
			for (ArrayList<Room> i : roomList) {
				for (Room j : i) {                                        // For loops checks to see if rooms are not overlapping
					int currentOverlap = j.calculateOverlap(newRoom);
					if (currentOverlap == -1) 
						maxOverlap = -1;
					if (maxOverlap != -1) 
						if(currentOverlap !=0)
							adjacentRooms.add(currentOverlap);
				}

			}
			if (maxOverlap > -1) {
				for (int i = 0; i < sizeX; i++) {
					for (int j = 0; j < sizeY; j++) {
						levelMap[i + posX][j + posY] = 0;
						overlayMap[i + posX][j + posY] = roomID;
					}
				}
				if (adjacentRooms.isEmpty()) { // if there is not an adjacent room, add new room cluster to arraylist
					roomList.add(new ArrayList<Room>());
					roomList.get(roomList.size()-1).add(newRoom);
				}else 
					for (int i = 0; i < roomList.size(); i++) 
						for (int j = 0; j < roomList.get(i).size(); j++) 
							for(int k : adjacentRooms) 
								if(roomList.get(i).get(j).getRoomID() == k) // checks to see if there are adjacent rooms
									if(!simplifyRooms.contains(i))
										simplifyRooms.add(i);

				if(!simplifyRooms.isEmpty()) {
					roomList.get(simplifyRooms.get(0)).add(newRoom);
				}
				
				if(simplifyRooms.size() > 1) {
					for(int i = 1; i<simplifyRooms.size();i++) {
						roomList.get(simplifyRooms.get(0)).addAll(roomList.get(simplifyRooms.get(i)));
						roomList.get(simplifyRooms.get(i)).clear();
					}

				}
				System.out.println("for RoomID: "+ roomID);
				for(int i : adjacentRooms)
					System.out.print(i + " ");
				
				System.out.println("");
				for(int i : simplifyRooms)
					System.out.print(i + " ");
				System.out.println("");
				adjacentRooms.clear();
				simplifyRooms.clear();
				roomsCompleted++;
				roomID++;

				
				/*for (ArrayList<Room> i : roomList) {
					for (Room j : i) {
						System.out.print(j.getRoomID()+" ");
					}
					System.out.println("");
				}
				*/
			} else {
				errors++;
			}
			if (errors > 100) { // prevents the room generation from potentially getting stuck in an infinite
									// loop.
				errors = 0;
				System.out.println("Lots of errors");
				roomsCompleted++;
			}
		}

			
		/// this segment is intended to simplify geometry to single blocks instead of
		/// multiple blocks
		/*
		for (ArrayList<Room> i : roomList) {
			for (Room j : i) {
				System.out.print(j.getRoomID()+" ");
			}
			System.out.println("");
		}
		*/
		for (ArrayList<Room> i : roomList) {
			for (Room j : i) {
				System.out.print(j.getRoomID()+" ");
			}
			System.out.println("");
		}
		
		for (int i = 0; i < levelX; i++) {
			for (int j = 0; j < levelY; j++) {
				String temp = String.format("%2d", overlayMap[j][i]);
				System.out.print("[" + temp + "]");
			}
			System.out.println("");
		}

		for (int i = 0; i < levelX; i++) {
			for (int j = 0; j < levelY; j++) {
				if (levelMap[i][j] == 1)
					levelGrid.add(new Block(new Vec2(i * blockScaleFactor, j * blockScaleFactor), blockScaleFactor,
							blockScaleFactor, Color.GREEN));
			}
		}
		return levelGrid;
	}
}
