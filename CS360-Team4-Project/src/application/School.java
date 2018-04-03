package application;

/**
 * @author Cooper
 *
 */
public class School {
	private String name;
	private int id;
	
	// null constructor
	public School(){
		this.name = null;
		this.id = -1;
	}
	
	// parameter constructor
	public School (String name, int id){
		this.name = name;
		this.id = id;	
		
	}
	

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("School [");
		builder.append(name);
		builder.append(",  id: ");
		builder.append(id);
		builder.append("]\n");
		return builder.toString();
	}

}
