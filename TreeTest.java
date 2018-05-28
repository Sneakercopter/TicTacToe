import java.awt.Color;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class TreeTest {
	
	int[][] gameBoard;
	int nodes = 0;
	Queue nodeQueue = new LinkedList<TTTNode>();
	TTTNode root;
	
	public TreeTest() {
		;
	}
	
	public ArrayList<int[][]> getLegalMoves(int[][] currState, int player){
		ArrayList<int[][]> toReturn = new ArrayList<int[][]>();
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 3; y++) {
				if(currState[x][y] == 0) {
					int[][] temp = new int[3][3];
					for(int _y = 0; _y < 3; _y++) {
						for(int _z = 0; _z < 3; _z++) {
							temp[_y][_z] = currState[_y][_z];
						}
					}
					temp[x][y] = player;
					toReturn.add(temp);
				}
			}
		}
		return toReturn;
		
	}
	
	public int isLeaf(int[][] currBoard) {
		// Many win conditions
		// -1 = not over
		// 0 = draw
		// 1 = X wins
		// 2 = O wins
		for(int i = 0; i < currBoard[0].length; i++) {
			// Full row (Diagonal) is equal
			if(currBoard[i][0] == currBoard[i][1] && currBoard[i][1] == currBoard[i][2]) {
				if(currBoard[i][0] == 1) {
					return 1;
				}
				else if(currBoard[i][0] == 2) {
					return 2;
				}
			}
			// Full row (Vertical) is equal
			else if(currBoard[0][i] == currBoard[1][i] && currBoard[1][i] == currBoard[2][i]) {
				if(currBoard[0][i] == 1) {
					return 1;
				}
				else if(currBoard[0][i] == 2) {
					return 2;
				}
			}
			// Full row (Diagonal top left to bottom right) is equal
			else if(currBoard[0][0] == currBoard[1][1] && currBoard[1][1] == currBoard[2][2]) {
				if(currBoard[0][0] == 1) {
					return 1;
				}
				else if(currBoard[0][0] == 2) {
					return 2;
				}
			}
			// Full row (Diagonal top right to bottom left) is equal
			else if(currBoard[0][2] == currBoard[1][1] && currBoard[1][1] == currBoard[2][0]) {
				if(currBoard[0][2] == 1) {
					return 1;
				}
				else if(currBoard[0][2] == 2) {
					return 2;
				}
			}
		}
		// All winning conditions have been considered,
		// now we need to make sure there are even still moves to be made.
		// If not, it is a draw.
		boolean draw = true;
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 3; y++) {
				if(currBoard[x][y] == 0) {
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
	
	public int[] populate() {
		// First we need to add a root node.
		// In this case, root is a blank board
		// 1. We should have a function that returns all legal moves for a node (game state)
		// 2. We should have a function that takes a node and decides if it is a leaf (winning/draw state)
		// 3. We should have a queue of nodes to populate
		// 4. Player 2 = Computer
		//    Player 1 = Human
		
		// First we make a root node
		root = new TTTNode(gameBoard, null);
		nodeQueue.add(root);
		nodes++;
		// Now we populate...
		int[] bestMove;
		bestMove = populateNodes();
		return bestMove;
	}
	
	public int[] populateNodes() {
		int currPlayer = 2; // Assuming the human has moved first
		Deque<TTTNode> toReturn = null;
		while(nodeQueue.size() > 0) {
			TTTNode curr = (TTTNode)nodeQueue.poll();
			ArrayList<int[][]> childrenStates = getLegalMoves(curr.getGameState(), currPlayer);
			for(int[][] childState : childrenStates) {
				TTTNode child = new TTTNode(childState, curr);
				curr.addChild(child);
				nodes++;
				if(isLeaf(child.getGameState()) >= 0) {
					// This is a leaf (game over) state!
					child.setIsLeaf(true);
					if(isLeaf(child.getGameState()) == 1){
						// Since this is BFS and it is optimal, if we find a loss scenario before
						// we find a victory, do that first because we don't want to let the player
						// win before we can.
						if(toReturn == null) {
							toReturn = new LinkedList<>();
							TTTNode x = child;
							while(x.getParent() != null) {
								toReturn.addFirst(x);
								x = x.getParent();
							}
							toReturn.addFirst(x);
						}
					}
					else if(isLeaf(child.getGameState()) == 2){
						// This is a computer victory, I am already populating as BFS
						if(toReturn == null) {
							// We found a good move
							toReturn = new LinkedList<>();
							TTTNode x = child;
							while(x.getParent() != null) {
								toReturn.addFirst(x);
								x = x.getParent();
							}
							toReturn.addFirst(x);
						}
					}
				}
				else {
					// This is not a leaf, still moves to be made. Add to queue.
					nodeQueue.add(child);
				}
			}
			if(currPlayer == 2) {
				currPlayer = 1;
			}
			else {
				currPlayer = 2;
			}
		}
		//System.out.println("Populated!");
		//System.out.println("Full search space: " + nodes);
		if(toReturn == null) {
			// No way of winning was found...
			return null;
		}
		TTTNode currentState = toReturn.poll();
		TTTNode moveToMake = toReturn.poll();
		/*System.out.println("Current state: ");
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 3; y++) {
				System.out.print(currentState.getGameState()[x][y]);
			}
			System.out.println();
		}
		System.out.println("Best Move:");
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 3; y++) {
				System.out.print(moveToMake.getGameState()[x][y]);
			}
			System.out.println();
		}
		*/
		int[] bestCoords = null;
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 3; y++) {
				if(currentState.getGameState()[x][y] != moveToMake.getGameState()[x][y]) {
					bestCoords = new int[2];
					bestCoords[0] = x;
					bestCoords[1] = y;
				}
			}
		}
		return bestCoords;
	}
	
	public void setGameBoard(int[][] toSet) {
		gameBoard = toSet;
	}
	
	public void findBestMove() {
		// This will use the bfs (Breadth First Search)
	}

}
