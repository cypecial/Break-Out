/**
 * @(#)BreakOut.java
 *
 * this is a simple game of classic break out.
 * the goal is to destroy all the bricks
 * pick up power ups to increase gaming experience
 *
 * @Yiping Che
 */


import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*; 
import java.io.*; 
import javax.imageio.*;

public class BreakOut extends JFrame implements ActionListener{
    JButton cButton = new JButton("Center");
    javax.swing.Timer myTimer;
	GamePanel game= new GamePanel();
	JMenuBar menuBar;
	JMenuItem newGame,mainMenu,instructions,endGame;
	JMenu menu;
	
    public BreakOut() {
		super("Break Out");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,625);//screen size
		setLayout(new BorderLayout());
		
		//top menu bar
		menu = new JMenu("Game");
		
		newGame = new JMenuItem("New Game");
		mainMenu = new JMenuItem("Main Menu");
		instructions = new JMenuItem("Instructions");
		endGame = new JMenuItem("End Game");
		
		newGame.addActionListener(this);
		mainMenu.addActionListener(this);
		instructions.addActionListener(this);
		endGame.addActionListener(this);
		
		menu.add(newGame);
		menu.add(mainMenu);
		menu.add(instructions);
		menu.add(endGame);
		
		menuBar = new JMenuBar();
		menuBar.add(menu);
		
		add(game, BorderLayout.CENTER);
		add(menuBar,BorderLayout.NORTH);
		
		myTimer = new javax.swing.Timer(10,this); //trigger every 10 ms
		myTimer.start();
		setVisible(true);
		setResizable(false);
		
		
    }

	public void actionPerformed(ActionEvent evt){
		Object source = evt.getSource();
		
		if(source==myTimer){
			game.gameScreens(); //decides which page/screen the game is on (user input)
		}
		if(source==newGame){
			game.myScreen = "game"; //game screen
			game.restart();
		}
		if(source==mainMenu){
			game.myScreen = "main"; //main page
		}
		if(source==instructions){
			game.myScreen = "instructions";  //instructions page
		}
		if(source==endGame){
			System.exit(0); //quit
		}
		game.repaint();
	}

    public static void main(String[] arguments)throws IOException {
		BreakOut frame = new BreakOut();
    }
}

class GamePanel extends JPanel implements MouseMotionListener, MouseListener, KeyListener{
	private int menuSelect, barx,bary,mx,my,lives,speedCD, score, laserX, laserY, laserCD, laserPCD; //laserCD = time interval between bullets
																									// laserPCD = count down before power up runs out
	private boolean start, win, lose, hasPower,hasSpeed, hasLaser, shoot;//hasSpeed & hasLaser used for power ups
	public boolean inGame = true;
	private boolean []keys;
	
	String myScreen = "main";
	int wait=0;
	
	//images
	BufferedImage main = null;
	BufferedImage instructionPic = null;
	BufferedImage back = null;
	
	BufferedImage gameSel = null;
	BufferedImage instrucSel = null;
	BufferedImage exitSel = null;
	
	BufferedImage barPic = null;
	BufferedImage paddlePic = null;
	BufferedImage ballPic = null;
	BufferedImage brickPic = null;
	BufferedImage winPic = null;
	BufferedImage gameOverPic = null;
	
	BufferedImage lifePic = null;
	BufferedImage speedPic = null;
	BufferedImage slowPic = null;
	BufferedImage laserPic = null;
		
	Ball ball = new Ball(392,485); //new ball
	Paddle paddle = new Paddle(400); //new paddle
	Brick[] bricks = new Brick[56]; //set bricks
	ArrayList<Laser> myLasers = new ArrayList <Laser>();
	
