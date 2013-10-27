package M2;

/**
 * Invariants applied on elements.
 * <p>
 * In HADL properties are invariant constraints that are checked
 * at each runtime step. If a property is not respected during execution
 * then an exception is thrown.
 * </p>
 * <p>
 * Use properties when you want to add global and high-level invariants on
 * architecture description.
 * </p>
 * @author CaterpillarTeam
 */
public class Property {
	
	private String name;
	private String description;
	
	/**
	 * Create a property with the given name and description.
	 * @param name the name of the property.
	 * @param description the informal description of the property.
	 */
	public Property(String name, String description) {
		/*
		 * TODO change property behavior to allow runtime checking
		 */
		this.name = name;
		this.description = description;
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
}
