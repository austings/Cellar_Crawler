/* 
 * Player.java 
 * 
 * Version: 
 *     $Id: Player.java,v 1.2 2014/12/01 00:54:25 trs5953 Exp $ 
 * 
 * Revisions: 
 *     $Log: Player.java,v $
 *     Revision 1.2  2014/12/01 00:54:25  trs5953
 *     Project is finished. Bug testing may continue.
 *
 *     Revision 1.5  2014/12/01 00:51:14  ags1098
 *     finished most of project. just bug testing and making sure all is well
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
 *      Basic getters and setters for each class are done.
 *
 *     Revision 1.1  2014/11/26 23:36:33  ags1098
 *     *** empty log message ***
 * 
 */

package project;

import java.util.ArrayList;

/**
 * The player class which holds all the data for the player object
 * in the node.
 * 
 * @author Austin Sierra
 * @author Tyler Shank
 *
 */
public class Player extends ActiveRoomObject
{
	private boolean hasSword;
	private boolean hasAmulet;
	private int lifeForce;
	private int level;
	private int room;
	private ArrayList<Item> items;
	
	/**
	 * Constructor for the player
	 */
	public Player(){
		hasSword = false;
		hasAmulet = false;
		lifeForce = 100;
		level = 1;
		room = 0;
		items = new ArrayList<Item>(level);
	}
	
	/**
	 * Returns the player's current health.
	 * 
	 * @return lifeForce	- the current health of the player.
	 */
	public int getHealth(){
		return lifeForce;
	}
	
	/**
	 * Returns the player's current level.
	 * 
	 * @return level	- the current level of the player.
	 */
	public int getLevel(){
		return level;
	}
	
	/**
	 * Returns the player's current room.
	 * 
	 * @return room	- the current room of the player.
	 */
	public int getRoom(){
		return room;
	}
	
	/**
	 * Moves the player to a new room
	 * 
	 * @param room	- the new room to move to
	 */
	public void move(int newRoom)
	{
		room = newRoom;
	}
	
	/**
	 * Increases the level of the player by one
	 */
	public void levelUp(){
		level++;
		ArrayList<Item> temp = new ArrayList<Item>(level);
		temp.addAll(items);
		items = temp;
	}
	
	/**
	 * Used to reduce the health of the player
	 * 
	 * @param damage	- the amount of damage to be taken
	 * 
	 * @param isMonster	- true if the source of the damage is the monster, false otherwise
	 */
	public void takeDamage(int damage, boolean isMonster)
	{
		double damageD = damage*.01;
		if(isMonster){
			if(10 > (lifeForce / 2)){
				lifeForce = lifeForce- 10;
			}
			else{
				lifeForce = lifeForce/2;
			}
		}
		else{
			double temp = lifeForce * damageD;
			lifeForce = (int) (lifeForce - temp);
		}
		if(lifeForce <= 0){
			Cellar.endGame(false);
		}
	}
	
	/**
	 * Adds an item to the player's inventory
	 * 
	 * @param name	- the name of the new item
	 */
	public boolean addItem(Item newItem){
		//if you have inventory space or you are trying to get amulet or sword
		if(getItems().size() < level||newItem.isAmulet()||newItem.isSword())
		{
			items.add(newItem);
			return true;
		}
		else
		{
			if(hasSword&&getItems().size()==level)
			{
				items.add(newItem);
				return true;
			}
		}
		//else display gui error
		return false;
	}
	
	/**
	 * Removes an item from the player's inventory
	 * 
	 * @param item	-  the item to drop
	 */
	public void dropItem(Item item){
		if(hasItem(item.getName())){
			if(item.isSword()){
				System.out.println("false");
				dropSword();
			}
			items.remove(item);
		}
	}
	
	/**
	 * destroys all items that are not the sword
	 */
	public void destroyItems()
	{
		if(hasSword)
		{
			items.clear();
			items.add(new Item("Sword",false,true));
		}
		else
		{
			items.clear();
		}
	}
	
	/**
	 * Used to test if the player has a certain item in their inventory
	 * 
	 * @param name	- the name of the item to check for
	 * 
	 * @return	true if the item is present in the inventory, false otherwise
	 */
	public boolean hasItem(String name){
		boolean test = false;
		for(Item x : items){
			if(x.getName().equals(name)){
				test = true;
			}
		}
		return test;
	}
	
	/**
	 * Used to retrieve the player's inventory
	 * 
	 * @return items	- the inventory of the player
	 */
	public ArrayList<Item> getItems(){
		return items;
	}
	
	/**
	 * Used to acquire the sword
	 */
	public void takeSword(){
		hasSword = true;
	}
	
	/**
	 * Used to drop the sword
	 */
	public void dropSword(){
		hasSword = false;
	}
	
	/**
	 * Used to determine if the player has the sword
	 * 
	 * @return true if the player has the sword, false otherwise
	 */
	public boolean hasSword(){
		return hasSword;
	}
	
	/**
	 * Used to acquire the amulet
	 */
	public void takeAmulet(){
		hasAmulet = true;
		Cellar.endGame(true);
	}
	
	/**
	 * Used to determine if the player has the amulet
	 * 
	 * @return true if the player has the amulet, false otherwise
	 */
	public boolean hasAmulet(){
		return hasAmulet;
	}
	
	/**
	 * Used to get the name of the player
	 * 
	 * @return "Player"
	 */
	public String getName() {
		
		return "Player";
	}
}