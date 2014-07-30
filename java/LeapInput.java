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
				if (hand.isLeft())
        	    {
        	    	if(hand.grabStrength() >= 0.7)
        	    	{
        	    		System.out.println("Landing");
        	    		isRunning = false;
        	    	}
        	    	
        	    	else
        	    	{
        	    		System.out.println("Hover");
        	    		cmd.hover();
        	    	}
        	    			
        	    }
				
				if(hand.isRight())
				{
					Vector direction = hand.direction();
				
					if(Math.toDegrees(direction.yaw()) > 15)
					{
						System.out.println("Move right");
						cmd.goRight(speed).doFor(1000).hover();
					}
				
					else if (Math.toDegrees(direction.yaw()) < -15)
					{
						System.out.println("Move left");
						cmd.goLeft(speed).doFor(1000).hover();
					}
					
				}
				
				for(Gesture gesture : frame.gestures())
				{
					switch (gesture.type()) 
					{
			        	case TYPE_CIRCLE:
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
			        		break;
			        		
			        	case TYPE_KEY_TAP:
			        		break;
			        		
			        	case TYPE_SCREEN_TAP:
			        		break;
			        		
			        	case TYPE_SWIPE:
			        		SwipeGesture swipe = new SwipeGesture(gesture);
	        	    		Vector swipeDirection = swipe.direction();
	        	    		
	        	    		if(swipeDirection.getX() > 0)
	        	    		{
	        	    			cmd.goRight(speed).doFor(1000).hover();
	        	    			System.out.println("Move right");
	        	    		}
	        	    		
	        	    		else if(swipeDirection.getX() < 0)
	        	    		{
	        	    			cmd.goLeft(speed).doFor(1000).hover();
	        	    			System.out.println("Move left");
	        	    		}
			        		break;
			        		
			        	default:
			        		break;
			    }
				}
        	    
        	    
      
			}
}
