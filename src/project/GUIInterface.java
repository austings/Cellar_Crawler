/* 
 * GUIInterface.java 
 * 
 * Version: 
 *     $Id: GUIInterface.java,v 1.2 2014/12/01 00:54:26 trs5953 Exp $ 
 * 
 * Revisions: 
 *     $Log: GUIInterface.java,v $
 *     Revision 1.2  2014/12/01 00:54:26  trs5953
 *     Project is finished. Bug testing may continue.
 *
 *     Revision 1.5  2014/12/01 00:51:14  ags1098
 *     finished most of project. just bug testing and making sure all is well
 *
 *     Revision 1.4  2014/11/29 03:37:54  ags1098
 *     GUI is finished and began work on communication between model and gui
 *
 *     Revision 1.1  2014/11/29 00:26:52  trs5953
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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import project.RoomGraph.Hallway;


/**
 * The Interface model which builds the window as well as holds the
 * listeners for the game.
 * 
 * @author Austin Sierra
 * @author Tyler Shank
 *
 */
public class GUIInterface implements Observer
{
	private JFrame window;
	
	private JPanel n;
	private JPanel s;
	private JPanel c;
	
	private JMenuBar mb;
	private JMenu file;
	private JMenuItem exit;
	private JMenuItem help;
	
	private JDialog win;
	private JButton winB;
	private String myText;
	
	private JPanel hallPanel;
	private JPanel roomPanel;
	private JPanel itemPanel;
	private JPanel invenPanel;
	
	private JPanel playerPanel;
	private JPanel textPanel;
	
	private JList<String> items;
	private JList<String> inventory;
	private JList<String> buttonList;
	private JTextArea myField;
	
	private JLabel healthD;
	private JLabel expD;
	
	private Model model;
	
	/**
	 * Constructor for the GUIInterface. Will construct the window and call
	 * different methods to construct parts of the panels.
	 */
	public GUIInterface(Model m)
	{
		myText = ("\n----------\n"
				+ "*You heard legends of a mystical artifact known to "
				+ "your "
				+ "people only as, 'The Amulet.' The Amulet was supposed "
				+ "to be lost in this dungeon many years ago. To bring"
				+ " pride to your people and your family, you set off today to"
				+ " bring back the artifact and be known as the ultimate"
				+ " champion. With only the clothes on your back, you enter"
				+ " the dark dungeon...");
		model = m;
		m.addObserver(this);
		//construct JFrame and add JPanels
		window = new JFrame("Austin Sierra ags1098 - Tyler Shank trs5953");
		window.setLayout(new BorderLayout());
		n =  new JPanel();
		n.setLayout(new BorderLayout());
		s = new JPanel();
		s.setLayout(new BorderLayout());
		c = new JPanel();
		c.setLayout(new GridLayout(1,4));
		
		window.add(n, BorderLayout.NORTH);
		window.add(s, BorderLayout.SOUTH);
		window.add(c, BorderLayout.CENTER);
		
		constructNorth();
		constructSouth("");
		constructCenter();
		
		//set close operation
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//set visible
		window.setSize(500,300);
		window.setVisible(true);
		helpPopup();
	}
	
	/**
	 * Construct the top part of the window.
	 */
	public void constructNorth()
	{
		//create Menu and MenuItems
		mb = new JMenuBar();
		file = new JMenu("File");
		exit = new JMenuItem("Exit");
		help = new JMenuItem("Help");
		
		//add actionlisteners
		MenuListener ml = new MenuListener();
		exit.addActionListener(ml);
		help.addActionListener(ml);
		
		//add menu and menuitems
		file.add(exit);
		file.add(help);
		mb.add(file);
		JLabel room = new JLabel();
		room.setText("You are in room " + model.getPlayer().getRoom());
		n.add(mb, BorderLayout.NORTH);
		n.add(room, BorderLayout.CENTER);
		
		//add titles
		JPanel titles = new JPanel();
		titles.setLayout(new GridLayout(1,4));
		JLabel hTitle = new JLabel("       Hallways");
		titles.add(hTitle);
		JLabel rTitle = new JLabel("       Room Info");
		titles.add(rTitle);
		JLabel iTitle = new JLabel("       Items in Room");
		titles.add(iTitle);
		JLabel inTitle = new JLabel("       Inventory");
		titles.add(inTitle);
		n.add(titles, BorderLayout.SOUTH);
	}
	
