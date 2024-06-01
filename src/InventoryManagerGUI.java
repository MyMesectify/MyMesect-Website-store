/*
 * Graphic User Interface to dynamically define the connection
 * to the online store inventory database
 */
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;

public class InventoryManagerGUI {

	public static Frame managerFrame;
	private JSeparator sectionDivisor;
	private Panel dataConfigPanel;
	private TextField usrnameField;
	private TextField pswdField;
	private Label sectionLabel1;
	private Label sectionLabel2;
	private Label dbUsername;
	private Label dbPassword;	
	private Button dbConnectButton;
		
	InventoryManagerGUI ()
	{
		managerFrame = new Frame("MESECT Inventory Management Utility");
		managerFrame.setSize(600,350);
		sectionDivisor = new JSeparator();
		dataConfigPanel = new Panel();
		dataConfigPanel.setSize(15,30);
		BorderLayout frameLayout = new BorderLayout();
		GridBagLayout panelLayout = new GridBagLayout();
		dataConfigPanel.setLayout(panelLayout);
		managerFrame.setLayout(frameLayout);
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
		sectionLabel1 = new Label("Database & Data Source");
		sectionLabel2 = new Label("Inventory Maintenance");
		dbUsername = new Label("Username:");
		dbPassword = new Label("Password:");
		dbUsername.setAlignment(Label.RIGHT);
		dbPassword.setAlignment(Label.RIGHT);
		
		/*
		 * set text fields
		 * for database credentials
		 */
		usrnameField = new TextField("Petrodjan", 25);
		pswdField = new TextField(25);
		//usrnameField.setEnabled(false);
		
		/*
		 * Layout the database credential
		 * fields
		 */
		gbLimits.fill = GridBagConstraints.VERTICAL;
		gbLimits.gridx = 0;
		gbLimits.gridy = 0;
		dataConfigPanel.add(sectionLabel1, gbLimits);
				
		/*
		 * gbLimits.gridx = 0; gbLimits.gridy = 1; dataConfigPanel.add(sectionDivisor,
		 * gbLimits);
		 */
		
		gbLimits.gridx = 0;
		gbLimits.gridy = 1;
		dataConfigPanel.add(dbUsername, gbLimits);
				
		gbLimits.gridx = 1;
		gbLimits.gridy = 1;
		dataConfigPanel.add(usrnameField, gbLimits);
				
		gbLimits.gridx = 0;
		gbLimits.gridy = 2;
		dataConfigPanel.add(dbPassword, gbLimits);
				
		gbLimits.gridx = 1;
		gbLimits.gridy = 2;
		dataConfigPanel.add(pswdField, gbLimits);
		
		gbLimits.gridx = 0;
		gbLimits.gridy = 3;
		dataConfigPanel.add(dbConnectButton, gbLimits);
		
		managerFrame.add(dataConfigPanel,BorderLayout.NORTH);
		managerFrame.setVisible(true);
		managerFrame.pack();
	}
	
	private void setPassword(String password) {
		Product.currentPassword = password;
	}
	
	private void setUserName(String usrName) {
		Product.userName = usrName;
	}
	
	private class ButtonClicked implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if(cmd.equals( "Connect" )) {
				setPassword(pswdField.getText());
				setUserName(usrnameField.getText());				
				File file = new File("C:\\Users\\peter_000\\"
						+ "Downloads\\Laserprinters.htm");
				IntcomexInventory inventory;
				try {
					inventory = new IntcomexInventory(file);
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
