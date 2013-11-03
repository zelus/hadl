package M2;

/**
 * High-level component interface, superclass of component ports and services.
 * @author CaterpillarTeam
 */
public class ComponentInterface extends Interface {

	protected Component parent;
	
	/**
	 * Create a component interface with the given name and parent.
	 * @param name the name of the component interface.
	 * @param parent the component parent of the interface.
	 */
	public ComponentInterface(String name, Component parent) {
		super(name);
		this.parent = parent;
	}
	
	@Override
	public Component getParent() {
		return parent;
	}
}
