import lejos.pc.comm.*;

public class LegoRobutt implements Robutt {
	public LegoRobutt() {
		try {
			NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.USB);
			// RemoteNXT nxt = new RemoteNXT("SwagBot", nxtConn);
			// nxt.B.setSpeed(360);
			// nxt.C.setSpeed(360);
		} catch (NXTCommException e){
			// LCD.clear();
			// LCD.drawString("Conn Failed", 0, 0);
			// Button.waitForAnyPress();
			System.exit(1);
		}
	}
	public void goLeft() {
		System.out.println("Slide to the left");
	}
	public void goRight() {
		System.out.println("Slide to the right");
	}
	public void turnLeft() {
		System.out.println("Turn left");
	}
	public void turnRight() {
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
