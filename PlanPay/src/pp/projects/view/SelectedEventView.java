package pp.projects.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import pp.projects.controller.ConsoleController;
import pp.projects.model.Event;

public class SelectedEventView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelEvents;
	
	public SelectedEventView(ConsoleController controller, CalendarView calendar, LocalDate day, Set<Event> setEvents) {
		
		setTitle("EVENTI DI " + day.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ITALIAN).toUpperCase() + " " + day.getDayOfMonth() + " " + day.getMonth().getDisplayName(TextStyle.FULL, Locale.ITALIAN).toUpperCase() + " " + day.getYear());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 323, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());
		
		setContentPane(contentPane);
		
		panelEvents = createEventPanel(setEvents, controller, calendar, day);
		
        JScrollPane scrollPane = new JScrollPane(panelEvents);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        contentPane.add(scrollPane);
	}
	
	private JPanel createEventPanel(Set<Event> setEvents, ConsoleController controller, CalendarView calendar, LocalDate day) {
        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setLayout(new GridBagLayout());
		
        GridBagConstraints gbc = createGridBagConstraints();

        setEvents.forEach(event -> {
            JButton button = new JButton(event.getInfoEventToString());
            button.addActionListener(createButtonActionListener(event, controller, calendar, day));
            panel.add(button, gbc);
        });

        return panel;
    }
	
	private GridBagConstraints createGridBagConstraints() {
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        return gbc;
    }

    private ActionListener createButtonActionListener(Event event, ConsoleController controller, CalendarView calendar, LocalDate day) {
        return e -> {
            EventView eventView = new EventView(false, day, controller, calendar);
            eventView.setEventDetail(event.getName(), day, event.getDaOra(), event.getAOra(), event.getDescription(), event.getState(), event.getIdentifier());
            eventView.setVisible(true);
            SelectedEventView.this.setVisible(false);
        };
    }
}

