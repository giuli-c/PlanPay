package pp.projects.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class ChartView extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public ChartView() {
	        setBorder(BorderFactory.createLineBorder(Color.black));
	    }

	    public Dimension getPreferredSize() {
	        return new Dimension(250,200);
	    }

	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);       

	        // Draw Text
	        g.drawString("This is my custom Panel!",10,20);
	    }  

}
