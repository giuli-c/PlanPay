package pp.projects.model;

import java.time.LocalDate;
import java.util.Objects;

public class EventImpl implements Event{
	
	private String name;
    private String description;
    private LocalDate daDate;
	private State eventState;
	private String daOra;
	private String aOra;
	private String identifier;
	
	public EventImpl(String name, String desc, LocalDate daData, String daOra, String aOra, State stato, String identifier) {
		this.name = name;
		this.description = desc;
		this.daDate = daData;
		this.daOra = daOra;
		this.aOra = aOra;
		this.eventState = stato;
		this.identifier = identifier;
	}
	
	@Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public LocalDate getDate() {
        return daDate;
    }
	
	@Override
	public State getState() {
		return this.eventState;
	}

	@Override
	public void setState(State s) {
		this.eventState = s;
	}
	
	@Override
	public String getInfoEventToString() {		
		return this.getDaOra() + " - " + this.getAOra() + " : '" + this.getName() + "'";
	}
	
	@Override
	public String getDaOra() {
		return this.daOra;
	}
	
	@Override
	public String getAOra() {
		return this.aOra;
	}
	
	@Override
	public String getInfoEventToFile() {
		return this.getDate() + "[,]" + this.getDaOra() + "[,]" + this.getAOra() + "[,]" + this.getName() + "[,]" + this.getDescription() + "[,]" + this.getState().toString() + "[,]" + this.getIdentifier();
	}
	
	@Override
	public String getIdentifier() {
        return identifier;
    }
	
	@Override
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
	
	@Override
	public void setDaOra(String da) {
		this.daOra = da;
	}
	
	@Override
	public void setAOra(String a) {
		this.aOra = a;
	}
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Event event = (Event) o;
	    return Objects.equals(identifier, event.getIdentifier()) &&
	           eventState == event.getState();
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(name, description, daDate, daOra, aOra, eventState);
	}
	
}
