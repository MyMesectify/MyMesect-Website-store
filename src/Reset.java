import javax.swing.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class Reset extends JDialog implements ActionListener {

	private JFrame parent;
	
	Reset (JFrame parent)
	{
		this.parent = parent;
		setSize(400, 250);
		setModal(true);
		setTitle("Reset database");
		setVisible(true);		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}