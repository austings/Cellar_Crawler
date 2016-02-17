/* 
 * ActiveRoomObject.java 
 * 
 * Version: 
 *     $Id: ActiveRoomObject.java,v 1.2 2014/12/01 00:54:24 trs5953 Exp $ 
 * 
 * Revisions: 
 *     $Log: ActiveRoomObject.java,v $
 *     Revision 1.2  2014/12/01 00:54:24  trs5953
 *     Project is finished. Bug testing may continue.
 *
 *     Revision 1.4  2014/11/29 03:37:54  ags1098
 *     GUI is finished and began work on communication between model and gui
 *
 *     Revision 1.1  2014/11/29 00:26:51  trs5953
 *     Model is implemented. GUI needs to be finished so game can be played.
 *
 *     Revision 1.3  2014/11/27 03:07:17  ags1098
 *     Worked on Model
 *
 *     Revision 1.2  2014/11/27 00:56:19  ags1098
 *     halfway done with reading from config, graph completed. 
 *     Basic getters and setters for each class are done.
 *
 *     Revision 1.1  2014/11/26 23:36:32  ags1098
 *     *** empty log message ***
 * 
 */

package project;

/**
 * Abstract class for any RoomObject which happens to move from room to room
 * (i.e. monster and player)
 * 
 * @author Austin Sierra
 * @author Tyler Shank
 *
 */
public abstract class ActiveRoomObject implements RoomObject
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
	public boolean canBePickedUp()
	{
		return false;
	}
	
	/**
	 * The move method for player and monster
	 */
	public abstract void move(int room);
}
