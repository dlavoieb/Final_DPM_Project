package dpm.lejos.project;

/**
 * High level mission planning.
 *
 * Implements the over all strategy
 *
 * @author David Lavoie-Boutin
 * @version 1.0
 */
public class MissionPlanner {

	public Navigation m_Navigation;
	public Grabber m_Grabber;
	public Localizer m_Localizer;

	public MissionPlanner() {

    }

    /**
     * perform the mission
     *
     * steps:
     *
     * 1- Localise within the playground
     * 2- Navigate to pickup zone
     * 3- Pickup blocks
     * 4- Navigate to drop off area
     * 5- Drop blocks
     * 6- repeat from 2 onwards
     */
    public void startMission(){}



}//end MissionPlanner