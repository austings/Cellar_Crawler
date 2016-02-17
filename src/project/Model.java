/* 
 * Model.java 
 * 
 * Version: 
 *     $Id: Model.java,v 1.2 2014/12/01 00:54:25 trs5953 Exp $ 
 * 
 * Revisions: 
 *     $Log: Model.java,v $
 *     Revision 1.2  2014/12/01 00:54:25  trs5953
 *     Project is finished. Bug testing may continue.
 *
 *     Revision 1.5  2014/12/01 00:51:12  ags1098
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
 *     Basic getters and setters for each class are done.
 *
 *     Revision 1.1  2014/11/26 23:36:31  ags1098
 *     *** empty log message ***
 * 
 */

package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import project.RoomGraph.Hallway;
import project.RoomGraph.Room;

/**
 * The Model part of the MVC which is manipulated by the controller and updates
 * the view when there is a change of data.
 * 
 * @author Austin Sierra
 * @author Tyler Shank
 */
public class Model extends Observable
{
	private int last = 1;
	
	private ArrayList<String> lines;
	private ArrayList<Traps> traps;
	private ArrayList<Item> items;
	private ArrayList<RoomObject> ro;
	private ArrayList<Traps> activate;
	private static RoomGraph game;
	private Player p = new Player();
	private Monster m;
	
