
package pp.projects.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pp.projects.controller.ConsoleController;
import pp.projects.model.Objective;
import pp.projects.model.OperationType;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import java.awt.Color;

public class ObjectiveView extends JFrame {
	

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textName;
	private JTextField textAmount;
	private ConsoleController controller;
	private JLabel lblDisplayBalance;
	private double savingAmount;
	private double balance;
	private boolean	hasSaved;
	private String description;
	private String nomeObbiettivo;
	private Optional<Objective> optObjective;
	
	/**
	 * Create the frame.
	 */
	public ObjectiveView(boolean bNew, String nomeObbiettivo, LocalDate date, ConsoleController controller, ConsolleObjectiveView consObj) {
		
		this.controller = controller;
		this.nomeObbiettivo = nomeObbiettivo;
		optObjective = controller.getObjective(nomeObbiettivo);
		savingAmount = updateSavingAmount(nomeObbiettivo);		
		description = updateDescription(nomeObbiettivo);
		balance = updateBalance(nomeObbiettivo);
		hasSaved = false;
		
		setTitle("OBBIETTIVO "+nomeObbiettivo+" - Data: "+date.toString());		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 484, 225);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblName = new JLabel("Nome:");
		lblName.setBounds(22, 25, 46, 14);
		lblName.setFont(new Font("Calibri", Font.PLAIN, 14));
		contentPane.add(lblName);
		