	/**
	 * Construct the center part of the JWindow.
	 */
	public void constructCenter(){
		//Create the panels
		hallPanel = new JPanel();
		roomPanel = new JPanel();
		itemPanel = new JPanel();
		invenPanel = new JPanel();
		
		//add content to hallPanel
		hallPanel.setLayout(new GridLayout(0,1));
		//create a list of buttons
		ArrayList<String> buttonArr = new ArrayList<String>();
		for(int x = 0; x < model.getHallways().size(); x++)
		{
			Hallway temp =model.getHallways().get(x);
			if(temp.doIConnectWithThisHallway(model.getPlayer().getRoom())!=-1)
			{
				buttonArr.add(temp.getName());
			}
			
		}
		String[] tempArr = new String[buttonArr.size()];
		buttonList = new JList<String>(buttonArr
				.toArray(tempArr));
		buttonList.addListSelectionListener(new MoveListener());
		JScrollPane bl = new JScrollPane(buttonList);
		hallPanel.add(bl);
		c.add(hallPanel);
		
		//add content to roomPanel
		roomPanel.setLayout(new GridLayout(0,1));
		JTextArea rInfo = new JTextArea();
		rInfo.setLineWrap(true);
		rInfo.setWrapStyleWord(true);
		rInfo.setEditable(false);
		rInfo.setText("You are in room " + model.getPlayer().getRoom());
		rInfo.setText(rInfo.getText()+"\n"+model.getRoom(model.getPlayer()
				.getRoom()).getSetting());
		JScrollPane rp = new JScrollPane(rInfo);
		roomPanel.add(rp);
		c.add(roomPanel);
		
		//add content to itemPanel
		itemPanel.setLayout(new GridLayout(0,1));
		//display items in room
		ArrayList<RoomObject> tempRO = model.getRoom(model.getPlayer()
				.getRoom()).getROs();
		//remove anything that isnt an instance of item.
		for(int j = 0;j<tempRO.size();j++)
		{
			if(!(tempRO.get(j) instanceof Item))
			{
				tempRO.remove(j);
				j = j-1;
			}
		}
		
		String[] itemsA = new String[tempRO.size()];
		for(int j =0;j<tempRO.size();j++)
		{
			itemsA[j] = ((Item)tempRO.get(j)).getName();
		}
		items = new JList<String>(itemsA);
		JScrollPane il = new JScrollPane(items);
		itemPanel.add(il);
		items.addListSelectionListener(new PickUpListener());
		c.add(itemPanel);
		
		//add content to invenPanel
		invenPanel.setLayout(new GridLayout(0,1));
		//display items in inventory
		String[] in = new String[model.getPlayer().getItems().size()];
		for(int i = 0; i < model.getPlayer().getItems().size(); i++){
			in[i] = model.getPlayer().getItems().get(i).getName();
		}
		inventory = new JList<String>(in);
		JScrollPane iil = new JScrollPane(inventory);
		invenPanel.add(iil);
		inventory.addListSelectionListener(new DropListener());
		c.add(invenPanel);
	}
	
	/**
	 * Construct the south part of the JWindow
	 */
	public void constructSouth(String text)
	{
		//construct panels
		playerPanel = new JPanel();
		playerPanel.setLayout(new GridLayout(4,1));
		textPanel = new JPanel();
		textPanel.setLayout(new BorderLayout());
		
		//construct text pane field
		myField = new JTextArea();
		myField.setEditable(false);
		myField.setLineWrap(true);
		myField.setWrapStyleWord(true);
		myField.setText(myText+text);
		myText = myField.getText();
		JScrollPane jsp = new JScrollPane(myField);
		jsp.setPreferredSize(new Dimension(400,60));
		textPanel.add(jsp, BorderLayout.CENTER);
		
		//construct player pane
		JLabel health = new JLabel("       Health:");
		healthD= new JLabel("          "+model.getPlayer().getHealth());
		JLabel exp = new JLabel("   Experience:   ");
		expD = new JLabel("            "+ model.getPlayer().getLevel());
		playerPanel.add(health);
		playerPanel.add(healthD);
		playerPanel.add(exp);
		playerPanel.add(expD);
		
		//add to south panel
		s.add(textPanel, BorderLayout.CENTER);
		s.add(playerPanel,BorderLayout.WEST);
		
		
	}
	
	/**
	 * Call this method when the player has won.
	 */
	public void winPopup()
	{
		//create dialog
		win = new JDialog(window);
		win.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		win.setModal(true);
		JLabel text = new JLabel("You Win!");
		winB = new JButton("Champion of the Amulet!");
		
		//add actionlistener
		WinListener huzzuh = new WinListener();
		winB.addActionListener(huzzuh);
		
		//add components
		win.setTitle(text.getText());
		win.add(winB);
		win.setSize(window.getWidth(), window.getHeight());
		win.setLocation(window.getX(), window.getY());
		win.setVisible(true);
		
	}

