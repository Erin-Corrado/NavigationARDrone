import com.leapmotion.leap.*;

import de.yadrone.base.*;
import de.yadrone.base.command.CommandManager;


public class LeapInput extends Listener {
	
	public boolean isRunning;
	public IARDrone drone;
	public CommandManager cmd;
	public int speed;
	
			public LeapInput()
			{
				isRunning = true;
				drone = new ARDrone();
				drone.start();
				
				cmd = drone.getCommandManager();
				speed = 20;
				cmd.takeOff();
			}
	
			public void onConnect()
			{
				System.out.println("Connected");
			}
			
			public void onDisconnect(Controller controller) 
			 	{
			        System.out.println("Disconnected");
			    }

			public void onExit(Controller controller) 
				{
			        System.out.println("Exited");
			    }

			public void onFrame(Controller controller)
			{
				Frame frame = controller.frame();
				
				if (frame.hands().isEmpty())
				{
					System.out.println("Hand not detected");
					return;
				}
				
				Hand hand = frame.hands().get(0);
				
				GestureList g = frame.gestures();
        	    for (Gesture gesture : g)
        	    {
        	    	if(gesture.type() == Gesture.Type.TYPE_CIRCLE) 
        	    	{
        	    		CircleGesture circle = new CircleGesture(gesture);
        	    		String clockwiseness;
        	    		
        	    		if(circle.pointable().direction().angleTo(circle.normal()) <= Math.PI/2)
        	    		{
        	    			clockwiseness = "clockwise";
        	    		}
        	    		
        	    		else
        	    		{
        	    			clockwiseness = "counterclockwise";
        	    		}
        	    		
        	    		if(clockwiseness.equals("clockwise"))
        	    		{
        	    			cmd.spinRight(speed).doFor(1000).hover();
        	    			System.out.println("Turn right");
        	    		}
        	    		
        	    		else
        	    		{
        	    			cmd.spinLeft(speed).doFor(1000).hover();
        	    			System.out.println("Turn left");
        	    		}
        	    	}
        	    }
        	    
        	    if (hand.grabStrength() >= 0.8 && hand.isLeft())
        	    {
        	    	System.out.println("Landing");
        	    	isRunning = false;
        	    }
			}
}
