package M2;

public abstract class ComponentPort extends ComponentInterface {

	public ComponentPort(String name, Component parent) {
		super(name,parent);
	}
	
	public abstract void setValue(Object object);
	public abstract Object getValue();

}
