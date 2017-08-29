package com.levo.entity;

public class Room {
	private int posX;
	private int posY;
	private int sizeX;
	private int sizeY;
	private int roomID;
	
	public Room(int posX, int posY, int sizeX, int sizeY, int roomID, int color) {
		this.posX = posX;
		this.posY = posY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.roomID = roomID;
		
	}
	
	public int getRoomID() {
		return this.roomID;
	
	}
	
	public int calculateOverlap(Room compare) {
		
		int intersectPosX= Math.max(this.posX, compare.posX);
		int intersectPosY= Math.max(this.posY, compare.posY);
		int xRight  = Math.min((this.posX + this.sizeX-1), (compare.posX + compare.sizeX-1));
		int yBottom = Math.min((this.posY + this.sizeY-1), (compare.posY + compare.sizeY-1));
	    int intersectSizeX = Math.max(0, xRight - intersectPosX + 1);
	    int intersectSizeY = Math.max(0, yBottom - intersectPosY + 1);
	    
	    
	    if(intersectSizeX > 0 && intersectSizeY > 0)
	    	return -1;
	    if(intersectSizeX < 1 && intersectSizeY < 1)
	    	return 0; 
	     if(intersectSizeX > 0) 
	    	if(intersectPosY == yBottom + 1)
	    		return this.roomID;
	    	else
	    		return 0;
	    else 
	    	if(intersectPosX == xRight + 1)
	    		return this.roomID;
	    	else
	    		return 0;

	}
	
	public int getDistance(Room compare) {
		int manhattenDistance = 0;
		manhattenDistance = Math.abs((this.posX + this.sizeX/2) - (compare.posX + compare.sizeX/2)) + Math.abs((this.posY + this.sizeY/2) - (compare.posY + compare.sizeY/2) );
		
		return manhattenDistance;
	}
	
	public int[][] drawPath(Room compare, int[][]levelMap){
		int xOffset = (compare.posX + compare.sizeX/2) -  (this.posX + this.sizeX/2);
		int yOffset = (compare.posY + compare.sizeY/2) -  (this.posY + this.sizeY/2) ;

		int xCurrent = (this.posX + this.sizeX/2); 
		int yCurrent = (this.posY + this.sizeY/2);
		/*
		if(xOffset == 0 )
			if(yOffset> 0) 
				for(int i=0; i < yOffset; i++) 
					levelMap[xCurrent][yCurrent+i]=0;				
			else 
				for(int i=0; i > yOffset; i--) 
					levelMap[xCurrent][yCurrent+i]=0;				
		else if(yOffset == 0)
			if(xOffset> 0) 
				for(int i=0; i < xOffset; i++) 
					levelMap[xCurrent+i][yCurrent]=0;				
			else 
				for(int i=0; i > xOffset; i--) 
					levelMap[xCurrent+i][yCurrent]=0;	
			
		*/
		
		while(xOffset!=0) {
			if(xOffset > 0) {
				levelMap[xCurrent+xOffset][yCurrent]=0;
				xOffset--;
			}else {
				levelMap[xCurrent+xOffset][yCurrent]=0;
				xOffset++;
			}				
		}
		while(yOffset!=0) {
			if(yOffset > 0) {
				levelMap[xCurrent][yCurrent+yOffset]=0;
				yOffset--;
			}else {
				levelMap[xCurrent][yCurrent+yOffset]=0;
				yOffset++;
			}	
		}
		return levelMap;
	}
}
