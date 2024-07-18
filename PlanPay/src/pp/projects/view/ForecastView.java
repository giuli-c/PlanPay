package pp.projects.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pp.projects.controller.ConsoleController;
import pp.projects.model.IllegalInputException;
import pp.projects.model.Objective;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Optional;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;

public class ForecastView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textAmount;
	private JLabel lblInserireMesi;
	private JTextField textCustomMonths;
	private JComboBox<String> comboBox;
	private JTextField textYears;
	private JTextField textMonths;
	private int years;
	private int months;
	private double monthsPerSaving;
	private double target;
	private double result;



	/**
	 * Create the frame.
	 */
	public ForecastView(ConsoleController controller, String objectiveName) {
		years = 0;
		months = 0;
		result = 0.00;
		target = 0.00;
		String[] freqSelection = {"Nessuna","Mensile","Semestrale","Annuale", "Altro"};
		Optional<Objective> optObjective = controller.getObjective(objectiveName);
		if(optObjective.isPresent()) 
			target = optObjective.get().getSavingTarget();
		else {
			JOptionPane.showMessageDialog(null, "Obbiettivo non trovato!", "Errore", JOptionPane.ERROR_MESSAGE);
			this.dispose();
		}

		setTitle("RISPARMIO PER OBBIETTIVO: "+ objectiveName);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 273);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblSomma = new JLabel("Per raggiungere la somma di");
		lblSomma.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblSomma.setBounds(10, 11, 164, 22);
		contentPane.add(lblSomma);

		JLabel lblFreq = new JLabel("Seleziona la frequenza di versamento");
		lblFreq.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblFreq.setBounds(10, 105, 211, 22);
		contentPane.add(lblFreq);

		JLabel lblNewLabel_1 = new JLabel("€");
		lblNewLabel_1.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(341, 15, 33, 14);
		contentPane.add(lblNewLabel_1);

		textAmount = new JTextField(Double.toString(target).replace(".", ","));
		textAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		textAmount.setFont(new Font("Calibri", Font.PLAIN, 13));
		textAmount.setColumns(10);
		textAmount.setBounds(231, 12, 98, 20);
		textAmount.addFocusListener(new FocusAdapter() {			
			@Override
			public void focusLost(FocusEvent e) {
				String input = textAmount.getText().trim();
				if(!input.isEmpty()) {
					try {
						String convInput = input.replace(".", "");
						convInput = convInput.replace(",", ".");
						double parsedInput = Double.parseDouble(convInput);	
						target = parsedInput;
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

		JLabel lblNewLabel_2 = new JLabel("Quanto dovresti versare?");
		lblNewLabel_2.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(10, 169, 189, 22);
		contentPane.add(lblNewLabel_2);

		comboBox = new JComboBox<>(freqSelection);
		comboBox.setFont(new Font("Calibri", Font.PLAIN, 13));
		comboBox.setBounds(231, 103, 143, 22);
		contentPane.add(comboBox);
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) comboBox.getSelectedItem();
                handleComboBoxSelection(selected);
            }
        });

		textCustomMonths = new JTextField("0");
		textCustomMonths.setHorizontalAlignment(SwingConstants.RIGHT);
		textCustomMonths.setFont(new Font("Calibri", Font.PLAIN, 13));
		textCustomMonths.setColumns(10);
		textCustomMonths.setBounds(231, 137, 98, 20);
		contentPane.add(textCustomMonths);
		textCustomMonths.setVisible(false);

		lblInserireMesi = new JLabel("inserire mesi versamento");
		lblInserireMesi.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblInserireMesi.setBounds(10, 136, 164, 22);
		contentPane.add(lblInserireMesi);
		lblInserireMesi.setVisible(false);

		JLabel lblDisplayResult = new JLabel(Double.toString(result));
		lblDisplayResult.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblDisplayResult.setHorizontalAlignment(SwingConstants.CENTER);
		lblDisplayResult.setBounds(231, 203, 143, 20);
		contentPane.add(lblDisplayResult);

		JLabel lblNewLabel = new JLabel("entro un periodo di");
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblNewLabel.setBounds(10, 44, 164, 14);
		contentPane.add(lblNewLabel);

		textYears = new JTextField("0");
		textYears.setHorizontalAlignment(SwingConstants.RIGHT);
		textYears.setFont(new Font("Calibri", Font.PLAIN, 13));
		textYears.setColumns(10);
		textYears.setBounds(231, 41, 98, 20);
		contentPane.add(textYears);

		JLabel lblAnni = new JLabel("anni");
		lblAnni.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAnni.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblAnni.setBounds(341, 45, 33, 14);
		contentPane.add(lblAnni);

		textMonths = new JTextField("0");
		textMonths.setHorizontalAlignment(SwingConstants.RIGHT);
		textMonths.setFont(new Font("Calibri", Font.PLAIN, 13));
		textMonths.setColumns(10);
		textMonths.setBounds(231, 72, 98, 20);
		contentPane.add(textMonths);

		JLabel lblMesi = new JLabel("mesi");
		lblMesi.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMesi.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblMesi.setBounds(339, 78, 35, 14);
		contentPane.add(lblMesi);

		JCheckBox ckboxNewCheckBox = new JCheckBox("Tieni conto del Saldo corrente");
		ckboxNewCheckBox.setSelected(true);
		ckboxNewCheckBox.setFont(new Font("Calibri", Font.PLAIN, 13));
		ckboxNewCheckBox.setBounds(6, 200, 193, 23);
		contentPane.add(ckboxNewCheckBox);

		JButton btnCalculate = new JButton("Calcola");
		btnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(optObjective.isPresent()) {
						years = Integer.parseInt(textYears.getText());
						months = Integer.parseInt(textMonths.getText());						
						if(comboBox.getSelectedItem().equals("Altro")) 
							monthsPerSaving = Double.parseDouble(textCustomMonths.getText());
						result = optObjective.get().savingForecast(target, monthsPerSaving, years, months, ckboxNewCheckBox.isSelected());
						String truncatedResult = String.format("%.2f",result);
						lblDisplayResult.setText(truncatedResult);
					}
					else
						JOptionPane.showMessageDialog(null, "Obbiettivo non trovato!", "Errore", JOptionPane.ERROR_MESSAGE);
				}
				catch(NumberFormatException | NullPointerException n) {
					JOptionPane.showMessageDialog(null, "Uno o più campi non validi!", "Errore", JOptionPane.ERROR_MESSAGE);					
				}
				catch(IllegalInputException i) {
					JOptionPane.showMessageDialog(null, i.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}				
			}
		});
		btnCalculate.setFont(new Font("Calibri", Font.PLAIN, 13));
		btnCalculate.setBounds(231, 169, 98, 23);
		contentPane.add(btnCalculate);

	}
	private void handleComboBoxSelection(String selected) {
		lblInserireMesi.setVisible(false);
    	textCustomMonths.setVisible(false);

	        switch (selected) {
	        	case "Nessuna":
	        		this.monthsPerSaving -= monthsPerSaving;
	        		break;
	            case "Mensile":
	                this.monthsPerSaving = 1.0;
	                break;
	            case "Semestrale":
	            	this.monthsPerSaving = 6.0;
	                break;
	            case "Annuale":
	            	this.monthsPerSaving = 12.0;
	            	break;
	            case "Altro":
	            	this.monthsPerSaving = 0.0;
	        		lblInserireMesi.setVisible(true);
	            	textCustomMonths.setVisible(true);
	            	break;
	        }
	}
}

