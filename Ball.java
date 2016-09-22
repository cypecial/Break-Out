/**
 * @(#)Ball.java
 * this is a ball class that contains all the access specifiers for the ball, 
 * accessors methods and other useful methods that supports the main BreakOut Class.
 *
 */
import java.awt.Rectangle;
//A Rectangle specifies an area in a coordinate space 
//that is enclosed by the Rectangle object's upper-left 
//point (x,y) in the coordinate space, its width, and its height.
//@ http://docs.oracle.com/javase/7/docs/api/

public class Ball {
	public int bx, by, dx, dy, speed;
    public Ball(int bx, int by) {
    	this.bx = bx;
    	this.by = by;
    	this.speed = 2;
    	this.dx=speed;
    	this.dy=-speed;
    }
    public void reset(){//reset ball to initial state
    	this.speed=2;
    	this.bx=392;
    	this.by=485;
    }
    public void setSpeed(int s){
    	this.speed = s;
    }
    public void setBX(int x){
    	this.bx=x;
    }
    public void setDX(int x){
    	this.dx=x;
    }
    public void setBY(int y){
    	this.by=y;
    }
    public void setDY(int y){
    	this.dy=y;
    }
    public void move(int px,int size){
    	bx+=dx;
    	by+=dy;
    	//divides paddle into 3 sections
    	int px2 = px+size/3;
    	int px3 = px2+size/3;
    	int paddleR = px+size;
    	
    	if (bx <= 0) {
	        setDX(speed);
	     }
     	if (bx >= 785) {
        	setDX(-speed);
     	}
        if (by <= 0) {//increase speed everytime it hits the top
        	speed+=1;
        	if(speed>=5){
        		speed=5;
        	}
        	setDY(speed);
        }
        if (by+15<=515&&by+15>=500 && bx<=px3 && bx >=px2){
        	setDY(-speed);
        }
        else if (by+15<=515&&by+15>=500 && bx<=paddleR && bx>=px3){//hits right of paddle
        	setDY(-speed);
        	setDX(speed);
        }
        else if (by+15<=515&&by+15>=500 && bx<=px2 && bx>=px){//hits left of paddle
        	setDY(-speed);
        	setDX(-speed);
        }
    }
    Rectangle getRect(){ //gets rectangle of the ball (used for collision check)
    	return new Rectangle(bx,by,15,15);
    }
    
    
}