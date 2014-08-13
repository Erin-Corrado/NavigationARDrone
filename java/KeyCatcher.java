import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

import de.yadrone.base.IARDrone;

public class KeyCatcher extends JFrame implements KeyListener {
	
	protected IARDrone drone;
	int speed;
	JTextField KeyCode = new JTextField("Key Code:");
	
	public KeyCatcher(IARDrone input)
	{
		drone = input;
		speed = 20;
		KeyCode.addKeyListener(this);
		KeyCode.setEditable(false);
		add(KeyCode);
		setSize(300,300);
		setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		handleCommand(key);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		drone.getCommandManager().hover();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void handleCommand(int key)
	{
		switch(key)
		{
			case (KeyEvent.VK_O):
				drone.getCommandManager().up(speed);
				System.out.println("Go up");
				break;
				
			case (KeyEvent.VK_L):
				drone.getCommandManager().down(speed);
				System.out.println("Go down");
				break;
		}
	}

	
}
