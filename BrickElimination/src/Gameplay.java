import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener  {

	private boolean play = false; //represents whether game is in play
	private int score = 0;
	private int totalBricks = 21; //total bricks in the wall
	
	private Timer timer;
	private int delay = 8; //timer and delay represents the ball speed 
	
	private int paddle = 310; //the paddle position
	
	private int ballPosX = 120; 
	private int ballPosY = 350; //starting position of the ball on the screen
	private int ballXdir = -1;
	private int ballYdir = -2; //initial direction in which the ball would move upon start
	
	private BrickMap brickWall; //initiating the wall
	
	//constructor
	public Gameplay() {
		brickWall = new BrickMap(3,7); //Creates the wall with 3 ros and 7 columns of bricks
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this); //create the timer used for ball movement 
		timer.start();
	}
	
	public void paint(Graphics g) { //used to draw the different UI elements on screen
		//background
		g.setColor(Color.white);
		g.fillRect(1,1,692,592); //Rect graphic used to represent the rectangle shape
		
		//Scores
		g.setColor(Color.black);
		g.setFont(new Font("Times New Roman", Font.BOLD, 25));
		g.drawString("Score: "+score, 580, 30);
		
		//Drawing wall
		brickWall.draw((Graphics2D) g);
		
		//Border
		g.setColor(Color.blue);
		g.fillRect(0,0,3,592);
		g.fillRect(0,0,692,3);
		g.fillRect(691,0,3,592);
		
		//The Paddle
		g.setColor(Color.green);
		g.fillRect(paddle, 550,100,8);
		
		//The Ball
		g.setColor(Color.blue);
		g.fillOval(ballPosX, ballPosY, 20,20); //Oval graphic used to represent the ball
		
		
		if (totalBricks <=0 ) { //if all bricks are destroyed a success screen is displayed
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.green);
			g.setFont(new Font("Times New Roman", Font.BOLD, 30));
			g.drawString("You Won! Your score: "+score, 190, 300);
			
			g.setFont(new Font("Times New Roman", Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 230, 350);
		}
		
		if(ballPosY > 570) { //if the ball falls past the paddle its game over
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("Times New Roman", Font.BOLD, 30));
			g.drawString("Game over! Your score: "+score, 190, 300);
			
			g.setFont(new Font("Times New Roman", Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 230, 350);
		}
		
		g.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		timer.start();
		
		if(play) {
			if(new Rectangle(ballPosX, ballPosY, 20,20).intersects(new Rectangle(paddle, 550, 100, 8))) {
				ballYdir = -ballYdir;
			}//during play if the ball hits the paddle it should bounce off this detects interaction
			
			A: for(int i = 0; i<brickWall.map.length; i++) {
				for (int j =0; j <brickWall.map[0].length; j++) {
					if(brickWall.map[i][j]>0) {
						int brickX = j*brickWall.brickWidth + 80;
						int brickY = i*brickWall.brickHeight + 50;
						int brickWidth = brickWall.brickWidth;
						int brickHeight = brickWall.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20,20);
						Rectangle brickRect = rect;
						
						if (ballRect.intersects(brickRect)) { //when the ball hits a brick it should eliminate the brick and increment the score
							brickWall.setBrickValue(0, i, j);
							totalBricks --;
							score +=5;
							
							if(ballPosX +19 <= brickRect.x || ballPosX +1 >= brickRect.x + brickRect.width) {
								ballXdir = - ballXdir;
							} else {
								ballYdir = -ballYdir;
							}
							
							break A;
						}
					}
				}
			}
			
			ballPosX +=ballXdir;
			ballPosY += ballYdir;
			if(ballPosX < 0) { //for the left border
				ballXdir = -ballXdir;
			}
			if(ballPosY < 0) { //for the top border
				ballYdir = -ballYdir;
			}
			if(ballPosX > 670) {//for the right border
				ballXdir = -ballXdir; //direction is changed once it hits each border
			}
		}
		repaint();	
	}
	

	@Override
	public void keyPressed(KeyEvent e) { //Actions that take place when a keyboard key is pressed
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) { 
			if(paddle>=600) { //moves paddle to the right until position 600 when pressing right key
				paddle = 600;
			}else {
				moveRight();
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (paddle<10) { //moves paddle to the left until position 10 when pressing left key
				paddle = 10;
			}else {
				moveLeft();
			}			
		}
		
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) { //when Enter key is pressed the game is restarted / Event only occurs at the end of the game
				play=true;
				ballPosX = 120;
				ballPosY = 350;
				ballXdir = -1;
				ballYdir = -2;
				paddle = 310;
				score = 0;
				totalBricks = 21;
				brickWall = new BrickMap(3,7);
				
				repaint(); //repaints the graphics
			}
		}
	}
	
	public void moveRight() {
		play = true;
		paddle+=20; //speed at which the paddle moves right
	}
	
	public void moveLeft() {
		play = true;
		paddle -=20; //speed at which the paddle moves left
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	
	//Auto generated methods for when invoking KeyListener and ActionListener

}
