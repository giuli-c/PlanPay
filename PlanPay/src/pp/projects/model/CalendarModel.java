package pp.projects.model;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.table.AbstractTableModel;

public class CalendarModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
    private static final int DAYS_IN_WEEK = 7;
    
    private YearMonth yearMonth;
    private List<LocalDate> days;
    private Map<LocalDate, Set<Event>> cellData;
    
    public CalendarModel(int year, int month) {
        this.cellData = new HashMap<>();
        setYearMonth(year, month);
    }
    
    private void initializeDays() {
        this.days = new ArrayList<>();
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        int daysInMonth = yearMonth.lengthOfMonth();
        int firstDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();
        
        for (int i = 1; i < firstDayOfWeek; i++) {
            days.add(null);
        }
        
        for (int i = 1; i <= daysInMonth; i++) {
            days.add(LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), i));
        }
        
        while (days.size() % DAYS_IN_WEEK != 0) {
            days.add(null);
        }        
    }
    
    public void setYearMonth(int year, int month) {
        this.yearMonth = YearMonth.of(year, month);
        initializeDays();
        fireTableDataChanged();
    }
    
    public int getYear() {
        return this.yearMonth.getYear();
    }
    
    public int getMonthValue() {
        return this.yearMonth.getMonthValue();
    }
    
    public Month getMonth(int monthValue) {
        return Month.values()[monthValue - 1];
    }
    
    public void nextMonth() {
        if (yearMonth.getMonthValue() == 12) {
            setYearMonth(yearMonth.getYear() + 1, 1);
        } else {
            setYearMonth(yearMonth.getYear(), yearMonth.getMonthValue() + 1);
        }
    }

    public void previousMonth() {
    	if (yearMonth.getMonthValue() == 1) {
            setYearMonth(yearMonth.getYear() - 1, 12);
        } else {
            setYearMonth(yearMonth.getYear(), yearMonth.getMonthValue() - 1);
        }
    }
    
    @Override
    public int getRowCount() {
        return days.size() / DAYS_IN_WEEK;
    }

    @Override
    public int getColumnCount() {
        return DAYS_IN_WEEK;
    }
    
    private Set<Event> getEventsAt(int rowIndex, int columnIndex) {
        LocalDate date = getDateAt(rowIndex, columnIndex);
        return date == null ? Collections.emptySet() : cellData.getOrDefault(date, new TreeSet<>(new ComparatorEvents()));
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        LocalDate date = getDateAt(rowIndex, columnIndex);
        return date == null ? "" : getValueAtDate(date, getEventsAt(rowIndex, columnIndex));
    }
    
    public Object getValueAtDate(LocalDate date, Set<Event> events) {
        StringBuilder cellContent = new StringBuilder("<html>").append(date.getDayOfMonth());
        for (Event event : events) {
            String eventHtml = toHtml(event) + event.getInfoEventToString().replace("\n", " ").replace("\r", " ");
            cellContent.append("<br>").append(eventHtml);
        }
        return cellContent.append("</html>").toString();
    }
    
    public LocalDate getDateAt(int rowIndex, int columnIndex) {
        int dayIndex = rowIndex * getColumnCount() + columnIndex;
        return dayIndex >= days.size() ? null : days.get(dayIndex);
    }
    
    public String getColumName(int columnIndex) {
        String[] dayNames = {"Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica"};
        return columnIndex < dayNames.length ? dayNames[columnIndex] : "";
    }

    private int[] findDateIndices(LocalDate date) {
        int dayIndex = days.indexOf(date);
        if (dayIndex == -1) return null;
        return new int[]{dayIndex / getColumnCount(), dayIndex % getColumnCount()};
    }
    
    private void setValueCalendarEvent(LocalDate date) {
        int[] index = findDateIndices(date);
        if (index != null) fireTableCellUpdated(index[0], index[1]);
    }
    
    public void setValueAddEvent(LocalDate date, Event addEvent) {
    	// verifico se k esiste, in caso negativa creo un nuovo arraylist,
    	// in caso positivo restituisce il valore (la lista degli eventi) associato a quella chiave. 
    	// poi con .add(event) aggiungo l'evento alla lista associato a k.
    	cellData.computeIfAbsent(date, k -> new TreeSet<>(new ComparatorEvents())).add(addEvent);
    		
    	setValueCalendarEvent(date);
    }
    
    public Set<Event> getEventsInDate(LocalDate date) {
        return cellData.get(date);
    }
    
    public boolean removeEvent(LocalDate date, Event event) {
        Set<Event> events = getEventsInDate(date);
        if (events != null) {
            events.remove(event);
            if (events.isEmpty()) cellData.remove(date);
        } else {
            return false;
        }
        setValueCalendarEvent(date);
        return true;
    }
    
    public void loadEvents(Set<Event> events) {
    	cellData.clear(); // Pulisci la mappa per evitare duplicazioni
        for (Event event : events) {
            LocalDate date = event.getDate();
            setValueAddEvent(date, event);
        }
        fireTableDataChanged();
    }
    
    public String toHtml(Event event) {
        String color;
        switch (event.getState()) {
            case DA_AVVIARE: color = "red"; break;
            case IN_CORSO: color = "yellow"; break;
            case CONCLUSO: color = "green"; break;
            default: color = "transparent";
        }
        return "<span style='color:" + color + "; font-size:20px;'>&#8226;</span> ";
    }
}
