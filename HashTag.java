
public class HashTag extends FibonacciNode {

	private String title;

	public String getHashTagTitle() { //Getter method of HashTag title
		return title;
	}

	public void setHashTagTitle(String title) { //Setter method of HashTag title
		this.title = title;
	}

	@Override
	public boolean equals(Object obj) { //Checking with the Hashcode value
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HashTag check = (HashTag) obj;
		if (title == null) {
			if (check.title != null)
				return false;
		} else if (!title.equals(check.title))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() { //Returns the result computed of the hashCode
		final int prime = 31;
		int res = 1;
		res = prime * res + ((title == null) ? 0 : title.hashCode());
		return res;
	}

	

	
	
}