	/**
	 * The model will read the information from the config file and create a
	 * game world with the correct configurations.
	 * 
	 * @param f the config file we are working with
	 * 
	 * @throws IOException 
	 */
	public Model(File f) throws IOException{
		//take data from file and add to string arraylist
		try{
		FileInputStream fis = new FileInputStream(f);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String cl = null;
		lines = new ArrayList<String>();
		while((cl = br.readLine())!=null)
		{
			lines.add(cl);
		}
		br.close();
		} 
		catch (FileNotFoundException e) {
			System.err.println("Usage: java Cellar config_file_name");
		}
		//get trap data
		
		int numTraps = Integer.parseInt(lines.get(0));
		items = new ArrayList<Item>();
		Item amulet = new Item("Amulet",true,false);
		items.add(amulet);
		traps = new ArrayList<Traps>();
		for(int i = 0;i<numTraps;i++)
		{
			boolean hasVal =false;
			int value = -1;
			String name= (lines.get(i+1)).substring
					(0, lines.get(i+1).indexOf(" "));
			lines.set(i+1, lines.get(i+1).substring(lines.get(i+1)
					.indexOf(" ")+1,lines.get(i+1).length()));
			String type= (lines.get(i+1)).substring
					(0, lines.get(i+1).indexOf(" "));
			lines.set(i+1, lines.get(i+1).substring(lines.get(i+1)
					.indexOf(" ")+1,lines.get(i+1).length()));
			if(type.equals("weaken")||type.equals("warp"))
			{
				hasVal = true;
				value = Integer.parseInt((lines.get(i+1)).substring
						(0, lines.get(i+1).indexOf(" ")));
				lines.set(i+1, lines.get(i+1).substring(lines.get(i+1)
						.indexOf(" ")+1,lines.get(i+1).length()));
			}
			String prot = (lines.get(i+1));
			if(hasVal)
				traps.add(new Traps(name,type,value,prot));
			else
				traps.add(new Traps(name,type,prot));
			last++;
		}
		//get room data, amulet room
		String roomData = lines.get(last);
		last = last+1;
		int roomNum = Integer.parseInt(roomData.substring
				(0, roomData.indexOf(" ")));
		roomData = roomData.substring(roomData.indexOf(" ")+1);
		int amuletRoom = Integer.parseInt(roomData);
		game = new RoomGraph();
		//loop through roomNumber times
		for(int x = 0; x < roomNum; x++)
		{
			ro = new ArrayList<RoomObject>();
			if(roomNum==0)//player
				ro.add(p);
			if((x)==(amuletRoom))//amulet
			{
				ro.add(amulet);
			}
			
			//add traps to room
			roomData = lines.get(last);
			if(!roomData.equals("none"))//if not keyword none
			{
				while(roomData.length()!=0)//while length is not 0
				{
					if(roomData.contains(" "))//if there is a space
					{
						String roomObject = (roomData.substring(0,roomData
								.indexOf(" ")));//substring to that space
						for(int j =0;j<traps.size();j++)//loop through traps
						{
							//find matching trap name
							if(roomObject.equals(traps.get(j).getName()))
							{
								ro.add(traps.get(j));//add that trap to room
							}
						}
						//substring out that part of the line we already read
						roomData = (roomData.substring(roomData.indexOf(" ")+1));
					}
					else//if no space, we know we are at the last word
					{
						for(int j =0;j<traps.size();j++)
						{
							//do the same thing as above 
							if(roomData.equals(traps.get(j).getName()))
							{
								ro.add(traps.get(j));
							}
						}
						//set roomData length to 0
						roomData = "";
					}
				}
				
			}
			//add protections to room
			last = last+1;
			roomData = lines.get(last);
			if(!roomData.equals("none"))//if not keyword none
			{
				while(roomData.length()!=0)//while length is not 0
				{
					if(roomData.contains(" "))//if there is a space
					{
						String roomObj = (roomData.substring(0,roomData
								.indexOf(" ")));//substring to that space
						if(roomObj.equals("Sword"))
							items.add(new Item(roomObj,false,true));
						else
							items.add(new Item(roomObj,false,false));
						//add the newly created item.
						ro.add(items.get(items.size()-1));
						//substring out that part of the line we already read
						roomData = (roomData.substring(roomData.indexOf(" ")+1));
					}
					else//if no space, we know we are at the last word
					{
						if(roomData.equals("Sword"))
							items.add(new Item(roomData,false,true));
						else
							items.add(new Item(roomData,false,false));
						//add the newly created item.
						ro.add(items.get(items.size()-1));
						//set roomData length to 0
						roomData = "";
					}//if else
				}//while length not 0
			}//if not none
			game.addVertex(ro);
			last = last+1;
		}//for loop
		
		int numHalls = Integer.parseInt(lines.get(last));
		last = last+1;
		for(int i =0;i<numHalls;i++)
		{
			String hall = lines.get(last);
			
			//name of hall
			String hallname = hall.substring(0, hall.indexOf(" "));
			hall = hall.substring(hall.indexOf(" ")+1);
			
			//first room connected to
			int to = Integer.parseInt(hall.substring(0,hall.indexOf(" ")));
			hall = hall.substring(hall.indexOf(" ")+1);
			
			//second room connected to
			int from = Integer.parseInt(hall);
			game.addEdge(hallname, to, from);
			last = last+1;
		}
		
		//read monster
		if(lines.get(last).equals("1"))
		{
			last = last+1;
			String monster = lines.get(last);
			String monsterName = monster.substring(0, monster.indexOf(" "));
			monster = monster.substring(monster.indexOf(" ")+1);
			int room = Integer.parseInt(monster);
			m = new Monster(monsterName,room);
			game.getVertex(room-1).addRO(m);
		}
		
		
	}//constructor

	/**
	 * Get roomgraph method so that we can access hallways and rooms
	 * from the interface class.
	 * 
	 * @return game the graph of all the rooms and hallways in the game.
	 */
	public RoomGraph getRoomGraph()
	{
		return game;
	}
	
	/**
	 * A get room method so we can access its data without returning the
	 * entire room graph.
	 * 
	 * @param id - the room id we are trying to find.
	 * 
	 * @return the room requested
	 */
	public Room getRoom(int id)
	{
		return game.getVertex(id);
	}
	
	/**
	 * Gets an arraylist of all the hallways.
	 * @return a list of all hallways in the RoomGraph
	 */
	public ArrayList<Hallway> getHallways()
	{
		return game.getHallways();
	}
	