	public GamePanel(){
		addMouseMotionListener(this);
		addMouseListener(this);
		keys=new boolean[KeyEvent.KEY_LAST+1];
		addKeyListener(this);
		
		try{ //load all images
			main = ImageIO.read(new File ("main.png"));
			instructionPic = ImageIO.read(new File ("instructions.png"));
			back = ImageIO.read(new File ("back.png"));
			
			gameSel = ImageIO.read(new File ("gameSelected.png"));
			instrucSel = ImageIO.read(new File ("instructionSelected.png"));
			exitSel = ImageIO.read(new File ("exitSelected.png"));
			
			barPic = ImageIO.read(new File ("bar.png"));
			paddlePic = ImageIO.read(new File ("paddle.png"));
			ballPic = ImageIO.read(new File ("ball.png"));
			brickPic = ImageIO.read(new File ("brick.png"));
			winPic = ImageIO.read(new File ("win.png"));
			gameOverPic = ImageIO.read(new File ("gameover.png"));
			
			lifePic = ImageIO.read(new File ("life.png"));
			speedPic = ImageIO.read(new File ("speed.png"));
			slowPic = ImageIO.read(new File ("slow.png"));
			laserPic = ImageIO.read(new File ("laser.png"));
		}
		catch (IOException e){
		}
		/*menu selection (user input) to decide game screen
		 *0 = new game
		 *1 = instructions
		 *2 = exit*/
		
		menuSelect = 0;
		
	    barx = 375;
        bary = 500;
        mx = 400;
        my = 500;
        
        laserY=500;
        
        speedCD=0;
        laserPCD=0;
        laserCD=0;
        
        score = 0;	
        restart();
        setSize(800,600);
	}
	public void gameScreens(){
		//controls current page/screen the game is on
		if(myScreen.equals("game")){
			move();
		}
		w
		else if(myScreen.equals("instructions")){
			instructions();
		}
	}
	public void mainMenu(){
		//control selections in the main menu
		if(wait>0){//cooldown between presses, so doesn't spam key
			wait--;
		}
		if(wait==0){
			//up down arrow keys to choose options
			if(keys[KeyEvent.VK_DOWN]){
				menuSelect++;
				if(menuSelect>=2){
					menuSelect=2;
				}
				wait=10;
			}
			if (keys[KeyEvent.VK_UP]){
				menuSelect--;
				if(menuSelect<=0){
					menuSelect=0;
				}
				wait=10;
			}
			if(keys[KeyEvent.VK_ENTER]){ //enter to select
				if(menuSelect==0){
					myScreen = "game";
				}
				else if(menuSelect == 1){
					myScreen = "instructions";
				}
				else if(menuSelect ==2 ){
					System.exit(0);
				}
			}
			

		}
	}
	public void instructions(){
		if (keys[KeyEvent.VK_ESCAPE]){
			myScreen = "main";
		}
	}
	public void restart(){ //reset all game components (new game)
		start=false;
		
    	int index = 0;
        for(int i=0;i<7;i++){
        	for(int j=0;j<8;j++){
        		bricks[index] = new Brick(j*80+80,i*50+30);//brick location and space intervals
        		index++;
        	}
        }
        ball.reset();
        paddle.reset();
        lives = 3;
        speedCD=0;
        laserPCD=0;
        laserCD=0;
        score = 0;
        win = false;
        lose = false;
        inGame=true;
        hasSpeed=false;
		hasLaser=false;
    }
	public void addNotify(){
		super.addNotify();
		requestFocus();
	}
	public void setLaserX(int x){
		laserX=x;
	}
	public void move(){
		if(start==false){//ball is linked with paddle until click to start
			ball.bx=mx-8;
			paddle.move(mx);
			int rBorder = 795-paddle.size/2;
			if(paddle.px>=795-paddle.size){
	        	paddle.px=795-paddle.size;
	        	ball.bx=rBorder;
	        }
			else if(paddle.px<=0){
	        	paddle.px=0;
	        	ball.bx=paddle.size/2-8;
	        }
	        
		}
		else if(start==true){
			ball.move(paddle.px,paddle.size);
			paddle.move(mx);
			if(hasSpeed==true){
				speedCollide();//speedCollide used for speed buff
							// ball goes through bricks, destroying them on its way
			}
			checkCollide();//normal ball-brick collision, determine which direction the ball bounces back at
			
			if(paddle.px>=795-paddle.size){//prevents paddle from going off screen
	        	paddle.px=795-paddle.size;
	        }
			if(ball.by>=560){//if ball hits bottom
				lives-=1;
				paddle.reset();
				ball.reset();
				mx=375;
				start=false;
				hasSpeed=false;
				speedCD=0;
				laserCD=0;
				laserPCD=0;
				for(int i=0;i<56;i++){
					if(bricks[i].destroyed){
						bricks[i].dropped=true; //remove all dropping power ups upon losing a life
						bricks[i].powerX=-50;   
						bricks[i].powerY=-50;
					}
				}
				if(lives==0){
					lose = true;
					endGame();
				}
			}
			for(int i=0;i<56;i++){
				if(bricks[i].destroyed && bricks[i].hasPowerUp){
					bricks[i].dropPower(); //checks for powerUps upon destroying a brick and drop power up
				}			
			}	
		}
		if(hasLaser){
			if(shoot){
				for(int i=0;i<myLasers.size();i++){
					myLasers.get(i).shoot();
					laserCollide(myLasers.get(i));//checks for laser collision with bricks
				}
				
				
			}				
		}	
		if(speedCD>0){//cooldown for speed (time limit of powerUp avaliable)
    		speedCD--;
    	}
    	if(laserPCD>0){//cooldown for laser powerUp
    		laserPCD--;
    	}
    	if(laserCD>0){//cooldown for bullet shot by laser powerUp (time interval between each shot)
    		laserCD--;
    	}
    	if(speedCD==0){
    		hasSpeed=false;
    	}
	
		
	}
	public void endGame(){
		inGame=false;
	}
	public boolean PowerCollide(Brick brick){
		//check if user picks up power up
		boolean collide = false;
		if(brick.getPowerRect().intersects(paddle.getRect())){
			collide=true;
		}
		return collide;
	}
	public void laserCollide(Laser myLaser){
		// check if laser hits brick
		for(int j=0;j<56;j++){
			if(!bricks[j].isDestroyed()){
				if(myLaser.getRect().intersects(bricks[j].getRect())){
					myLasers.remove(myLaser);
					bricks[j].setDestroyed(true);
				}
			}				
		}
		
	}
	public void speedCollide(){
		// speed power up collision, neglects direction which it bounces back
		int count=56;
		for(int i=0;i<56;i++){
			if(bricks[i].isDestroyed()){
				count--;
			}
            if ((ball.getRect()).intersects(bricks[i].getRect())) {
                if (!bricks[i].isDestroyed()) {
                	bricks[i].setDestroyed(true);
                }
            }
		}
		if(count==0){
			win=true;
			endGame();
			
		}
	}
	public void checkCollide(){
		//normal collison between ball and brick
		int count=56;
		score=0;//keep count in score
		for(int i=0;i<56;i++){
			if(bricks[i].isDestroyed()){
				score+=10;
				count--;//counts bricks left
			}
			if(PowerCollide(bricks[i])){//if power collides with paddle
			//determines what each power up do
				if(bricks[i].powerUp=="life"){	                    	
	            	lives++;
	            	if(lives>=5){
	            		lives=5;
	            	}
	            }
	            else if(bricks[i].powerUp=="speed"){
	            	ball.setSpeed(5);
	            	hasSpeed=true;
	            	speedCD=200;
	            }
	            else if(bricks[i].powerUp=="slow"){
	            	ball.setSpeed(2);
   				}
   				else if(bricks[i].powerUp=="laser"){   					
   					hasLaser=true;
   					laserPCD=500;
   				}
   				bricks[i].dropped=true;
   				bricks[i].powerX=-50;
				bricks[i].powerY=-50;
			}
            if ((ball.getRect()).intersects(bricks[i].getRect())) {
                if (!bricks[i].isDestroyed()) {
                	//postion of ball
                	int ballL = ball.bx;
			        int ballT = ball.by;
			        int ballR = ballL+15;
			        int ballB = ballT+15;
			        
                	//the position of the ball that hit the brick to determine the direction it bounces back 	
                	
                    if (bricks[i].getRect().contains(ballR+1, ballT)) {//if the rect of the brick has the point 
                        ball.setDX(-ball.speed);					  //hits top right portion of the ball - left of brick
                    }
                    else if(bricks[i].getRect().contains(ballR+1,ballB)){//hits bottom right of ball
                    	ball.setDX(-ball.speed);
                    }
					else if(bricks[i].getRect().contains(ballL-1,ballB)){//hits bottom left of ball - top right of brick
						ball.setDX(ball.speed);
					}
                    else if (bricks[i].getRect().contains(ballL-1, ballT)) {//hits top left portion of the ball - right of brick
                        ball.setDX(ball.speed);
                    }

                    if (bricks[i].getRect().contains(ballL, ballT-1)) {//hits top left portion of the ball - bottom of brick
                        ball.setDY(ball.speed);
                    }
					else if(bricks[i].getRect().contains(ballR,ballT-1)){//hits top right portion of the ball - bottom of brick
						ball.setDY(ball.speed);
					}
                    else if (bricks[i].getRect().contains(ballL, ballB+1)) {//hits bottom left of the ball - top of brick
                        ball.setDY(-ball.speed);
                    }
                    else if(bricks[i].getRect().contains(ballR,ballB+1)){//hits bottom right of the ball - top left of brick
                    	ball.setDY(-ball.speed);
                    }
                    bricks[i].setDestroyed(true);
                    
                }
				
			}
		}
		if(count==0){//no more bricks left in game
			win=true;
			endGame();
			
		}
	}
	//-------------MouseListener ------------------------------------------------
	
