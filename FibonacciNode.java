
public class FibonacciNode {
 
	private FibonacciNode leftNode;
	private FibonacciNode rightNode;
	private FibonacciNode parent;
	private int data;
	private FibonacciNode child;
	private int degree;
	String deg="";
	private boolean childCut;

	public FibonacciNode getChild() {
		return child;
	}

	public void setChild(FibonacciNode child) {
		this.child = child;
	}
	
	public int getData() {
		return data;
	}
	
	public boolean isChildCut() {
		return childCut;
	}

	public void setChildCut(boolean childCut) {
		this.childCut = childCut;
	}
	public void setData(int data) {
		this.data = data;
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}


	public FibonacciNode getLeftNode() {
		return leftNode;
	}

	public void setLeftNode(FibonacciNode leftNode) {
		this.leftNode = leftNode;
	}
	
	public FibonacciNode getParent() {
		return parent;
	}

	public void setParent(FibonacciNode parent) {
		this.parent = parent;
	}
	
	public FibonacciNode getRightNode() {
		return rightNode;
	}

	public void setRightNode(FibonacciNode rightNode) {
		this.rightNode = rightNode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;
		FibonacciNode other = (FibonacciNode) o;
		if (child == null) {
			if (other.child != null)
				return false;
		} else if (!child.equals(other.child))
			return false;
		if (childCut != other.childCut)
			return false;
		if (data != other.data)
			return false;
		if (degree != other.degree)
			return false;
		if (leftNode == null) {
			if (other.leftNode != null)
				return false;
		} else if (!leftNode.equals(other.leftNode))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (rightNode == null) {
			if (other.rightNode != null)
				return false;
		} else if (!rightNode.equals(other.rightNode))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((child == null) ? 0 : child.hashCode());
		result = prime * result + (childCut ? 1231 : 1237);
		result = prime * result + data;
		result = prime * result + degree;
		result = prime * result + ((leftNode == null) ? 0 : leftNode.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + ((rightNode == null) ? 0 : rightNode.hashCode());
		return result;
	}

	
	
	
}
