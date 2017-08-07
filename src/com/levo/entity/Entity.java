package com.levo.entity;

import java.awt.Graphics2D;

import com.levo.physics.Vec2;
import com.levo.physics.AABB;

// Top level entity class for anything which is displayed on screen and interacts with other entities (i.e. Player, Blocks, Enemies, Particles, etc.)
public abstract class Entity {

        //Each entity's coordinates and size are represented by this AABB
	private final AABB aabb;
        //The type of the entity as an ID
        private ID type;
	
        //Constructor is only accessible by its subclasses
        protected Entity (Vec2 pos, int w, int h, ID inType) {
            this.aabb = new AABB(pos, w, h);
            this.type = inType;
        }
        
        public abstract void draw(Graphics2D g);
        
	//Returns the centerpoint of the enitty
        public Vec2 centerPoint() {
		return this.aabb.centerPoint();
	}
        
        public AABB getAABB() {
		return this.aabb;
	}
        
	public ID getID(){
            return this.type;
        }
        
        public void setID(ID inType) {
            this.type = inType;
        }
        
}
