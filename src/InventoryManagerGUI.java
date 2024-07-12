/*
 * Graphic User Interface to dynamically define the connection
 * to the online store inventory database
 */
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class InventoryManagerGUI implements ActionListener {

	public static JFrame managerFrame;
	private JMenuBar managerMenuBar;
	private JMenu database, inventory, help;
	private JMenuItem about, configure, reset, imporT, update;
	private JPanel activePanel = new JPanel();

		
	InventoryManagerGUI ()
	{
		/*
		 *  Laying out the manager window frame
		 */
		managerFrame = new JFrame("MESECT Online Store Inventory Management Utility");
		managerFrame.setSize(600,400);
		
		
		// Menu items
		about = new JMenuItem("About");
		configure = new JMenuItem("Configure");
		reset = new JMenuItem("Reset");
		imporT = new JMenuItem("Import");
		update = new JMenuItem("Update");
		
		// Menus
		database = new JMenu("Database");
		inventory = new JMenu("Inventory");
		help = new JMenu("Help");
		
		// Menu bar
		managerMenuBar = new JMenuBar();
		
		
		// *** Add the menu items to their respective menus ***
		
		// About menu
		help.add(about);
		
		// Database menu items
		database.add(configure);
		database.add(reset);
		
		// Inventory menu items
		inventory.add(imporT);
		inventory.add(update);
		
		// Add the menus to the menubar
		managerMenuBar.add(database);
		managerMenuBar.add(inventory);
		managerMenuBar.add(help);
		
		// Add the menu bar to the window frame
		managerFrame.add(managerMenuBar, BorderLayout.NORTH);		
		
		// Add and configure action listener to manager window
		managerFrame.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) 
			{
				System.exit(0);
			}
		});		
		
		// Add action listener to menu selections
		configure.addActionListener(this);
		reset.addActionListener(this);
		imporT.addActionListener(this);
		update.addActionListener(this);
		about.addActionListener(this);
		
		managerFrame.setVisible(true);
	}
	
	// Execute actions subject to menu selection 
		@Override
		public void actionPerformed(ActionEvent e) {
			
			// Open the database configuration window
			if( e.getSource() == configure)
			{
				new ConfigureDB(managerFrame);
			}
			
			// Open the reset database window
			if( e.getSource() == reset)
			{
				new Reset(managerFrame);
			}
			
			// Open the import inventory window
			if( e.getSource() == imporT)
			{
				new ImportInventory(managerFrame);
			}
			
			/// Open the database update window
			if( e.getSource() == update)
			{
				new UpdateDB(managerFrame);
			}
			
			// Open this window for information about the utility 
			if( e.getSource() == about)
			{				
				JOptionPane.showMessageDialog(managerFrame, 
						"This utility is used for acquiring\n"
						+ "inventory details from MESECT's\n"
						+ "suppliers, to populate and update\n"
						+ "our online store.\n\n"
						+ "Author: Peter D Morris");
				
			}
		
			
		}

	
		
		
}
