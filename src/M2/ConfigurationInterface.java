package M2;

/**
 * High-level configuration interface, superclass of configuration ports,services 
 * and roles.
 * @author CaterpillarTeam
 */
public abstract class ConfigurationInterface extends Interface implements Valuable {

	protected Configuration parent;
	
	/**
	 * Create a configuration interface with the given name and parent.
	 * @param name the name of the configuration interface.
	 * @param parent the parent handling the configuration interface.
	 */
	public ConfigurationInterface(String name, Configuration parent) {
		super(name);
		this.parent = parent;
	}

	@Override
	public Configuration getParent() {
		return this.parent;
	}
	
	@Override
	public Configuration getParentConfig() {
		return getParent();
	}
	
	@Override
	public void updateFrom(Valuable iface) {
		this.setValue(iface.getValue());
	}
}
