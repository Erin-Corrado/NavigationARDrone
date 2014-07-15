import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Key extends KeyAdapter {
	
	public char lastChar;
	
	public Key(){
		lastChar = '0';
	}
	
	public void keyPressed(KeyEvent event)
	{
		char c = event.getKeyChar();
		lastChar = c;
	}
	

}
