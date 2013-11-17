package M2;

/**
 * Defines a component port.
 * <p>
 * In HADL, a component port is associated to a value. This value can be accessed
 * and modified by dedicated methods.
 * It also defines runtime helpers to check if a port is a provided or required 
 * port.
 * </p>
 * @author CaterpillarTeam
 */
public abstract class ComponentPort extends ComponentInterface implements Valuable {

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
	
	@Override
	public void updateFrom(Valuable iface) {
		setValue(iface.getValue());
	}
	
	@Override
	public String toString() {
		return "component port " + this.name;
	}

}
