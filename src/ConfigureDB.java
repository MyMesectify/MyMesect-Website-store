import javax.swing.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class ConfigureDB extends JDialog implements ActionListener {

	private JFrame parent;
	
	ConfigureDB (JFrame parent)
	{
		this.parent = parent;
		setSize(400, 250);
		setModal(true);
		setTitle("Configure database");
		setVisible(true);		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}