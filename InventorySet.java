package myhw3.data;

import java.util.Map;
import myhw3.command.CommandHistory;
import java.util.Comparator;
import java.util.Collections;
import java.util.Iterator;

/**
 * Implementation of Inventory interface.
 * @see Data
 */
final class InventorySet implements Inventory {
	private Map<Video,Record> data;
	private final CommandHistory history;

	InventorySet() {
		// TODO
		this.data = null;
		this.history = null;
		 
	}

	/**
	 * If <code>record</code> is null, then delete record for <code>video</code>;
	 * otherwise replace record for <code>video</code>.
	 */
	@SuppressWarnings("cast")
	void replaceEntry(Video video, Record record) {
		data.remove(video);
		if (record != null)
			data.put(video,((RecordObj)record));
	}

	/**
	 * Overwrite the map.
	 */
	void replaceMap(Map<Video,Record> data) {
		this.data = data;
	}

	public int size() {
		// TODO
		return 0;
	}

	public Record get(Video v) {
		// TODO
		return null;
	}

	public Iterator<Record> iterator() {
		return Collections.unmodifiableCollection(data.values()).iterator();
	}

	public boolean undo() {
		return history.undo();
	}

	public boolean redo() {
		return history.redo();
	}

	public Iterator<Record> iterator(Comparator<Record> comparator) {
		// TODO
		return null;
	}

	/**
	 * Add or remove copies of a video from the inventory.
	 * If a video record is not already present (and change is
	 * positive), a record is created.
	 * If a record is already present, <code>numOwned</code> is
	 * modified using <code>change</code>.
	 * If <code>change</code> brings the number of copies to be zero,
	 * the record is removed from the inventory.
	 * @param video the video to be added.
	 * @param change the number of copies to add (or remove if negative).
	 * @return A copy of the previous record for this video (if any)
	 * @throws IllegalArgumentException if video null, change is zero, if attempting to remove more copies than are owned, or if attempting to remove copies that are checked out.
	 */
	Record addNumOwned(Video video, int change) {
		// TODO
		return null;
	}

	/**
	 * Check out a video.
	 * @param video the video to be checked out.
	 * @return A copy of the previous record for this video
	 * @throws IllegalArgumentException if video has no record or numOut
	 * equals numOwned.
	 */
	Record checkOut(Video video) {
		// TODO
		return null;
	}

	/**
	 * Check in a video.
	 * @param video the video to be checked in.
	 * @return A copy of the previous record for this video
	 * @throws IllegalArgumentException if video has no record or numOut
	 * non-positive.
	 */
	Record checkIn(Video video) {
		// TODO
		return null;
	}

	/**
	 * Remove all records from the inventory.
	 * @return A copy of the previous inventory as a Map
	 */
	Map<Video,Record> clear() {
		// TODO
		return null;
	}

	/**
	 * Return a reference to the history.
	 */
	CommandHistory getHistory() {
		// TODO
		return null;
	}

	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("Database:\n");
		Iterator<Record> i = data.values().iterator();
		while (i.hasNext()) {
			buffer.append("  ");
			buffer.append(i.next());
			buffer.append("\n");
		}
		return buffer.toString();
	}


	/**
	 * Implementation of Record interface.
	 *
	 * <p>This is a utility class for Inventory.  Fields are immutable and
	 * package-private.</p>
	 *
	 * <p><b>Class Invariant:</b> No two instances may reference the same Video.</p>
	 *
	 * @see Record
	 */
	private static final class RecordObj implements Record {
		final Video video; // the video
		final int numOwned;   // copies owned
		final int numOut;     // copies currently rented
		final int numRentals; // total times video has been rented

		RecordObj(Video video, int numOwned, int numOut, int numRentals) {
			this.video = video;
			this.numOwned = numOwned;
			this.numOut = numOut;
			this.numRentals = numRentals;
		}
		public Video video() {
			return video;
		}
		public int numOwned() {
			return numOwned;
		}
		public int numOut() {
			return numOut;
		}
		public int numRentals() {
			return numRentals;
		}
		public String toString() {
			StringBuilder buffer = new StringBuilder();
			buffer.append(video);
			buffer.append(" [");
			buffer.append(numOwned);
			buffer.append(",");
			buffer.append(numOut);
			buffer.append(",");
			buffer.append(numRentals);
			buffer.append("]");
			return buffer.toString();
		}
	}
}
