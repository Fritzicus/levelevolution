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
		int xHighest= Math.max((this.posX + this.sizeX - 1), (compare.posX + compare.sizeX -1));
		int yHighest= Math.max((this.posY + this.sizeY - 1), (compare.posY + compare.sizeY -1));
		

	    
	    int intersectSizeX = Math.max(0, (Math.min((this.posX + this.sizeX-1), (compare.posX + compare.sizeX-1))) - intersectPosX + 1);
	    int intersectSizeY = Math.max(0, (Math.min((this.posY + this.sizeY-1), (compare.posY + compare.sizeY-1))) - intersectPosY + 1);
	    
	    // System.out.println(  (this.posX + this.sizeX) + " " +  + (compare.posX + compare.sizeX) + " " +intersectPosX + " " + intersectSizeX + " " + intersectSizeY);
	    // System.out.println(  (this.posX + this.sizeX) + " " +  intersectSizeX + " " + intersectSizeY);
	    
	    if(intersectPosX > xHighest){
	    	if(intersectPosX > xHighest + 1)
	    		return -1;
	    	else if(intersectSizeY>0){
	    		return this.roomID;
	    	}else {
	    		return -1;
	    	}
	    }
	    if(intersectPosY > yHighest){
	    	if(intersectPosY > yHighest + 1)
	    		return 0;
	    	else if(intersectSizeX>0){
	    		return this.roomID;
	    	}else {
	    		return -1;
	    	}
	    }
	    
	    if(intersectSizeX < 1 || intersectSizeY < 1) // return 0 if there is no intersect  area
	    	return 0;	
	   
	    
	    /*
	    double totalIntersectArea = (double) (intersectSizeX * intersectSizeY);
	    double totalArea = (double) (sizeX * sizeY);
	   
	    double compareTotalArea = (double) (double) (compare.sizeX * compare.sizeY);
		/*
	    if(totalArea < compareTotalArea ) {
	    	//System.out.println(totalArea + " " + totalIntersectArea);
	    	return (totalArea / totalIntersectArea);
	    }else {
	    	//System.out.println(compareTotalArea + " " + totalIntersectArea);
	    	return (compareTotalArea / totalIntersectArea);
	    }
		
		*/
	    return -1;
		
		
	}
}
