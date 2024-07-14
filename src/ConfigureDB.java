import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class ConfigureDB extends JDialog implements ActionListener {

	private JFrame parent;
	private static String dbFile, dbPath, dbUser, dbUserPassword;
	
	// Create components for this window
	JPanel dbDetailsPanel = new JPanel();
	private JButton findFile = new JButton("Find");
	private JButton connectDB = new JButton("Test connection");
	private JLabel dbFileName, userName, userPassword;
	private JTextField fileName, usrName, password;
	
	ConfigureDB (JFrame parent)
	{
		this.parent = parent;
		setSize(400, 250);
		setModal(true);
		setTitle("Configure database");
		
		// Assign layout manager(s) to organize components
		GroupLayout layoutView = new GroupLayout(dbDetailsPanel);
		dbDetailsPanel.setLayout(layoutView);
		layoutView.setAutoCreateContainerGaps(true);
		layoutView.setAutoCreateGaps(true);
		
				
		/*
		 * Create fields and field labels,
		 * for user input of database,
		 * and connection string details 
		*/ 
				
		// Create and configure components
		fileName = new JTextField();
		usrName = new JTextField();
		password = new JTextField();
		dbFileName = new JLabel("File :"); 
		userName = new JLabel("User :");
		userPassword = new JLabel("Pasword :");
		
		/* 
		 * 	Add components to panel using GroupLayout
		 */
		
		// Add horizontal grouping
		layoutView.setHorizontalGroup(
				layoutView.createSequentialGroup()
									
				// First column of components
				.addGroup(layoutView.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(dbFileName)
						.addComponent(userName)
						.addComponent(userPassword)
						)
				
				// Second column of components
				.addGroup(layoutView.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(fileName)
						.addComponent(usrName)
						.addComponent(password)
						)
				
				// Third column of components
				.addGroup(layoutView.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(findFile)
						)
				
				);
		
		// Add vertical grouping
		layoutView.setVerticalGroup(layoutView.createSequentialGroup()
				
				// First row of components
				.addGroup(layoutView.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(dbFileName)
						.addComponent(fileName)
						.addComponent(findFile)
						)
				
				// Second row of components
				.addGroup(layoutView.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(userName)
						.addComponent(usrName)
						)
				
				// Third row of components
				.addGroup(layoutView.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(userPassword)
						.addComponent(password)						
						)
				);
		
				
	
		add(dbDetailsPanel);
		add(connectDB, BorderLayout.SOUTH);
		
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
			System.out.print("Connect to database\n");
		}
		
		if ( e.getSource() == findFile )
		{
			System.out.print("Find file\n");
		}
	}
	
}