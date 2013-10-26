package M2;

public abstract class ComponentPort extends ComponentInterface {

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
	
	public final boolean isProvPort() {
		if(parent.getProvPort(name) != null) {
			return true;
		}
		return false;
	}
	
	public final boolean isReqPort() {
		if(parent.getReqPort(name) != null) {
			return true;
		}
		return false;
	}
	
	public abstract void setValue(Object object);
	public abstract Object getValue();

}
