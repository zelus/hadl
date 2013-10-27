package M2;

/**
 * High-level configuration interface, superclass of configuration ports,services 
 * and roles.
 * @author CaterpillarTeam
 */
public class ConfigurationInterface extends Interface{

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
}
