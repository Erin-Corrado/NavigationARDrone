import java.io.IOException;

import com.leapmotion.leap.*;

import java.util.Iterator;

public class NavMain {
	
	public static void main(String[] args) throws IOException
	{
		try
		{
			Controller controller = new Controller();;
			controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
			LeapInput listener = new LeapInput();
			controller.addListener(listener);
		
			
			while(listener.isRunning)
			{
			
			}
			
		
			listener.cmd.landing();
			System.out.println("shutting down");
			
			if (listener.drone != null)
				listener.drone.stop();
			
			controller.removeListener(listener);
			
		}
		
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
		finally
		{	
				System.exit(0);
		}
		
		
		
		
		
	}

}
