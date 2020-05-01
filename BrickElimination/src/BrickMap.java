import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class BrickMap {

	public int map[][]; //2D array representing the brick wall
	public int brickWidth;
	public int brickHeight;
	public BrickMap(int row, int col) {
		map = new int[row][col];
		for (int i = 0; i < map.length; i++) {
			for(int j=0; j < map[0].length; j++) {
				map[i][j] = 1; //Will detect that this brick has not collided with the ball / is set to 0 if it has
			}
		}
		
		brickWidth = 540/col;
		brickHeight = 150/row;	
	}
	
	public void draw(Graphics2D g) {
		for (int i = 0; i < map.length; i++) {
			for(int j=0; j < map[0].length; j++) {
				if(map[i][j] > 0) {
					g.setColor(Color.pink);
					g.fillRect(j*brickWidth + 80, i*brickHeight + 50, brickWidth, brickHeight);
					
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRect(j*brickWidth + 80, i*brickHeight + 50, brickWidth, brickHeight);
				}
			}
		}
		
	}
	
	public void setBrickValue(int value, int row, int col) {
		map[row][col] = value;
	}
}
