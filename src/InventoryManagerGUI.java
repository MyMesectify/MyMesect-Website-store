/*
 * Graphic User Interface to dynamically define the connection
 * to the online store inventory database
 */
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class InventoryManagerGUI {

	public static Frame managerFrame;
	private JSeparator sectionDivisor;
	private JPanel dataConfigPanel, actionPanel, labelPanel;
	private TextField usrnameField, pswdField;
	private JLabel sectionLabel1, sectionLabel2, dbUsername, dbPassword;
	private Button dbConnectButton, actionButton1, actionButton2, 
	actionButton3, actionButton4, actionButton5, actionButton6;
		
	InventoryManagerGUI ()
	{
		managerFrame = new Frame("MESECT Inventory Management Utility");
		managerFrame.setSize(500,350);
		sectionDivisor = new JSeparator();
		dataConfigPanel = new JPanel();
		dataConfigPanel.setSize(350,250);
		actionPanel = new JPanel();
		labelPanel = new JPanel();
		//BorderLayout frameLayout = new BorderLayout();
		GridBagLayout panelLayout = new GridBagLayout();
		dataConfigPanel.setLayout(panelLayout);
		actionPanel.setLayout(panelLayout);
		//labelPanel.setLayout(panelLayout);
		managerFrame.setLayout(panelLayout);
		GridBagConstraints gbLimits = new GridBagConstraints();
		
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
		dbConnectButton = new Button("Connect");
		dbConnectButton.setActionCommand("Connect");
		dbConnectButton.addActionListener(new ButtonClicked());
		
		
		/*
		 * set label(s)
		 */
		sectionLabel1 = new JLabel("Database & Data Source");
		sectionLabel2 = new JLabel("Inventory Maintenance Tasks");
		dbUsername = new JLabel("Username:");
		dbPassword = new JLabel("Password:");
		dbUsername.setHorizontalAlignment(JLabel.RIGHT);
		dbPassword.setHorizontalAlignment(JLabel.RIGHT);
		
		/*
		 * set text fields
		 * for database credentials
		 */
		usrnameField = new TextField("Petrodjan", 25);
		pswdField = new TextField(25);
		//usrnameField.setEnabled(false);
		
		/*
		 * create the action buttons
		 */
		actionButton1 = new Button("Action 1");
		actionButton2 = new Button("Action 2");
		actionButton3 = new Button("Action 3");
		actionButton4 = new Button("Action 4");
		actionButton5 = new Button("Action 5");
		actionButton6 = new Button("Action 6");
		
		
		
		/*
		 * Set the layout the database credential
		 * fields
		 */
		gbLimits.fill = GridBagConstraints.VERTICAL;
		gbLimits.gridx = 0;
		gbLimits.gridy = 0;
		dataConfigPanel.add(sectionLabel1, gbLimits);
				
		gbLimits.gridx = 0; 
		gbLimits.gridy = 1; 
		dataConfigPanel.add(sectionDivisor,
				gbLimits);
		 		
		gbLimits.gridx = 0;
		gbLimits.gridy = 2;
		dataConfigPanel.add(dbUsername, gbLimits);
				
		gbLimits.gridx = 1;
		gbLimits.gridy = 2;
		dataConfigPanel.add(usrnameField, gbLimits);
				
		gbLimits.gridx = 0;
		gbLimits.gridy = 3;
		dataConfigPanel.add(dbPassword, gbLimits);
				
		gbLimits.gridx = 1;
		gbLimits.gridy = 3;
		dataConfigPanel.add(pswdField, gbLimits);
		
		gbLimits.gridx = 0;
		gbLimits.gridy = 4;
		dataConfigPanel.add(dbConnectButton, gbLimits);
		
		
		/*
		 * Set the layout of the action buttons 
		 */
		
		gbLimits.gridx = 0;
		gbLimits.gridy = 0;
		labelPanel.add(sectionLabel2, gbLimits);
		
		gbLimits.gridx = 0;
		gbLimits.gridy = 1;
		actionPanel.add(actionButton1, gbLimits);
		
		gbLimits.gridx = 1;
		gbLimits.gridy = 1;
		actionPanel.add(actionButton2, gbLimits);
		
		gbLimits.gridx = 2;
		gbLimits.gridy = 1;
		actionPanel.add(actionButton3, gbLimits);
		
		gbLimits.gridx = 3;
		gbLimits.gridy = 1;
		actionPanel.add(actionButton4, gbLimits);
		
		gbLimits.gridx = 1;
		gbLimits.gridy = 2;
		actionPanel.add(actionButton5, gbLimits);
		
		gbLimits.gridx = 2;
		gbLimits.gridy = 2;
		actionPanel.add(actionButton6, gbLimits);
		
		
		/*
		 * Layout the manager frame
		 */
			
		//Add the database configuration panel
		gbLimits.gridx = 0;
		gbLimits.gridy = 0;
		managerFrame.add(dataConfigPanel, gbLimits);
		
		//Add section label as separator
		gbLimits.gridx = 0;
		gbLimits.gridy = 1;
		managerFrame.add(labelPanel, gbLimits);
		
		//Add the action buttons panel
		gbLimits.gridx = 0;
		gbLimits.gridy = 2;
		managerFrame.add(actionPanel, gbLimits);
		
		//Show the manager frame with the panels
		managerFrame.setVisible(true);
		
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
