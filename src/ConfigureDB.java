import javax.swing.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class ConfigureDB extends JDialog implements ActionListener {

	// Create components for this window
	JPanel dbConnectPanel = new JPanel();
	private JButton findFile = new JButton("Find");
	private JButton connectDB = new JButton("Connect");
	private JLabel userName, userPassword, statusInfo;
	private JTextField usrName, password;
	
	ConfigureDB (JFrame parent)
	{	
		
		setSize(400, 140);
		setModal(true);
		setTitle("Connect database");
		
		// Assign layout manager(s) to organize components
		GroupLayout layoutView = new GroupLayout(dbConnectPanel);
		dbConnectPanel.setLayout(layoutView);
		layoutView.setAutoCreateContainerGaps(true);
		layoutView.setAutoCreateGaps(true);
		
				
		/*
		 * Create fields and field labels,
		 * for user input of database,
		 * and connection string details 
		*/ 
				
		// Create and configure components		
		usrName = new JTextField();
		password = new JTextField();
		userName = new JLabel("User:");
		userPassword = new JLabel("Pasword:");
		statusInfo = new JLabel("Waiting for connection...");
		
		/* 
		 * 	Add components to panel using GroupLayout
		 */
		
		// Add horizontal grouping
		layoutView.setHorizontalGroup(
				layoutView.createSequentialGroup()
									
				// First column of components
				.addGroup(layoutView.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addComponent(userName)
						.addComponent(userPassword)
						.addComponent(connectDB)
						)
				
				// Second column of components
				.addGroup(layoutView.createParallelGroup(GroupLayout.Alignment.LEADING)						
						.addComponent(usrName)
						.addComponent(password)
						.addComponent(statusInfo)
						)
				
				);
		
		// Add vertical grouping
		layoutView.setVerticalGroup(layoutView.createSequentialGroup()
				
				// First row of components				
				.addGroup(layoutView.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(userName)
						.addComponent(usrName)						
						)
				
				// Second row of components
				.addGroup(layoutView.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(userPassword)
						.addComponent(password)
						)
				
				// Third row of components
				.addGroup(layoutView.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(connectDB)
						.addComponent(statusInfo)						
						)
				);
		
				
	
		add(dbConnectPanel);
		
		// Add action listener to JButton(s)
		connectDB.addActionListener(this);
		findFile.addActionListener(this);

		setVisible(true);		
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if ( e.getSource() == connectDB )
		{
			Main.userName = usrName.getText();
			Main.userPassword = password.getText();
			statusInfo.setText(Main.setConnection());
		}
		
		if ( e.getSource() == findFile )
		{
			System.out.print("Find file\n");
		}
	}
	
	
	
}