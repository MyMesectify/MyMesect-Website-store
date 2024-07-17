import javax.swing.*;
import java.io.File;
import java.awt.event.*;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ImportInventory extends JDialog implements ActionListener {

	private JPanel importPanel = new JPanel();
	private JLabel source, supplier, statusReport;
	private JTextField sourceFile;
	private DefaultComboBoxModel<String> supplierList = new DefaultComboBoxModel<>(); 
	private JComboBox<String> theSuppliers;
	private ArrayList<String> suppliers = new ArrayList<String>();
	private JButton findFile = new JButton("Find")
			, addSupplier = new JButton("Add")
			, importButton = new JButton("Import");
	private int selectedSupplier;
	private JFileChooser search4File = new JFileChooser();
	
	ImportInventory (JFrame parent)
	{
		suppliers.add("Intcomex");
		setSize(400, 150);
		setModal(true);
		setTitle("Import inventory");
		
		// Setup the layout for the panel
		GroupLayout importLayout = new GroupLayout(importPanel);
		importPanel.setLayout(importLayout);
		importLayout.setAutoCreateContainerGaps(true);
		importLayout.setAutoCreateGaps(true);
		
		// Configure panel components
		source = new JLabel("Source file:");
		supplier = new JLabel("Supplier:");
		statusReport = new JLabel("Waiting for inventory...");
		sourceFile = new JTextField();
		
		setSuppliers(suppliers);
		
		/*
		 *  Layout panel components
		 */
		// Layout the horizontal groups
		importLayout.setHorizontalGroup(
				importLayout.createSequentialGroup()
				
				// First column
				.addGroup(importLayout.createParallelGroup
						(GroupLayout.Alignment.TRAILING)
						.addComponent(source)
						.addComponent(supplier)
						.addComponent(importButton)
						)
				
				// Second column
				.addGroup(importLayout.createParallelGroup
						(GroupLayout.Alignment.LEADING)
						.addComponent(sourceFile)
						.addComponent(theSuppliers)
						.addComponent(statusReport)
						)
				
				// Third column
				.addGroup(importLayout.createParallelGroup
						(GroupLayout.Alignment.LEADING)
						.addComponent(findFile)
						.addComponent(addSupplier)
						)
				);
		
		// Layout the vertical groups
		importLayout.setVerticalGroup(
				importLayout.createSequentialGroup()
				
				// First row
				.addGroup(importLayout.createParallelGroup(
						GroupLayout.Alignment.BASELINE)
						.addComponent(source)
						.addComponent(sourceFile)
						.addComponent(findFile)
						)
				
				// Second row
				.addGroup(importLayout.createParallelGroup(
						GroupLayout.Alignment.BASELINE)
						.addComponent(supplier)
						.addComponent(theSuppliers)
						.addComponent(addSupplier)
						)
				
				// Third row
				.addGroup(importLayout.createParallelGroup(
						GroupLayout.Alignment.CENTER)
						.addComponent(importButton)
						.addComponent(statusReport)
						)				
				);
		
		// Add listener to the buttons
		importButton.addActionListener(this);
		addSupplier.addActionListener(this);
		findFile.addActionListener(this);
		theSuppliers.addActionListener(this);
		
		/*
		 *  Set the selected supplier to the default item,
		 *  after the combobox is added to the panel,
		 *  and a listener is assigned to it.
		 */		
		selectedSupplier = theSuppliers.getSelectedIndex() + 1;
		
		add(importPanel);
		setVisible(true);		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		// Listen for the import button
		if ( e.getSource() == importButton )
		{
			statusReport.setText("Import inventory from supplier");
			try {
				Main.inventoryDefinition(
						sourceFile.getText(), selectedSupplier);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this, e1);
				e1.printStackTrace();
			}
		}
		
		// Listen for the add supplier button
		if ( e.getSource() == addSupplier )
		{
			statusReport.setText("Add a new supplier");
		}
		
		// Listen for the find file button
		if ( e.getSource() == findFile )
		{
			statusReport.setText("Search for inventory file");
			search4File.setCurrentDirectory(new File
					(System.getProperty("user.home")));
			int selectedFile = search4File.showOpenDialog(this);
			if ( selectedFile == search4File.APPROVE_OPTION )
			{
				sourceFile.setText(
						search4File.getSelectedFile().getAbsolutePath()
						);
			}
			
		}
		
		// Listed to the combobox selection
		
		if ( e.getSource() == theSuppliers )
		{
			selectedSupplier = theSuppliers.getSelectedIndex() + 1;
		}

		
	}

	public void setSuppliers(ArrayList<String> listOfSuppliers)
	{
		listOfSuppliers.forEach( 
				supplier -> 
				{
					if ( ! suppliers.contains(supplier) )
					{ 
						suppliers.add(supplier);
					}
				});
		
		suppliers.forEach(
				supplier ->
				supplierList.addElement(supplier)
				);
		
		theSuppliers = new JComboBox<>(supplierList);
		
	}
	
}