/* 
 * RoomGraph.java 
 * 
 * Version: 
 *     $Id: RoomGraph.java,v 1.2 2014/12/01 00:54:24 trs5953 Exp $ 
 * 
 * Revisions: 
 *     $Log: RoomGraph.java,v $
 *     Revision 1.2  2014/12/01 00:54:24  trs5953
 *     Project is finished. Bug testing may continue.
 *
 *     Revision 1.5  2014/12/01 00:51:13  ags1098
 *     finished most of project. just bug testing and making sure all is well
 *
 *     Revision 1.4  2014/11/29 03:37:54  ags1098
 *     GUI is finished and began work on communication between model and gui
 *
 *     Revision 1.1  2014/11/29 00:26:50  trs5953
 *     Model is implemented. GUI needs to be finished so game can be played.
 *
 *     Revision 1.3  2014/11/27 03:07:17  ags1098
 *     Worked on Model
 *
 *     Revision 1.2  2014/11/27 00:56:19  ags1098
 *     halfway done with reading from config, graph completed.
 *      Basic getters and setters for each class are done.
 *
 *     Revision 1.1  2014/11/26 23:36:32  ags1098
 *     *** empty log message ***
 * 
 */

package project;

import java.util.ArrayList;

/**
 * The graph that contains all the rooms and the room objects
 * 
 * @author Austin Sierra
 * @author Tyler Shank
 *
 */
public class RoomGraph 
{
	private ArrayList<Room> rooms;
	private ArrayList<Hallway> hallways;
	private int lastRoomID;

	/**
	 * Constructor for the RoomGraph
	 */
	public RoomGraph()
	{
		rooms = new ArrayList<Room>();
		lastRoomID = 0;
		hallways = new ArrayList<Hallway>();
	}
	
	/**
	 * Constructs a room with the given objects.
	 * 
	 * @param ro - an array of all the objects in this room.
	 */
	public void addVertex(ArrayList<RoomObject> ro)
	{
		rooms.add(new Room(lastRoomID,ro));
		lastRoomID = lastRoomID+1;
	}
	
