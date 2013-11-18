package M2;

/**
 * Invariants applied on elements.
 * <p>
 * In HADL properties are invariant constraints that are checked
 * at each runtime step. If a property is not respected
 * the runtime stop the execution.
 * </p>
 * <p>
 * Use properties when you want to add global and high-level invariants on
 * architecture description.
 * </p>
 * @author CaterpillarTeam
 */
public abstract class Property {
	
	private String name;
	private String description;
	
	/**
	 * Create a property with the given name and description.
	 * @param name the name of the property.
	 * @param description the informal description of the property.
	 */
	public Property(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	/**
	 * Create a property with the given name and an empty description.
	 * @param name the name of the property.
	 */
	public Property(String name) {
		this(name,"");
	}
	
	/**
	 * @return the name of the property.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the informal description of the property.
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * User-defined invariant checking function.
	 * @return true if the invariant is respected, false
	 * otherwise.
	 */
	public abstract boolean checkInvariant(Element element);
	
	@Override
	public String toString() {
		return(name + " : " + description);
	}
}
