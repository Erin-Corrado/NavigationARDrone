import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.*;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import de.yadrone.base.*;
import de.yadrone.base.video.*;


public class VideoListener extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage image = null;
	
	public VideoListener(final ARDrone drone)
	{
		super("Navigation");
        
        setSize(640,360);
        setVisible(true);
        
        drone.getVideoManager().addImageListener(new ImageListener() {
            public void imageUpdated(BufferedImage newImage)
            {
                image = newImage;
                SwingUtilities.invokeLater(new Runnable() {
                    public void run()
                    {
                        repaint();
                    }
                });
            }
        });
        
        /*addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) 
            {
                drone.stop();
                System.exit(0);
            }
        });*/
	}
	
	public void paint(Graphics g)
    {
        if (image != null)
            g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
    }
	
	
	
	
	
}
