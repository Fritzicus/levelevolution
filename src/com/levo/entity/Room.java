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
	
	public double calculateOverlap(Room compare) {
		
		int intersectPosX= Math.min(this.posX, compare.posX);
		int intersectPosY= Math.min(this.posY, compare.posY);
		
		//System.out.println(intersectPosX + " " + this.posX + " " + compare.posX);
	    if(intersectPosX > Math.max((this.posX + this.sizeX), (compare.posX + compare.sizeX))) 
	    		return 0.0;
	    if(intersectPosY > Math.max((this.posY + this.sizeY), (compare.posY + compare.sizeY)))
    		    return 0.0;
	    
	    int intersectSizeX = (Math.max((this.posX + this.sizeX), (compare.posX + compare.sizeX))) - intersectPosX - 1;
	    int intersectSizeY = (Math.max((this.posY + this.sizeY), (compare.posY + compare.sizeY))) - intersectPosY - 1;
	    
	    System.out.println(  (this.posX + this.sizeX) + " " +  intersectSizeX + " " + intersectSizeY);
	    if (intersectSizeX < 0 || intersectSizeY < 0)
	    	return 0.0;
	    if(intersectSizeX < 1 || intersectSizeY < 1) // return 0 if there is no intersect  area
	    	return 0.0;	
	   
	    
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
	    return 1.0;
		
		
	}
}
