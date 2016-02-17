/* 
 * Monster.java 
 * 
 * Version: 
 *     $Id: Monster.java,v 1.2 2014/12/01 00:54:24 trs5953 Exp $ 
 * 
 * Revisions: 
 *     $Log: Monster.java,v $
 *     Revision 1.2  2014/12/01 00:54:24  trs5953
 *     Project is finished. Bug testing may continue.
 *
 *     Revision 1.3  2014/12/01 00:51:13  ags1098
 *     finished most of project. just bug testing and making sure all is well
 *
 *     Revision 1.2  2014/11/29 03:37:54  ags1098
 *     GUI is finished and began work on communication between model and gui
 *
 *     Revision 1.1  2014/11/29 00:26:51  trs5953
 *     Model is implemented. GUI needs to be finished so game can be played.
 *
 *     Revision 1.1  2014/11/26 23:36:32  ags1098
 *     *** empty log message ***
 * 
 */

package project;

/**
 * The Monster class that holds the data for the monster object
 * that is within the room.
 * 
 * @author Austin Sierra
 * @author Tyler Shank
 *
 */
public class Monster extends ActiveRoomObject
{

	//NOTE: possibly use dijkstra's to have monster systematically 
	//		seek out player and murder him. HARDCORE MODE.
	private boolean isAlive;
	private String name;
	private int room;
	
	/**
	 * Constructor for the monster.
	 * 
	 * @param name	- the name of the monster.
	 * 
	 * @param room	- the starting room of the monster.
	 */
	public Monster(String name, int room){
		isAlive = true;
		this.name = name;
		this.room = room;
	}
	
	/**
	 * Used to move the monster to a new room.
	 * 
	 * @param newRoom	- the number of the new room to move to.
	 */
	public void move(int newRoom)
	{
		room = newRoom;
	}
	
	/**
	 * Used to attack the player.
	 * 
	 * @param p	- the player to attack.
	 */
	public void attack(Player p){
		p.takeDamage(0, true);
	}
	
	/**
	 * Used to kill the monster
	 */
	public void die(){
		isAlive = false;
	}
	
	/**
	 * Used to determine the state of the monster.
	 * 
	 * @return true if the monster is alive, false otherwise.
	 */
	public boolean isAlive(){
		return isAlive;
	}
	
	/**
	 * Used to determine the current room of the monster
	 * 
	 * @return room	- the current room number of the monster.
	 */
	public int getRoom(){
		return room;
	}
	
	/**
	 * Used to determine the name of the monster.
	 * 
	 * @return name	- the name of the monster.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns if the monster can be picked up
	 * 
	 * @return false, because the monster cannot be picked up by a player.
	 */
	public boolean canBePickedUp() {
		return false;
	}
}
