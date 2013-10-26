package M2;

public abstract class ComponentPort extends ComponentInterface {

	/**
	 * Create a new port for the given component.
	 * @param name the name of the port.
	 * @param parent the component the port belongs to.
	 */
	public ComponentPort(String name, Component parent) {
		super(name,parent);
	}
	
	@Override
	public boolean equals(Object object) {
		System.out.println("pouet");
		if(object instanceof ComponentPort) {
			ComponentPort port = (ComponentPort)object;
			return name.equals(name.equals(port.getName()));
		}
		return false;
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
	
	public abstract void setValue(Object object);
	public abstract Object getValue();

}
