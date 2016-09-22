/**
 * @Paddle.java
 * this is a paddle class that contains all the access specifiers for the paddle, 
 * accessors methods and other useful methods that supports the main BreakOut Class.
 */

import java.awt.Rectangle;
public class Paddle {
	public int mx,my,px,size;
    public Paddle(int mx) {
    	this.px=375;
    	this.size=70;
    }
    public void move(int mx){
    	px=mx-size/2;
    	if(px>=745){
        	px=745;
        }
		else if(px<=0){
        	px=0;
        }
    }
    public void reset(){
    	px = 375;
    }
    Rectangle getRect(){
    	return new Rectangle(px,500,70,15);
    }
    
    
}