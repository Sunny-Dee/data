package myhw3.data;

import myhw3.command.Command;

/**
 * Implementation of command to check out a video.
 * @see Data
 */
final class CmdOut implements Command {
	private InventorySet inventory;
	private Record oldvalue;
	private Video video;
	CmdOut(InventorySet inventory, Video video) {
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
