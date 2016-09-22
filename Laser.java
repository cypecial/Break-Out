/**
 *laser.java
 * this is a laser class that contains all the access specifiers for the individual bullets shot, 
 * accessors methods and other useful methods that supports the main BreakOut Class.
 */

import java.awt.Rectangle;
public class Laser {
	public int laserX,laserY;
	public boolean hit;
	
    public Laser(int laserX) {
    	this.laserX=laserX;
    	this.laserY=500;
    }
    public void shoot(){
    	laserY--;
    }
    Rectangle getRect(){//get rectangle for individual bullets used for collison check
    	return new Rectangle(laserX,laserY,4,10);
    }
    
    
}