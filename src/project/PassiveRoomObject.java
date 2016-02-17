/* 
 * PassiveRoomObject.java 
 * 
 * Version: 
 *     $Id: PassiveRoomObject.java,v 1.2 2014/12/01 00:54:25 trs5953 Exp $ 
 * 
 * Revisions: 
 *     $Log: PassiveRoomObject.java,v $
 *     Revision 1.2  2014/12/01 00:54:25  trs5953
 *     Project is finished. Bug testing may continue.
 *
 *     Revision 1.4  2014/11/29 03:37:55  ags1098
 *     GUI is finished and began work on communication between model and gui
 *
 *     Revision 1.1  2014/11/29 00:26:52  trs5953
 *     Model is implemented. GUI needs to be finished so game can be played.
 *
 *     Revision 1.3  2014/11/27 03:07:18  ags1098
 *     Worked on Model
 *
 *     Revision 1.2  2014/11/27 00:56:20  ags1098
 *     halfway done with reading from config, graph completed. 
 *     Basic getters and setters for each class are done.
 *
 *     Revision 1.1  2014/11/26 23:36:31  ags1098
 *     *** empty log message ***
 * 
 */


package project;

/**
 * This is the abstract class which will be a parent for all the
 * objects in the game which do not move from room to room.
 * 
 * @author Austin Sierra
 * @author Tyler Shank
 *
 */
public abstract class PassiveRoomObject implements RoomObject
{
	private String name;
	
	/**
	 * get the name of this room object
	 * @return the name of the room object.
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * return whether or not this object can be picked up
	 * @return true or false
	 */
	public abstract boolean canBePickedUp();
}
