package application;

/**
 * @author Cooper
 *
 */
public class School {
	private String name;
	private int id;
	private String address;
	private double lat;
	private double lon;

	// null constructor
	public School() {
		this.name = null;
		this.id = -1;
		this.setLat(-1);
		this.setLon(-1);
	}

	// parameter constructor
	public School(String name, int id, String address, double lat, double lon) {
		this.name = name;
		this.id = id;
		this.address = address;
		this.setLat(lat);
		this.setLon(lon);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
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
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the latitude
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * @param lat
	 *            the latitude to set
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}

	/**
	 * @return the longitude
	 */
	public double getLon() {
		return lon;
	}

	/**
	 * @param lon
	 *            the longitude to set
	 */
	public void setLon(double lon) {
		this.lon = lon;
	}

	/*
	 * (non-Javadoc)
	 * 
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