		JButton btnProjection = new JButton("Previsione");
		btnProjection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				optObjective = controller.getObjective(nomeObbiettivo);
				if(optObjective.isPresent()) {
					String name = optObjective.get().getName();
					ForecastView forecast = new ForecastView(controller, name);
					forecast.setVisible(true);
				}
				else
					JOptionPane.showMessageDialog(null, "Obbiettivo non creato", "Errore", JOptionPane.ERROR_MESSAGE);					
			}
		});
		btnProjection.setBounds(10, 154, 123, 27);
		btnProjection.setFont(new Font("Calibri", Font.PLAIN, 14));
		contentPane.add(btnProjection);
		
		textName = new JTextField(nomeObbiettivo);
		textName.setFont(new Font("Calibri", Font.PLAIN, 14));
		textName.setBounds(151, 22, 208, 20);
		contentPane.add(textName);
		textName.setColumns(10);
		textName.setEditable(bNew);
		
		JLabel lblDescr = new JLabel("Descrizione:");
		lblDescr.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblDescr.setBounds(22, 57, 92, 14);
		contentPane.add(lblDescr);
		
		JLabel lblThreshold = new JLabel("Soglia risparmio");
		lblThreshold.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblThreshold.setBounds(22, 120, 101, 14);
		contentPane.add(lblThreshold);
				
		textAmount = new JTextField(Double.toString(savingAmount).replace(".", ","));
		textAmount.setFont(new Font("Calibri", Font.PLAIN, 14));
		textAmount.setColumns(10);
		textAmount.setBounds(151, 117, 99, 20);
		textAmount.addFocusListener(new FocusAdapter() {	
			@Override
			public void focusLost(FocusEvent e) {
				String input = textAmount.getText().trim();
				if(!input.isEmpty()) {
					try {
						String convInput = input.replace(".", "");
						convInput = convInput.replace(",", ".");
						double parsedInput = Double.parseDouble(convInput);	
						savingAmount = parsedInput;
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
		
		JTextArea textDescr = new JTextArea(description);
		textDescr.setFont(new Font("Calibri", Font.PLAIN, 14));
		textDescr.setBounds(151, 53, 208, 53);
		contentPane.add(textDescr);
		
		JLabel lblCurrentAmount = new JLabel("Saldo:");
		lblCurrentAmount.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblCurrentAmount.setBounds(272, 120, 46, 14);
		contentPane.add(lblCurrentAmount);
		
		JLabel lblDisplayBalance = new JLabel(Double.toString(balance)+" €");
		lblDisplayBalance.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblDisplayBalance.setBounds(331, 120, 82, 14);
		contentPane.add(lblDisplayBalance);
		
		JProgressBar progressBar = new JProgressBar(0, (int)savingAmount);
		progressBar.setForeground(Color.BLUE);
		progressBar.setFont(new Font("Calibri", Font.PLAIN, 12));
		progressBar.setValue((int) balance);
		progressBar.setOrientation(SwingConstants.VERTICAL);
		progressBar.setBounds(414, 22, 17, 115);
		contentPane.add(progressBar);
		
		JButton btnSave = new JButton("Salva modifiche");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					optObjective = controller.getObjective(textName.getText());
					if(textAmount.getText().isBlank() || textName.getText().isBlank())
						JOptionPane.showMessageDialog(null, "Inserire nome obbiettivo"+
								" e ammontare da risparmiare", "Errore", JOptionPane.ERROR_MESSAGE);					
					else if(optObjective.isEmpty()){
						//l'obbiettivo ha un nome nuovo
						//controllo che il nome non sia già preso
						description = textDescr.getText().isEmpty()? updateDescription(textName.getText()): textDescr.getText();
						controller.saveObjective(bNew, textName.getText(), description, savingAmount);
						hasSaved = true;
						textName.setEditable(false);
						savingAmount = updateSavingAmount(textName.getText());
						setVisible(false);
					}//altrimenti se l'obbiettivo è già stato creato ma viene modificato
					else if((optObjective.get().getSavingTarget() != savingAmount) ||
							 (optObjective.get().getDescription().equals(description))) {
						description = textDescr.getText().isEmpty()? updateDescription(textName.getText()): textDescr.getText();
						controller.saveObjective(bNew, textName.getText(), description, savingAmount);
						savingAmount = updateSavingAmount(textName.getText());
						setVisible(false);
					}
					else if(bNew && hasSaved == false){
						JOptionPane.showMessageDialog(null, "Obbiettivo già presente!", "Errore", JOptionPane.ERROR_MESSAGE);
					}
				}
				catch(NumberFormatException n) {
					JOptionPane.showMessageDialog(null, "Inserire cifra numerica per la soglia di risparmio", "Errore", JOptionPane.ERROR_MESSAGE);
					textAmount.setText("0,00");
				}
				catch(IllegalStateException l) {
					JOptionPane.showMessageDialog(null, l.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}
				finally {
					consObj.updateUI();
					progressBar.setValue((int) balance);;
				}
			}
		});
		btnSave.setFont(new Font("Calibri", Font.PLAIN, 14));
		btnSave.setBounds(143, 154, 139, 27);
		contentPane.add(btnSave);
		
		JButton btnOperation = new JButton("Deposita/Preleva");
		btnOperation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSave.doClick();
				// apro il form di ServicesView solo se l'obbettivo è stato salvato
				// e non sto creando un nuovo obbiettivo con lo stesso nome di un obbiettivo già presente
				if((!bNew || hasSaved) && controller.getObjective(nomeObbiettivo).isPresent()) {
					ServicesView serviceView = new ServicesView(OperationType.OBIETTIVO, textName.getText(), controller);
					serviceView.setVisible(true);
					setVisible(false);
				}
				
			}		
		});	
		btnOperation.setBounds(292, 154, 139, 27);
		btnOperation.setFont(new Font("Calibri", Font.PLAIN, 14));
		contentPane.add(btnOperation);
	}
	
	public void updateUIObjective() {
		double newBalance = updateBalance(this.nomeObbiettivo);
		lblDisplayBalance.setText(Double.toString(newBalance));
	}
	
	private double updateBalance(String nomeObbiettivo) {
		return (controller.getObjective(nomeObbiettivo).isPresent()) ?
				controller.getObjective(nomeObbiettivo).get().getBalance() : 0.00;
	}
	
	private double updateSavingAmount(String nomeObbiettivo) {
			return controller.getObjective(nomeObbiettivo).isPresent()?
					controller.getObjective(nomeObbiettivo).get().getSavingTarget(): 0.00;
	}
	
	private String updateDescription(String nomeObbiettivo) {
		return controller.getObjective(nomeObbiettivo).isPresent()? 
				controller.getObjective(nomeObbiettivo).get().getDescription():"";
	}
}