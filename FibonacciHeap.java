import java.util.LinkedList;
import java.util.Queue;
import java.util.HashMap;
import java.util.Iterator;
import java.io.*;

public class FibonacciHeap<T extends FibonacciNode> {

	 T maximumNode;
	 
	 public T getMax() {
			return maximumNode;
		}

		public void setMax(T maximumNode) {
			this.maximumNode = maximumNode;
		}
	 public FibonacciHeap<T> insert(FibonacciHeap<T> H, T x) { //Inserts a new heap
			if (H.getMax() == null) {
				x.setParent(null);
				x.setLeftNode(x);
				x.setRightNode(x);
				H.setMax(x);
				return H;
			}
			T maximumNode = (T) H.getMax();
			T rightNodeOfMax = (T) maximumNode.getRightNode();
			x.setParent(null);
			x.setRightNode(rightNodeOfMax);
			x.setLeftNode(maximumNode);
			maximumNode.setRightNode(x);
			rightNodeOfMax.setLeftNode(x);
			if (maximumNode.getData() <= x.getData()) {
				H.setMax(x);
			}
			return H;
		}

		
	public FibonacciHeap<T> increaseKey(FibonacciHeap<T> H, T child, int increaseValue) {
		child.setData(child.getData() + increaseValue);
		T parent = (T) child.getParent(); //Finds the parent of the node
		if (parent != null) {
			if (parent.getData() <= child.getData()) {
				H = cut(H, child, parent);
				H = cascadingCut(H, parent); //Cuts the node if the value of the parent changes
			}
		}
		//Changes the max pointer if the new value is the new max
		if (H.getMax() == null || (H.getMax().getData() <= child.getData())) {
			H.setMax(child);
		}
		return H;
	}

	
	public FibonacciHeap<T> cut(FibonacciHeap<T> H, T child, T parent) { //Put it on the top of the list
		child.setChildCut(false); //By default ChildCut is false
		parent.setDegree(parent.getDegree() - 1); //Degree decreases by one
		child = removeFibonacciNode(child); //Removes the child of the node for childcut false
		H = insert(H, child);
		return H;
	}

	public FibonacciHeap<T> cascadingCut(FibonacciHeap<T> H, T parent) { //Checks the childcut of the parent and cut it if it's true
		T grandParent = (T) parent.getParent();
		if (grandParent != null) {
			if (!parent.isChildCut()) {
				parent.setChildCut(true); //The parent is set to be true if the child is cut
			} else {
				H = cut(H, parent, grandParent);
			}
		}
		return H;
	}

	public FibonacciHeap<T> removeMax(FibonacciHeap<T> H) { //Remove the max nodes of the Heap
		T maximumNode = (T) H.getMax();
		T newMax = (T) maximumNode.getRightNode();
		if (!maximumNode.equals(newMax)) {
			H.setMax(newMax);

			maximumNode = removeFibonacciNode(maximumNode); //This removes from Hashtag
			T temp = newMax; //Temporary variable for the maximum node
			do { 
				T tempLeftNode = (T) temp.getLeftNode();  //Temporary left node after the max node has been removed
				H = insert(H, temp);
				temp = tempLeftNode;
			} while (!newMax.equals(temp));
		} else {
			H.setMax(null);
		}
		T temp = (T) maximumNode.getChild();
		
		if (temp != null) {
			do {
				T tempLeft = (T) temp.getLeftNode();
				H = insert(H, temp);
				temp = tempLeft;
			} while (!maximumNode.getChild().equals(temp));
		}
	
		maximumNode.setChild(null);
		
		if (H.getMax() != null) {
			H = pairwiseCombine(H); //Pairwise combined is called when remove max has been done
		}
		return H;
	}

	
	

	public T meld(T x, T y) { //Combines the nodes
		if (x.getData() >= y.getData()) {
			y.setParent(x);
			T child = (T) x.getChild();
			if (child != null) {
				T rightNodeOfChild = (T) child.getRightNode();
				y.setRightNode(rightNodeOfChild);
				y.setLeftNode(child);
				child.setRightNode(y);
				rightNodeOfChild.setLeftNode(y);
			} else {
				x.setChild(y);
				y.setLeftNode(y);
				y.setRightNode(y);
			}
			
			x.setDegree(x.getDegree() + 1); //Degree increases if it's combined
			return x;
		} else {
			x.setParent(y);
			T child = (T) y.getChild();
			if (child != null) {
				T rightNodeOfChild = (T) child.getRightNode();
				x.setRightNode(rightNodeOfChild);
				x.setLeftNode(child);
				child.setRightNode(x);
				rightNodeOfChild.setLeftNode(x);
			} else {
				y.setChild(x);
				x.setLeftNode(x);
				x.setRightNode(x);
			}
			y.setDegree(y.getDegree() + 1);
			return y;
		}
	}

	public T removeFibonacciNode(T FibonacciNode) {
	
		if (!FibonacciNode.getLeftNode().equals(FibonacciNode)) {
			FibonacciNode.getLeftNode().setRightNode(FibonacciNode.getRightNode());
			FibonacciNode.getRightNode().setLeftNode(FibonacciNode.getLeftNode());
			if (FibonacciNode.getParent() != null && (FibonacciNode.equals(FibonacciNode.getParent().getChild()))) {
				FibonacciNode.getParent().setChild(FibonacciNode.getLeftNode());
			}
		}else if (FibonacciNode.getParent() != null && (FibonacciNode.equals(FibonacciNode.getParent().getChild()))) {
			FibonacciNode.getParent().setChild(null);
		}
		FibonacciNode.setLeftNode(null);
		FibonacciNode.setRightNode(null);
		
		FibonacciNode.setParent(null);

		return FibonacciNode;
	}
	
	public FibonacciHeap<T> pairwiseCombine(FibonacciHeap<T> H) {
		HashMap<Integer, T> hmap = new HashMap<Integer, T>();
		Queue<T> tempNodesList = new LinkedList<>(); //Temporary list for removing the nodes
		T maximumNode = H.getMax();
		T tempFibonacciNode = maximumNode;
		do {
			T tempFibonacciNodeLeftNode = (T) tempFibonacciNode.getLeftNode();
			tempFibonacciNode.setLeftNode(null);
			tempFibonacciNode.setRightNode(null);
			tempNodesList.add(tempFibonacciNode);
			tempFibonacciNode = tempFibonacciNodeLeftNode;
		} while (!maximumNode.equals(tempFibonacciNode));

	
		while (!tempNodesList.isEmpty()) {
			T currentFibonacciNode = tempNodesList.poll();
			T sameDegreeFibonacciNodeInMap = hmap.get(currentFibonacciNode.getDegree());
			while (sameDegreeFibonacciNodeInMap != null) {
				hmap.remove(sameDegreeFibonacciNodeInMap.getDegree());
				currentFibonacciNode = meld(sameDegreeFibonacciNodeInMap, currentFibonacciNode);
				sameDegreeFibonacciNodeInMap = hmap.get(currentFibonacciNode.getDegree());
			}
			hmap.put(currentFibonacciNode.getDegree(), currentFibonacciNode);
		}
		H.setMax(null); //Resets the value to null
		for (Iterator<T> iterator = hmap.values().iterator(); iterator.hasNext();) {
			T FibonacciNode = (T) iterator.next();
			H = insert(H, FibonacciNode);
		}
		return H;
	}

	

}
