package pp.projects.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import pp.projects.controller.LoginControllerImpl;
import pp.projects.model.RegistrationException;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Color;

public class SignupView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edNewUser;
	private JTextField edNewPassword;
	private JTextField edName;
	private String userName;
	private String user;
	private String password;

	public SignupView(LoginControllerImpl controller) {
		setTitle("SIGN UP");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 402, 443);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);		
		
		initComponents(controller);
	}
	
	private void initComponents(LoginControllerImpl controller) {
        JLabel lbName = createLabel("Account Name", 10, 18, 108, 21, 16);
        edName = createTextField(10, 45, 368, 33, 16);

        JLabel lbNewUser = createLabel("User", 10, 94, 90, 21, 16);
        edNewUser = createTextField(10, 119, 368, 33, 16);

        JLabel lbNewPassword = createLabel("Password", 10, 174, 90, 21, 16);
        edNewPassword = createTextField(10, 201, 368, 33, 16);

        JLabel lbLoginY = createLabel("Ho un account!", 10, 348, 115, 21, 16);
        JButton btnLoginY = createButton("\r\nLogin", 123, 336, 108, 42, 22, SystemColor.activeCaption, Color.WHITE);
        btnLoginY.addActionListener(e -> controller.newSignupButtonClick());

        JButton btnSignup = createButton("Sign Up", 93, 260, 217, 51, 26, new Color(50, 205, 50), Color.WHITE);
        btnSignup.addActionListener(e -> handleSignup(controller));

        contentPane.add(lbName);
        contentPane.add(edName);
        contentPane.add(lbNewUser);
        contentPane.add(edNewUser);
        contentPane.add(lbNewPassword);
        contentPane.add(edNewPassword);
        contentPane.add(lbLoginY);
        contentPane.add(btnLoginY);
        contentPane.add(btnSignup);
    }

    private JLabel createLabel(String text, int x, int y, int width, int height, int fontSize) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Calibri", Font.PLAIN, fontSize));
        label.setBounds(x, y, width, height);
        return label;
    }

    private JTextField createTextField(int x, int y, int width, int height, int fontSize) {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Calibri", Font.PLAIN, fontSize));
        textField.setBounds(x, y, width, height);
        return textField;
    }

    private JButton createButton(String text, int x, int y, int width, int height, int fontSize, Color bg, Color fg) {
        JButton button = new JButton(text);
        button.setFont(new Font("Calibri", Font.PLAIN, fontSize));
        button.setBackground(bg);
        button.setForeground(fg);
        button.setBounds(x, y, width, height);
        return button;
    }

    private void handleSignup(LoginControllerImpl controller) {
        userName = edName.getText();
        user = edNewUser.getText();
        password = edNewPassword.getText();
        try {
            if (controller.signupButtonClick(user, password, userName)) {
                JOptionPane.showMessageDialog(null, "Registrazione avvenuta con successo!", "Info", JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "ERRORE! Credenziali mancanti!", "Errore", JOptionPane.ERROR_MESSAGE);
        } catch (RegistrationException e) {
            JOptionPane.showMessageDialog(null, "ERRORE! Utente già registrato!", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
}