	/**
	 * A get room method so we can access its data without returning the
	 * entire room graph.
	 * 
	 * @param name - the hallway we are trying to find.
	 * 
	 * @return the hallway requested
	 */
	public Hallway getHallway(String name)
	{
		return game.getEdge(name);
	}
	/**
	 * Get player so that we can access its data
	 * 
	 * @return p the player.
	 */
	public Player getPlayer()
	{
		return p;
	}
	
	/**
	 * Get monster so we can acces its data
	 * 
	 * @return m the monster
	 */
	public Monster getMonster()
	{
		return m;
	}
	
	/**
	 * This is the method to pick something up off the ground and put
	 * into inventory.
	 * 
	 * @param name the name of the object to be picked up
	 */
	public void pickUp(String name)
	{
		//if you can add it do so.
		
		ArrayList<RoomObject> remove = getRoom(getPlayer()
				.getRoom()).getROs();
			
		for(int i =0;i<remove.size();i++)
		{
			if(remove.get(i).getName().equals(name)&&remove.get(i) 
					instanceof Item)
			{
				//if amulet, win game
				if(((Item)remove.get(i)).isAmulet())
				{
				getPlayer().takeAmulet();
				//winPopup();
				}
				//if sword, change variable in player
				if(((Item)remove.get(i)).isSword())
				{
					getPlayer().takeSword();
				}
				//remove from items on ground array and put in inventory
				String o = remove.get(i).getName();
				if(getPlayer().addItem((Item)remove.get(i)))
				{
					getRoom(getPlayer().getRoom())
					.takeRO(remove.get(i).getName());
					String n = getInfo();
					setChanged();
					notifyObservers("\n----------\n*You picked up the "
							+ ""+o+"."+n);
				}
				else
				{
					setChanged();
					notifyObservers("\n----------\n*Your inventory is full.");
				}
				//update
			}
		}
	}
	
	/**
	 * Used to remove an item from the player's inventory
	 * 
	 * @param name - the name of the item to drop
	 */
	public void drop(String name)
	{
		if(name.equalsIgnoreCase("sword")){
			setChanged();
			notifyObservers("\n----------\n*You should not drop the Sword.");
		}
		else{
			ArrayList<Item> itemList = getPlayer().getItems();
			for(int x = 0; x < itemList.size(); x++){
				if(itemList.get(x).getName().equals(name)){
					Item temp = itemList.remove(x);
					getPlayer().dropItem(temp);
					getRoom(getPlayer().getRoom()).addRO(temp);
					//update
					String n = getInfo();
					setChanged();
					notifyObservers("\n----------\n*You dropped the "
					+temp.getName()+"."+n);
				}
			}
		}
	}
	
	/**
	 * Used to move the player from room to room. Also moves the monster.
	 * 
	 * @param name - the hallway the player wishes to take.
	 */
	public void move(String name)
	{
		int roomId = getPlayer().getRoom();
		ArrayList<Hallway> halls = game.getHallways();
		//move player
		for(int i = 0;i<halls.size();i++)
		{
			//loop halls, if name equals the one we want,
			if(halls.get(i).getName().equals(name))
			{
				//check if we connect with this hallway, and what room we connect with.
				int id = halls.get(i).doIConnectWithThisHallway(roomId);
				if(id!=-1&&id!=getPlayer().getRoom())
				{
					game.getVertex(getPlayer().getRoom()).removeActive(p);
					getPlayer().move(id);
					game.getVertex(getPlayer().getRoom()).addRO(p);
					String n =getInfo();
					setChanged();
					notifyObservers("\n----------\n*You used the "+name+"."+n);
				}
			}
		}
	}
	
	/**
	 * Makes the monster move and figures out if the player triggered
	 * any events
	 * 
	 * @return a string containing whether or not the player did anything.
	 */
	public String getInfo()
	{
		moveMonster();
		String n = activate();
		return n;
	}
	
