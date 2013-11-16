package M2;

/**
 * High-level element interface, superclass of either ports, services and roles.
 * <p>
 * In HADL, all the interfaces must be named, this is needed by runtime methods
 * to retrieve a given interface from an element (configuration, component or
 * connector).
 * </p>
 * @author CaterpillarTeam
 */
public abstract class Interface {
	
	protected String name;
	
	/**
	 * Create an interface with the given name.
	 * @param name the name of the interface.
	 */
	public Interface(String name) {
		this.name = name;
	}
	
	/**
	 * @return the name of the interface.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the parent element of the interface.
	 */
	public abstract Element getParent();
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof Interface) {
			Interface i = (Interface)object;
			return(name.equals(i.name) && this.getParent().getLevel() == i.getParent().getLevel());
		}
		return false;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

}
