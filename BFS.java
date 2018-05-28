import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

// Breadth First  Search
// This is a UnitTest implementation, not used for TicTacToe

public class BFS {
	
	SearchTree tree;
	Queue nodeQueue;
	String goal;
	
	public BFS(SearchTree _tree, String goalState) {
		tree = _tree;
		goal = goalState;
		nodeQueue = new LinkedList<Node>();
		// We start with the root node
		//nodeQueue.add(tree.getRoot());
	}
	
	public Deque<Node> search(Node currNode, ArrayList<Node> path) {
		// Is this node our goal? If so, end search
		//System.out.println("Working with " + currNode.getVal());
		path.add(currNode);
		if(currNode.getVal().equals(goal)) {
			Deque<Node> toReturn = new LinkedList<>();
			Node x = currNode;
			while(x.getParent() != null) {
				toReturn.addFirst(x);
				x = x.getParent();
			}
			toReturn.addFirst(x);
			return toReturn;
		}
		// This node is not our goal, continue our search
		ArrayList<Node> children = currNode.expandNode();
		for(Node child : children) {
			if(child == null) {
				;
			}
			else {
				//System.out.println("Adding " + child.getVal() + " to nodeQueue");
			    nodeQueue.add(child);
			}
		}
		Node toAdd = (Node)nodeQueue.poll();
		if(toAdd == null) {
			// There is nothing left in queue, and we haven't found the goal.
			// Goal is not present
			return null;
		}
		else {
			// Queue still has stuff in it, continue...
			return search(toAdd, path);
		}
	}

}
