package pp.projects.view;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

import pp.projects.controller.ConsoleController;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Component;
import javax.swing.SwingConstants;

public class ListObjectiveView extends JPanel {
	
	
	private static final long serialVersionUID = 1L;

	/**
	 * Create the frame.
	 *
	 *
	 */
	public ListObjectiveView(String name, double balance, LocalDate date, ConsoleController controller, ConsolleObjectiveView consolleObj) {
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		
		Dimension preferredSize = new Dimension(800, 40);
        setMinimumSize(preferredSize);
        setMaximumSize(preferredSize);
        setLayout(new GridLayout(0, 2, 0, 0));
        
        JLabel lblnome = new JLabel(name);
        lblnome.setHorizontalAlignment(SwingConstants.CENTER);
        lblnome.setVerticalAlignment(SwingConstants.BOTTOM);
        lblnome.setFont(new Font("Calibri", Font.PLAIN, 14));
        add(lblnome);
        
        JButton btnNewButton = new JButton("Apri");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ObjectiveView newObjective = new ObjectiveView(false, name, date, controller, consolleObj);
        		newObjective.setVisible(true);
        	}
        });        
        add(btnNewButton);
        
        JLabel lblBalance = new JLabel("Saldo: "+ Double.toString(balance)+"€");
        lblBalance.setHorizontalAlignment(SwingConstants.CENTER);
        lblBalance.setVerticalAlignment(SwingConstants.BOTTOM);
        lblBalance.setFont(new Font("Calibri", Font.PLAIN, 14));
        add(lblBalance);
        
        JButton btnNewButton_1 = new JButton("Elimina");
        btnNewButton_1.setAlignmentY(Component.TOP_ALIGNMENT);
        btnNewButton_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.removeObjective(name);
        		consolleObj.updateUI();
        	}
        });
        add(btnNewButton_1);      
        setVisible(true);
	}
}