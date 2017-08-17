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
 		int xHighest= Math.max((this.posX + this.sizeX - 1), (compare.posX + compare.sizeX -1));
 		int yHighest= Math.max((this.posY + this.sizeY - 1), (compare.posY + compare.sizeY -1));
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
}
