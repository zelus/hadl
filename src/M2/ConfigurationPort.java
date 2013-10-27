package M2;

public abstract class ConfigurationPort extends ConfigurationInterface {

	public ConfigurationPort(String name, Configuration parent) {
		super(name, parent);
	}
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof ConfigurationPort) {
			ConfigurationPort port = (ConfigurationPort)object;
			return name.equals(port.name);
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
