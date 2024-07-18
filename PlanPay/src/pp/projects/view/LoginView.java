package pp.projects.view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import pp.projects.controller.LoginController;
import pp.projects.model.AuthenticationException;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.UIManager;

public class LoginView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edUser;
	private JPasswordField edPassword;
	private String username;
	private String password;

	public LoginView(LoginController controller) {
		setForeground(UIManager.getColor("activeCaption"));
		this.username = "";
		this.password = "";	
		
		setTitle("LOGIN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 410, 428);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		initComponents(controller);
	}
	
	private void initComponents(LoginController controller) {
        JLabel lbUser = createLabel("User", 10, 35, 90, 21, 16);
        JLabel lbPassword = createLabel("Password", 10, 129, 90, 21, 16);

        edUser = createTextField(10, 64, 361, 33, 16);
        edPassword = new JPasswordField(16);
        edPassword.setBounds(10, 156, 328, 33);

        JButton btnShowPassword = new JButton(new ImageIcon("src/images/show_pwd.png"));
        btnShowPassword.setBackground(new Color(255, 255, 255));
        btnShowPassword.setBounds(341, 156, 45, 33);
        btnShowPassword.addActionListener(new ActionListener() {
            private boolean showingPassword = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (showingPassword) {
                	edPassword.setEchoChar('*');
                } else {
                	edPassword.setEchoChar((char) 0);
                }
                showingPassword = !showingPassword;
            }
        });

        
        JButton btnLogin = createButton("Login", 99, 219, 207, 52, 28, new Color(41, 235, 20), Color.WHITE);
        btnLogin.addActionListener(e -> handleLogin(controller));

        JButton btnNew = createButton("Sign Up", 146, 311, 108, 42, 22, SystemColor.activeCaption, Color.WHITE);
        btnNew.addActionListener(e -> {
            edUser.setText("");
            edPassword.setText("");
            controller.newSignupButtonClick();
        });

        JLabel lbNew = createLabel("Non ho un account!", 10, 334, 153, 21, 16);

        contentPane.add(lbUser);
        contentPane.add(lbPassword);
        contentPane.add(edUser);
        contentPane.add(edPassword);
        contentPane.add(btnShowPassword);
        contentPane.add(btnLogin);
        contentPane.add(btnNew);
        contentPane.add(lbNew);
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
        textField.setBounds(10, 64, 376, 33);
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

    private void handleLogin(LoginController controller) {
        username = edUser.getText().trim();
        password = edPassword.getText().trim();
        try {
            if (controller.loginButtonClick(username, password)) {
                this.setVisible(false);
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Nome utente e password non possono essere vuoti o contenere spazi.", "Errore", JOptionPane.ERROR_MESSAGE);
        } catch (AuthenticationException e) {
            JOptionPane.showMessageDialog(null, "Autenticazione fallita. Credenziali non valide o spazi presenti. Riprova.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
}