	public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}   
    	 
    public void mouseClicked(MouseEvent e){
    	start=true; //release ball from resting position
    	if(hasLaser && laserCD==0 && laserPCD>0){//if has laser power up
    		shoot=true;
    		setLaserX(paddle.px);
    		Laser myLaserL = new Laser(laserX+5); //laser on each side of paddle
    		Laser myLaserR = new Laser(laserX+paddle.size-5);
    		myLasers.add(myLaserL);
    		myLasers.add(myLaserR);
    		laserCD=50;//delay in shooting so doesn't spam laser
    		
    	}
	}  
    public void mousePressed(MouseEvent e){}
    
	//------------Mouse Motion Listener ------------------------------------------
	
	public void mouseDragged(MouseEvent e){}
    public void mouseMoved(MouseEvent e){
    	mx = e.getX();
		my = e.getY();
	}
    
    //------------KeyListener-----------------------------------------------------
    public void keyTyped(KeyEvent e){}
    
    public void keyPressed(KeyEvent e){
    	keys[e.getKeyCode()]=true;
    }
    public void keyReleased(KeyEvent e){
    	keys[e.getKeyCode()]=false;
    }
    
    
	public void paintComponent(Graphics g){
		if(myScreen.equals("game")){
			g.drawImage(back,0,0,this);//draw background
		
			for (int i=0;i<56;i++){ //draw bricks
	     		if(bricks[i].destroyed==false){
	         		g.drawImage(brickPic,bricks[i].getX(),bricks[i].getY(),this);
	         	}
	         }
			if(inGame){
				g.drawImage(paddlePic,paddle.px,500,this);//draw paddle
	         	g.drawImage(ballPic,ball.bx,ball.by,this);//draw ball
	         	for(int i=0;i<56;i++){//draw power ups upon destroy a brick(if any)
		         	if(bricks[i].destroyed&&bricks[i].hasPowerUp && bricks[i].dropped==false){	         		
		         		if(bricks[i].powerUp=="speed"){
		         			g.drawImage(speedPic,bricks[i].powerX,bricks[i].powerY,this);
		         		}
		         		else if(bricks[i].powerUp=="life"){
		         			g.drawImage(lifePic,bricks[i].powerX,bricks[i].powerY,this);
		         		}
		         		else if(bricks[i].powerUp=="slow"){
		         			g.drawImage(slowPic,bricks[i].powerX,bricks[i].powerY,this);
		         		}
		         		else if(bricks[i].powerUp=="laser"){
		         			g.drawImage(laserPic,bricks[i].powerX,bricks[i].powerY,this);
		         		}		         	
		         	}
		         	
		         }
		         g.setColor(new Color(255,0,0));
		         if(shoot){//draw laser
		         	for(int i=0; i<myLasers.size(); i++){
		         		g.fillRect(myLasers.get(i).laserX,myLasers.get(i).laserY,4,10);
		         	}
		         }
	         	g.drawImage(barPic,0,530,this);//stats bar (lives, score)
	         	
	         	//display lives, and score
	         	g.setFont(new Font("Monospaced",Font.BOLD,35));
	         	g.setColor(new Color(255,0,0));
	         	g.drawString(String.format("%d",score),720,568);
	         	g.drawString(String.format("%d",lives),120,568);
			}
			else{
				if(win){
					g.drawImage(winPic,0,0,this);
				}
				else{
					g.drawImage(gameOverPic,0,0,this);
				}
				
			}
		}
		if(myScreen.equals("main")){
			g.drawImage(main,0,0,this);
			if(menuSelect == 0){//new game selection
				g.drawImage(gameSel,0,0,this);
			}
			else if(menuSelect == 1){//instruction selection
				g.drawImage(instrucSel,0,0,this);
			}
			else if(menuSelect ==2){//exit selection
				g.drawImage(exitSel,0,0,this);
			}
		}
		if(myScreen.equals("instructions")){
			g.drawImage(instructionPic,0,0,this);
		}
		
         
    }
}