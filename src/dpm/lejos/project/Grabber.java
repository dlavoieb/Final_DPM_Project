package dpm.lejos.project;

import lejos.nxt.TouchSensor;

import java.util.LinkedList;

/**
 * Class managing the grabbing mechanism
 *
 * @author David Lavoie-Boutin
 * @version 1.0
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