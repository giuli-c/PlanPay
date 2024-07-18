package pp.projects.model;

import java.util.Comparator;

public class ComparatorEvents implements Comparator<Event>{

	@Override
	public int compare(Event o1, Event o2) {		
		int dateComparison = o1.getDate().compareTo(o2.getDate());
        if (dateComparison != 0) {
            return dateComparison;
        }
        // se le date sono uguali gli eventi vengono confrontati per orario
        int startTimeComparison = o1.getDaOra().compareTo(o2.getDaOra());
        if (startTimeComparison != 0) {
            return startTimeComparison;
        }
        // se sia le date che gli orari di inizio sono uguali, gli eventi vengono confrontati per nome.
        return o1.getName().compareTo(o2.getName());
	}
}
