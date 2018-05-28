import java.util.ArrayList;

public class TTTNode {
	
	int[][] gameState;
	TTTNode parent;
	ArrayList<TTTNode> children = new ArrayList<>();
	boolean isLeaf = false;
	
	public TTTNode(int[][] _gameState, TTTNode _parent) {
		gameState = _gameState;
		parent = _parent;
	}
	
	public void addChild(TTTNode child) {
		children.add(child);
	}
	
	public ArrayList<TTTNode> getChildren() {
		return children;
	}
	
	public TTTNode getParent() {
		return parent;
	}
	
	public void setIsLeaf(boolean toSet) {
		isLeaf = toSet;
	}
	
	public boolean getIsLeaf() {
		return isLeaf;
	}
	
	public int[][] getGameState(){
		return gameState;
	}
	
}
