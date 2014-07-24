import com.leapmotion.leap.*;

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