	/**
	 * Retrieve a vertex from the array
	 * @param id - the id of the room you're looking for
	 */
	public Room getVertex(int id)
	{
		if(id<rooms.size())
		{
			return rooms.get(id);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Retrieve a hallway from the array
	 * 
	 * @param hall - the name of the hall you're looking for
	 */
	public Hallway getEdge(String hall)
	{
		for(int i=0;i<hallways.size();i++)
		{
			if(hallways.get(i).getName().equals(hall))
				return hallways.get(i);
		}
		return null;
	}
	
	/**
	 * The method to construct a new hallway with the given name.
	 * Although it takes params to and from, the hallway is capable of
	 * heading to or leaving from either room.
	 * 
	 * @param name - the name of the hallway
	 * 
	 * @param to - the room id this hallway leads to
	 * 
	 * @param from - the room id this hallway heads from
	 * 
	 */
	public void addEdge(String name, int to, int from)
	{
		Integer[] temp = new Integer[2];
		temp[0] = to;
		temp[1] = from;
		hallways.add(new Hallway(name, temp));
	}
	
	/**
	 * Gets a arraylist of hallways.
	 * 
	 * @return hallways- an arraylist of all hallways
	 */
	public ArrayList<Hallway> getHallways()
	{
		return hallways;
	}
	
	/**
	 * The Vertex class for each room held within the graph.
	 * 
	 * @author Austin Sierra
	 * @author Tyler Shank
	 */
	class Room
	{
		private int id;
		private ArrayList<RoomObject> ro;
		private String description ="";
		
		/**
		 * Constructor for the Room vertex
		 * 
		 * @param num - this room's id
		 */
		public Room(int num, ArrayList<RoomObject> myROs)
		{
			id = num;
			ro = myROs;
			description = randomSetting();
			
		}
		
		/**
		 * Get the description of this room
		 * 
		 * @return description- a description of the room.
		 */
		public String getSetting()
		{
			return description;
		}
		
		/**
		 * A generator for one of ten settings a room can
		 * take place in.
		 * 
		 * @return a string description of the room.
		 */
		public String randomSetting()
		{
			int zero;
			if(id==0)
				zero = 10;
			else
				zero = (int)(Math.random()*10);
			switch(zero){
				case 0:
					return "This is a very old dining hall. Ancient china "
							+ "fills"
							+ " the old mahogany table and splintered wooden"
							+ " chairs line the walls. There is an old grand"
							+ "father clock ticking away in the corner. ";
				case 1:
					return "This is some sort of barracks. There are "
							+ " dummies and archery targets to your left. "
							+ "There"
							+ " is a barred window in the back of the room "
							+ "which lets in a very dull light. ";
				case 2:
					return "This is a dark throne room. The walls are lined"
							+ " with drapes and banners of clans you cannot "
							+ "identify. At the back of the room is a marble "
							+ "throne, which has remarkable engravings. ";
				case 3:
					return "This is a retired study. Bookcases cover every "
							+ "wall and tattered scrolls cover the floor."
							+ " There is a broken desk here, as well as a"
							+ " lectern which is covered in spilled, dried"
							+ " ink. ";
				case 4:
					return "This is a conference hall of some kind. There is"
							+ " a large, round stone table coming out of the"
							+ " ground, surrounded by little stone thrones."
							+ " A bit of water drips down from the corner of"
							+ " the ceiling.";
				case 5:
					return "This room is quite disturbing. There is a bed, for"
							+ " what looks like a demented creature. The floor"
							+ " is covered in bones, possibly of unfortunate"
							+ " adventurers "
							+ "who ran into the sickly creature during its"
							+ " feeding time. ";
				case 6:
					return "This room is a decorative parlour. A bar is within"
							+ " plain sight in the back of the room, with "
							+ "dusty "
							+ "bar stools every couple of feet. There are "
							+ "eerie paintings coupling the walls, of men "
							+ "whose "
							+ "eyes seem to follow you around the room.";
				case 7:
					return "This room looks like a makeshift room-of-prayer."
							+ " There are stained-glass windows on either"
							+ " side, along with limestone pillers that reach"
							+ " the ceiling. At the back is an alter with a"
							+ " red quilt over it.";
				case 8:
					return "This room looks like a scientific laboratory."
							+ " There are many desks covered with vials and"
							+ " beakers filled with unknown liquids. Large"
							+ " machines fill the opposite side of this room"
							+ ", and some clear pipes flow a green liquid "
							+ "between them.";
				default:
					return "This entryway is covered in caskets and tall "
							+ "statues. The way back up is blocked behind you."
							+ " Most of the sarcophaguses are sealed shut, but"
							+ " the stresses of time or perhaps an inquisitive"
							+ " looter or two have shoved a few open. Whats "
							+ "revealed is mostly bones, though.";
				case 9:
					return "This room is a master bedroom, the bed covered"
							+ " in silk sheets and silk blankets. There is"
							+ " a veil overhead, and the oak dressers have"
							+ " left what seems like only dust and mothballs"
							+ " in their drawers.";
			}
		}
		
		/**
		 * Getter for the room id;
		 * 
		 * @return id- this room's id
		 */
		public int getID()
		{
			return id;
		}
	
		/**
		 * Setter for the room id
		 * 
		 * @param newID- the id to be set as
		 */
		public void setID(int newID)
		{
			id = newID;
		}
	
		/**
		 * Returns an arraylist of all the objects in the room;
		 * 
		 * @return ro- all the objects in the room;
		 */
		public ArrayList<RoomObject> getROs()
		{
			return ro;
		}
	
		/**
		 * Method for taking an object from the room.
		 * 
		 * @param n- the name of the object being taken.
		 */
		public void takeRO(String n)
		{
			for(int i = 0;i<ro.size();i++)
			{
				if(ro.get(i).canBePickedUp()&&ro.get(i).getName().equals(n))
				{
					ro.remove(i);
				}
			}
		}
		
		/**
		 * Method for taking the activeRoomObject out of the room.
		 * 
		 * @param n the object you are trying to remove
		 */
		public void removeActive(Object n)
		{
			for(int i = 0;i<ro.size();i++)
			{
				if(n instanceof Player)
				{
					if(ro.get(i) instanceof Player)
					{
						ro.remove(i);
					}
				}
				if(n instanceof Monster)
				{
					if(ro.get(i) instanceof Player)
					{
						ro.remove(i);
					}
				}
			}
		}
		
		/**
		 * Method for adding an object to the room
		 * 
		 * @param me- the roomobject to be added to the room
		 */
		public void addRO(RoomObject me)
		{
			ro.add(me);
		}
	}
	
	/**
	 * The edge class for each hallway within the graph;
	 * @author Austin Sierra
	 * @author Tyler Shank
	 *
	 */
	class Hallway
	{
		private String name;
		private Integer[] connectors;
		//an array of the room ids which this hallway connects
		
		/**
		 * Constructor for the edge
		 * 
		 * @param myName - the name of this hallway
		 * 
		 * @param connected - the array of connected ids
		 */
		public Hallway(String myName, Integer[] connected)
		{
			name = myName;
			connectors = connected;
		}
		
		/**
		 * Getter for the string name;
		 * 
		 * @return name - the name of this hallway
		 */
		public String getName()
		{
			return name;
		}
		
		/**
		 * Setter for the string name;
		 * 
		 * @param newName - the string that name should be set to.
		 */
		public void setName(String newName)
		{
			name = newName;
		}
	
		/**
		 * This is the method to check if this hallway connects one room with
		 * another.
		 * @param id - the id of the room you are checking. If this hallway
		 * 			   is connected to it, it returns the id of the room
		 * 		       on the other side. If it does not it returns -1;
		 * 
		 * @return an integer with the adjacent room's id;
		 */
		public int doIConnectWithThisHallway(int id)
		{
			if(connectors[0]==id)
			{
				return connectors[1];
			}
			else
			{
				if(connectors[1]==id)
					return connectors[0];
			}
			return -1;
			
		}
	}
}
