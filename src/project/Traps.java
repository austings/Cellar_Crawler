/* 
 * Traps.java 
 * 
 * Version: 
 *     $Id: Traps.java,v 1.2 2014/12/01 00:54:25 trs5953 Exp $ 
 * 
 * Revisions: 
 *     $Log: Traps.java,v $
 *     Revision 1.2  2014/12/01 00:54:25  trs5953
 *     Project is finished. Bug testing may continue.
 *
 *     Revision 1.3  2014/12/01 00:51:14  ags1098
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
 * The class which holds the different types of traps in the game
 * as well as the data and parameters associated with the different traps.
 * 
 * @author Austin Sierra
 * @author Tyler Shank
 *
 */
public class Traps extends PassiveRoomObject
{
	private String name;
	private String type;
	private int special;//can be damage or room to warp to
	private String protection;
	
	/**
	 * Constructor for a trap without a special attribute
	 * 
	 * @param name	- the name of the trap
	 * 
	 * @param type	- the type of the trap
	 * 
	 * @param protection	- the protection against the trap
	 */
	public Traps(String name, String type, String protection){
		this.name = name;
		this.type = type;
		special = 0;
		this.protection = protection;
	}
	
	/**
	 * Constructor for a trap with a special attribute
	 * @param name	- the name of the trap
	 * 
	 * @param type	- the type of the trap
	 * 
	 * @param special	- the special attribute of a trap, can be either damage
	 *	or a room to warp to.
	 * 
	 * @param protection	- the protection against the trap
	 */
	public Traps(String name, String type, int special, String protection){
		this.name = name;
		this.type = type;
		this.special = special;
		this.protection = protection;
	}
	
	/**
	 * Used to activate a trap.
	 * 
	 * @param p	- the player to activate on.
	 * 
	 * @return saved - true if the player is saved from the trap.
	 */
	public boolean spring(Player p){
		boolean saved = true;
		if(getType().equals("weaken"))
		{
			if(p.hasItem(getProtection()))
			{
				int i = 0;
				boolean found = false;
				while(!found)
				{
					if(p.getItems().get(i).getName().equals(protection))
					{
						p.getItems().remove(i);
						found = true;
					}
					i++;
				}
				p.levelUp();
			}
			else{
				p.takeDamage(special, false);
				saved = false;
			}
		}
		else if(getType().equalsIgnoreCase("warp"))
		{
			if(p.hasItem(getProtection()))
			{
				int i = 0;
				boolean found = false;
				while(!found)
				{
					if(p.getItems().get(i).getName().equals(protection))
					{
						p.getItems().remove(i);
						found = true;
					}
					i++;
				}
				p.levelUp();
			}
			else
			{
				//warps a player to a specific room
				p.move(special);
				saved = false;
			}
		}
		//vanish trap, removes all items
		else{
			if(p.hasItem(getProtection()))
			{
				int i = 0;
				boolean found = false;
				while(!found)
				{
					if(p.getItems().get(i).getName().equals(protection))
					{
						p.getItems().remove(i);
						found = true;
					}
					i++;
				}
				p.levelUp();
			}
			else{
				p.destroyItems();
				saved = false;
			}
		}
		return saved;
	}
	
	/**
	 * Returns the name of the trap
	 * 
	 * @return name	- the name of the trap.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the type of the trap
	 * 
	 * @return type	- the type of the trap.
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * Returns the damage to be dealt by the trap or the room to warp to.
	 * 
	 * @return	special	- the damage dealt or the room to warp to(type dependent).
	 */
	public int getSpecial(){
		return special;
	}
	
	/**
	 * Returns the protection item for the trap
	 * 
	 * @return protection	- the protection item for the trap
	 */
	public String getProtection(){
		return protection;
	}
	
	/**
	 * Returns if the trap can be picked up
	 * 
	 * @return false, because a trap cannot be picked up by a player.
	 */
	public boolean canBePickedUp() {
		return false;
	}

}
