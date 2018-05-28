import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TicTacToe implements ActionListener{
	
	JFrame guiFrame;
	int WIDTH = 3;
	int HEIGHT = 3;
	ButtonSquare[][] gameBoard = new ButtonSquare[WIDTH][HEIGHT];
	int[][] computerBoard = new int[WIDTH][HEIGHT]; // The computer needs this to visualise the game
	boolean computerTurn = true;
	boolean gameOver = false;
	Random rand = new Random();
	TreeTest tt;
	
	public static void main(String[] argv) {
		TicTacToe t = new TicTacToe();
	}
	
	public TicTacToe() {
		populateGameBoard();
		createGui();
		computerFirstTurn();
	}
	
	public void computerFirstTurn() {
		ButtonSquare sel = gameBoard[1][1];
		computerBoard[1][1] = 2;
		sel.setText("X");
		sel.setOccupied(true);
		sel.setVal(2);
		sel.removeActionListener(this);
		computerTurn = false;
	}
	
	public void createGui() {
		guiFrame = new JFrame();
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Tic-Tac-Toe");
		guiFrame.getContentPane().setLayout( new FlowLayout() );
		guiFrame.setPreferredSize(new Dimension(75*WIDTH, 80*HEIGHT));
		for(int x = 0; x < WIDTH; x++) {
			for(int y = 0; y < HEIGHT; y++) {
				gameBoard[x][y].addActionListener(this);
				guiFrame.getContentPane().add(gameBoard[x][y]);
			}
		}
		guiFrame.pack();
		guiFrame.setVisible(true);
	}
	
	public void populateGameBoard() {
		for(int x = 0; x < WIDTH; x++) {
			for(int y = 0; y < HEIGHT; y++) {
				gameBoard[x][y] = new ButtonSquare(60, 60, x, y, new Color(0, 255, 0));
				computerBoard[x][y] = 0;
			}
		}
	}
	
	public int isGameOver() {
		// Many win conditions
		// -1 = not over
		// 0 = draw
		// 1 = O wins
		// 2 = X wins
		for(int i = 0; i < gameBoard[0].length; i++) {
			// Full row (Diagonal) is equal
			if(gameBoard[i][0].getVal() == gameBoard[i][1].getVal() && gameBoard[i][1].getVal() == gameBoard[i][2].getVal()) {
				if(gameBoard[i][0].getVal() == 1) {
					return 1;
				}
				else if(gameBoard[i][0].getVal() == 2) {
					return 2;
				}
			}
			// Full row (Vertical) is equal
			else if(gameBoard[0][i].getVal() == gameBoard[1][i].getVal() && gameBoard[1][i].getVal() == gameBoard[2][i].getVal()) {
				if(gameBoard[0][i].getVal() == 1) {
					return 1;
				}
				else if(gameBoard[0][i].getVal() == 2) {
					return 2;
				}
			}
			// Full row (Diagonal top left to bottom right) is equal
			else if(gameBoard[0][0].getVal() == gameBoard[1][1].getVal() && gameBoard[1][1].getVal() == gameBoard[2][2].getVal()) {
				if(gameBoard[0][0].getVal() == 1) {
					return 1;
				}
				else if(gameBoard[0][0].getVal() == 2) {
					return 2;
				}
			}
			// Full row (Diagonal top right to bottom left) is equal
			else if(gameBoard[0][2].getVal() == gameBoard[1][1].getVal() && gameBoard[1][1].getVal() == gameBoard[2][0].getVal()) {
				if(gameBoard[0][2].getVal() == 1) {
					return 1;
				}
				else if(gameBoard[0][2].getVal() == 2) {
					return 2;
				}
			}
		}
		// All winning conditions have been considered,
		// now we need to make sure there are even still moves to be made.
		// If not, it is a draw.
		boolean draw = true;
		for(int x = 0; x < WIDTH; x++) {
			for(int y = 0; y < HEIGHT; y++) {
				if(gameBoard[x][y].getVal() == 0) {
					// There is still a move to be made
					draw = false;
				}
			}
		}
		if(draw == true) {
			return 0;
		}
		return -1;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		ButtonSquare src = (ButtonSquare)arg0.getSource();
		if(computerTurn) {
			;
			
		}
		else {
			if(src.getOccupied() != true) {
				computerTurn = true;
				src.setText("O");
				src.setOccupied(true);
				src.setVal(1);
				src.removeActionListener(this);
				computerBoard[src.getXCoord()][src.getYCoord()] = 1;
			}
		}
		int gameResult = isGameOver();
		if(gameResult > 0) {
			System.out.println("Game over");
			gameOver = true;
			if(gameResult == 0) {
				JOptionPane.showMessageDialog(null, "Game is a draw!");
			}
			else if(gameResult == 1) {
				JOptionPane.showMessageDialog(null, "X wins!");
			}
			else if(gameResult == 2) {
				JOptionPane.showMessageDialog(null, "O wins!");
			}
		}
		// When playing the AI, this will only be triggered when the user takes a turn
		// so now it's the computer's turn.
		if(!gameOver) {
			computerTurn();
		}
	}
	
	public void computerTurn() {
		tt = new TreeTest();
		tt.setGameBoard(computerBoard);
		int[] bestCoords = tt.populate();
		if(bestCoords != null) {
			System.out.println("Best move: " + bestCoords[0] + ", " + bestCoords[1]);
			ButtonSquare sel = gameBoard[bestCoords[0]][bestCoords[1]];
			sel.setText("X");
			sel.setOccupied(true);
			sel.setVal(2);
			computerBoard[bestCoords[0]][bestCoords[1]] = 2;
			sel.removeActionListener(this);
			
			int gameResult = isGameOver();
			if(gameResult > 0) {
				System.out.println("Game over");
				if(gameResult == 0) {
					JOptionPane.showMessageDialog(null, "Game is a draw!");
				}
				else if(gameResult == 1) {
					JOptionPane.showMessageDialog(null, "O wins!");
				}
				else if(gameResult == 2) {
					JOptionPane.showMessageDialog(null, "X wins!");
				}
			}
		}
		else {
			// The computer sees no scenario where it can win anymore, pick a random square.
			System.out.println("The computer sees no way of winning anymore, picking randomly...");
			computerTurn1();
		}
		computerTurn = false;
	}
	
	public void computerTurn1() {
		
		ArrayList<ButtonSquare> available = new ArrayList<>();
		for(int x = 0; x < WIDTH; x++) {
			for(int y = 0; y < HEIGHT; y++) {
				if(gameBoard[x][y].getVal() == 0) {
					// There is still a move to be made
					available.add(gameBoard[x][y]);
				}
			}
		}
		if(available.size() > 0) {
			rand.nextInt(available.size());
			ButtonSquare sel = available.get(rand.nextInt(available.size()));
			sel.setText("X");
			sel.setOccupied(true);
			sel.setVal(2);
			sel.removeActionListener(this);
		}
		int gameResult = isGameOver();
		if(gameResult >= 0) {
			System.out.println("Game over");
			if(gameResult == 0) {
				JOptionPane.showMessageDialog(null, "Game is a draw!");
			}
			else if(gameResult == 1) {
				JOptionPane.showMessageDialog(null, "X wins!");
			}
			else if(gameResult == 2) {
				JOptionPane.showMessageDialog(null, "O wins!");
			}
		}
		computerTurn = false;
	}
}
