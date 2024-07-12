import javax.swing.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class ImportInventory extends JDialog implements ActionListener {

	private JFrame parent;
	
	ImportInventory (JFrame parent)
	{
		this.parent = parent;
		setSize(400, 250);
		setModal(true);
		setTitle("Import inventory");
		setVisible(true);		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}