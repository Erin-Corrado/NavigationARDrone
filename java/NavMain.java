import java.io.IOException;
import com.leapmotion.leap.*;

public class NavMain {
	public static void main(String[] args)
	{
		//IARDrone drone = null;
		ConsoleRobutt cr = new ConsoleRobutt();
		//LegoRobutt leggoMyEggo = new LegoRobutt();
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
	}
}
