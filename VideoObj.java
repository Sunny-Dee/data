package myhw3.data;


/**
 * Implementation of Video interface.
 * @see Data
 */
final class VideoObj implements Video {
	private final String title;
	private final int    year;
	private final String director;
	private final int hashConstant = 37;

	/**
	 * Initialize all object attributes.
	 * Title and director are "trimmed" to remove leading and final space.
	 * @throws IllegalArgumentException if object invariant violated.
	 */
	VideoObj(String title, int year, String director) {
		this.title = title;
		this.director = director;
		this.year = year;
	}

	public String director() {
		// DONE
		return director;
	}

	public String title() {
		// DONE
		return title;
	}

	public int year() {
		// DONE
		return year;
	}

	public boolean equals(Object thatObject) {
		// DONE
		if (!(thatObject instanceof Video))
			return false;
		Video that = (Video) thatObject;
		return year == that.year() &&
				title.equals(that.title()) &&
				director.equals(that.director());
	}

	
	public int hashCode() {
		// DONE		 
		int hash = 17;
		hash = hashConstant*hash + title.hashCode();
		hash = hashConstant*hash + year;
		hash = hashConstant*hash + director.hashCode();
		
		return hash;
	}

	public int compareTo(Video that) {
		// DONE
		int titleDiff = title.compareTo(that.title());
		
		if (titleDiff != 0){
			return titleDiff;
		}
			
		int yearDiff = year - that.year();
		if (yearDiff != 0){
			return yearDiff;
		}
		
		int dirDiff = director.compareTo(that.director());
		if (dirDiff != 0){
			return dirDiff;
		}
		return 0;
		
	}

	public String toString() {
		// DONE
		//Should look like "El Mariachi (1996) : Rodriguez";
		StringBuilder buffer = new StringBuilder();
		buffer.append(title);
		buffer.append(" (");
		buffer.append(year);
		buffer.append(") : ");
		buffer.append(director);
		return buffer.toString();
	}

}