	/**
	 * Collects a list of all the hallways near the Monster and randomly picks
	 * one to move to.
	 * 
	 * @param halls the list of all the hallways.
	 */
	public void moveMonster()
	{
		if(getMonster().isAlive())
		{
			ArrayList<Hallway> halls = game.getHallways();
			int myRoom = getMonster().getRoom();
			//move monster
			ArrayList<Integer> possibilities = new ArrayList<Integer>();
			for(int i = 0;i<halls.size();i++)
			{
				int id = halls.get(i).doIConnectWithThisHallway(myRoom);
				if(id!=-1)
				{
					possibilities.add(id);
				}
			}
			//if you have at least one choice
			if(!(possibilities.isEmpty()))
			{
				//generate a random number of the size of the list and move
				//to the room at the index of the move.
				Random generator = new Random();
				int random = generator.nextInt(possibilities.size());
				game.getVertex(getMonster().getRoom()).removeActive(m);
				getMonster().move(possibilities.get(random));
				game.getVertex(getMonster().getRoom()).addRO(m);
			}
		}
		else
		{
			game.getVertex(m.getRoom()).takeRO(m.getName());
		}
	}

	
	/** Adds an observer to the set of observers for this object, 
	 * provided that it is not the same as some observer already in the set.
	 * 
	 * @param o the observer to be added
	 */
	public void addObserver(Observer o)
	{
		super.addObserver(o);
	}
	
	/**
	 * Notifies all observers of this object that a change has happened.
	 * @param a the object to be sent to the interface.
	 */
	public void notifyObservers(Object a)
	{
		super.notifyObservers(a);
	}
	
	/**
	 * Activates the monster or a trap if the player is in the same room.
	 * 
	 * @return a string result of the encounter.
	 */
	public String activate()
	{
		String value = "";
		boolean withMonster = false;
		//loop through objects in room
		ArrayList<RoomObject> traps =getRoom(getPlayer().getRoom()).getROs();
		activate = new ArrayList<Traps>();
		for(int i =0;i<traps.size();i++)
		{
			//check if we see a monster is in the room
			if(traps.get(i) instanceof Monster)
			{
				withMonster = true;
			}
			else
			{
				//check if there are traps in the room
				if(traps.get(i) instanceof Traps)
				{
					activate.add((Traps)traps.get(i));
					String type = "";
					String damage ="took ";
					if(!((Traps)traps.get(i)).spring(p))
					{
						//check the kind of trap after activating
						if(((Traps)traps.get(i)).getType().equals("weaken"))
						{
							type = "weaken";
							damage = damage+(Integer.toString(((Traps)
									traps.get(i)).getSpecial()))+"% damage.";
						}
						else
						{
							if(((Traps)traps.get(i)).getType().equals("warp"))
							{
								type = "warp";
								damage = "were teleported to room "+
								(Integer.toString(((Traps)
										traps.get(i)).getSpecial()))+".";
							}
							else
							{
								type = "vanish";
								damage = "lost all your items!";
							}
						}
						//create a string value which will indicate to the player
						//what happened as a result of the trap.
						value= value+"\n----------\n*You sprung a "+traps.get(i)
								.getName()+" "+type+" trap and "+damage;
					}
					else
					{
						value = value+"\n----------\n*A protection item you had"
								+ " saved you from activing the trap.";
					}//else
				}//if instance of traps
			}
		}
		if(withMonster && m.isAlive())
		{
			if(getPlayer().hasSword()){
				getMonster().die();
				value = value+"\n----------\n*You were attacked by " + 
				getMonster().getName() 
						+ " but you vanquished it with the Sword."
						+ " It fades away into dust.";
				getPlayer().levelUp();
			}
			else{
				getMonster().attack(getPlayer());
				value = value+"\n----------\n*You were attacked by " + 
						getMonster().getName() + " you now have "
						+ getPlayer().getHealth() + " health.";
			}
		}
		return value;
	}
	
}//Model
