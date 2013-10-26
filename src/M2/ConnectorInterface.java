package M2;

public class ConnectorInterface extends Interface {

	private Connector parent;
	
	public ConnectorInterface(String name, Connector parent) {
		super(name);
		this.parent = parent;
	}
	
	@Override
	public Connector getParent() {
		return parent;
	}

}
