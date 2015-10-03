package myhw3.data;

import myhw3.command.Command;

/**
 * Implementation of command to check in a video.
 * @see Data
 */
final class CmdIn implements Command {
	private InventorySet inventory;
	private Record oldvalue;
	private Video video;
	CmdIn(InventorySet inventory, Video video) {
		this.inventory = inventory;
		this.video = video;
	}
	public boolean run() {
		// TODO
		return false;
	}
	public void undo() {
		// TODO
	}
	public void redo() {
		// TODO
	}
}
