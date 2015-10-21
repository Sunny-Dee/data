package myhw3.data;

import java.util.Map;

import myhw3.command.CommandHistory;
import myhw3.command.CommandHistoryObj;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Implementation of Inventory interface.
 * @see Data
 */
final class InventorySet implements Inventory {
	private Map<Video,Record> data;
	private final CommandHistory history;

	InventorySet() {
		// DONE
		this.data = new HashMap<Video, Record>();
		this.history = new CommandHistoryObj();
		 
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
		// DONE
		return data.size();
	}

	public Record get(Video v) {
		// DONE
		return data.get(v);
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
		// DONE
		List<Record> recordList = new ArrayList<Record>(data.values());
		Collections.sort(recordList, comparator);
		return Collections.unmodifiableList(recordList).iterator();
	}
	
	
//	//Helper method by Deliana
//	public String topNvids(int topN, Comparator<Record> comparator){
//		Iterator<Record> iter = iterator(comparator);
//		StringBuilder buffer = new StringBuilder();
//		buffer.append("Top " + topN + " videos:\n");
//		for (int i = 1; i <= topN; i++){
//			Record r = iter.next();
//			buffer.append(i +".  ");
//			buffer.append(r);
//			buffer.append("\n");
//		}	
//		return buffer.toString();
//	}

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
		// DONE
		if (video == null || change == 0)
			throw new IllegalArgumentException("Video or change not valid");
		
		RecordObj r = (RecordObj) data.get(video);
		//System.out.println("First " + r);
		if (r == null && change < 1)
			throw new IllegalArgumentException("Don't have that vid in database");
		else if (r == null){
			data.put(video, new RecordObj(video, change, 0, 0));
			return null;
		}
		else if (r.numOwned+change < r.numOut)
			throw new IllegalArgumentException("Not enough copies to do this");
		else if (r.numOwned + change < 1)
			data.remove(video);
		else {
			int newNumOwned = r.numOwned + change;
			RecordObj replacement = new RecordObj(video, newNumOwned, r.numOut(), r.numRentals());
			replaceEntry(video, replacement);
		}
		//test that r is not changing
		//QUESTION FOR PROF RIELY: I thought r is only a pointer to data.get(video), 
		//how come line 109 of the code not be changing it?
//		System.out.println("Second " + r);
//		System.out.println("Database " + data.get(video));
		return r;

	}

	/**
	 * Check out a video.
	 * @param video the video to be checked out.
	 * @return A copy of the previous record for this video
	 * @throws IllegalArgumentException if video has no record or numOut
	 * equals numOwned.
	 */
	Record checkOut(Video video) {
		// DONE
		RecordObj r = (RecordObj) data.get(video);
		if (r == null || r.numOut == r.numOwned)
			throw new IllegalArgumentException("Video not in database");
		RecordObj updatedRecord = new RecordObj(video, r.numOwned(), r.numOut()+1, r.numRentals()+1);
		replaceEntry(video, updatedRecord);
		return r;
	}

	/**
	 * Check in a video.
	 * @param video the video to be checked in.
	 * @return A copy of the previous record for this video
	 * @throws IllegalArgumentException if video has no record or numOut
	 * non-positive.
	 */
	Record checkIn(Video video) {
		// DONE
		//QUESTION FOR PROF RIELY: do I still need to cast RecordObj on line 148?
		Record r = data.get(video);
		if (r == null || r.numOut() < 1)
			throw new IllegalArgumentException();
		RecordObj updatedRecord = new RecordObj(video, r.numOwned(), r.numOut()-1, r.numRentals());
		replaceEntry(video, updatedRecord);
		return r;
	}

	/**
	 * Remove all records from the inventory.
	 * @return A copy of the previous inventory as a Map
	 */
	Map<Video,Record> clear() {
		// DONE
		Map<Video, Record> clearedMap = new HashMap<Video, Record>();
		Map<Video, Record> temp = data;
		replaceMap(clearedMap);
		return temp;
	}

	/**
	 * Return a reference to the history.
	 */
	CommandHistory getHistory() {
		// DONE
		// 
		return history;
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
//	public static void main(String[] args){
//		InventorySet inv = new InventorySet();
//		final VideoObj v1 = new VideoObj( "A", 2000, "B" );
//		final VideoObj v2 = new VideoObj( "B", 2000, "B" );
//		
//		inv.addNumOwned(v1, 4);
//		inv.addNumOwned(v1, 4);
//		System.out.println(inv);
//	}
}
