package M2;

/**
 * Defines a component port.
 * <p>
 * In HADL, a component port is associated to a value. This value can be accessed
 * and modified by dedicated methods.
 * It also defines runtime methods to check if a port is a provided or required 
 * port.
 * </p>
 * @author CaterpillarTeam
 */
public abstract class ComponentPort extends ComponentInterface {

	/**
	 * Create a new port for the given component.
	 * @param name the name of the port.
	 * @param parent the component the port belongs to.
	 */
	public ComponentPort(String name, Component parent) {
		super(name,parent);
	}
	
	/**
	 * @return true if the port is registered as a provided port to its component,
	 * false otherwise.
	 */
	public final boolean isProvPort() {
		if(parent.getProvPort(name) != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * @return true if the port is registered as a required port to its component,
	 * false otherwise.
	 */
	public final boolean isReqPort() {
		if(parent.getReqPort(name) != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Set the value associated to the current port.
	 * @param object the new value to set.
	 */
	public abstract void setValue(Object object);
	/**
	 * Returns the value associated to the current port.
	 * @return the value associated to the port.
	 */
	public abstract Object getValue();

}
