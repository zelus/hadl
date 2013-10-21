package M2;

public class ConfigurationInterface extends Interface{

	private Configuration parent;
	
	public ConfigurationInterface(String name, Configuration parent) {
		super(name);
		this.parent = parent;
	}

	@Override
	public Configuration getParent() {
		return this.parent;
	}
}
