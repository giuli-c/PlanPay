package pp.projects.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pp.projects.controller.ConsoleController;
import pp.projects.model.CalendarModel;
import pp.projects.model.DayCellRenderer;
import pp.projects.model.Event;

import javax.swing.JTable;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class CalendarView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;    
    private CalendarModel calendarModel;
    private JTable tblCalendario;
    private JLabel lbMese;
    private JLabel lbNeventi_concluso;
    private JLabel lbNeventi_incorso;
    private JLabel lbNeventi_daAvviare;
    
    private EventView eventView;
    private SelectedEventView selectedEventView;
    private boolean isSelectingEvent = false;
	private int eventiDaAvviare = 0;
	private int eventiIncorso = 0;
	private int eventiConclusi = 0;
	private ConsoleController controller;
	
	public CalendarView(ConsoleController controller, CalendarModel model) {
        this.controller = controller;
        this.calendarModel = model;

        setTitle("CALENDARIO");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1201, 790);
        setResizable(false);

        initComponents();
        addEventListeners();

        updateLegenda(controller.loadEvents());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                controller.drawCalendar();
                calendarModel.loadEvents(controller.loadEvents());
            }
        });
    }

    private void initComponents() {
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JButton btnIndietro = new JButton("<<");
		btnIndietro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calendarModel.previousMonth();
				updateMonthLabel();
			}
		});
		btnIndietro.setFont(new Font("Calibri", Font.PLAIN, 18));
		btnIndietro.setBounds(10, 10, 82, 41);
		contentPane.add(btnIndietro);
		
		JButton btnAvanti = new JButton(">>");
		btnAvanti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calendarModel.nextMonth();
				updateMonthLabel();
			}
		});
		btnAvanti.setFont(new Font("Calibri", Font.PLAIN, 18));
		btnAvanti.setBounds(1086, 10, 82, 41);
		contentPane.add(btnAvanti);

        lbMese = new JLabel(calendarModel.getMonth(calendarModel.getMonthValue()).name().toUpperCase() + "  " + calendarModel.getYear());
		lbMese.setHorizontalAlignment(SwingConstants.CENTER);
		lbMese.setBackground(new Color(255, 255, 255));
		lbMese.setFont(new Font("Calibri", Font.PLAIN, 30));
		lbMese.setBounds(10, 49, 1158, 41);
		contentPane.add(lbMese);

		JButton btnNewevent = new JButton("Aggiungi evento");
		btnNewevent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// creo un nuovo evento
				eventView = new EventView(true, LocalDate.now(), controller, CalendarView.this);
				eventView.setVisible(true);
			}
		});
		btnNewevent.setFont(new Font("Calibri", Font.PLAIN, 14));
		btnNewevent.setBounds(560, 675, 157, 49);
		contentPane.add(btnNewevent);

        tblCalendario = new JTable(calendarModel);
        tblCalendario.setRowHeight(82);
        tblCalendario.setDefaultRenderer(Object.class, new DayCellRenderer());
        tblCalendario.setBounds(10, 150, 1167, 494);
        contentPane.add(tblCalendario);

        addDayLabels();

        JPanel pnLegenda = new JPanel();
		pnLegenda.setBounds(959, 652, 218, 99);
		contentPane.add(pnLegenda);
		pnLegenda.setLayout(null);
        
        addLegendaColors(pnLegenda);
        addLegendaLabels(pnLegenda);
    }

    private JLabel createLabel(String text, int x, int y, int width, int height, int fontSize) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Calibri", Font.PLAIN, fontSize));
        label.setBounds(x, y, width, height);
        return label;
    }
    
    private void addEventListeners() {
        tblCalendario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                	int row = tblCalendario.rowAtPoint(e.getPoint());
                    int column = tblCalendario.columnAtPoint(e.getPoint());
                    LocalDate date = calendarModel.getDateAt(row, column);
                    Set<Event> events = calendarModel.getEventsInDate(date);

                    if (events != null) {
                    	if(events.size() > 1) {
	                    	isSelectingEvent = false;
	                    	selectedEventView = new SelectedEventView(controller, CalendarView.this, date, events);
	                    	selectedEventView.setVisible(true);
	                    } else if(events.size() == 1) {
	                    	// se c'è un evento lo visualizzo su eventView
	                    	for(Event event : events) {
	                    		isSelectingEvent = true; 
	                    		eventView = new EventView(false, date, controller, CalendarView.this);
	                    		// passo i valori che mi serviranno per la modifica/cancellazione dell'evento.
	                    		eventView.setEventDetail(event.getName(), date, event.getDaOra(), event.getAOra(), event.getDescription(), event.getState(), event.getIdentifier());
	                    		eventView.setVisible(true);
	                    		isSelectingEvent = false;
	                    	}
	                    }   
                    } else {
                    	eventView = new EventView(true, date, controller, CalendarView.this);
                    	eventView.setVisible(true);
                    }

                }
            }
        });
    }


    private void updateMonthLabel() {
        lbMese.setText(calendarModel.getMonth(calendarModel.getMonthValue()).name().toUpperCase() + "  " + calendarModel.getYear());
    }

    private void addDayLabels() {
        String[] days = {"Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica"};
        int[] positions = {40, 205, 377, 542, 703, 866, 1037};

        for (int i = 0; i < days.length; i++) {
            JLabel label = createLabel(days[i], positions[i], 111, 105, 29, 18);
            label.setBackground(new Color(137, 168, 203));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            contentPane.add(label);
        }
    }
    
    private void addLegendaColors(JPanel pnLegenda) {
    	JPanel pnState_inCorso = new JPanel();
		pnState_inCorso.setBackground(new Color(255, 255, 51));
		pnState_inCorso.setBounds(10, 38, 25, 25);
				
		JPanel pnState_concluso = new JPanel();
		pnState_concluso.setBackground(new Color(0, 204, 0));
		pnState_concluso.setBounds(10, 66, 25, 25);
				
		JPanel pnState_daAvviare = new JPanel();
		pnState_daAvviare.setBackground(new Color(255, 51, 0));
		pnState_daAvviare.setBounds(10, 10, 25, 25);

		pnLegenda.add(pnState_daAvviare);
		pnLegenda.add(pnState_inCorso);
		pnLegenda.add(pnState_concluso);
    }
    
    private void addLegendaLabels(JPanel pnLegenda) {        
        lbNeventi_daAvviare = createLabel("0 attività da avviare", 45, 10, 168, 25, 14);
        lbNeventi_incorso = createLabel("0 attività in corso", 45, 38, 168, 25, 14);
        lbNeventi_concluso = createLabel("0 attività concluse", 45, 66, 168, 25, 14);

        pnLegenda.add(lbNeventi_daAvviare);
        pnLegenda.add(lbNeventi_incorso);
        pnLegenda.add(lbNeventi_concluso);
    }
    
    public void updateLegenda(Set<Event> events) {
        Set<String> uniqueEvents = new HashSet<>();
        eventiDaAvviare = 0;
        eventiIncorso = 0;
        eventiConclusi = 0;

        for (Event event : events) {
            String key = event.getName() + event.getDaOra() + event.getAOra() + event.getState();

            if (!uniqueEvents.contains(key)) {
                uniqueEvents.add(key);

                switch (event.getState()) {
                    case DA_AVVIARE:
                        eventiDaAvviare++;
                        break;
                    case IN_CORSO:
                        eventiIncorso++;
                        break;
                    case CONCLUSO:
                        eventiConclusi++;
                        break;
				default:
					break;
                }
            }
        }
        lbNeventi_daAvviare.setText(eventiDaAvviare + " attività da avviare");
        lbNeventi_incorso.setText(eventiIncorso + " attività in corso");
        lbNeventi_concluso.setText(eventiConclusi + " attività conclus" + (eventiConclusi == 1 ? "a" : "e"));
    }

    public LocalDate selectDate() {
        int indexRow = tblCalendario.getSelectedRow();
        int indexColumn = tblCalendario.getSelectedColumn();
        return calendarModel.getDateAt(indexRow, indexColumn);
    }

    public void updateUI(Set<Event> events) {
    	calendarModel.loadEvents(events);
        refreshUI();
    }
    
    public void updateUIRemoveEvent(LocalDate date, Set<Event> setEvent) {
    	for(Event event : setEvent) {
    		calendarModel.removeEvent(event.getDate(), event);
        	refreshUI();
    	}
    }

    private void refreshUI() {
        contentPane.revalidate();
        contentPane.repaint();
    }
    
    public boolean getSelectingEvent() {
		return isSelectingEvent;
	}
}