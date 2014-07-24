public class ARRobutt implements Robutt {
	public ARRobutt() {
		drone = new ARDrone();
		drone.start();
		int speed = 20;
		CommandManager cmd = drone.getCommandManager();
		cmd.setLedsAnimation(LEDAnimation.BLINK_ORANGE, 3, 10);
		cmd.takeOff();
		cmd.waitFor(5000);
	}
	void goLeft() {
		cmd.goLeft(speed).doFor(1000).hover();
		System.out.println("Move left");
	}
	void goRight() {
		cmd.goRight(speed).doFor(1000).hover();
		System.out.println("Move right");
	}
	void turnLeft() {
		cmd.spinLeft(speed).doFor(1000).hover();
		System.out.println("Turn left");
	}
	void turnRight() {
		cmd.spinRight(speed).doFor(1000).hover();
		System.out.println("Turn right");
	}
	void goUp();
	void goDown();
	void goForward();
	void goBackward();
}
