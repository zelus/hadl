package M2;

import java.util.ArrayList;
import java.util.Collection;

/**
 * High-level element in HADL.
 * <p>
 * Element is the parent class of each base architectural element : component,
 * connector and configuration.
 * </p>
 * <p>
 * An element is defined by its name, its architectural level and a set of 
 * constraints and properties.
 * </p>
 * @author CaterpillarTeam
 */
public class Element {

	protected String name;
	protected int level;
	private ArrayList<Constraint> constraints;
	private ArrayList<Property> properties;
	
	/**
	 * Create an element with the given name and the parent.
	 * @param name the name of the element.
	 * @param parent the parent of the element.
	 */
	public Element(String name, Element parent) {
		this.name = name;
		if(parent == null) {
			this.level = 0;
		}
		else {
			this.level = parent.level + 1;
		}
		this.constraints = new ArrayList<Constraint>();
		this.properties = new ArrayList<Property>();
	}
	
	/**
	 * Add a constraint to the element constraints.
	 * @param constraint the constraint to add.
	 */
	public void addConstraint(Constraint constraint) {
		constraints.add(constraint);
	}
	
	/**
	 * Add a property to the element properties.
	 * @param property the property to add.
	 */
	public void addProperty(Property property) {
		properties.add(property);
	}
	
	/**
	 * Set the architectural level of the element.
	 * @param level the level.
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the name of the element.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the architectural level of the element.
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * @return the constraints associated to the element.
	 */
	public Collection<Constraint> getConstraints() {
		return constraints;
	}
	
	/**
	 * @return the properties associated to the element.
	 */
	public Collection<Property> getProperties() {
		return properties;
	}
}
