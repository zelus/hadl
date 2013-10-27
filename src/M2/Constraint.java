package M2;

/**
 * Non-functional constraint applied to elements.
 * <p>
 * In HADL constraints are not checked at runtime. It is just a helper
 * for architecture designer to show non-functional constraints.
 * </p>
 * <p>
 * Use constraints when you want to add extra-information purely indicative
 * on architecture description.
 * </p>
 * @author CaterpillarTeam
 */
public class Constraint {

	private String name;
	private String description;
	
	/**
	 * Create a new constraint with the given name and description.
	 * @param name the name of the constraint.
	 * @param description the informal description of the constraint.
	 */
	public Constraint(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	/**
	 * @return the name of the constraint.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the informal description of the constraint.
	 */
	public String getDescription() {
		return description;
	}

}
