package M2;

public class ComponentInterface extends Interface {

	private Component parent;
	
	public ComponentInterface(String name, Component parent) {
		super(name);
		this.parent = parent;
	}
	
	@Override
	public Component getParent() {
		return parent;
	}

}
