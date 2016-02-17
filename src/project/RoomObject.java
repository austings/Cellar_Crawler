/* 
 * RoomObject.java 
 * 
 * Version: 
 *     $Id: RoomObject.java,v 1.2 2014/12/01 00:54:25 trs5953 Exp $ 
 * 
 * Revisions: 
 *     $Log: RoomObject.java,v $
 *     Revision 1.2  2014/12/01 00:54:25  trs5953
 *     Project is finished. Bug testing may continue.
 *
 *     Revision 1.2  2014/11/29 03:37:54  ags1098
 *     GUI is finished and began work on communication between model and gui
 *
 *     Revision 1.1  2014/11/29 00:26:52  trs5953
 *     Model is implemented. GUI needs to be finished so game can be played.
 *
 *     Revision 1.1  2014/11/26 23:36:32  ags1098
 *     *** empty log message ***
 * 
 */

package project;

/**
 * interface room object for any objects that exist within a room node.
 * 
 * @author Austin Sierra
 * @author Tyler Shank
 *
 */
public interface RoomObject 
{
	/**
	 * get the name of this room object
	 * @return the name of the room object.
	 */
	public String getName();
	
	/**
	 * return whether or not this object can be picked up
	 * @return true or false
	 */
	public boolean canBePickedUp();
}
