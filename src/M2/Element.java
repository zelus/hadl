package M2;

import java.util.ArrayList;
import java.util.Collection;

public class Element {

	private String name;
	private int level;
	private ArrayList<Constraint> constraints;
	private ArrayList<Property> properties;
	
	public Element(String name, int level) {
		this.name = name;
		this.level = level;
		constraints = new ArrayList<Constraint>();
		properties = new ArrayList<Property>();
	}
	
	public void addConstraint(Constraint constraint) {
		constraints.add(constraint);
	}
	
	public void addProperty(Property property) {
		properties.add(property);
	}
	
	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public int getLevel() {
		return level;
	}
	
	public Collection<Constraint> getConstraints() {
		return constraints;
	}
	
	public Collection<Property> getProperties() {
		return properties;
	}
}
