import java.util.ArrayList;

public class Node {
	
	ArrayList<Node> children;
	Node parent;
	String val;
	boolean expanded = false;
	
	public Node(Node _parent, String _val) {
		children = new ArrayList<Node>();
		parent = _parent;
		val = _val;
	}
	
	public void addChild(Node child) {
		children.add(child);
	}
	
	public ArrayList<Node> expandNode(){
		expanded = true;
		return children;
	}
	
	public boolean isExpanded() {
		return expanded;
	}
	
	public String getVal() {
		return val;
	}
	
	public Node getParent() {
		return parent;
	}
}
