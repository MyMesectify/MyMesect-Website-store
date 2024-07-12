import javax.swing.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class UpdateDB extends JDialog implements ActionListener {

	private JFrame parent;
	
	UpdateDB (JFrame parent)
	{
		this.parent = parent;
		setSize(400, 250);
		setModal(true);
		setTitle("Update inventory");
		setVisible(true);		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}