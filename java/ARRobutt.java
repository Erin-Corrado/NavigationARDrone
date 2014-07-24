import de.yadrone.base.IARDrone;
import de.yadrone.base.ARDrone;
import de.yadrone.base.command.CommandManager;
import de.yadrone.base.command.LEDAnimation;

public class ARRobutt implements Robutt {
	private static final int SPEED = 20;
	private CommandManager cmd;

	public ARRobutt() {
		IARDrone drone = null;
		drone = new ARDrone();
		drone.start();
		cmd = drone.getCommandManager();
		cmd.setLedsAnimation(LEDAnimation.BLINK_ORANGE, 3, 10);
		cmd.takeOff();
		cmd.waitFor(5000);
	}
	public void goLeft() {
		cmd.goLeft(SPEED).doFor(1000).hover();
		System.out.println("Move left");
	}
	public void goRight() {
		cmd.goRight(SPEED).doFor(1000).hover();
		System.out.println("Move right");
	}
	public void turnLeft() {
		cmd.spinLeft(SPEED).doFor(1000).hover();
		System.out.println("Turn left");
	}
	public void turnRight() {
		cmd.spinRight(SPEED).doFor(1000).hover();
		System.out.println("Turn right");
	}
	public void goUp() {
		System.out.println("Ascend");
	}
	public void goDown() {
		System.out.println("Can you go way down low? All the way to the floor?");
	}
	public void goForward() {
		System.out.println("Go forward; move ahead");
	}
	public void goBackward() {
		System.out.println("Take it back now ya'll");
	}
}
