package main;

import java.awt.EventQueue;
import java.io.IOException;

import pp.projects.controller.LoginControllerImpl;
import pp.projects.controller.LoginController;
import pp.projects.view.LoginView;

public class Main {

	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		LoginController lc = new LoginControllerImpl();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView frame = new LoginView(lc);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
