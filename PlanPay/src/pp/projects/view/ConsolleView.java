package pp.projects.view;

import javax.swing.*;

import pp.projects.controller.ConsoleController;
import pp.projects.model.Account;
import pp.projects.model.ComparatorEvents;
import pp.projects.model.OperationType;
import pp.projects.model.Event;

import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.awt.SystemColor;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;

public class ConsolleView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnCalendar;
	private JButton btnServices;
	private JButton btnTransactions;
	private JLabel lbTransactions;
	private JLabel lbUsername;
	private JLabel lbImp;
	private JLabel lblmporto;
	private JButton btnObjectives;
	private DefaultListModel<String> transactionListModel;
	private JList<String> transactionList;
	private DefaultListModel<String> eventListModel;
	private JList<String> eventListToday;	
	private JScrollPane scrollPane;
	
	private int count;
	private String headerText;
	
	private ConsoleController controller;
	private CalendarView calendarView;
	private ServicesView servicesView;
	private ConsolleObjectiveView consolleObjectiveView;
	private Account account;
	
	public ConsolleView(ConsoleController c, Account account) throws IOException {
		setTitle("CONSOLLE");
		this.controller = c;
		this.servicesView = new ServicesView(OperationType.SERVIZIO, "", c);//modificato costruttore
		this.consolleObjectiveView = new ConsolleObjectiveView(c);		
		this.account = account;
		
		initComponents();
        initListeners();
	}
	
    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 772, 490);
        contentPane = new JPanel();
        contentPane.setForeground(SystemColor.control);
        contentPane.setBackground(new Color(245, 245, 245));
        contentPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        lbUsername = createLabel("", new Font("Calibri", Font.PLAIN, 38), 31, 24, 461, 42);
        contentPane.add(lbUsername);

        btnServices = createButton("SERVIZI", new Font("Calibri", Font.PLAIN, 36), 414, 275, 320, 100);
        setButtonIcon(btnServices, "/images/payment.png");
        contentPane.add(btnServices);

        lbImp = createLabel("Importo:", new Font("Calibri", Font.PLAIN, 33), 31, 76, 134, 42);
        contentPane.add(lbImp);

        lblmporto = createLabel("0,00 €", new Font("Calibri", Font.PLAIN, 34), 175, 76, 373, 42);
        contentPane.add(lblmporto);

        lbTransactions = createLabel("Transazioni", new Font("Calibri", Font.PLAIN, 36), 31, 385, 597, 42);
        contentPane.add(lbTransactions);

        btnCalendar = createIconButton("/images/calendar.png", 600, 25, 134, 108);
        contentPane.add(btnCalendar);

        btnTransactions = createIconButton("/images/freccia.png", 672, 385, 62, 42);
        contentPane.add(btnTransactions);

        btnObjectives = createButton("OBBIETTIVI", new Font("Calibri", Font.PLAIN, 36), 414, 155, 320, 100);
        setButtonIcon(btnObjectives, "/images/objective.png");
        contentPane.add(btnObjectives);

        transactionListModel = new DefaultListModel<>();
        transactionList = new JList<>(transactionListModel);
        transactionList.setFont(new Font("Calibri", Font.PLAIN, 19));
        transactionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane = new JScrollPane(transactionList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(31, 433, 703, 249);
        contentPane.add(scrollPane);
        transactionList.setVisible(false);
        scrollPane.setVisible(false);

        eventListModel = new DefaultListModel<>();
        eventListToday = new JList<>(eventListModel);
        eventListToday.setForeground(SystemColor.DARK_GRAY);
        eventListToday.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(200, 200, 200), new Color(200, 200, 200), new Color(200, 200, 200), new Color(200, 200, 200)));
        eventListToday.setFont(new Font("Calibri", Font.PLAIN, 16));
        eventListToday.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        eventListToday.setBackground(new Color(245, 245, 245));

        headerText = "<html><body style='font-size: 16px;'>" +
                     "<u>Eventi di oggi " + LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ITALIAN) + " " +
                     LocalDate.now().getDayOfMonth() + " " +
                     LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, Locale.ITALIAN) + " " +
                     LocalDate.now().getYear() + "</u></body></html>";
        eventListModel.addElement(headerText);

        JScrollPane scrollPaneEv = new JScrollPane(eventListToday);
        scrollPaneEv.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneEv.setBounds(31, 155, 373, 220);
        contentPane.add(scrollPaneEv);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                lbUsername.setText(controller.setNameController().toUpperCase());
                updateEventsUI();
            }
        });
    }
    
    private void initListeners() {
        btnServices.addActionListener(e -> servicesView.setVisible(true));

        btnCalendar.addActionListener(e -> {
            calendarView = controller.drawCalendar();
            calendarView.setVisible(true);
        });

        btnTransactions.addActionListener(e -> {
            count++;
            if (count % 2 == 0) {
                if (updateTransactionsUI()) {
                    transactionList.setVisible(false);
                    scrollPane.setVisible(false);
                    setBounds(100, 100, 772, 490);
                }
            } else {
                if (updateTransactionsUI()) {
                    transactionList.setVisible(true);
                    scrollPane.setVisible(true);
                    setBounds(100, 100, 772, 722);
                }
            }
        });

        btnObjectives.addActionListener(e -> consolleObjectiveView.setVisible(true));
    }

    private JLabel createLabel(String text, Font font, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setBounds(x, y, width, height);
        return label;
    }

    private JButton createButton(String text, Font font, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(Color.WHITE);
        button.setBounds(x, y, width, height);
        return button;
    }

    private JButton createIconButton(String iconPath, int x, int y, int width, int height) {
        JButton button = new JButton();
        try {
            button.setIcon(new ImageIcon(this.getClass().getResource(iconPath)));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        button.setBounds(x, y, width, height);
        return button;
    }

    public boolean updateTransactionsUI() {
        List<String> transactions = controller.getDatiTransazione();
        if (transactions != null) {
            transactionListModel.clear();
            transactionListModel.addElement("<html><div style='height:1px;'></div></html>");
            for (String transaction : transactions) {
                transactionListModel.addElement(transaction);
            }
            return !transactions.isEmpty();
        }
        return false;
    }

    public void updateEventsUI() {
    	Set<Event> eventsToFile = controller.getAllEventToFile();	
		if(!eventsToFile.isEmpty()) {
			Set<Event> eventsToday = filterEventsByDate(eventsToFile);
			filterFutureEvents(eventsToFile, eventsToday);
		}
    }

    private Set<Event> filterEventsByDate(Set<Event> eventsToFile) {
    	Set<Event> eventsToday = eventsToFile.stream()
				  							 .filter(e -> e.getDate().equals(LocalDate.now()))
				  							 .collect(Collectors.toCollection(() -> new TreeSet<>(new ComparatorEvents())));
		if(eventsToday != null) {
			eventListModel.clear();			
			eventListModel.addElement(headerText + "<html><div style='height:3px;'></div></html>"); 
			for (Event event : eventsToday) {
				eventListModel.addElement(event.getInfoEventToString());
			}
		} 
		
		return eventsToday;
    }

    private void filterFutureEvents(Set<Event> eventsToFile, Set<Event> todayEvents) {
    	Set<String> todayIdentifiers = todayEvents.stream()
									                .map(Event::getIdentifier)
									                .collect(Collectors.toSet());

    	Set<Event> nextEvents = eventsToFile.stream()
	            							.filter(event -> event.getDate().isAfter(LocalDate.now()) 
	            											&& !todayIdentifiers.contains(event.getIdentifier()))
	            							.collect(Collectors.toCollection(() -> new TreeSet<>(new ComparatorEvents())));
    	
		if(nextEvents != null && nextEvents.size() > 0) {		
			eventListModel.addElement("<html><div style='height:3px;'></div></html>"); 
			eventListModel.addElement("<html><body style='font-size: 16px;'>" + "<u>Prossimi eventi: </u></body></html>");
			eventListModel.addElement("<html><div style='height:3px;'></div></html>"); 
			for (Event event : nextEvents) {
				eventListModel.addElement(event.getDate() + " >> " + event.getInfoEventToString());
			}
		}
    }
    
    private void setButtonIcon(JButton button, String path) {
		Image scaledImg;
        try {
            ImageIcon icon = new ImageIcon(this.getClass().getResource(path));
            Image img = icon.getImage();
            if(button.equals(btnObjectives))
            	scaledImg = img.getScaledInstance(90, 90, Image.SCALE_SMOOTH); // Ridimensiona l'immagine
            else
            	scaledImg = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH); // Ridimensiona l'immagine            
            icon = new ImageIcon(scaledImg);
            button.setIcon(icon);
            button.setHorizontalTextPosition(SwingConstants.RIGHT); // Posiziona il testo a destra dell'immagine
            button.setIconTextGap(20); // Imposta lo spazio tra l'immagine e il testo
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    public void updateUIconto() {
		lblmporto.setText(formattedImport(account.getBalance()) + " €");
		consolleObjectiveView.updateUI();
	}
    
    private String formattedImport(double amount) {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ITALY);
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);
        return decimalFormat.format(amount);
	}
}