	/**
	 * This is the window for when the player loses.
	 */
	public void losePopup()
	{
		//create dialog
		win = new JDialog(window);
		win.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		win.setModal(true);
		JLabel text = new JLabel("You Died!");
		winB = new JButton("What a horrible fate.");
		
		//add actionlistener
		WinListener huzzuh = new WinListener();
		winB.addActionListener(huzzuh);
		
		//add components
		win.setTitle(text.getText());
		win.add(winB);
		win.setSize(window.getWidth(), window.getHeight());
		win.setLocation(window.getX(), window.getY());
		win.setVisible(true);
	}
	
	/**
	 * This window tells the player how to play the game.
	 */
	public void helpPopup(){
		String help = "Click a hallway to move to a new room, "
				+ "an item on the ground to pick it up, \n"
				+ "or an item in your inventory to drop it. "
				+ "Search for the amulet but be wary of \ntraps and "
				+ "the mysterious monster.";
		final JOptionPane pane = new JOptionPane(help);
		final JDialog dialog = pane.createDialog(window, "Help");
		dialog.setLocation(dialog.getParent().getX(),
				dialog.getParent().getY());
		dialog.setModal(true);
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}
	
	/**
	 * This is the window which displays if the player performs an illegal action
	 * 
	 * @param error	- the error message to display.
	 */
	public static void errorPopup(String error){
		JOptionPane pane = new JOptionPane(error);
		pane.setVisible(true);
	}
	
	/**
	 * This is the listener for all menu items
	 * 
	 * @author Austin Sierra
	 * @author Tyler Shank
	 *
	 */
	class MenuListener implements ActionListener
	{
		/**
		 * This is the method which does an action after an event has been
		 * made by the user.
		 * @param e - the event which caused this method to be called
		 */
		public void actionPerformed(ActionEvent e) {
			
			//if the user clicked 'Exit'
			if(e.getSource().equals(exit))
			{
				//exit the program
				window.dispose();
				System.exit(0);
				
			}
			else if(e.getSource().equals(help)){
				helpPopup();
			}
			
		}
		
	}
	
	/**
	 * This is the listener for selecting an item in the on the ground list
	 * 
	 * @author Austin Sierra
	 * @author Tyler Shank
	 *
	 */
	class PickUpListener implements ListSelectionListener
	{
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(!(items.isSelectionEmpty())&&!(e.getValueIsAdjusting()))
			{
				//if the selection isn't empty pass it into the model
				String name = items.getSelectedValue();
				model.pickUp(name);
			}
		}
		
	}
	
	/**
	 * This is the listener for dropping an item from the inventory
	 * 
	 * @author Austin Sierra
	 * @author Tyler Shank
	 *
	 */
	class DropListener implements ListSelectionListener{
		public void valueChanged(ListSelectionEvent e){
			if(!inventory.isSelectionEmpty()&&!(e.getValueIsAdjusting())){
				model.drop(inventory.getSelectedValue());
			}
		}
	}
	
	/**
	 * This is the listener for selecting a hallway to move down.
	 * @author Austin Sierra
	 * @author Tyler Shank
	 *
	 */
	class MoveListener implements ListSelectionListener
	{

		/**
		 * This method is called whenver a hallway is selected.
		 * @param e - the source of the event 
		 */
		public void valueChanged(ListSelectionEvent e) {
			if(!buttonList.isSelectionEmpty()&&!(e.getValueIsAdjusting())){
				model.move(buttonList.getSelectedValue());
			}
			
		}
		
	}
	
	/**
	 * This is the listener for the win/lose dialog boxes
	 * @author Austin Sierra
	 * @author Tyler Shank
	 *
	 */
	class WinListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(winB))
			{
				win.dispose();
				window.dispose();
				System.exit(0);
			}
			
		}
		
	}

	/**
	 * Updates the GUI after a change has occurred in the model.
	 */
	public void update(Observable m, Object text) 
	{
		window.remove(n);
		window.remove(c);
		window.remove(s);
		n =  new JPanel();
		n.setLayout(new BorderLayout());
		s = new JPanel();
		s.setLayout(new BorderLayout());
		c = new JPanel();
		c.setLayout(new GridLayout(1,4));
		window.add(n, BorderLayout.NORTH);
		window.add(s, BorderLayout.SOUTH);
		window.add(c, BorderLayout.CENTER);
		constructNorth();
		constructCenter();
		constructSouth((String) text);
		window.validate();
		
	}
}
