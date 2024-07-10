/*
 * Graphic User Interface to dynamically define the connection
 * to the online store inventory database
 */
import java.io.File;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.*;


public class InventoryManagerGUI {

	public static JFrame managerFrame;
	private JMenuBar managerMenuBar;
	private JMenu databaseMenu, inventoryMenu, helpMenu;
	private JMenuItem about, dbItem_1, dbItem_2, inventoryItem_1, inventoryItem_2;
	private JPanel activePanel;
	private JTextField usrnameField, pswdField;
	
		
	InventoryManagerGUI ()
	{
		// Menu items
		about = new JMenuItem("About");
		dbItem_1 = new JMenuItem("Configure");
		dbItem_2 = new JMenuItem("Reset");
		inventoryItem_1 = new JMenuItem("Import");
		inventoryItem_2 = new JMenuItem("Update");
		
		// Menus
		databaseMenu = new JMenu("Database");
		inventoryMenu = new JMenu("Inventory");
		helpMenu = new JMenu("Help");
		
		// Menu bar
		managerMenuBar = new JMenuBar();
		
		
		/*
		 *  Laying out the manager window frame
		 */
		managerFrame = new JFrame("MESECT Online Store Inventory Management Utility");
		managerFrame.setSize(600,400);
		
		// Add the menu items to their respective menus
		

		// About menu
		helpMenu.add(about);
		
		// Database menu items
		databaseMenu.add(dbItem_1);
		databaseMenu.add(dbItem_2);
		
		// Inventory menu items
		inventoryMenu.add(inventoryItem_1);
		inventoryMenu.add(inventoryItem_2);
		
		// Add the menus to the menubar
		managerMenuBar.add(databaseMenu);
		managerMenuBar.add(inventoryMenu);
		managerMenuBar.add(helpMenu);
		
		// Add the menu bar to the window frame
		managerFrame.add(managerMenuBar, BorderLayout.NORTH);
		managerFrame.setVisible(true);
		
		
		/*
		 * Add and configure event listener
		 */
		managerFrame.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		
		
		
		/*
		 * Configuring the connect button
		 * to connect with database when it is clicked
		 */
//		dbConnectButton = new Button("Connect");
//		dbConnectButton.setActionCommand("Connect");
//		dbConnectButton.addActionListener(new ButtonClicked());
		
		
	// ********* END OF CONSTRUCTOR ***********
	}
	
	/*
	 * Set the database connection password
	 * to the user supplied password
	 */
	private void setPassword(String password) {
		Product.currentPassword = password;
	}
	
	/*
	 * Set the user name for the database connection
	 * to the user supplied user name
	 */
	private void setUserName(String usrName) {
		Product.userName = usrName;
	}
	
	
	
	private class ButtonClicked implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if(cmd.equals( "Connect" )) {
				setPassword(pswdField.getText());
				setUserName(usrnameField.getText());				
				File sourceFile = new File("C:\\Users\\peter_000\\"
						+ "Downloads\\Laserprinters.htm");
				IntcomexInventory inventory;
				try {
					inventory = new IntcomexInventory(sourceFile);
					inventory.getProducts();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					
				}
								
				managerFrame.dispose();
			}
		}
		
		
		
	}
}
