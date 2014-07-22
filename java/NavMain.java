import java.io.IOException;
import com.leapmotion.leap.*;
import de.yadrone.base.IARDrone;
import de.yadrone.base.ARDrone;
import de.yadrone.base.command.CommandManager;
import de.yadrone.base.command.LEDAnimation;

import javax.swing.JFrame;
import javax.swing.JTextField;

class LeapInput extends Listener {
	private Robutt controllee;

	public LeapInput(Robutt rob) {
		controllee = rob;
	}

    public void onInit(Controller controller) {
        System.out.println("Initialized");
    }

    public void onConnect(Controller controller) {
        System.out.println("Connected");
    }

    public void onDisconnect(Controller controller) {
        System.out.println("Disconnected");
    }

    public void onExit(Controller controller) {
        System.out.println("Exited");
    }

	public void onFrame(Controller controller) {
		Frame frame = controller.frame();
		if (frame.hands().isEmpty())
			return;

		Hand hand = frame.hands().get(0);

		Vector normal = hand.palmNormal();
		Vector direction = hand.direction();

		System.out.println("roll: " + Math.toDegrees(normal.roll()) + " degrees, "
						   + "yaw: " + Math.toDegrees(direction.yaw()) + " degrees\n");

		if (Math.toDegrees(direction.yaw()) > 10)
		{
			controllee.turnRight();
		}
		else if (Math.toDegrees(direction.yaw())< -10)
		{
			controllee.turnLeft();
		}

		else if (Math.toDegrees(normal.roll()) > 10)
		{
			controllee.goRight();
		}

		else if (Math.toDegrees(normal.roll()) < -10)
		{
			controllee.goLeft();
		}
	}
}

public class NavMain {

	public static void main(String[] args)
	{
		//IARDrone drone = null;
		JFrame window = new JFrame("Navigation");
		window.setBounds(50, 100, 300, 300);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTextField txt = new JTextField();
		Key k = new Key();
		txt.addKeyListener(k);
		window.add(txt);
		window.setSize(300,300);
		window.setVisible(true);

		ConsoleRobutt cr = new ConsoleRobutt();
		//Leap Stuff
		Controller controller = new Controller();
		LeapInput listener = new LeapInput(cr);
		controller.addListener(listener);

		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		controller.removeListener(listener);
		/*
		
		try
		{
			//AR Drone stuff
			//drone = new ARDrone();
			//drone.start();
			boolean isRunning = true;
			//int speed = 30;
			//CommandManager cmd = drone.getCommandManager();
			//cmd.setLedsAnimation(LEDAnimation.BLINK_ORANGE, 3, 10);
			//cmd.takeOff();
			//cmd.waitFor(5000);

			while(isRunning)
			{
				if(k.lastChar == 'q')
				{
					isRunning = false;
					//drone.landing();
					System.out.println("Landing");
				}
			}
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
		finally
		{
			//if (drone != null)
			//	drone.stop();
			System.exit(0);
			}*/
	}
}
