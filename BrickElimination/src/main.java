import javax.swing.JFrame;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame obj = new JFrame(); //creates the java frame the game is played on
		Gameplay gamePlay = new Gameplay(); //initiates new game play
		obj.setBounds(10, 10, 700, 600); //the size of the frame the game will be played on
		obj.setTitle("Break Past the Wall");
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gamePlay);
	}

}
