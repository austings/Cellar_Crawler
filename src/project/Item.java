/* 
 * Item.java 
 * 
 * Version: 
 *     $Id: Item.java,v 1.2 2014/12/01 00:54:25 trs5953 Exp $ 
 * 
 * Revisions: 
 *     $Log: Item.java,v $
 *     Revision 1.2  2014/12/01 00:54:25  trs5953
 *     Project is finished. Bug testing may continue.
 *
 *     Revision 1.3  2014/11/29 03:37:55  ags1098
 *     GUI is finished and began work on communication between model and gui
 *
 *     Revision 1.1  2014/11/29 00:26:51  trs5953
 *     Model is implemented. GUI needs to be finished so game can be played.
 *
 *     Revision 1.2  2014/11/27 03:07:18  ags1098
 *     Worked on Model
 *
 *     Revision 1.1  2014/11/26 23:36:31  ags1098
 *     *** empty log message ***
 * 
 */

package project;

/**
 * The class which contains all the items in the game
 * 
 * @author Austin Sierra
 * @author Tyler Shank
 *
 */
public class Item extends PassiveRoomObject
{
	private String name;
	private boolean isAmulet;
	private boolean isSword;
	
	/**
	 * Constructs a new item.
	 * 
	 * @param name	- the name of the item.
	 * 
	 * @param isAmulett	- true if the item is the amulet, false otherwise.
	 * 
	 * @param isSwordd	- true if the item is the sword, false otherwise.
	 */
	public Item(String name, boolean isAmulett, boolean isSwordd){
		this.name = name;
		isAmulet = isAmulett;
		isSword = isSwordd;
	}
	
	/**
	 * Used to obtain the name of the item.
	 * 
	 * @return name	- the name of the monster.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Used to determine if the item is the amulet.
	 * 
	 * @return true if the item is the amulet, false otherwise.
	 */
	public boolean isAmulet(){
		return isAmulet;
	}
	
	/**
	 * Used to determine if the item is the sword.
	 * 
	 * @return true if the item is the sword, false otherwise.
	 */
	public boolean isSword(){
		return isSword;
	}
	
	/**
	 * Used to determine if the item can be picked up.
	 * 
	 * @return true because items can be added to the player's inventory.
	 */
	public boolean canBePickedUp() {
		return true;
	}

}
