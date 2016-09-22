/**
 * @(#)Brick.java
 * this is a brick class that contains all the access specifiers for the individual bricks, 
 * accessors methods and other useful methods that supports the main BreakOut Class.
 */
import java.awt.Rectangle;//to check collisions
//A Rectangle specifies an area in a coordinate space 
//that is enclosed by the Rectangle object's upper-left 
//point (x,y) in the coordinate space, its width, and its height.
//@ http://docs.oracle.com/javase/7/docs/api/
import java.util.*;

public class Brick {
	public int x, y, powerX, powerY;
	public boolean destroyed,hasPowerUp,dropped;
	public String powerUp;
    public Brick(int x, int y) {
    	this.x = x;
    	this.y = y;
    	this.powerUp=null;
    	
    	destroyed = false;
    	dropped=false;
    	
    	if(hasPower()){
    		powerX=x+17;
    		powerY=y+25;
    		Random rand = new Random();
    		int pow = rand.nextInt(6);
    		if(pow==0){//low chance of getting life
    			powerUp = "life";
    		}
    		else if(pow==1 || pow==2){
    			powerUp = "speed";
    		}
    		else if(pow==3 || pow==4){
    			powerUp = "slow";
    		}
    		if(pow==5){
    			powerUp = "laser";
    		}
    	}
    }
    public boolean hasPower(){
    	//checks if brick has power
    	hasPowerUp=false;
    	Random rand = new Random();
    	int num = rand.nextInt(6);//one in six chance of getting power up
    	if(num==0){
    		hasPowerUp=true;
    	}
    	return hasPowerUp;
    }
    public void dropPower(){
    	powerY+=1;
    }
    public int getX(){
    	return x;
    }
    public int getY(){
    	return y;
    }
    public boolean isDestroyed(){
    	return destroyed;
    }
    public void setDestroyed(boolean destroyed){
    	this.destroyed = destroyed;
    }
    Rectangle getRect(){//get rectangle for brick
    	return new Rectangle(x,y,70,25);
    }
    Rectangle getPowerRect(){//get rectangle for power up 
    	return new Rectangle(powerX,powerY,35,35);
    }
    
    
}