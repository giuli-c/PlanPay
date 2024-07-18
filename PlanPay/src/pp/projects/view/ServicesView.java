package pp.projects.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pp.projects.controller.ConsoleController;
import pp.projects.model.IllegalOperationException;
import pp.projects.model.Objective;
import pp.projects.model.OperationType;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;
import java.awt.event.ActionEvent;

public class ServicesView extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textAmount;
	private JTextField textName;
	private LocalDate date;
	private double Amount;
	private ConsoleController controller;

	/**
	 * Create the frame.
	 */
	public ServicesView(OperationType operationType, String operationName, ConsoleController controller) {
		
		date = LocalDate.now();
		this.controller = controller;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 184);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCurrency = new JLabel("€");
		lblCurrency.setBounds(173, 35, 37, 14);
		lblCurrency.setFont(new Font("Calibri", Font.PLAIN, 14));
		contentPane.add(lblCurrency);
		
		JLabel lblAmount = new JLabel("Importo:");
		lblAmount.setBounds(10, 38, 62, 14);
		lblAmount.setFont(new Font("Calibri", Font.PLAIN, 14));
		contentPane.add(lblAmount);
		
		textAmount = new JTextField("0,00");
		textAmount.setFont(new Font("Calibri", Font.PLAIN, 14));
		textAmount.setColumns(10);
		textAmount.setBounds(82, 32, 86, 20);
		textAmount.addFocusListener(new FocusAdapter() {
			
			@Override
			public void focusLost(FocusEvent e) {
				String input = textAmount.getText().trim();
				if(!input.isEmpty()) {
					try {
						String convInput = input.replace(".", "");
						convInput = convInput.replace(",", ".");
						double parsedInput = Double.parseDouble(convInput);	
						Amount = parsedInput;
						DecimalFormatSymbols sym = new DecimalFormatSymbols(Locale.ITALY);
						sym.setDecimalSeparator(',');
						sym.setGroupingSeparator('.');
						DecimalFormat decFormat =  new DecimalFormat("#,##0.00", sym);
						String formattedAmount = decFormat.format(parsedInput);
						textAmount.setText(formattedAmount);
					} catch(NumberFormatException ne) {						
						JOptionPane.showMessageDialog(null, "Inserire un valore numerico  per l'operazione!", 
								"Errore", JOptionPane.ERROR_MESSAGE);
						textAmount.setText("0,00");
					}					
				}				
			}				
		});
		contentPane.add(textAmount);
		
		JLabel lblTitle = new JLabel("Causale");
		lblTitle.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblTitle.setBounds(10, 63, 62, 14);
		contentPane.add(lblTitle);
		
		textName = new JTextField(operationName);
		textName.setFont(new Font("Calibri", Font.PLAIN, 13));
		textName.setBounds(83, 60, 331, 20);
		textName.setColumns(10);	
		contentPane.add(textName);
		
		if(operationType == OperationType.OBIETTIVO) {
			setTitle("OBBIETTIVO - Data: " + date);
			textName.setEditable(false);
		}
			
		else 
			setTitle("SERVIZIO CONTO - Data: " + date);
		
		JButton btnDeposit = new JButton("Deposita");
		btnDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//leggo la cifra inserita, se non è stata inserita alcuna cifra
				//do messaggio di errore
				try {
					DoOperation(Amount, true, operationName, operationType);
				}catch( IllegalOperationException i) {
					JOptionPane.showMessageDialog(null, i.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnDeposit.setBounds(10, 101, 149, 35);
		btnDeposit.setFont(new Font("Calibri", Font.PLAIN, 14));
		contentPane.add(btnDeposit);
		
		JButton btnWithdraw = new JButton("Preleva");
		btnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				//Se questa view è stata chiamata dalla consoleView
				try {
					DoOperation(Amount, false, operationName, operationType);
				} 
				catch(IllegalArgumentException | IllegalOperationException i){
					JOptionPane.showMessageDialog(null, i.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
					textAmount.setText("0,00");
				} 				
			}
		});
		btnWithdraw.setBounds(264, 101, 149, 35);
		btnWithdraw.setFont(new Font("Calibri", Font.PLAIN, 14));
		contentPane.add(btnWithdraw);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				textAmount.setText("0,00");
				textName.setText("");
			}			
		});
	}
	
	private void DoOperation(Double Amount, Boolean operationSign, String operationName, OperationType operationType) 
			throws IllegalOperationException
	{
		if(Amount <= 0)
			throw new IllegalOperationException("Inserire una cifra positiva per l'operazione!");
		
		String inputTextName = textName.getText().trim();
		switch(operationType) {
			case  SERVIZIO :
				if(textName.getText().trim().isEmpty())
					JOptionPane.showMessageDialog(null, "Inserire una causale ", "Errore", JOptionPane.ERROR_MESSAGE);
				else {
					controller.updateConto(Amount, operationSign, inputTextName, operationType);
					setVisible(false);
					textAmount.setText("0,00");
					textName.setText("");
				}
			break;
			
			case OBIETTIVO :
				//cerco nella lista degli obbiettivi 
				Optional<Objective> optObjective = controller.getObjective(operationName);
				//se trovo l'obbiettivo posso eseguire l'operazione
				if(optObjective.isPresent()) {
					controller.updateConto(Amount, operationSign, operationName, operationType);
					setVisible(false);
					textAmount.setText("0,00");
					textName.setText("");
				}
				else
					//messaggio di errore 
					JOptionPane.showMessageDialog(null, 
							"Obbiettivo non trovato! creare obbiettivo '"+ operationName +"' prima di effettuare un'operazione!",
							"Errore", JOptionPane.ERROR_MESSAGE);
			break;				
		} 
	}
}