package M2;


/**
 * Defines a connector role.
 * <p>
 * In HADL, a connector role is associated to a value. This value can be accessed
 * and modified by dedicated methods.
 * It also defines runtime methods to check if a role is a from or to role.
 * </p>
 * @author CaterpillarTeam
 */
public abstract class ConnectorRole extends ConnectorInterface {

	/**
	 * Create a new role with the given name and parent.
	 * @param name the name of the role.
	 * @param parent the connector parent handling the role.
	 */
	public ConnectorRole(String name, Connector parent) {
		super(name,parent);
	}
	
	/**
	 * @return true if the role is registered as a from role to its connector,
	 * false otherwise.
	 */
	public final boolean isFromRole() {
		if(parent.getFromRole(name) != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * @return true if the role is registered as a to role to its connector,
	 * false otherwise.
	 */
	public final boolean isToRole() {
		if(parent.getToRole(name) != null) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "connector role " + this.name;
	}
}
