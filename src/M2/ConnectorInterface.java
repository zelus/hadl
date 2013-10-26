package M2;

public class ConnectorInterface extends Interface {

	protected Connector parent;
	
	public ConnectorInterface(String name, Connector parent) {
		super(name);
		this.parent = parent;
	}
	
	@Override
	public Connector getParent() {
		return parent;
	}

}
