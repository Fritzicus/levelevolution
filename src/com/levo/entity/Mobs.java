package com.levo.entity;

import com.levo.physics.Collision;
import java.util.ArrayList;

import com.levo.physics.Vec2;
import java.util.List;

//Players and Enemies
public abstract class Mobs extends Entity {
    
        //The amount of hitpoints a mob has.
        private int hitPoints;
        //2D Vector representing the velocity of a Mob
        private final Vec2 vel;
        
        // Array containing every mob
        private static ArrayList<Mobs> mobs = new ArrayList();
        
        private boolean onGround = false;
        
        private boolean onLeftWall = false;
    
        private boolean onRightWall = false;
    
        public Mobs (Vec2 pos, int w, int h, ID id, int hp) {
            super(pos, w, h, id);
            this.hitPoints = hp;
            this.vel = new Vec2(0,0);
        }
    
        public abstract void update();
    
        public void updateAll() {
            for (int i = 0; i < mobs.size(); i++){
                mobs.get(i).update();
            }
        }
    
        public abstract void attack();
    
        public abstract void takeDamage();
        //Functions only intended for the Enemy and Player classes
        protected void removeMob(int inIndex) {
            mobs.remove(inIndex);
        }
    
        protected void addMob(Mobs inMob){
            mobs.add(inMob);
        }
    
        protected Vec2 getVel() {
            return vel;
        }
        
        protected boolean isOnGround() {
            return onGround;
        }
    
        protected boolean isOnLeftWall() {
            return onLeftWall;
        }
    
        protected boolean isOnRightWall() {
            return onRightWall;
        }
        
        protected int getHP () {
            return this.hitPoints;
        }
        
        protected void setHP (int inHP) {
            this.hitPoints = inHP;
        }
        
        public void handleCollisions(List<Entity> blocks) {
		// Make these checks false and set them to true they are detected 
		onGround = false;
		onLeftWall = false;
		onRightWall = false;
            for (Entity e : blocks) {
                Block b = (Block) e; 
                // TODO check entity type before casting and doing special routines
                // Check if block is colliding with player
                if (b.getAABB().isColliding(this.getAABB())) {
                    Collision c = b.getAABB().getCollision(this.getAABB());
                    
                    // Check collision normal to know what direction collision occured in
                    if (c.getNormal().y == -1) {
                        this.getVel().y = 0;
                        onGround = true;
                    } else if (c.getNormal().y == 1) {
                        this.getVel().y = .1;
                    } else if (c.getNormal().x == 1) {
                        onLeftWall = true;
                    } else if (c.getNormal().x == -1) {
                        onRightWall = true;
                    }
                    
                    if (c.getDepth() >= 0) {
                        // Resolve collision by moving player back in the direction of the normal
                        this.getAABB().addVec(c.getNormal().scaled(c.getDepth()));
                    }
                }
            }
	}
}
