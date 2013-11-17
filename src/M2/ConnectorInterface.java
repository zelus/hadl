package M2;

/**
 * High-level connector interface, superclass of connector roles.
 * @author CaterpillarTeam
 */
public abstract class ConnectorInterface extends Interface implements Valuable {

	protected Connector parent;
	
	/**
	 * Create a connector interface with the given name and parent.
	 * @param name the name of the connector interface.
	 * @param parent the connector parent of the interface.
	 */
	public ConnectorInterface(String name, Connector parent) {
		super(name);
		this.parent = parent;
	}
	
	@Override
	public Connector getParent() {
		return parent;
	}

	@Override
	public void updateFrom(Valuable iface) {
		this.setValue(iface.getValue());
	}

}
