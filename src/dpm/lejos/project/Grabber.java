package dpm.lejos.project;

import lejos.nxt.TouchSensor;

import java.util.LinkedList;

/**
 * @author david
 * @version 1.0
 * @created 24-oct.-2014 12:37:24
 */
public class Grabber {

	public boolean hasObject = false;
	public LinkedList<BlockInStorage> listBlocksInStorage;
	private TouchSensor touchSensor;

	public Grabber(){

	}

	/**
	 * close claw,
	 * check for touch contact
	 * release perform again with repositionning
	 */
	public void grab(){

	}

	/**
	 * store block in bucket or smth
	 */
	public void store(){

	}
}//end Grabber